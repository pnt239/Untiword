/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.events;

import untiword.components.UWEditor;

/**
 *
 * @author NThanh
 */
public class RenameDocumentEvent {
    private UWEditor editor;
    private String newName;
    
    public RenameDocumentEvent(UWEditor editor, String newName) {
        this.editor = editor;
        this.newName = newName;
    }

    /**
     * @return the editor
     */
    public UWEditor getEditor() {
        return editor;
    }

    /**
     * @return the newName
     */
    public String getNewName() {
        return newName;
    }
    
    
}
