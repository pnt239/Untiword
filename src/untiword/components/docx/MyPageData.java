/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.components.docx;

/**
 *
 * @author NThanh
 */
public class MyPageData {
    
    public static int MIN_PAGE_LEFT = 15;
    public static int MIN_PAGE_TOP = 15;
    public static int MIN_GAP_HF_PAGE = 10; /* HF stand for Header and Footer */
    
    private int pageLeft = 15;
    private int pageTop = 15;
    private int pageGap = 20;
    private int pageNumber = 1;

    /**
     * @return the pageLeft
     */
    public int getPageLeft() {
        return pageLeft;
    }

    /**
     * @param pageLeft the pageLeft to set
     */
    public void setPageLeft(int pageLeft) {
        this.pageLeft = pageLeft;
    }

    /**
     * @return the pageTop
     */
    public int getPageTop() {
        return pageTop;
    }

    /**
     * @param pageTop the pageTop to set
     */
    public void setPageTop(int pageTop) {
        this.pageTop = pageTop;
    }

    /**
     * @return the pageGap
     */
    public int getPageGap() {
        return pageGap;
    }

    /**
     * @param pageGap the pageGap to set
     */
    public void setPageGap(int pageGap) {
        this.pageGap = pageGap;
    }

    /**
     * @return the pageNumber
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * @param pageNumber the pageNumber to set
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    
}
