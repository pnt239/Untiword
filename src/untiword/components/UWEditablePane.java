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
    private MyPageableEditorKit jPageableEditorKit;
    
    public UWEditablePane() {
        jPageableEditorKit = new MyPageableEditorKit();
        jPageableEditorKit.setHeader(jPageableEditorKit.createHeader());
        jPageableEditorKit.setFooter(jPageableEditorKit.createFooter());
        super.setEditorKit(jPageableEditorKit);
        
        PageFormat pf = new PageFormat();
        pf.setPaper(new Paper());
        final MyPaginationPrinter pp = new MyPaginationPrinter(pf, this);
    }
}
