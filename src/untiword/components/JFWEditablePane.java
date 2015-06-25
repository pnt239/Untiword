/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.components;

import javax.swing.JEditorPane;

/**
 *
 * @author NThanh
 */
public class JFWEditablePane extends JEditorPane {
    private JFWPageableEditorKit jPageableEditorKit;
    
    public JFWEditablePane() {
        jPageableEditorKit = new JFWPageableEditorKit();
        
        super.setEditorKit(jPageableEditorKit);
    }
}
