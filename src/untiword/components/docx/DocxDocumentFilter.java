/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.components.docx;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import untiword.components.UWEditor;
import untiword.controller.ClientController;
import untiword.events.DocumentContentListener.EditType;

/**
 *
 * @author NThanh
 */
public class DocxDocumentFilter extends DocumentFilter {

    private int docNum;
    private ClientController client;

    public DocxDocumentFilter(ClientController client, int docNum) {
        super();
        this.client = client;
        this.docNum = docNum;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String text,
            AttributeSet attrs) throws BadLocationException {
        super.insertString(fb, offset, text, attrs);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
            AttributeSet attrs) throws BadLocationException {
        update(fb, offset, length, text, attrs, EditType.INSERT);
    }

    @Override
    public void remove(FilterBypass fb, int offset, int length)
            throws BadLocationException {
        if (this.client.getIgnoreNext() > 0) {
            this.client.setIgnoreNext(this.client.getIgnoreNext() - 1);
            super.remove(fb, offset, length);
            return;
        }

        update(fb, offset, length, "", null, EditType.DELETE);
    }

    private void update(FilterBypass fb, int offset, int length, String text,
            AttributeSet attrs, EditType action) {

        DocxDocument document = (DocxDocument) fb.getDocument();

        if (action == EditType.INSERT) {

            String content = text;
            if (content.contains("|")) {
                return;
            }
            client.getUser().createRequest(
                    docNum,
                    "" + action.toString() + "|" + (offset) + "|"
                    + (offset + length) + "|" + content);
        } else {
            client.getUser().createRequest(
                    docNum,
                    "" + action.toString() + "|" + offset + "|"
                    + (offset + length) + "|" + "wooo");
        }

        client.sendMessage(client.getUser().pullRequest());
    }
}
