/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.controller;

import untiword.events.AuthorizationListener;
import com.alee.laf.optionpane.WebOptionPane;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import libraryclasses.CustomRequest;
import untiword.components.UWEditor;
import untiword.components.docx.DocxDocument;
import untiword.events.ConnectionResultListener;
import untiword.events.CreateDocumentListener;
import untiword.events.ListDocumentEvent;
import untiword.events.ListDocumentListener;
import untiword.events.NotFoundDocumentEvent;
import untiword.events.NotFoundDocumentListener;
import untiword.events.RenameDocumentEvent;
import untiword.events.RenameDocumentListener;
import untiword.events.ReturnDataListener;
import untiword.events.ReveiceNotifyListener;
import untiword.model.ServerRequestDQ;
import untiword.model.UserDQ;

/**
 *
 * @author NThanh
 */
public class ClientController {
    
    private AuthorizationListener _authorizationListener;
    public void setAuthorizationListener(AuthorizationListener listener)
    {
        _authorizationListener = listener;
    }
    
    private ConnectionResultListener _connectionResultListener;
    public void setConnectionResultListener(ConnectionResultListener listener)
    {
        _connectionResultListener = listener;
    }
    
    private ReturnDataListener _returnDataListener;
    public void setReturnDataListener(ReturnDataListener listener)
    {
        _returnDataListener = listener;
    }
    
    private ReveiceNotifyListener _reveiceNotifyListener;
    public void setReveiceNotifyListener(ReveiceNotifyListener listener)
    {
        _reveiceNotifyListener = listener;
    }

    private HashMap<Integer, UWEditor> docIDtoDocPanel;
    private UserDQ user;
    private Socket serverSocket;
    private PrintWriter out;
    private BufferedReader in;
    private MessageHandlingThread MHT;
    private int ignoreNext = 0;

    private ArrayList<RenameDocumentListener> renameListenerList = new ArrayList<>();
    private ArrayList<NotFoundDocumentListener> notFoundDocListenerList = new ArrayList<>();
    private ArrayList<ListDocumentListener> listDocListenerList = new ArrayList<>();
    private ArrayList<String> documentIdAndNames = new ArrayList<>();

    private CreateDocumentListener docListener;

    public ClientController() {
        docIDtoDocPanel = new HashMap<>();
    }

    public synchronized void addRenameDocumentListener(RenameDocumentListener listener) {
        if (!renameListenerList.contains(listener)) {
            renameListenerList.add(listener);
        }
    }

    public synchronized void addNotFoundListener(NotFoundDocumentListener listener) {
        if (!notFoundDocListenerList.contains(listener)) {
            notFoundDocListenerList.add(listener);
        }
    }

    public synchronized void addListDocumentListener(ListDocumentListener listener) {
        if (!listDocListenerList.contains(listener)) {
            listDocListenerList.add(listener);
        }
    }

    public void setCreateDocumentListener(CreateDocumentListener listener) {
        docListener = listener;
    }

    public void connectToServer(String server, String port) throws UnknownHostException, IOException {
        try {
            setServerSocket(new Socket(server, Integer.parseInt(port)));
            out = new PrintWriter(getServerSocket().getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(getServerSocket()
                    .getInputStream()));

            handleServerGreeting();

            MHT = new MessageHandlingThread(this, in);
            MHT.start();
        } catch (NumberFormatException e) {
            WebOptionPane.showMessageDialog(null, "Connection failed. Please double check your server address and port number.");
            if(_connectionResultListener != null)
            {
                _connectionResultListener.connectFailed();
            }
        } catch (UnknownHostException e) {
            WebOptionPane.showMessageDialog(null, "Connection failed. Please double check your server address and port number.");
            if(_connectionResultListener != null)
            {
                _connectionResultListener.connectFailed();
            }
        } catch (IOException e) {
            WebOptionPane.showMessageDialog(null, "Connection failed. Please double check your server address and port number.");
            if(_connectionResultListener != null)
            {
                _connectionResultListener.connectFailed();
            }
        }
    }
    
    public void getDocList() {
        this.sendMessage(this.createControlMessage("getdoclist", -1, ""));
    }

