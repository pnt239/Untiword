/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import javax.swing.JComponent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BoxView;
import javax.swing.text.ComponentView;
import javax.swing.text.Element;
import javax.swing.text.IconView;
import javax.swing.text.LabelView;
import javax.swing.text.ParagraphView;
import javax.swing.text.Position;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

/**
 *
 * @author NThanh
 */
public class UWPageableEditorKit extends StyledEditorKit {

    PageableViewFactory factory = new PageableViewFactory();

    protected int pageWidth = 150;
    protected int pageHeight = 200;
    protected Insets pageMargins = new Insets(10, 10, 10, 10);

    public static int DRAW_PAGE_INSET = 15;
    public static String PAGE_BREAK_ATTR_NAME = "page_break_attribute";

    //protected JEditorPane header;
    //protected JEditorPane footer;
    protected boolean isValidHF;
    public static int HF_SHIFT = 3;
    boolean isPageBreakInsertion = false;

    /**
     * Constructs kit instance
     */
    public UWPageableEditorKit() {
    }

    /**
     * Gets page width
     *
     * @return Width in pixels
     */
    public int GetPageWidth() {
        return pageWidth;
    }

    /**
     * Sets page width
     *
     * @param width Value in pixels
     */
    public void SetPageWidth(int width) {
        pageWidth = width;
        isValidHF=false;
    }

    /**
     * Gets page height
     *
     * @return Page height in pixels
     */
    public int GetPageHeight() {
        return pageHeight;
    }

    /**
     * Sets page height
     *
     * @param height Value in pixels
     */
    public void SetPageHeight(int height) {
        pageHeight = height;
    }

    /**
     * Sets page margins (distance between page content and page edge).
     *
     * @param margins Insets margins.
     */
    public void setPageMargins(Insets margins) {
        pageMargins = margins;
        isValidHF=false;
    }

    protected void calculateHFSizes() {
        int hfWidth=pageWidth-pageMargins.left-pageMargins.right-2*DRAW_PAGE_INSET;
        int maxHeight=(pageHeight-pageMargins.top-pageMargins.bottom-2*DRAW_PAGE_INSET)/2;
    }

    public void validateHF() {
        if (!isValidHF) {
            calculateHFSizes();

            isValidHF=true;
        }
    }
    
    @Override
    public ViewFactory getViewFactory() {
        return factory;
    }

    /**
     * The view factory class creates custom views for pagination root view
     * (SectionView class) and paragraph (PageableParagraphView class)
     *
     * @author Pham Ngoc Thanh
     * @version 1.0
     */
    class PageableViewFactory implements ViewFactory {

