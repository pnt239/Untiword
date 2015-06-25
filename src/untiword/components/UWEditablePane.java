/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.components;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import javax.swing.JEditorPane;

/**
 *
 * @author NThanh
 */
public class UWEditablePane extends JEditorPane {
    private UWPageableEditorKit jPageableEditorKit;
    
    public UWEditablePane() {
        jPageableEditorKit = new UWPageableEditorKit();
        //jPageableEditorKit.setHeader(jPageableEditorKit.createHeader());
        //jPageableEditorKit.setFooter(jPageableEditorKit.createFooter());
        super.setEditorKit(jPageableEditorKit);
        
        PageFormat pf = new PageFormat();
        pf.setPaper(new Paper());
        final UWPaginationPrinter pp = new UWPaginationPrinter(pf, this);
    }
}