    /**
     * parses server messages. If NOT a control message, passes the message to
     * the user and updates the TextPane. If a control message, applies the
     * update to the Editor.
     *
     * @param line message to be parsed
     */
    public void handleLine(String line) {
        if (line.startsWith("CONTROL")) {
            String[] splitString = line.split("\\|");
            String reqType = splitString[1];

            switch (reqType) {
                case "ERROR":
                    WebOptionPane.showMessageDialog(null, "SsplitString[2]", "Error", WebOptionPane.ERROR_MESSAGE);
                    //JOptionPane.showMessageDialog(tabbedPane, splitString[2]);                
                    break;
                case "DOCRENAMED":
                    String[] broken = splitString[2].split("~");
                    UWEditor docToRename = docIDtoDocPanel.get(Integer
                            .parseInt(broken[0]));
                    processRenameEvent(new RenameDocumentEvent(docToRename, broken[1]));
                    break;
                case "DOCLIST":
                    // Send new info to new doc panel, send as list loading
                    // label into list
                    if (splitString.length < 3) {
                        //Show second without list..... Quang
                        processNotFoundEvent(new NotFoundDocumentEvent());
                    } else {
                        documentIdAndNames.removeAll(documentIdAndNames);
                        String[] docNames = new String[splitString.length - 2];
                        for (int i = 2; i < splitString.length; i++) {
                            documentIdAndNames.add(splitString[i]);
                            docNames[i - 2] = splitString[i].split("~")[1];
                        }
                        //Show second with list
                        processListDocEvent(new ListDocumentEvent(docNames));
                    }   break;
                case "REQNEWPROCESSED":
                    // create a new doc with the new name pass to method close +,
                    // reopen
                    // update userDQ with new doc, send a load message
                    String[] splitDoc = splitString[2].split("~");
                    UWEditor newDocWindow = new UWEditor(Integer.parseInt(splitDoc[0]), splitDoc[1], this);
                    getUser().addDocument(Integer.parseInt(splitDoc[0]), (DocxDocument)newDocWindow.getTextPane().getDocument());
                    newDocWindow.setVisible(true);
                    docListener.getUWPanel(newDocWindow);
                    //                DocPanel newDocWindow = new DocPanel(
//                        Integer.parseInt(splitDoc[0]), splitDoc[1], this);
                    this.docIDtoDocPanel.put(newDocWindow.getNum(), newDocWindow);
                    //                tabbedPane.setComponentAt(tabbedPane.getTabCount() - 1,
//                        newDocWindow);
//                tabbedPane.setTitleAt(tabbedPane.getTabCount() - 1,
//                        newDocWindow.getName());
//                tabbedPane.add("Open/Create", new NewDocPanel(this));
//                initTabComponent(tabbedPane.getTabCount() - 2);
//                tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 2);
                    this.sendMessage(createControlMessage("load",
                        Integer.parseInt(splitDoc[0]), splitDoc[1]));
                    break;
            }

        } 
        else if(line.startsWith("CUSTOM_REQUEST/AUTHORIZE"))
        {
            CustomRequest request = new CustomRequest(line);
            if("success".equals(request.getValue("result")))
            {
                if(_authorizationListener != null)
                {
                    _authorizationListener.authorizeSucess();
                }              
            }
            else
            {
                if(_authorizationListener != null)
                {
                    _authorizationListener.authorizeFailed();
                }               
            }
        }
        else if(line.startsWith("CUSTOM_REQUEST/RETURN"))
        {
            CustomRequest request = new CustomRequest(line);
            if(request.getValue("type").equals("loginUsers"))
            {
                if(_returnDataListener != null)
                {
                    _returnDataListener.loginUsersReturned(request.getValue("value"));
                }
            }
        }
        else if(line.startsWith("CUSTOM_REQUEST/NOTIFY"))
        {
            if(_reveiceNotifyListener != null)
            {
                CustomRequest request = new CustomRequest(line);
                _reveiceNotifyListener.notifyReveiced(request.getValue("message"));
            }
        }
        else {
            synchronized (this) {
                this.ignoreNext = 1;
            }
            this.getUser().pushRequest(line);
            String[] splitString = line.split("\\|");

            this.updateView(splitString[2]);
        }
    }

