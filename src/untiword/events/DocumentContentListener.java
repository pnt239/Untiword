/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.events;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import untiword.components.UWEditor;
import untiword.components.docx.DocxDocument;
import untiword.controller.ClientController;

/**
 *
 * @author NThanh
 */
public class DocumentContentListener implements DocumentListener {

    final int docNum;
    final ClientController clientController;

    /**
     * Creates new DocumentContentListener on passed in document 
     * 
     * @param docNum document number of document listener listens to
     * @param editor to which the listener belongs
     */
    public DocumentContentListener(int docNum, ClientController client) {
        this.docNum = docNum;
        this.clientController = client;
    }

    /**
     * wrapper method for the update method for inserts
     */
    public void insertUpdate(DocumentEvent e) {
        try {
            update(e, EditType.INSERT);
        } catch (BadLocationException e1) {
           // e1.printStackTrace();
        }
    }

    /**
     * wrapper method for the update method for deletes
     */
    public void removeUpdate(DocumentEvent e) {
        try {
            update(e, EditType.DELETE);
        } catch (BadLocationException e1) {
           // e1.printStackTrace();
        }
    }

    /**
     * superclass method that we have to add.
     */
    public void changedUpdate(DocumentEvent e) {
        // Plain text components don't fire these events.
    }

    /**
     * update method for handling user inserts and deletes in the Editor to the
     * document. Gets the correct locations and sends the appropriate messages
     * to the server.
     * 
     * @param e
     * @param action
     * @throws BadLocationException
     */
    public void update(DocumentEvent e, EditType action)
            throws BadLocationException {
        synchronized (clientController) {
            if (clientController.getIgnoreNext() > 0) {
                clientController.setIgnoreNext(clientController.getIgnoreNext() - 1);
                return;
            }

            Document doc = (Document) e.getDocument();
            DocxDocument document = (DocxDocument)e.getDocument();
            
            int offset = e.getOffset();
            int length = e.getLength();

            if (action == EditType.INSERT) {

                String content = doc.getText(offset, length).replace("\n", "~");
                if (content.contains("|")) {
                    JOptionPane
                            .showMessageDialog(
                                    null,
                                    "Hi Katrina! We were hoping you won't see this... but pipe usage is HIGHLY discouraged.");

                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {

                            UWEditor pan = clientController.getDocIDtoDocPanel().get(
                                    docNum);
                            pan.getTextPane().setCaretPosition(0);
                            clientController.updateView(docNum + "");
                        }
                    });
                    return;
                }
                clientController.getUser().createRequest(
                        docNum,
                        "" + action.toString() + "|" + (offset) + "|"
                                + (offset + length) + "|" + content);
            } else {
                clientController.getUser().createRequest(
                        docNum,
                        "" + action.toString() + "|" + offset + "|"
                                + (offset + length) + "|" + "wooo");
            }
            clientController.sendMessage(clientController.getUser().pullRequest());
        }
    }

    /**
     * enum for the 2 edit types.
     *
     */
    public static enum EditType {
        INSERT, DELETE,
    }
}
