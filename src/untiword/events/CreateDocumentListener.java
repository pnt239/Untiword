/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.events;

import java.util.EventListener;
import untiword.components.UWEditor;

/**
 *
 * @author Untitled25364
 */
public interface CreateDocumentListener extends EventListener{
    public void getUWPanel(UWEditor editor);
}
