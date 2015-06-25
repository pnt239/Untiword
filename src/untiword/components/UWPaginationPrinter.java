package untiword.components;

import java.awt.*;
import java.awt.print.*;
import javax.swing.*;

import untiword.components.UWPageableEditorKit.*;

/**
 *
 *
 * @author Stanislav Lapitsky
 * @version 1.0
 */

public class UWPaginationPrinter implements Printable, Pageable {
    private PageFormat pageFormat;
    private JEditorPane editorPane;
    boolean isPaginated = false;
    UWPageableEditorKit kit = null;

    public UWPaginationPrinter(PageFormat pageFormat, JEditorPane pane) {
        this.pageFormat = pageFormat;
        this.editorPane = pane;

        if (pane.getEditorKit() instanceof UWPageableEditorKit) {
            isPaginated = true;

            kit = (UWPageableEditorKit) pane.getEditorKit();
            kit.SetPageWidth( (int) pageFormat.getWidth() + MyPageableEditorKit.DRAW_PAGE_INSET * 2);
            kit.SetPageHeight( (int) pageFormat.getHeight() + MyPageableEditorKit.DRAW_PAGE_INSET * 2);
            int top = (int) pageFormat.getImageableY();
            int left = (int) pageFormat.getImageableX();
            int bottom = (int) (pageFormat.getHeight() - pageFormat.getImageableHeight() - top);
            int right = (int) (pageFormat.getWidth() - pageFormat.getImageableWidth() - left);
            kit.setPageMargins(new Insets(top, left, bottom, right));

            pane.revalidate();
        }
    }

    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Graphics2D g2d = (Graphics2D) g;
        if (isPaginated) {
            g2d.translate(0, -kit.GetPageHeight() * pageIndex);

            g2d.translate( -MyPageableEditorKit.DRAW_PAGE_INSET, -MyPageableEditorKit.DRAW_PAGE_INSET);
            Shape oldClip=g2d.getClip();

            g2d.setClip(0,kit.GetPageHeight() * pageIndex,kit.GetPageWidth(), kit.GetPageHeight());
            editorPane.printAll(g2d);

            g2d.setClip(oldClip);
            if (pageIndex < getPageCount()) {
                return PAGE_EXISTS;
            }
        }
        else {
            if (pageIndex == 0) {
                g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                editorPane.printAll(g);
                return PAGE_EXISTS;
            }
        }
        return NO_SUCH_PAGE;
    }

    public int getPageCount() {
        if (isPaginated) {
            return ( (SectionView) editorPane.getUI().getRootView(editorPane).getView(0)).GetPageCount();
        }
        return 1;
    }

    public int getNumberOfPages() {
        return getPageCount();
    }

    public PageFormat getPageFormat(int pageIndex) throws IndexOutOfBoundsException {
        return pageFormat;
    }

    public Printable getPrintable(int pageIndex) throws IndexOutOfBoundsException {
        return this;
    }
}
