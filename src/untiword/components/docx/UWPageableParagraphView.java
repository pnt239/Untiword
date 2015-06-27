/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.components.docx;

import java.awt.Insets;
import javax.swing.JEditorPane;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.ParagraphView;
import untiword.components.MyPageableEditorKit;
import untiword.components.UWMultiPageView;

/**
 *
 * @author NThanh
 */
public class UWPageableParagraphView extends ParagraphView implements UWMultiPageView {

    protected int additionalSpace = 0;
    protected int breakSpan = 0;
    protected int pageOffset = 0;
    protected int startPageNumber = 0;
    protected int endPageNumber = 0;

    private int pageHeight;
    private int pageWidth;
    private Insets pageMargins;
    private int pageGap;

    public UWPageableParagraphView(Element elem) {
        super(elem);
    }

    @Override
    public void layout(int width, int height) {
        super.layout(width, height);
    }

    @Override
    protected void layoutMajorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
        super.layoutMajorAxis(targetSpan, axis, offsets, spans);
        performMultiPageLayout(targetSpan, axis, offsets, spans);
    }

    /**
     * Layout paragraph's content splitting between pages if needed. Calculates
     * shifts and breaks for parent view (SectionView)
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
        DocxDocument doc = (DocxDocument) getDocument();
        pageHeight = doc.getPageHeight();
        pageWidth = doc.getPageWidth();
        pageMargins = doc.getPageMargins();
        pageGap = doc.getPageData().getPageGap();
        
        additionalSpace = 0;
        endPageNumber = startPageNumber;
        int topInset = this.getTopInset();
        int offs = 0;

        for (int i = 0; i < offsets.length; i++) {
            if (offs + spans[i] + topInset > space) {
                int newOffset = endPageNumber * pageHeight + endPageNumber * pageGap;
                int addSpace = newOffset - (startPageNumber - 1) * pageHeight - pageOffset - offs;
                additionalSpace += addSpace;
                offs += addSpace;
                for (int j = i; j < offsets.length; j++) {
                    offsets[j] += addSpace;
                }
                endPageNumber++;
                space = (endPageNumber * pageHeight - pageMargins.top - pageMargins.bottom + pageGap * (endPageNumber - 1)) - (startPageNumber - 1) * pageHeight - pageOffset;
            }
            offs += spans[i];
        }
    }

    /**
     * Gets view's start page number
     *
     * @return page number
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