        @Override
        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                if (kind.equals(AbstractDocument.ContentElementName)) {
                    return new LabelView(elem);
                } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                    return new PageableParagraphView(elem);
                } else if (kind.equals(AbstractDocument.SectionElementName)) {
                    return new SectionView(elem, View.Y_AXIS);
                } else if (kind.equals(StyleConstants.ComponentElementName)) {
                    return new ComponentView(elem);
                } else if (kind.equals(StyleConstants.IconElementName)) {
                    return new IconView(elem);
                }
            }
            // default to text display
            return new LabelView(elem);
        }

    }

    /**
     * Root view which perform pagination and paints frame around pages.
     *
     * @author Pham Ngoc Thanh
     * @version 1.0
     */
    class SectionView extends BoxView {
        int pageNumber = 0;

        /**
         * Creates view instace
         * @param elem Element
         * @param axis int
         */
        public SectionView(Element elem, int axis) {
            super(elem, axis);
        }

        /**
         * Gets amount of pages
         * @return Page count
         */
        public int GetPageCount() {
            return pageNumber;
        }

        /**
         * Perform layout on the box
         *
         * @param width the width (inside of the insets) >= 0
         * @param height the height (inside of the insets) >= 0
         */
        @Override
        public void layout(int width, int height) {
            width = pageWidth - 2 * DRAW_PAGE_INSET - pageMargins.left - pageMargins.right;
            this.setInsets( (short) (DRAW_PAGE_INSET + pageMargins.top), (short) (DRAW_PAGE_INSET + pageMargins.left), (short) (DRAW_PAGE_INSET + pageMargins.bottom),
                           (short) (DRAW_PAGE_INSET + pageMargins.right));
            super.layout(width, height);
        }

        /**
         * Determines the maximum span for this view along an
         * axis.
         *
         * overriddedn
         */
        @Override
        public float getMaximumSpan(int axis) {
            return getPreferredSpan(axis);
        }

        /**
         * Determines the minimum span for this view along an
         * axis.
         *
         * overriddedn
         */
        @Override
        public float getMinimumSpan(int axis) {
            return getPreferredSpan(axis);
        }

        /**
         * Determines the preferred span for this view along an
         * axis.
         * overriddedn
         */
        @Override
        public float getPreferredSpan(int axis) {
            float span;
            if (axis == View.X_AXIS) {
                span = pageWidth;
            }
            else {
                span = pageHeight * GetPageCount();
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
            validateHF();
            addHF();

            int n = offsets.length;
            pageNumber = 0;
            int headerHeight=0;//header!=null ? header.getHeight() +HF_SHIFT:0;
            int footerHeight=0;//footer!=null ? footer.getHeight() +HF_SHIFT:0;
            int totalOffset = headerHeight;
            for (int i = 0; i < n; i++) {
                offsets[i] = totalOffset;
                View v = getView(i);
                if (isPageBreak(v)) {
                    offsets[i] = pageNumber * pageHeight+headerHeight;
                    pageNumber++;
                }

                if (v instanceof UWMultiPageView) {
                    ( (UWMultiPageView) v).setBreakSpan(0);
                    ( (UWMultiPageView) v).setAdditionalSpace(0);
                }

                if ( (offsets[i] + spans[i]) > (pageNumber * pageHeight - DRAW_PAGE_INSET * 2 - pageMargins.top - pageMargins.bottom-footerHeight)) {
                    if ( (v instanceof UWMultiPageView) && (v.getViewCount() > 1)) {
                        UWMultiPageView multipageView = (UWMultiPageView) v;
                        int space = offsets[i] - (pageNumber - 1) * pageHeight;
                        int breakSpan = (pageNumber * pageHeight - DRAW_PAGE_INSET * 2 - pageMargins.top - pageMargins.bottom-footerHeight) - offsets[i];
                        multipageView.setBreakSpan(breakSpan);
                        multipageView.setPageOffset(space);
                        multipageView.setStartPageNumber(pageNumber);
                        multipageView.setEndPageNumber(pageNumber);
                        int height = (int) getHeight();

                        int width = ( (BoxView) v).getWidth();
                        if (v instanceof PageableParagraphView) {
                            PageableParagraphView parView = (PageableParagraphView) v;
                            parView.layout(width, height);
                        }

                        pageNumber = multipageView.getEndPageNumber();
                        spans[i] += multipageView.getAdditionalSpace();
                    }
                    else {
                        offsets[i] = pageNumber * pageHeight+headerHeight;
                        pageNumber++;
                    }
                }
                totalOffset = (int) Math.min( (long) offsets[i] + (long) spans[i], Integer.MAX_VALUE);
            }
        }

        protected boolean isPageBreak(View v) {
            AttributeSet attrs=v.getElement().getElement(v.getElement().getElementCount()-1).getAttributes();
            Boolean pb=(Boolean)attrs.getAttribute(PAGE_BREAK_ATTR_NAME);
            if (pb==null) {
                return false;
            }
            else {
                return pb;
            }
        }

        private void addHF() {
            //JTextComponent text = (JTextComponent) getContainer();
        }

        protected boolean isAdded(JComponent text, JComponent c) {
            for (int i=0; i<text.getComponentCount(); i++) {
                if (text.getComponent(i)==c) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public int viewToModel(float x, float y, Shape a, Position.Bias[] bias) {
            Point location=getClickedHFLocation(x,y);
            if (location!=null) {
                if (location.y % pageHeight < pageHeight / 2) {
                    //header
                }
                else {
                    //footer
                }
                return -1;
            }
            else {
                return super.viewToModel(x, y, a, bias);
            }
        }
        public Point getClickedHFLocation(float x, float y) {
            if (! (x >= DRAW_PAGE_INSET + pageMargins.left
                   && x <= pageWidth - DRAW_PAGE_INSET - pageMargins.right)) {
                return null;
            }
            int headerHeight=0;//getHeader().getHeight();
            int footerHeight=0;//getFooter().getHeight();
            int headerStartY=DRAW_PAGE_INSET + pageMargins.top;
            int footerStartY=pageHeight - DRAW_PAGE_INSET - pageMargins.bottom - footerHeight;
            int hfWidth=pageWidth-pageMargins.left-pageMargins.right-2*DRAW_PAGE_INSET;
            for (int i=0; i<GetPageCount(); i++) {
                if (y<headerStartY) {
                    return null;
                }
                if (y<headerStartY+headerHeight) {
                    return new Point(DRAW_PAGE_INSET + pageMargins.left,headerStartY);
                }
                if (y>footerStartY && y<footerStartY+footerHeight) {
                    return new Point(DRAW_PAGE_INSET + pageMargins.left,footerStartY);
                }
                headerStartY+=pageHeight;
                footerStartY+=pageHeight;
            }
            return null;
        }

        /**
         * Paints view content and page frames.
         * @param g Graphics
         * @param a Shape
         */
        public void paint(Graphics g, Shape a) {
            Rectangle alloc = (a instanceof Rectangle) ? (Rectangle) a : a.getBounds();
            Shape baseClip = g.getClip().getBounds();
            int pageCount = GetPageCount();
            Rectangle page = new Rectangle();
            page.x = alloc.x;
            page.y = alloc.y;
            page.height = pageHeight;
            page.width = pageWidth;
            String sC = Integer.toString(pageCount);
            for (int i = 0; i < pageCount; i++) {
                page.y = alloc.y + pageHeight * i;
                paintPageFrame(g, page, (Rectangle) baseClip);
                //paintHeader(g, i, page);
                //paintFooter(g, i, page);
                g.setColor(Color.blue);
                String sN = Integer.toString(i + 1);
                String pageStr = "Page: " + sN;
                pageStr += " of " + sC;
                g.drawString(pageStr,
                             page.x + page.width - 100,
                             page.y + page.height - 3);
            }
            super.paint(g, a);
            g.setColor(Color.gray);
            // Fills background of pages
            int currentWidth = (int) alloc.getWidth();
            int currentHeight = (int) alloc.getHeight();
            int x = page.x + DRAW_PAGE_INSET;
            int y = 0;
            int w = 0;
            int h = 0;
            if (pageWidth < currentWidth) {
                w = currentWidth;
                h = currentHeight;
                g.fillRect(page.x + page.width, alloc.y, w, h);
            }
            if (pageHeight * pageCount < currentHeight) {
                w = currentWidth;
                h = currentHeight;
                g.fillRect(page.x, alloc.y + page.height * pageCount, w, h);
            }
        }
        
        /**
         * Paints frame for specified page
         * @param g Graphics
         * @param page Shape page rectangle
         * @param container Rectangle
         */
        public void paintPageFrame(Graphics g, Shape page, Rectangle container) {
            Rectangle alloc = (page instanceof Rectangle) ? (Rectangle) page : page.getBounds();
            if (container.intersection(alloc).height <= 0)
                return;
            Color oldColor = g.getColor();

            //borders
            g.setColor(Color.gray);
            g.fillRect(alloc.x, alloc.y, alloc.width, DRAW_PAGE_INSET);
            g.fillRect(alloc.x, alloc.y, DRAW_PAGE_INSET, alloc.height);
            g.fillRect(alloc.x, alloc.y + alloc.height - DRAW_PAGE_INSET, alloc.width, DRAW_PAGE_INSET);
            g.fillRect(alloc.x + alloc.width - DRAW_PAGE_INSET, alloc.y, DRAW_PAGE_INSET, alloc.height);

            //frame
            g.setColor(Color.black);
            g.drawLine(alloc.x + DRAW_PAGE_INSET, alloc.y + DRAW_PAGE_INSET, alloc.x + alloc.width - DRAW_PAGE_INSET, alloc.y + DRAW_PAGE_INSET);
            g.drawLine(alloc.x + DRAW_PAGE_INSET, alloc.y + DRAW_PAGE_INSET, alloc.x + DRAW_PAGE_INSET, alloc.y + alloc.height - DRAW_PAGE_INSET);
            g.drawLine(alloc.x + DRAW_PAGE_INSET, alloc.y + alloc.height - DRAW_PAGE_INSET, alloc.x + alloc.width - DRAW_PAGE_INSET, alloc.y + alloc.height - DRAW_PAGE_INSET);
            g.drawLine(alloc.x + alloc.width - DRAW_PAGE_INSET, alloc.y + DRAW_PAGE_INSET, alloc.x + alloc.width - DRAW_PAGE_INSET, alloc.y + alloc.height - DRAW_PAGE_INSET);

            //shadow
            g.fillRect(alloc.x + alloc.width - DRAW_PAGE_INSET, alloc.y + DRAW_PAGE_INSET + 4, 4, alloc.height - 2 * DRAW_PAGE_INSET);
            g.fillRect(alloc.x + DRAW_PAGE_INSET + 4, alloc.y + alloc.height - DRAW_PAGE_INSET, alloc.width - 2 * DRAW_PAGE_INSET, 4);

            g.setColor(oldColor);
        }


    }

    /**
     * Represents multi page paragraph.
     *
     * @author Pham Ngoc Thanh
     * @version 1.0
     */
    class PageableParagraphView extends ParagraphView implements UWMultiPageView {

        protected int additionalSpace = 0;
        protected int breakSpan = 0;
        protected int pageOffset = 0;
        protected int startPageNumber = 0;
        protected int endPageNumber = 0;

        public PageableParagraphView(Element elem) {
            super(elem);
        }
        
        @Override
        public void layout(int width, int height) {
            super.layout(width, height);
        }

        @Override
        protected void layoutMajorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
            super.layoutMajorAxis(targetSpan, axis, offsets, spans);
            //performMultiPageLayout(targetSpan, axis, offsets, spans);
        }

        /**
         * Layout paragraph's content splitting between pages if needed.
         * Calculates shifts and breaks for parent view (SectionView)
         *
         * @param targetSpan int
         * @param axis int
         * @param offsets int[]
         * @param spans int[]
         */
        @Override
        public void performMultiPageLayout(int targetSpan, int axis, int[] offsets, int[] spans) {
            if (breakSpan == 0) {
                return;
            }

            int space = breakSpan;

            additionalSpace = 0;
            endPageNumber = startPageNumber;
            int topInset = this.getTopInset();
            int offs = 0;
            int headerHeight = 0;
            int footerHeight = 0;

            for (int i = 0; i < offsets.length; i++) {
                if (offs + spans[i] + topInset > space) {
                    int newOffset = endPageNumber * pageHeight;
                    int addSpace = newOffset - (startPageNumber - 1) * pageHeight - pageOffset - offs - topInset - headerHeight;
                    additionalSpace += addSpace;
                    offs += addSpace;
                    for (int j = i; j < offsets.length; j++) {
                        offsets[j] += addSpace;
                    }
                    endPageNumber++;
                    space = (endPageNumber * pageHeight - 2 * DRAW_PAGE_INSET - pageMargins.top - pageMargins.bottom) - (startPageNumber - 1) * pageHeight - pageOffset - footerHeight;
                }
                offs += spans[i];
            }
        }

        /**
         * Gets view's start page number
         *
         * @return Page number
         */
        @Override
        public int getStartPageNumber() {
            return startPageNumber;
        }

        /**
         * Gets view's end page number
         *
         * @return page number
         */
        @Override
        public int getEndPageNumber() {
            return endPageNumber;
        }

        /**
         * Gets view's extra space (space between pages)
         *
         * @return extra space
         */
        @Override
        public int getAdditionalSpace() {
            return additionalSpace;
        }

        /**
         * Gets view's break span
         *
         * @return break span
         */
        @Override
        public int getBreakSpan() {
            return breakSpan;
        }

        /**
         * Gets view's offsets on the page
         *
         * @return offset
         */
        @Override
        public int getPageOffset() {
            return pageOffset;
        }

        /**
         * Sets view's start page number
         *
         * @param startPageNumber page number
         */
        @Override
        public void setStartPageNumber(int startPageNumber) {
            this.startPageNumber = startPageNumber;
        }

        /**
         * Sets view's end page number
         *
         * @param endPageNumber page number
         */
        @Override
        public void setEndPageNumber(int endPageNumber) {
            this.endPageNumber = endPageNumber;
        }

        /**
         * Sets extra space (space between pages)
         *
         * @param additionalSpace additional space
         */
        @Override
        public void setAdditionalSpace(int additionalSpace) {
            this.additionalSpace = additionalSpace;
        }

        /**
         * Sets view's break span.
         *
         * @param breakSpan break span
         */
        @Override
        public void setBreakSpan(int breakSpan) {
            this.breakSpan = breakSpan;
        }

        /**
         * Sets view's offset on the page
         *
         * @param pageOffset offset
         */
        @Override
        public void setPageOffset(int pageOffset) {
            this.pageOffset = pageOffset;
        }

    }
}