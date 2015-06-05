/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jfateword.components;

import javax.swing.text.BoxView;
import javax.swing.text.Element;
import javax.swing.text.ParagraphView;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

/**
 *
 * @author NThanh
 */
public class JFWPageableEditorKit extends StyledEditorKit {
    
    PageableViewFactory factory = new PageableViewFactory();
    
    /**
     * Constructs kit instance
     */
    public JFWPageableEditorKit() {
    }
    
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
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
    
    /**
     * Root view which perform pagination and paints frame around pages.
     *
     * @author Pham Ngoc Thanh
     * @version 1.0
     */
    class SectionView extends BoxView {

        public SectionView(Element elem, int axis) {
            super(elem, axis);
        }
        //
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
