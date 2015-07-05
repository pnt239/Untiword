/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.events;

/**
 *
 * @author NThanh
 */
public class ListDocumentEvent {
    private String[] docNameList;
    
    public ListDocumentEvent(String[] docs) {
        this.docNameList = docs;
    }
    
    public String[] DocumentNameList() {
        return docNameList;
    }
}
