package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;


import Model.ServerRequestDQ;
import Server.BusinessLogicLayer.AccountBlo;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import libraryclasses.CustomRequest;
import org.xml.sax.SAXException;
import untiword.gui.server.UWServerGui;

/**
 * The SEditServer class is the main entry point of the server side of the
 * application.
 * 
 * It sets up the server, listens for incoming client connections, and manages
 * the client-document communication
 * 
 * Thread safety argument for the server: To prevent race conditions and other
 * concurrency-related bugs, we decided to implement an overall lock on the
 * SEditServer object which might limit concurrency to an extent but this should
 * not produce visible delays considering the number of users we're expecting to
 * have connected to the server at the same time for this project.
 * 
 * All methods in SEditServer are synchronized with the exception of serve(),
 * which only locks on the server when it's performing an action, not when it's
 * waiting for a new user.
 * 
 * Since we're intentionally exposing the rep to SEditDocument, we also need to
 * make sure that all access from there is thread-safe. This is satisfied by
 * design since none of the client threads access any of the SEditDocuments
 * directly but they need to pass their messages through synchronized methods on
 * the SEditServer -> all SEditDocument methods must therefore run on the main 
 * server thread.
 * 
 * Methods in the SEditThread class can get either get called from a SEditThread thread 
 * or from the server thread. The data in SEditThread is definitely thread safe 
 * since all the access (mainly to socket/the out object, to which the server is printing) 
 * is read only and all the fields are declared final.
 * 
 * @author Michael Turek
 */
public class SEditServer {
    private final ServerSocket serverSocket;
    protected final Map<String, SEditThread> users;
    private int userIDCounter;
    private final Map<Integer, SEditDocument> documents;
    private int documentIDCounter;
    // a flag signifying whether shutdown has been requested
    private boolean die;
    private UWServerGui serverGui;
    
        /**
     * Creates a new SEditServer object listening for connections on the
     * specified port
     * 
     * @param port
     *            to listen on
     * @throws IOException
     *             if socket cannot be opened properly
     */
    
