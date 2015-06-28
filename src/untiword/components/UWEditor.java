/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTMLDocument;
import untiword.components.docx.BorderAttributes;
import untiword.components.docx.DocxDocument;

/**
 *
 * @author NThanh
 */
public class UWEditor extends JScrollPane implements ActionListener {

    private UWRuler jRuler;
    private UWEditorPane _editor;

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public UWEditor() {
        //super(new UWEditorPane(), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        super(new UWEditorPane());

        init();
    }

    private void init() {
        // get the text pane
        JViewport v = (JViewport) super.getComponent(0);
        _editor = (UWEditorPane) v.getComponent(0);
        
        try {
        FileInputStream in=new FileInputStream("thanh_test.docx");
         _editor.getEditorKit().read(in, _editor.getDocument(), 0);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //showFeatures();
        
        // Ruler
        jRuler = new UWRuler((DocxDocument)_editor.getDocument());
        jRuler.setMaximumSize(new Dimension(0, 15));
        jRuler.setMinimumSize(new Dimension(0, 15));
        jRuler.setPreferredSize(new Dimension(0, 15));
        super.setColumnHeaderView(jRuler);
    }

    public void showFeatures() {
        DocxDocument doc = (DocxDocument) _editor.getDocument();
        try {
            //margins support
            //doc.setDocumentMargins(new Insets(50, 50, 50, 50));

            //paragraph features
            String paragraph = "Usual paragraph text ";
            for (int i = 0; i < 4; i++) {
                paragraph += paragraph;
            }
            doc.insertString(0, paragraph + "\n ", null);

            paragraph = "Show paragraph features support. Alignment justified, first line indent is 50, line spacing is 1.5. ";
            paragraph += paragraph;
            MutableAttributeSet attrs = new SimpleAttributeSet();
            StyleConstants.setAlignment(attrs, StyleConstants.ALIGN_JUSTIFIED);
            StyleConstants.setFirstLineIndent(attrs, 50);
            StyleConstants.setLineSpacing(attrs, 1.5f);
            StyleConstants.setLeftIndent(attrs, 10);
            StyleConstants.setRightIndent(attrs, 10);
            doc.insertString(0, paragraph + "\n", null);
            doc.setParagraphAttributes(0, paragraph.length(), attrs, false);
            doc.insertString(0, "\n", null);
            doc.setParagraphAttributes(0, 1, new SimpleAttributeSet(), true);

            //tables
            BorderAttributes ba = new BorderAttributes();
            ba.setBorders(1 + 2 + 4 + 8 + 16 + 32);
            ba.lineColor = Color.black;
            int[] widths = new int[]{150, 100, 50};
            int[] heights = new int[]{1, 50};
            attrs = new SimpleAttributeSet();
            attrs.addAttribute("BorderAttributes", ba);
            doc.insertTable(1, 2, 3, attrs, widths, heights);
            doc.insertString(2, "Tables support", null);

            //nested tables
            ba = new BorderAttributes();
            ba.setBorders(1 + 2 + 4 + 8 + 16 + 32);
            ba.lineColor = Color.blue;
            widths = new int[]{70, 50};
            heights = new int[]{1, 50, 1};
            attrs = new SimpleAttributeSet();
            attrs.addAttribute("BorderAttributes", ba);
            StyleConstants.setAlignment(attrs, StyleConstants.ALIGN_RIGHT);
            doc.insertTable(1, 3, 2, attrs, widths, heights);
            doc.insertString(1, "Nested tables support", null);

            //images
            BufferedImage bi = new BufferedImage(100, 50, BufferedImage.TYPE_INT_RGB);
            Graphics g = bi.getGraphics();
            Color old = g.getColor();
            g.setColor(Color.yellow);
            g.fillRect(0, 0, 100, 50);
            g.setColor(Color.blue);
            g.fillOval(15, 15, 70, 30);
            g.setFont(new Font("Arial", Font.BOLD, 16));
            g.setColor(Color.red);
            g.drawString("Image test", 10, 10);
            g.setColor(old);
            doc.insertPicture(new ImageIcon(bi), doc.getLength() - 1);

            MutableAttributeSet attrss = new SimpleAttributeSet();
            StyleConstants.setFontSize(attrss, 14);
            StyleConstants.setItalic(attrss, true);
            doc.insertString(0, "1111111111111111111\n2222222222222222222\n3333333333333333333", attrss);

            attrs = new SimpleAttributeSet();
            StyleConstants.setFontFamily(attrs, "Arial");
            doc.setCharacterAttributes(0, 20, attrs, false);

            StyleConstants.setFontFamily(attrs, "Serif");
            doc.setCharacterAttributes(20, 20, attrs, false);

            attrs = new SimpleAttributeSet();
            StyleConstants.setAlignment(attrs, StyleConstants.ALIGN_RIGHT);
            doc.setParagraphAttributes(0, 60, attrs, false);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
