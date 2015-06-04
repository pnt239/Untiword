/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfateword.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author NThanh
 */
public class JFWEditor extends JScrollPane implements ActionListener{
    private JFWRuler jRuler;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public JFWEditor() {
        super(new JFWEditablePane(), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        init();
    }
    
    private void init() {
        jRuler = new JFWRuler();
        super.setColumnHeaderView(jRuler);
    }
}
