/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.components.docx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import javax.swing.text.BoxView;
import javax.swing.text.Element;
import javax.swing.text.View;
import untiword.components.MyPageableEditorKit;
import static untiword.components.MyPageableEditorKit.HF_SHIFT;
import static untiword.components.MyPageableEditorKit.MIN_GAP_HEADER_PAGE;
import static untiword.components.MyPageableEditorKit.MIN_PAGE_LEFT;
import static untiword.components.MyPageableEditorKit.MIN_PAGE_TOP;
import untiword.components.UWMultiPageView;

/**
 *
 * @author NThanh
 */
public class UWSectionView extends BoxView {
    
    private int pageNumber;
    private int pageHeight;
    private int pageWidth;
    private Insets pageMargins;
    private int pageGap;
    private int pageTop;
    private int pageLeft;

    /**
     * Constructs a section view.
     *
     * @param elem the element this view is responsible for
     * @param axis either <code>View.X_AXIS</code> or <code>View.Y_AXIS</code>
     */
    public UWSectionView(Element elem, int axis) {
        super(elem, axis);
    }

    /**
     * Perform layout on the box
     *
     * @param width the width (inside of the insets) >= 0
     * @param height the height (inside of the insets) >= 0
     */
    @Override
    protected void layout(int width, int height) {
        DocxDocument doc = (DocxDocument) getDocument();
        pageWidth = doc.getPageWidth();
        pageMargins = doc.getPageMargins();
        
        super.layout(pageWidth - pageMargins.left - pageMargins.right, height);
    }

    /**
     * Determines the minimum span for this view along an axis.
     *
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return the span the view would like to be rendered into >= 0. Typically
     * the view is told to render into the span that is returned, although there
     * is no guarantee. The parent may choose to resize or break the view.
     * @exception IllegalArgumentException for an invalid axis type
     */
    @Override
    public float getMinimumSpan(int axis) {
//        if (axis == View.X_AXIS) {
//            DocxDocument doc = (DocxDocument) this.getDocument();
//            return doc.getPageWidth();
//        } else {
//            return super.getMinimumSpan(axis);
//        }
        return getPreferredSpan(axis);
    }

    /**
     * Determines the maximum span for this view along an axis.
     *
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return the span the view would like to be rendered into >= 0. Typically
     * the view is told to render into the span that is returned, although there
     * is no guarantee. The parent may choose to resize or break the view.
     * @exception IllegalArgumentException for an invalid axis type
     */
    @Override
    public float getMaximumSpan(int axis) {
//        if (axis == View.X_AXIS) {
//            DocxDocument doc = (DocxDocument) this.getDocument();
//            return doc.getPageWidth();
//        } else {
//            return super.getMaximumSpan(axis);
//        }
        return getPreferredSpan(axis);
    }

    /**
     * Determines the preferred span for this view along an axis.
     *
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return the span the view would like to be rendered into >= 0. Typically
     * the view is told to render into the span that is returned, although there
     * is no guarantee. The parent may choose to resize or break the view.
     * @exception IllegalArgumentException for an invalid axis type
     */
    @Override
    public float getPreferredSpan(int axis) {
        float span;
        DocxDocument doc = (DocxDocument) getDocument();
        pageNumber = doc.getPageData().getPageNumber();
        pageHeight = doc.getPageHeight();
        pageWidth = doc.getPageWidth();
        pageGap = doc.getPageData().getPageGap();
        
        if (axis == View.X_AXIS) {
            span = 2 * MIN_PAGE_LEFT + pageWidth;
        } else {
            span = pageHeight * pageNumber
                    + 2 * MIN_PAGE_TOP
                    + pageGap * (pageNumber - 1);
        }
        return span;
    }

    /**
     * Performs layout along Y_AXIS with shifts for pages.
     *
     * @param targetSpan int
     * @param axis int
     * @param offsets int[]
     * @param spans int[]
     */
    @Override
    protected void layoutMajorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
        super.layoutMajorAxis(targetSpan, axis, offsets, spans);
        //validate header and footer sizes if necessary
            /* TODO: add page header and footer here */
        DocxDocument doc = (DocxDocument) getDocument();
        pageNumber = 1;
        doc.getPageData().setPageNumber(pageNumber);
        pageHeight = doc.getPageHeight();
        pageWidth = doc.getPageWidth();
        pageMargins = doc.getPageMargins();
        pageGap = doc.getPageData().getPageGap();
        
        int n = offsets.length;
        
