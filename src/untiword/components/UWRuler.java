/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;
import untiword.components.docx.DocxDocument;
import untiword.components.docx.DocxMeasureConverter;

/**
 *
 * @author NThanh
 */
public class UWRuler extends JPanel implements ComponentListener {

    private DocxDocument document;
    private int dpi;
    private int haftDPI;
    private int quadDPI;
    private int eightDPI;

    public UWRuler(DocxDocument document) {
        this.document = document;
        dpi = DocxMeasureConverter.getDPI();
        haftDPI = dpi / 2;
        quadDPI = dpi / 4;
        eightDPI = dpi / 8;
        
        this.addComponentListener(this);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        Rectangle clip = graphics.getClipBounds();
        Insets margins = document.getPageMargins();

        paintBorder(graphics, clip);
        Rectangle rec = new Rectangle(clip.x, clip.y + 1, clip.width, clip.height - 2);
        paintAlign(graphics, rec, margins);
        
        rec.x = document.getPageData().getPageLeft() + margins.left;
        rec.width = document.getPageWidth() - margins.left;
        paintUnits(graphics, rec);
    }

    private void paintBorder(Graphics graphics, Rectangle clip) {
        graphics.setColor(new Color(217, 217, 217));
        graphics.fillRect(clip.x, clip.y, clip.width, clip.height);
    }

    private void paintAlign(Graphics g, Rectangle clip, Insets margins) {
        g.setColor(new Color(238, 238, 238));
        g.fillRect(clip.x, clip.y, clip.width, clip.height);

        g.setColor(Color.white);
        g.fillRect(document.getPageData().getPageLeft() + margins.left,
                clip.y,
                document.getPageWidth() - margins.left - margins.right,
                clip.height);
    }

    private void paintUnits(Graphics g, Rectangle clip) {
        g.setColor(new Color(204, 204, 204));
        //g.fillRect(clip.x, clip.y, clip.width, clip.height);
        paintUnitPart(g, clip, 0);
        Rectangle rec = new Rectangle(document.getPageData().getPageLeft(), clip.y,
            document.getPageMargins().left,
            clip.height);
        paintUnitPart(g, rec, 1);
    }

    private void paintUnitPart(Graphics g, Rectangle clip, int direct) {
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        FontMetrics fm = g.getFontMetrics();
        int fontY = fm.getAscent();
        int offset = clip.x;

        if (direct == 0) { // Draw from left to right
            for (int x = 0, maxX = clip.width; x <= maxX; x += eightDPI, offset += eightDPI) {

                if (x == 0) {
                    continue;
                }

                if (x % dpi == 0) {
                    String numString = Integer.toString(x / dpi);
                    Color oldColor = g.getColor();
                    g.setColor(Color.black);
                    g.drawString(numString, offset
                            - fm.stringWidth(numString) / 2, fontY + 1);
                    g.setColor(oldColor);
                } else if (x % haftDPI == 0) {
                    g.drawLine(offset, 3, offset, 3 + 7);
                } else {
                    g.drawLine(offset, 5, offset, 5 + 3);
                }
            }
        } else {
            int x = 0;
            offset += clip.width;
            for (; offset >= clip.x; x += eightDPI, offset -= eightDPI) {
                if (x == 0) {
                    continue;
                }

                if (x % dpi == 0) {
                    String numString = Integer.toString(x / dpi);
                    Color oldColor = g.getColor();
                    g.setColor(Color.black);
                    g.drawString(numString, offset
                            - fm.stringWidth(numString) / 2, fontY + 1);
                    g.setColor(oldColor);
                } else if (x % haftDPI == 0) {
                    g.drawLine(offset, 3, offset, 3 + 7);
                } else {
                    g.drawLine(offset, 5, offset, 5 + 3);
                }
            }
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        this.repaint();
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        /* Do nothing */
    }

    @Override
    public void componentShown(ComponentEvent e) {
        /* Do nothing */
    }

    @Override
    public void componentHidden(ComponentEvent e) {
        /* Do nothing */
    }
}
