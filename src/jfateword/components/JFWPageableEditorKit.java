/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfateword.components;

import java.awt.Point;
import javafx.scene.shape.Shape;
import javax.swing.text.AbstractDocument;
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
public class JFWPageableEditorKit extends StyledEditorKit {
    
    PageableViewFactory factory = new PageableViewFactory();
    protected int pageWidth = 150;
    protected int pageHeight = 200;
    
    /**
     * Constructs kit instance
     */
    public JFWPageableEditorKit() {
    }
    
    @Override
    public ViewFactory getViewFactory() {
        return factory;
    }
    
    /**
     * The view factory class creates custom views for pagination
     * root view (SectionView class) and paragraph (PageableParagraphView class)
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
                }
                else if (kind.equals(AbstractDocument.ParagraphElementName)) {
                    return new PageableParagraphView(elem);
                }
                else if (kind.equals(AbstractDocument.SectionElementName)) {
                    return new SectionView(elem, View.Y_AXIS);
                }
                else if (kind.equals(StyleConstants.ComponentElementName)) {
                    return new ComponentView(elem);
                }
                else if (kind.equals(StyleConstants.IconElementName)) {
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
        
        public SectionView(Element elem, int axis) {
            super(elem, axis);
        }
        
        /* Override method */
        
        /**
         * Perform layout on the box
         *
         * @param width the width (inside of the insets) >= 0
         * @param height the height (inside of the insets) >= 0
         */
        @Override
        public void layout(int width, int height) {
            super.layout(width, height);
        }
        
        /**
         * Determines the maximum span for this view along an
         * axis.
         *
         */
        @Override
        public float getMaximumSpan(int axis) {
            return getPreferredSpan(axis);
        }
        
        /**
         * Determines the minimum span for this view along an
         * axis.
         *
         */
        @Override
        public float getMinimumSpan(int axis) {
            return getPreferredSpan(axis);
        }

        /**
         * Determines the preferred span for this view along an
         * axis.
         * 
         */
        @Override
        public float getPreferredSpan(int axis) {
            return super.getPreferredSpan(axis);
//            float span;
//            
//            if (axis == View.X_AXIS) {
//                span = pageWidth;
//            } else {
//                span = pageHeight * getPageCount();
//            }
//            
//            return span;
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
            /*
            //validate header and footer sizes if necessary
            //validateHF();
            //addHF();

            int n = offsets.length;
            pageNumber = 0;
            int headerHeight=header!=null ? header.getHeight() +HF_SHIFT:0;
            int footerHeight=footer!=null ? footer.getHeight() +HF_SHIFT:0;
            int totalOffset = headerHeight;
            
            for (int i = 0; i < n; i++) {
                offsets[i] = totalOffset;
                View v = getView(i);
                if (isPageBreak(v)) {
                    offsets[i] = pageNumber * pageHeight+headerHeight;
                    pageNumber++;
                }

                if (v instanceof MultiPageView) {
                    ( (MultiPageView) v).setBreakSpan(0);
                    ( (MultiPageView) v).setAdditionalSpace(0);
                }

                if ( (offsets[i] + spans[i]) > (pageNumber * pageHeight - DRAW_PAGE_INSET * 2 - pageMargins.top - pageMargins.bottom-footerHeight)) {
                    if ( (v instanceof MultiPageView) && (v.getViewCount() > 1)) {
                        MultiPageView multipageView = (MultiPageView) v;
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
            */
        }
        
        /* User method */
        /**
         * Gets amount of pages
         * @return int
         */
        public int getPageCount() {
            return pageNumber;
        }
    }
    
    /**
     * Represents multi page paragraph.
     *
     * @author Pham Ngoc Thanh
     * @version 1.0
     */
    class PageableParagraphView extends ParagraphView implements JFWMultiPageView {

        public PageableParagraphView(Element elem) {
            super(elem);
        }

        @Override
        public void performMultiPageLayout(int targetSpan, int axis, int[] offsets, int[] spans) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getStartPageNumber() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getEndPageNumber() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getAdditionalSpace() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getBreakSpan() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public int getPageOffset() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setStartPageNumber(int startPageNumber) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setEndPageNumber(int endPageNumber) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setAdditionalSpace(int additionalSpace) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setBreakSpan(int breakSpan) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void setPageOffset(int pageOffset) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
}
