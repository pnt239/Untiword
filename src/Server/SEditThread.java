package Server;

import Controller.AccountController.FacebookController;
import Model.Account.Account;
import Model.Account.AccountLoginViewModel;
import Server.BusinessLogicLayer.AccountBlo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import libraryclasses.CustomRequest;
import org.xml.sax.SAXException;

/**
 * The SEditThread class represents a connection to an individual client. It
 * listens to messages from users and redirects them to the server queue. The
 * server also uses its sendMessage() method to inform users about updates made
 * by other users.
 * 
 */
public class SEditThread extends Thread {

    private final Socket socket;
    private final SEditServer server;
    private final String userID;

    private final BufferedReader in;
    private final PrintWriter out;

    /**
     * Creates a new SEditThread object to communicate with a client on the
     * specified socket
     * 
     * @param socket
     *            - socket on which the client is communicating
     * @param server
     *            - the server that spawned this thread
     * @param userID
     *            - the ID of the user whose communication this thread is
     *            managing
     * @throws IOException
     *             if IO streams can't be opened on the socket
     */
    public SEditThread(Socket socket, SEditServer server, String userID)
            throws IOException {
        super("SEditThread");
        this.socket = socket;
        this.server = server;

        this.userID = userID;

        this.in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }

    /**
     * Returns the ID of this user
     * 
     * @return String - userID
     */
    public String getUserID() {
        return userID;
    }

    /**
     * Method to be run when this thread is created
     */
    @Override
    public void run() {
        try {
            handleConnection(socket);
        } catch (IOException e) {
            System.out.println("Problem closing the IO streams");            
            
        }
    }

    /**
     * Sends the specified message to the user
     * 
     * @param message
     *            to be sent
     */
    public void sendMessage(String message) {
        out.println(message);        
    }

    /**
     * Listens to incoming messages from the client and redirects them to their
     * destination
     * 
     * @param socket
     *            on which the thread is listening
     * @throws IOException
     *             if there is a communication error
     */
    private void handleConnection(Socket socket) throws IOException {

        try {
            // Send hello message:
            /* HELLO|USER_ID */
            out.println("HELLO|" + userID);
            
            for (String line = in.readLine(); line != null; line = in.readLine()) 
            {
                if (line.equals("BYE")) {
                    System.out.println("Disconnecting user");

                    server.removeUser(this);
                    break;
                }
                else if(line.startsWith("CUSTOM_REQUEST/AUTHORIZE")) 
                {
                    AccountLoginViewModel model = new AccountLoginViewModel(line);

                    CustomRequest request = new CustomRequest(4);
                    request.setAction("AUTHORIZE");                       
                    request.setValue("applicationId", model.getApplicationUserId());
                    request.setValue("loginType", model.getLoginType());
                    request.setValue("accessToken", model.getAccessToken());

                    if(FacebookController.getInstance().getUser(model.getAccessToken()) != null)
                    {
                        System.out.println("User id" + userID + " was login");    
                        try
                        {
                            AccountBlo.getInstance().loginWithFacebook(model.getAccessToken());
                            request.setValue("result", "success");      
                            System.out.println();
                        }
                        catch (ParserConfigurationException | SAXException | IOException | TransformerException | XPathExpressionException e)
                        {
                            System.out.println(e.toString());
                        }                                                
                    }
                    else
                    {
                        request.setValue("result", "failed");
                    }

                    this.sendMessage(request.toString());
                }
                else if(line.startsWith("CUSTOM_REQUEST/LOGOUT"))
                {
                    CustomRequest request = new CustomRequest(line);
                    try {
                        AccountBlo.getInstance().logout(request.getValue("accessToken"));
                    } catch (ParserConfigurationException | SAXException | TransformerException | XPathExpressionException ex) {
                        System.out.println(ex.toString());
                    }
                }
                else if(line.startsWith("CUSTOM_REQUEST/GET"))
                {
                    CustomRequest request = new CustomRequest(line);
                    if(request.getValue("type").equals("loginUsers"))
                    {
                        try 
                        {
                            Account[] logins = AccountBlo.getInstance().getLoginUsers();
                            if(logins != null)
                            {
                                String usersString = "";
                                for(int i=0; i<logins.length-1; i++)
                                {
                                    if(logins[i] != null)
                                    {
                                        usersString += logins[i].getId()+ "|" + logins[i].getFacebookEmail() + "|";
                                    }                                   
                                }
                                
                                if(logins[logins.length - 1] != null)
                                {
                                     usersString += logins[logins.length - 1].getId() + "|" + logins[logins.length - 1].getFacebookEmail();
                                }                           
                                
                                CustomRequest newRequest = new CustomRequest(2);
                                newRequest.setAction("RETURN");
                                newRequest.setValue("type", "loginUsers");
                                newRequest.setValue("value", usersString);
                                sendMessage(newRequest.toString());
                                System.out.println("Return get login users request: " + usersString);
                            }
                        } catch (ParserConfigurationException | SAXException | TransformerException | XPathExpressionException ex) {
                            Logger.getLogger(SEditThread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

                System.out.println("Incoming message from " + userID + ": " + line);

                // only one thread can send a request at a time, others
                // have to wait
                synchronized (server) {
                    server.handleRequest(line);
                }
            }
        } catch (IOException e) {
            // Ignore IOExceptions but close streams
        } finally {
            in.close();
            out.close();
        }

    }
}
