/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.gui.client;

import com.alee.laf.panel.WebPanel;
import java.util.EventListener;

/**
 *
 * @author Untitled25364
 */
public interface EditorListener extends EventListener{
    
    public void panelCreated(WebPanel input);
}
