/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author NThanh
 */
public class UWEditor extends JScrollPane implements ActionListener{
    private UWRuler jRuler;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public UWEditor() {
        //super(new UWEditablePane(), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        super(new UWEditablePane());
        init();
    }
    
    private void init() {
        jRuler = new UWRuler();
        super.setColumnHeaderView(jRuler);
    }
}
