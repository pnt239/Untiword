package untiword.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import static javax.swing.text.DefaultStyledDocument.BUFFER_SIZE_DEFAULT;
import javax.swing.text.GapContent;
import javax.swing.text.StyleContext;
import untiword.components.docx.DocxDocument;
import untiword.components.docx.DocxReader;

/**
 * DocumentDQ represents the client side of a document.
 * It stores the most current synchronized version and
 * a queue of local updates to be applied to it before
 * it is displayed to the user.
 * 
 * @author Michael Turek
 */
public class DocumentDQ {
    private String userID;
    
    //private String syncCopy;
    private DocxDocument syncCopy;
    private int syncVersion;
    
    private String lastMessageReceived;
        
    private List<ServerRequestDQ> localQueue;
    
    /**
     * Creates a new instance of DocumentDQ
     * and saves the owner ID into a private field
     * 
     * @param userID - the userID of the client
     * @param doc
     */
    public DocumentDQ(String userID, DocxDocument doc) {

        this.syncCopy = doc;
        this.syncVersion = 0;
        
        this.userID = userID;
        this.localQueue = new ArrayList<ServerRequestDQ>();
        
        this.lastMessageReceived = "|0|0|INSERT|0|0||0";
    }
    
    /**
     * Applies the queue of local changes to the current 
     * synchronized copy and returns it for display
     * 
     * @return String - a viewCopy to be displayed by the GUI
     */
    public synchronized DocxDocument getView() {
        DocxDocument viewCopy = syncCopy;
        
        for(ServerRequestDQ localChange : localQueue) {
            //applyChange(viewCopy, localChange);
        }
        
        return viewCopy;
    }
    
    /**
    * Returns the current syncCopy of this document
    * This is mostly for debugging purposes as the syncCopy
    * represents internal data that is never actually displayed 
    * by the client
    * 
    * @return String - current syncCopy
    */
    public synchronized DocxDocument getSyncCopy() {
        return syncCopy;
    }
    
    /**
     * Returns the version ID of the most recent update
     * to the synchronized copy
     * 
     * @return int - current syncVersion
     */
    public synchronized int getCurrentVersion() { 
        return syncVersion; 
    }
    
    /**
     * Adds a request to the local queue to be displayed
     * to the user immediately after inputting it
     * 
     * @param request - the request to be added
     */
    public synchronized void addRequest(String request) {
        localQueue.add(new ServerRequestDQ(request));  
    }
    
    /**
     * Accepts a request that's targeted towards this document
     * and processes it
     * 
     * @param requestText - request to be processed
     */
    public synchronized void pushRequest(String requestText) {
        ServerRequestDQ request = new ServerRequestDQ(requestText);
        lastMessageReceived = requestText;
        
        /* Awaiting  step */
        // If own request -> remove and continue
        if(request.getUserID().equals(userID)) {
            if(request.getRequestNumber() == localQueue.get(0).getRequestNumber()) {
                localQueue.remove(0);
                syncVersion = request.getVersionID();
                return;
            } else {
                throw new RuntimeException("Request ordering error");
            }
        } else {
            // Update the synchronized version
            applyChange(syncCopy, request);
            syncVersion = request.getVersionID();
        }
       
        // If someone else's request, update local queue
        for(ServerRequestDQ localChange : localQueue) {
            localChange.applyUpdate(request);
        }       
    }
    
    /**
     * Applies the specified edit request to a String and returns the
     * new version
     * 
     * @param source - the beginning String which we're applying the change to
     * @param request - the change to be applied
     * @return String - the final version of the String after the change is applied
     */
    public void applyChange(DocxDocument source, ServerRequestDQ request) {
        
        if(request.getAction().equals("INSERT")) {
            try {
                source.insertString(request.getBeginning(), request.getContent().replace("~", "\n"), null);
            } catch (BadLocationException ex) {
                Logger.getLogger(DocumentDQ.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if(request.getAction().equals("DELETE")) {
            try {
                source.remove(request.getBeginning(), request.getEnd() - request.getBeginning());
            } catch (BadLocationException ex) {
                Logger.getLogger(DocumentDQ.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Action not supported, ignoring...");
        }

    }
    
    /**
     * Takes in a dummy request produced by the GUI to represent
     * the current selection and applies the last request received
     * from the server to it to get a new selection
     * 
     * @param request to be updated
     * @return String - the updated request
     */
    public synchronized String updateSelection(String requestText) {
        ServerRequestDQ currentSelection = new ServerRequestDQ(requestText);
        ServerRequestDQ lastRequestReceived = new ServerRequestDQ(lastMessageReceived);
        
        if(lastRequestReceived.getUserID().equals(userID)) {
            return requestText;
        }
        
        currentSelection.applyUpdate(lastRequestReceived);
        return currentSelection.toString();
    }
}
