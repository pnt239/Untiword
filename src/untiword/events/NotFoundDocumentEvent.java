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
public class NotFoundDocumentEvent {
    private final String message = "Not found any document!";
    
    public NotFoundDocumentEvent(){
        /* Do nothing */
    }
    
    public String getMessage() {
        return message;
    }
}
