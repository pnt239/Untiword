/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.controller;

import Model.UserDQ;
import com.alee.laf.optionpane.WebOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import untiword.components.UWEditor;
import untiword.components.docx.DocxDocument;
import untiword.events.CreateDocumentListener;
import untiword.events.ListDocumentEvent;
import untiword.events.ListDocumentListener;
import untiword.events.NotFoundDocumentEvent;
import untiword.events.NotFoundDocumentListener;
import untiword.events.RenameDocumentEvent;
import untiword.events.RenameDocumentListener;
import untiword.model.ServerRequestDQ;

/**
 *
 * @author NThanh
 */
public class ClientController {

    private HashMap<Integer, UWEditor> docIDtoDocPanel;
    private UserDQ user;
    private Socket serverSocket;
    private PrintWriter out;
    private BufferedReader in;
    private MessageHandlingThread MHT;
    private int ignoreNext = 0;

    private ArrayList<RenameDocumentListener> renameListenerList = new ArrayList<RenameDocumentListener>();
    private ArrayList<NotFoundDocumentListener> notFoundDocListenerList = new ArrayList<NotFoundDocumentListener>();
    private ArrayList<ListDocumentListener> listDocListenerList = new ArrayList<ListDocumentListener>();
    private ArrayList<String> documentIdAndNames = new ArrayList<String>();
    
    private CreateDocumentListener docListener;

    public ClientController() {
        docIDtoDocPanel = new HashMap<Integer, UWEditor>();
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
        } catch (NumberFormatException e) {
            WebOptionPane
                    .showMessageDialog(null,
                            "Connection failed. Please double check your server address and port number.");
        } catch (UnknownHostException e) {
            WebOptionPane
                    .showMessageDialog(null,
                            "Connection failed. Please double check your server address and port number.");
        } catch (IOException e) {
            WebOptionPane
                    .showMessageDialog(null,
                            "Connection failed. Please double check your server address and port number.");
        }

        out = new PrintWriter(getServerSocket().getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(getServerSocket()
                .getInputStream()));

        handleServerGreeting();

        MHT = new MessageHandlingThread(this, in);
        MHT.start();
        
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

            if (reqType.equals("ERROR")) {
                WebOptionPane.showMessageDialog(null, "SsplitString[2]", "Error", WebOptionPane.ERROR_MESSAGE);
                //JOptionPane.showMessageDialog(tabbedPane, splitString[2]);                
            } else if (reqType.equals("DOCRENAMED")) {
                String[] broken = splitString[2].split("~");
                UWEditor docToRename = docIDtoDocPanel.get(Integer
                        .parseInt(broken[0]));
                processRenameEvent(new RenameDocumentEvent(docToRename, broken[1]));
            } else if (reqType.equals("DOCLIST")) {

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
                }

            } else if (reqType.equals("REQNEWPROCESSED")) {
                // create a new doc with the new name pass to method close +,
                // reopen
                // update userDQ with new doc, send a load message
                String[] splitDoc = splitString[2].split("~");
                getUser().addDocument(Integer.parseInt(splitDoc[0]));

                UWEditor newDocWindow = new UWEditor(Integer.parseInt(splitDoc[0]), splitDoc[1], this);
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

            }

        } else {
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
            this.ignoreNext = 2;

            int docId = Integer.parseInt(docID);
            UWEditor panelToUpdate = docIDtoDocPanel.get(docId);
            DocxDocument document = (DocxDocument)panelToUpdate.getTextPane().getDocument();
            JTextPane tempPane = panelToUpdate.getTextPane();

            ServerRequestDQ newSelection = null;
            
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
            String content = user.getView(docId).replace("~", "\n");
            if (content.equals("")) {
                ignoreNext = 0;
            }
            try {
                //panelToUpdate.getTextPane().setText(content);
                document.insertString(newSelection.getBeginning(), content, null);
            } catch (BadLocationException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (newSelection.getAction().equals("INSERT")) {
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
        if (messageType.equals("requestNew")) {
            return prefix + "|REQUESTNEW|" + docName;
        } else if (messageType.equals("getdoclist")) {
            return prefix + "|GETDOCLIST";
        } else if (messageType.equals("close")) {
            return prefix + "|CLOSE|" + docID;
        } else if (messageType.equals("rename")) {
            return prefix + "|RENAME|" + docID + "~" + docName;
        } else if (messageType.equals("load")) {
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
}