    /**
     * updates the textPane of the panel corresponding to the give docID to add
     * the new updates from the server.
     *
     * @param docID docID of the document to be updated.
     */
    public void updateView(String docID) {
        synchronized (this) {
            //this.ignoreNext = 1;

            int docId = Integer.parseInt(docID);
            UWEditor panelToUpdate = docIDtoDocPanel.get(docId);
            DocxDocument document = (DocxDocument) panelToUpdate.getTextPane().getDocument();
            JTextPane tempPane = panelToUpdate.getTextPane();

            ServerRequestDQ newSelection;

            // insert message
            if (tempPane.getSelectionStart() == tempPane.getSelectionEnd()) {
                String req = ("dummyUser" + "|" + 24 + "|" + docId + "|"
                        + "INSERT" + "|" + tempPane.getCaretPosition() + "|"
                        + 0 + "|" + "filleer" + "|" + "0");
                newSelection = new ServerRequestDQ(user.updateSelection(req));
            } // delete message
            else {
                String req = ("dummyUser" + "|" + 24 + "|" + docId + "|"
                        + "DELETE" + "|" + tempPane.getSelectionStart() + "|"
                        + tempPane.getSelectionEnd() + "|" + "filler" + "|" + "0");
                newSelection = new ServerRequestDQ(user.updateSelection(req));
            }

            DocxDocument doc = user.getView(docId);
            //String content = user.getView(docId).replace("~", "\n");
            String content = "";
            try {
                content = doc.getText(0, 1);
            } catch (BadLocationException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (content.equals("")) {
                ignoreNext = 0;
            }

            //panelToUpdate.getTextPane().setText(content);
            //panelToUpdate.getTextPane().setDocument(doc);

            if (newSelection.getAction().equals("INSERT")) {
                ignoreNext = 0;
                //tempPane.setCaretPosition(newSelection.getBeginning());
            } else {
                //tempPane.setSelectionStart(newSelection.getBeginning());
                //tempPane.setSelectionEnd(newSelection.getEnd());
            }
        }
    }

    /**
     * Create a new message from the given parameters that can be understood by
     * the server.
     *
     * @param messageType The type of request to be sent
     * @param docID docID of the doc to be edited.
     * @param docName document name of the doc to be changed or added
     * @return String with correctly formatted server message
     */
    public String createControlMessage(String messageType, int docID,
            String docName) {
        String prefix = "CONTROL|" + getUser().getUserID();
        if (docName.equals("")) {
            docName = "document";
        }
        switch (messageType) {
            case "requestNew":
                return prefix + "|REQUESTNEW|" + docName;
            case "getdoclist":
                return prefix + "|GETDOCLIST";
            case "close":
                return prefix + "|CLOSE|" + docID;
            case "rename":
                return prefix + "|RENAME|" + docID + "~" + docName;
            case "load":
                return prefix + "|LOAD|" + docID;
        }
        return null;
    }

    /**
     * sends a message to the server.
     *
     * @param message message to be sent to the server.
     */
    public void sendMessage(String message) {
        out.println(message);
    }

    /**
     * reads the first message from the server, which assigns a userID. shows an
     * error if the server doesn't respond properly
     *
     * @throws IOException
     */
    private void handleServerGreeting() throws IOException {
        String helloMessage = in.readLine();
        String[] elements = helloMessage.split("\\|");
        if (!elements[0].equals("HELLO")) {
            JOptionPane
                    .showMessageDialog(null,
                            "Sorry, the server has encountered unexpected error. Please try again.");
            System.exit(0);
        } else {
            setUser(new UserDQ(elements[1]));
        }
    }

    private void processRenameEvent(RenameDocumentEvent renameEvent) {
        ArrayList<RenameDocumentListener> tempRenameListenerList;

        synchronized (this) {
            if (renameListenerList.isEmpty()) {
                return;
            }

            tempRenameListenerList = (ArrayList<RenameDocumentListener>) renameListenerList.clone();
        }

        tempRenameListenerList.stream().forEach((listener) -> {
            listener.RenameDocument(renameEvent);
        });
    }

    private void processNotFoundEvent(NotFoundDocumentEvent notFoundEvent) {
        ArrayList<NotFoundDocumentListener> tempNotFoundDocListenerList;

        synchronized (this) {
            if (notFoundDocListenerList.isEmpty()) {
                return;
            }

            tempNotFoundDocListenerList = (ArrayList<NotFoundDocumentListener>) notFoundDocListenerList.clone();
        }

        notFoundDocListenerList.stream().forEach((listener) -> {
            listener.NotFoundDocument(notFoundEvent);
        });
    }

    private void processListDocEvent(ListDocumentEvent listDocEvent) {
        ArrayList<ListDocumentListener> tempListDocListenerList;

        synchronized (this) {
            if (listDocListenerList.isEmpty()) {
                return;
            }

            tempListDocListenerList = (ArrayList<ListDocumentListener>) listDocListenerList.clone();
        }

        listDocListenerList.stream().forEach((listener) -> {
            listener.ListDocument(listDocEvent);
        });
    }

    /**
     * returns the user object associated with the GUI.
     *
     * @return user object associated with GUI
     */
    public UserDQ getUser() {
        return user;
    }

    /**
     * sets user object associated with GUI
     *
     * @param user user object to associate with GUI
     */
    public void setUser(UserDQ user) {
        this.user = user;
    }

    /**
     * gets the socket associated with the GUI
     *
     * @return socket associated with the GUI
     */
    public Socket getServerSocket() {
        return serverSocket;
    }

    /**
     * sets the serverSocket associated with the GUI
     *
     * @param serverSocket associated with GUI
     */
    public void setServerSocket(Socket serverSocket) {
        this.serverSocket = serverSocket;
    }

    /**
     * Getter for the ignoreNext variable.
     *
     * @return ignoreNext
     */
    public int getIgnoreNext() {
        return ignoreNext;
    }

    /**
     * Setter for the ignoreNext variable
     *
     * @param ignoreNext
     */
    public void setIgnoreNext(int ignoreNext) {
        this.ignoreNext = ignoreNext;
    }

    /**
     * Getter for the hash map from docID to the panel that holds the document
     *
     * @return docIDtoDocPanel
     */
    public HashMap<Integer, UWEditor> getDocIDtoDocPanel() {
        return docIDtoDocPanel;
    }

    /**
     * Setter for the above mentioned hashmap
     *
     * @param docIDtoDocPanel
     */
    public void setDocIDtoDocPanel(HashMap<Integer, UWEditor> docIDtoDocPanel) {
        this.docIDtoDocPanel = docIDtoDocPanel;
    }

    public List<String> getDocumentIdAndNames() {
        return documentIdAndNames;
    }

    public void authorize(String accessToken) 
    {
        if(getUser() != null)
        {
            CustomRequest request = new CustomRequest(3);
            request.setAction("AUTHORIZE");
            request.setValue("applicationId", getUser().getUserID());
            request.setValue("loginType", "Facebook");
            request.setValue("accessToken", accessToken);
            sendMessage(request.toString());
        }     
    }
    
    public void logout(String accessToken)
    {
        if(getUser() != null)
        {
            CustomRequest request = new CustomRequest(3);
            request.setAction("LOGOUT");
            request.setValue("applicationId", getUser().getUserID());
            request.setValue("accessToken", accessToken);
            sendMessage(request.toString());
        }     
    }
    
    public void requestLoginUsers()
    {
        if(getUser() != null)
        {
            CustomRequest request = new CustomRequest(2);
            request.setAction("GET");
            request.setValue("applicationId", getUser().getUserID());
            request.setValue("type", "loginUsers");
            sendMessage(request.toString());
        }
    }
    
    public void requestShareDocument(int documentId, String[] applicationId)
    {
        if(documentId >= 0
                && applicationId != null
                && getUser() != null)
        {
            CustomRequest request = new CustomRequest(3);
            request.setAction("INVITE");
            request.setValue("applicationId", getUser().getUserID());
            request.setValue("documentId", String.valueOf(documentId));
            
            String clientList = "";
            for(int i=0; i<applicationId.length - 1; i++)
            {
                clientList += applicationId[i] + "|";
            }
            clientList += applicationId[applicationId.length - 1];
            
            request.setValue("clientList", clientList);       
            sendMessage(request.toString());
        }
    }
}
