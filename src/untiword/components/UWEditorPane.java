/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.components;

import java.awt.print.PageFormat;
import java.awt.print.Paper;
import javax.swing.JTextPane;
import javax.swing.text.html.HTMLDocument;
import untiword.components.docx.DocxEditorKit;

/**
 *
 * @author NThanh
 */
public class UWEditorPane extends JTextPane {
    //private MyPageableEditorKit jPageableEditorKit;
    
    public UWEditorPane() {
        super();
        
        //jPageableEditorKit = new MyPageableEditorKit();
        //jPageableEditorKit.setHeader(jPageableEditorKit.createHeader());
        //jPageableEditorKit.setFooter(jPageableEditorKit.createFooter());
        //super.setEditorKit(jPageableEditorKit);
        
        super.setEditorKit(new DocxEditorKit());
        //super.setDocument(new HTMLDocument());
        
        PageFormat pf = new PageFormat();
        pf.setPaper(new Paper());
        final MyPaginationPrinter pp = new MyPaginationPrinter(pf, this);
    }
}