        int totalOffset = 0;
        
        for (int i = 0; i < n; i++) {
            offsets[i] = totalOffset;
            View v = getView(i);
//                if (isPageBreak(v)) {
//                    offsets[i] = pageNumber * pageHeight;
//                    pageNumber++;
//                }

            if (v instanceof UWMultiPageView) {
                ((UWMultiPageView) v).setBreakSpan(0);
                ((UWMultiPageView) v).setAdditionalSpace(0);
            }
            
            int docHeight = pageNumber * pageHeight - pageMargins.top - pageMargins.bottom + pageGap * (pageNumber - 1);
            if ((offsets[i] + spans[i]) > docHeight) {
                if ((v instanceof UWMultiPageView) && (v.getViewCount() > 1)) {
                    UWMultiPageView multipageView = (UWMultiPageView) v;
                    int space = offsets[i] - (pageNumber - 1) * pageHeight;
                    int breakSpan = docHeight - offsets[i];
                    
                    multipageView.setBreakSpan(breakSpan);
                    multipageView.setPageOffset(space);
                    multipageView.setStartPageNumber(pageNumber);
                    multipageView.setEndPageNumber(pageNumber);
                    
                    int height = (int) getHeight();
                    int width = ((BoxView) v).getWidth();
                    
                    if (v instanceof UWPageableParagraphView) {
                        UWPageableParagraphView parView = (UWPageableParagraphView) v;
                        parView.layout(width, height);
                    }
                    
                    pageNumber = multipageView.getEndPageNumber();
                    doc.getPageData().setPageNumber(pageNumber);
                    spans[i] += multipageView.getAdditionalSpace();
                } else {
                    //offsets[i] = pageTop + pageNumber * pageHeight + pageGap * (pageNumber - 1);
                    offsets[i] = (pageHeight + pageGap) * pageNumber;
                    pageNumber++;
                    doc.getPageData().setPageNumber(pageNumber);
                }
            }
            totalOffset = (int) Math.min((long) offsets[i] + (long) spans[i], Integer.MAX_VALUE);
        }
    }
    
    @Override
    public void paint(Graphics g, Shape a) {
        Rectangle alloc = (a instanceof Rectangle) ? (Rectangle) a : a.getBounds();
        //Shape baseClip = g.getClip().getBounds();
        DocxDocument doc = (DocxDocument) getDocument();
        pageNumber = doc.getPageData().getPageNumber();
        pageHeight = doc.getPageHeight();
        pageWidth = doc.getPageWidth();
        pageGap = doc.getPageData().getPageGap();
        pageTop = doc.getPageData().getPageTop();
        
        doc.getPageData().setPageLeft((alloc.width - pageWidth) / 2);
        pageLeft = doc.getPageData().getPageLeft();
        
        this.setInsets((short) (pageTop + pageMargins.top),
                (short) (pageLeft + pageMargins.left),
                (short) (pageTop + pageMargins.bottom),
                (short) 0 /* Dont care */);
        
        /* Draw background */
        paintBackground(g, a);
        
        Rectangle page = new Rectangle();
        page.x = alloc.x + pageLeft;
        page.y = alloc.y + pageTop;
        page.height = pageHeight;
        page.width = pageWidth;
        
        for (int i = 0; i < pageNumber; i++) {
            paintPageFrame(g, page);
            page.y += pageGap + pageHeight;
        }
        super.paint(g, a); /* Draw text */
        
        g.setColor(Color.gray);
        
    }
    
    protected void paintBackground(Graphics g, Shape a) {
        Rectangle alloc = (a instanceof Rectangle) ? (Rectangle) a : a.getBounds();
        
        g.setColor(new Color(0xEE, 0xEE, 0xEE));
        g.fillRect(alloc.x, alloc.y, alloc.width, alloc.height);
    }

    /**
     * Paints frame for specified page
     *
     * @param g Graphics
     * @param page Shape page rectangle
     */
    public void paintPageFrame(Graphics g, Shape page) {
        Rectangle alloc = (page instanceof Rectangle) ? (Rectangle) page : page.getBounds();

        // Draw border
        g.setColor(new Color(0xC6, 0xC6, 0xC6));
        g.fillRect(alloc.x - 1, alloc.y - 1, alloc.width + 2, alloc.height + 2);

        // Draw page
        g.setColor(Color.WHITE);
        g.fillRect(alloc.x, alloc.y, alloc.width, alloc.height);
    }
}