    public SEditServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);

        this.users = new HashMap<>();
        this.userIDCounter = 0;

        this.documents = new HashMap<>();
        this.documentIDCounter = 0;
        serverGui = new UWServerGui();
        
        serverGui.setLocationRelativeTo(null);
        serverGui.setVisible(true);
        serverGui.setTitle("Server Log!"); 
        
        System.setOut(new PrintStream(System.out) {
        @Override
        public void println(String s) {
          serverGui.getText().append(s + "\n");
            }
            // override some other methods?
          });
        this.die = false;
        
        System.out.println("Server created on port " + port);        
    }
    
    /**
     * Reacts appropriately to an incoming request: 
     * Incoming CONTROL requests: 
     * - CONTROL|<userID>|<requestType>|<message dependent tail> 
     * --- GETDOCLIST, tail: empty 
     * --- REQUESTNEW, tail: suggested document name 
     * --- RENAME, tail: documentID~suggested name 
     * --- LOAD, tail: documentID 
     * --- CLOSE, tail: documentID 
     * 
     * Outgoing CONTROL messages: 
     * - CONTROL|<messageType>|<message dependent tail> 
     * --- DOCLIST, tail: (|documentID~documentName)* 
     * --- REQNEWPROCESSED, tail: |documentID~documentName (behavior specified in the design document)
     * 
     * RequestServerDQ requests are passed on to their respective target documents
     * 
     * @param input
     *            - request to be processed
     */
    public synchronized void handleRequest(String input) {
        if (input.startsWith("CONTROL")) 
        {
            String[] elements = input.split("\\|");

            String userID = elements[1];
            String request = elements[2];

            switch (request) {
                case "GETDOCLIST":
                    {              
                        String message = getDocListMessage(userID);
                        users.get(userID).sendMessage(message);
                        break;
                    }
                case "REQUESTNEW":
                    {
                try {
                    String suggestedName = elements[3];
                    int newDocumentID = documentIDCounter;
                    documentIDCounter++;
                    // Make sure there are no duplicate names
                    for (SEditDocument document : documents.values()) {
                        if (document.getDocumentName().equals(suggestedName)) {
                            suggestedName += ("-" + newDocumentID);
                        }
                    }       SEditDocument newDocument = new SEditDocument(newDocumentID,
                            suggestedName);
                    documents.put(newDocumentID, newDocument);
                    String message = "CONTROL|REQNEWPROCESSED";
                    message += ("|" + newDocument.getDocumentID() + "~" + newDocument
                            .getDocumentName());
                    // Send REQNEWPROCESSES to the user that requested it
                    users.get(userID).sendMessage(message);
                    // and distribute DOCLIST to everyone
                    distributeMessage(getDocListMessage(userID));
                    
                    AccountBlo.getInstance().createDocument(newDocumentID, userID);
                    // The user still needs to load the document and is NOT
                    // subscribed automatically
                    break;
                } catch (ParserConfigurationException | SAXException | IOException | TransformerException | XPathExpressionException ex) {
                    Logger.getLogger(SEditServer.class.getName()).log(Level.SEVERE, null, ex);
                }
                    }
                case "LOAD":
                {
                    int documentID = Integer.valueOf(elements[3]);
                        documents.get(documentID).subscribeUser(users.get(userID));
                        break;
                }
                case "CLOSE":
                    {
                        int documentID = Integer.valueOf(elements[3]);
                        documents.get(documentID).unsubscribeUser(users.get(userID));
                        break;
                    }
                case "RENAME":
                {
                    String[] content = elements[3].split("~");
                    int documentID = Integer.valueOf(content[0]);
                    String suggestedName = content[1];
                    // Make sure there are no duplicate names
                    for (SEditDocument document : documents.values()) {
                        if (document.getDocumentName().equals(suggestedName)) {
                            users.get(userID).sendMessage(
                                    "CONTROL|ERROR|Name already in use");
                        return;
                    }
                    }       documents.get(documentID).rename(suggestedName);
                    // also distribute DOCLIST to everyone
                distributeMessage(getDocListMessage(userID));
                        break;
                    }
            // Ignore invalid CONTROL messages
                default:
                    break;
            }

        }
        else if(input.startsWith("CUSTOM_REQUEST/GET"))
        {
            CustomRequest request = new CustomRequest(input);
            if(request.getValue("type").equals("sharedDocuments"))
            {
                CustomRequest newRequest = new CustomRequest(2);
                newRequest.setAction("RETURN");                           
                newRequest.setValue("type", "sharedDocuments");
                
                String documentList = "";
                try {
                    int[] sharedDocList = AccountBlo.getInstance().getSharedDocumentList(request.getValue("applicationId"));
                    for (SEditDocument document : documents.values()) 
                    {
                        for(int i=0; i<sharedDocList.length; i++)
                        {
                            if(sharedDocList[i] == document.getDocumentID())
                            {
                                documentList += document.getDocumentID() + "|" + document.getDocumentName() + "|";
                                break;
                            }
                        }                     
                    }
                    
                } catch (ParserConfigurationException | SAXException | IOException | TransformerException | XPathExpressionException ex) {
                    Logger.getLogger(SEditServer.class.getName()).log(Level.SEVERE, null, ex);
                }            
                
                newRequest.setValue("value", documentList);             
                users.get(request.getValue("applicationId")).sendMessage(newRequest.toString());
            }
        }
        else if(input.startsWith("CUSTOM_REQUEST/INVITE"))
        {
            CustomRequest request = new CustomRequest(input);
            int documentId = Integer.parseInt(request.getValue("documentId"));
            String parts[] = request.getValue("clientList").split("[|]");
            for (String part : parts) {
                try 
                {
                    CustomRequest newRequest = new CustomRequest(1);
                    newRequest.setAction("NOTIFY");
                    newRequest.setValue("message", "You are shared document"
                            + String.valueOf(documentId) + "by " + request.getValue("applicationId") + "!");
                    users.get(part).sendMessage(newRequest.toString());
                    AccountBlo.getInstance().insertSharedDocument(documentId, part);
                }
                catch (ParserConfigurationException | SAXException | IOException | TransformerException | XPathExpressionException ex) {
                    Logger.getLogger(SEditServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else 
        {
            // Forward ServerRequestDQ messages to the document
            // they're related to

            ServerRequestDQ request = new ServerRequestDQ(input);
            if(!request.isCustomRequest())
            {
                int targetDocumentID = request.getDocumentID();
                documents.get(targetDocumentID).processRequest(request.toString());
            }          
            else
            {
                //Process custom request here
            }
        }
    }

    /**
     * Sends a message to all the users connected to the server
     * 
     * @param message
     *            to be sent to the users
     */
    private synchronized void distributeMessage(String message) {
        System.out.println("Server distributing message to connected clients: "
                + message);


        users.values().stream().forEach((user) -> {
            user.sendMessage(message);
        });
    }
    


    /**
     * Generates a DOCLIST message to be sent to the users
     * 
     * @return the DOCLIST message
     */
    private synchronized String getDocListMessage(String userId) {
        String message = "CONTROL|DOCLIST";
             
        message = documents.values().stream().map((document) -> ("|" + document.getDocumentID() + "~" + document
                .getDocumentName())).reduce(message, String::concat);
        
//        try 
//        {
//            int[] sharedDocList = AccountBlo.getInstance().getSharedDocumentList(userId);
//            if(sharedDocList != null)
//            {
//                for (SEditDocument document : documents.values()) 
//                {
//                    for(int i=0; i<sharedDocList.length; i++)
//                    {
//                        if(sharedDocList[i] == document.getDocumentID())
//                        {
//                            message += "|" + document.getDocumentID() + "~" + document.getDocumentName();
//                            break;
//                        }
//                    }                     
//                }
//            }         
//        } catch (ParserConfigurationException | SAXException | IOException | TransformerException | XPathExpressionException ex) {
//            Logger.getLogger(SEditServer.class.getName()).log(Level.SEVERE, null, ex);
//        }            

        return message;
    }

    /**
     * Unsubscribes the user from all the documents he is registered for and
     * removes him from the server
     * 
     * @param user
     *            to be removed
     */
    public synchronized void removeUser(SEditThread user) {
        documents.values().stream().forEach((document) -> {
            document.unsubscribeUser(user);
        });

        users.remove(user.getUserID());
    }

    /**
     * Listens at the serverSocket for incoming connections and creates a
     * SEditThread for them when it hears one
     */
    public void serve() {
        
        while (true) {
            try {
                String newUserID;
                synchronized (this) {
                    newUserID = "User" + userIDCounter;
                    userIDCounter++;
                }

                // Wait until a new user attempts to connect
                SEditThread user = new SEditThread(serverSocket.accept(), this,
                        newUserID);
                System.out.println("New client connection with userID: "
                        + newUserID);

                synchronized (this) {
                    users.put(newUserID, user);
                    user.start();
                }
            } catch (IOException e) {
                synchronized (this) {
                    if (die == true) {
                        // System.out.println("Server in process of shutting down");
                    } else {
                        System.out.println("Problem connecting to new user!");                     
                    }
                }
            }
        }
    }

    /**
     * Closes the serverSocket and shuts down the server
     * 
     */
    public synchronized void kill() {
        System.out.println("Killing the server!");


        this.die = true;

        try {
            // Closing the socket while listening on it
            // produces an IOException for the accept() call
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Could not close socket!");

        }

        System.exit(0);
    }

    
}
