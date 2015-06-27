package untiword.components;

/**
 * @author Stanislav Lapitsky
 * @version 1.0
 */
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.geom.AffineTransform;
import javax.swing.event.*;

public class MyPageableEditorKit extends StyledEditorKit {

    public static int MIN_PAGE_LEFT = 15;
    public static int MIN_PAGE_TOP = 15;
    public static int MIN_GAP_HEADER_PAGE = 10;

    PageableViewFactory factory = new PageableViewFactory();
    protected int pageWidth = 150;
    protected int pageHeight = 200;
    protected int pageTop = 15;
    protected int pageLeft = 15;
    protected int pageGap = 20; /* Gap between two pages */

    protected Insets pageMargins = new Insets(10, 10, 10, 10);

    public static int DRAW_PAGE_INSET = 15;
    public static String PAGE_BREAK_ATTR_NAME = "page_break_attribute";

    protected JEditorPane header;
    protected JEditorPane footer;
    protected int headerHeightDefault;
    protected int footerHeightDefault;

    protected boolean isValidHF;
    public static int HF_SHIFT = 3;
    boolean isPageBreakInsertion = false;

    DocumentListener relayoutListener = new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            relayout();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            relayout();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            relayout();
        }

        protected void relayout() {
            SwingUtilities.invokeLater(() -> {
                JEditorPane parent = null;
                int lastFooterHeight = 0;
                if (footer != null && footer.getParent() != null && footer.getParent() instanceof JEditorPane) {
                    parent = (JEditorPane) footer.getParent();
                    lastFooterHeight = footer.getHeight();
                } else if (header != null && header.getParent() != null && header.getParent() instanceof JEditorPane) {
                    parent = (JEditorPane) header.getParent();
                }
                isValidHF = false;
                validateHF();
                if (footer != null && lastFooterHeight != footer.getHeight()) {
                    int shift = lastFooterHeight - footer.getHeight();
                    footer.setLocation(footer.getX(), footer.getY() + shift);
                }
                ((SectionView) parent.getUI().getRootView(parent).getView(0)).layout(0, Short.MAX_VALUE);
                parent.repaint();
            });
        }
    };

    /**
     * Constructs kit instance
     */
    public MyPageableEditorKit() {
    }

    public JFrame init() {
        JFrame frame = new JFrame("Pagination");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JEditorPane editor = new JEditorPane();
        editor.setEditorKit(this);
//        editor.addCaretListener(new CaretListener() {
//            public void caretUpdate(CaretEvent e) {
//                if (!isPageBreakInsertion ) {
//                    ( (StyledEditorKit) editor.getEditorKit()).getInputAttributes().removeAttribute(PAGE_BREAK_ATTR_NAME);
//                }
//            }
//        });
        editor.setMargin(new Insets(0, 0, 0, 0));
        this.setHeader(createHeader());
        this.setFooter(createFooter());
        PageFormat pf = new PageFormat();
        pf.setPaper(new Paper());
        final MyPaginationPrinter pp = new MyPaginationPrinter(pf, editor);
        JScrollPane scroll = new JScrollPane(editor);
        frame.getContentPane().add(scroll);
        JToolBar tb = new JToolBar();
        JButton bPrint = new JButton("Print to default printer");
        bPrint.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                print(editor, pp);
            }
        });
        JButton bInsertPageBreak = new JButton("Insert page break");
        bInsertPageBreak.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                insertPageBreak(editor);
            }
        });
        tb.add(bPrint);
        tb.add(bInsertPageBreak);
        frame.getContentPane().add(tb, BorderLayout.NORTH);
        frame.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());

        return frame;
    }

    /**
     * Prints pages content
     *
     * @param editor JEditorPane pane with content.
     * @param pp PaginationPrinter Printable implementation.
     */
    protected void print(JEditorPane editor, MyPaginationPrinter pp) {
        PrinterJob pJob = PrinterJob.getPrinterJob();
        //by default paper is letter
        pJob.setPageable(pp);
        try {
            pJob.print();
        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MyPageableEditorKit().init().setVisible(true);
    }

    /**
     * gets page width
     *
     * @return Width in pixels
     */
    public int getPageWidth() {
        return pageWidth;
    }

    /**
     * Sets page width
     *
     * @param width Width value in pixels
     */
    public void setPageWidth(int width) {
        pageWidth = width;
        isValidHF = false;
    }

    /**
     * Gets page height
     *
     * @return Height in pixels
     */
    public int getPageHeight() {
        return pageHeight;
    }

    /**
     * Sets page height
     *
     * @param height Height in pixels
     */
    public void setPageHeight(int height) {
        pageHeight = height;
    }

    /**
     * Gets page top
     *
     * @return Page top
     */
    public int getPageTop() {
        return pageTop;
    }

    /**
     * Sets page top
     *
     * @param top Page top
     */
    public void setPageTop(int top) {
        pageTop = top;
    }

    /**
     * Gets page left
     *
     * @return Page left
     */
    public int getPageLeft() {
        return pageLeft;
    }

    /**
     * Sets page left
     *
     * @param left Page left
     */
    public void setPageLeft(int left) {
        pageLeft = left;
    }

    /**
     * Gets gap between two pages
     *
     * @return Page gap
     */
    public int getPageGap() {
        return pageGap;
    }

    /**
     * Sets gap between two pages
     *
     * @param gap Page gap
     */
    public void setPageGap(int gap) {
        pageGap = gap;
    }

    /**
     * Sets page margins (distance between page content and page edge.
     *
     * @param margins Insets margins.
     */
    public void setPageMargins(Insets margins) {
        pageMargins = margins;
        isValidHF = false;
    }

    public void setHeader(JEditorPane header) {
        this.header = header;
        headerHeightDefault = header.getPreferredSize().height;
        header.getDocument().addDocumentListener(relayoutListener);
        isValidHF = false;
    }

    public void setFooter(JEditorPane footer) {
        this.footer = footer;
        footerHeightDefault = footer.getPreferredSize().height;
        footer.getDocument().addDocumentListener(relayoutListener);
        isValidHF = false;
    }

    public JEditorPane getHeader() {
        return header;
    }

    public JEditorPane getFooter() {
        return footer;
    }

    protected void calculateHFSizes() {
        int hfWidth = pageWidth - pageMargins.left - pageMargins.right;
        int maxHeight = (pageHeight - pageMargins.top - pageMargins.bottom) / 2;

        if (header != null) {
            header.setSize(hfWidth, pageHeight);
            int hHeight = Math.min(maxHeight, header.getPreferredSize().height);
            header.setSize(hfWidth, hHeight);
        }

        if (footer != null) {
            footer.setSize(hfWidth, pageHeight);
            int fHeight = Math.min(maxHeight, footer.getPreferredSize().height);
            footer.setSize(hfWidth, fHeight);
        }
    }

    public void validateHF() {
        if (!isValidHF) {
            calculateHFSizes();

            isValidHF = true;
        }
    }

    public JEditorPane createHeader() {
        JEditorPane header = new JEditorPane();
        header.setEditorKit(new StyledEditorKit());
        try {
            header.getDocument().insertString(0, "header", new SimpleAttributeSet());
        } catch (BadLocationException ex) {
            //can't happen
        }

        return header;
    }

    public JEditorPane createFooter() {
        JEditorPane footer = new JEditorPane();
        footer.setEditorKit(new StyledEditorKit());
        try {
            footer.getDocument().insertString(0, "footer", new SimpleAttributeSet());
        } catch (BadLocationException ex) {
            //can't happen
        }

        return footer;
    }

    /**
     * Gets kit view factory.
     *
     * @return ViewFactory
     */
    @Override
    public ViewFactory getViewFactory() {
        return factory;
    }

    /**
     * Inserts page break element in current caret location
     *
     * @param editor
     */
    public void insertPageBreak(final JEditorPane editor) {
        int caretPos = editor.getCaretPosition();
        final SimpleAttributeSet breakAttrs = new SimpleAttributeSet();
        breakAttrs.addAttribute(PAGE_BREAK_ATTR_NAME, Boolean.TRUE);
        DefaultStyledDocument doc = (DefaultStyledDocument) editor.getDocument();
        try {
            isPageBreakInsertion = true;
            char ch = doc.getText(caretPos, 1).charAt(0);
            if (ch != '\n') {
                doc.insertString(caretPos, "\n", breakAttrs);
                caretPos++;
            } else {
                Element par = doc.getParagraphElement(caretPos);
                caretPos = par.getEndOffset() - 1;
            }
            final int pos = caretPos;
            ((StyledDocument) editor.getDocument()).setCharacterAttributes(pos, 1, breakAttrs, false);
            isPageBreakInsertion = false;
        } catch (BadLocationException ex) {
            //do nothing
        }
    }

    /**
     * The view factory class creates custom views for pagination root view
     * (SectionView class) and paragraph (PageableParagraphView class)
     *
     * @author Stanislav Lapitsky
     * @version 1.0
     */
    class PageableViewFactory implements ViewFactory {

        /**
         * Creates view for specified element.
         *
         * @param elem Element parent element
         * @return View created view instance.
         */
        @Override
        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null) {
                switch (kind) {
                    case AbstractDocument.ContentElementName:
                        return new LabelView(elem);
                    case AbstractDocument.ParagraphElementName:
                        //return new ParagraphView(elem);
                        return new PageableParagraphView(elem);
                    case AbstractDocument.SectionElementName:
                        return new SectionView(elem, View.Y_AXIS);
                    case StyleConstants.ComponentElementName:
                        return new ComponentView(elem);
                    case StyleConstants.IconElementName:
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
     * @author Stanislav Lapitsky
     * @version 1.0
     */
    class SectionView extends BoxView {

        int pageNumber = 0;

        /**
         * Creates view instace
         *
         * @param elem Element
         * @param axis int
         */
        public SectionView(Element elem, int axis) {
            super(elem, axis);
        }

        /**
         * Gets amount of pages
         *
         * @return int
         */
        public int getPageCount() {
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
            width = pageWidth - pageMargins.left - pageMargins.right;
            this.setInsets((short) (pageTop + pageMargins.top),
                    (short) (pageLeft + pageMargins.left),
                    (short) (pageTop + pageMargins.bottom),
                    (short) (pageLeft + pageMargins.right));
            super.layout(width, height);
        }

        /**
         * Determines the maximum span for this view along an axis.
         *
         * overriddedn
         */
        @Override
        public float getMaximumSpan(int axis) {
            return getPreferredSpan(axis);
        }

        /**
         * Determines the minimum span for this view along an axis.
         *
         * overriddedn
         */
        @Override
        public float getMinimumSpan(int axis) {
            return getPreferredSpan(axis);
        }

        /**
         * Determines the preferred span for this view along an axis.
         * overriddedn
         */
        @Override
        public float getPreferredSpan(int axis) {
            float span;
            if (axis == View.X_AXIS) {
                span = 2 * MIN_PAGE_LEFT + pageWidth;
            } else {
                span = pageHeight * getPageCount() + 2 * MIN_PAGE_TOP + pageGap * (getPageCount() - 1);
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
            pageNumber = 1;
            int headerHeight = header != null ? header.getHeight() + HF_SHIFT : 0;
            int footerHeight = footer != null ? footer.getHeight() + HF_SHIFT : 0;

            headerHeight -= headerHeightDefault + MIN_GAP_HEADER_PAGE;
            int gap = headerHeight < 0 ? 0 : MIN_GAP_HEADER_PAGE;
            int totalOffset = Math.max(0, headerHeight + gap);

            for (int i = 0; i < n; i++) {
                offsets[i] = totalOffset;
                View v = getView(i);
                if (isPageBreak(v)) {
                    offsets[i] = pageNumber * pageHeight;
                    pageNumber++;
                }

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

                        if (v instanceof PageableParagraphView) {
                            PageableParagraphView parView = (PageableParagraphView) v;
                            parView.layout(width, height);
                        }

                        pageNumber = multipageView.getEndPageNumber();
                        spans[i] += multipageView.getAdditionalSpace();
                    } else {
                        //offsets[i] = pageTop + pageNumber * pageHeight + pageGap * (pageNumber - 1);
                        offsets[i] = (pageHeight + pageGap) * pageNumber;
                        pageNumber++;
                    }
                }
                totalOffset = (int) Math.min((long) offsets[i] + (long) spans[i], Integer.MAX_VALUE);
            }
        }

        protected boolean isPageBreak(View v) {
            AttributeSet attrs = v.getElement().getElement(v.getElement().getElementCount() - 1).getAttributes();
            Boolean pb = (Boolean) attrs.getAttribute(PAGE_BREAK_ATTR_NAME);
            if (pb == null) {
                return false;
            } else {
                return pb;
            }
        }

        private void addHF() {
            JTextComponent text = (JTextComponent) getContainer();
            if (text != null) {
                if (!isAdded(text, header)) {
                    text.add(header);
                    header.setLocation(pageLeft + pageMargins.left,
                            pageTop + pageMargins.top - header.getHeight() - MIN_GAP_HEADER_PAGE);
                }
                if (!isAdded(text, footer)) {
                    footer.setLocation(pageLeft + pageMargins.left,
                            pageTop + (pageHeight - pageMargins.bottom) + MIN_GAP_HEADER_PAGE);
                    text.add(footer);
                }
            }
        }

        protected boolean isAdded(JComponent text, JComponent c) {
            for (int i = 0; i < text.getComponentCount(); i++) {
                if (text.getComponent(i) == c) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int viewToModel(float x, float y, Shape a, Position.Bias[] bias) {
            Point location = getClickedHFLocation(x, y);
            if (location != null) {
                if (location.y % pageHeight < pageHeight / 2) {
                    //header
                    header.setLocation(location);
                    SwingUtilities.invokeLater(() -> {
                        header.requestFocus();
                    });
                } else {
                    //footer
                    footer.setLocation(location);
                    SwingUtilities.invokeLater(() -> {
                        footer.requestFocus();
                    });
                }
                return -1;
            } else {
                return super.viewToModel(x, y, a, bias);
            }
        }

        public Point getClickedHFLocation(float x, float y) {
            if (!(x >= pageLeft + pageMargins.left
                    && x <= pageLeft + pageWidth - pageMargins.right)) {
                return null;
            }
            int headerHeight = getHeader().getHeight();
            int footerHeight = getFooter().getHeight();
            int headerStartY = pageTop + pageMargins.top - headerHeightDefault - MIN_GAP_HEADER_PAGE;
            int footerStartY = pageTop + (pageHeight - pageMargins.bottom) + MIN_GAP_HEADER_PAGE;

            for (int i = 0; i < getPageCount(); i++) {
                if (y < headerStartY) {
                    return null;
                }
                // Catch header
                if (y < headerStartY + headerHeight) {
                    return new Point(DRAW_PAGE_INSET + pageMargins.left, headerStartY);
                }

                // Catch footer
                if (y > footerStartY && y < footerStartY + footerHeight) {
                    return new Point(DRAW_PAGE_INSET + pageMargins.left, footerStartY);
                }

                headerStartY += pageHeight + pageGap;
                footerStartY += pageHeight + pageGap;
            }
            return null;
        }

        /**
         * Paints view content and page frames.
         *
         * @param g Graphics
         * @param a Shape
         */
        @Override
        public void paint(Graphics g, Shape a) {
            Rectangle alloc = (a instanceof Rectangle) ? (Rectangle) a : a.getBounds();
            //Shape baseClip = g.getClip().getBounds();

            /* Draw background */
            paintBackground(g, a);

            int pageCount = getPageCount();
            Rectangle page = new Rectangle();
            page.x = alloc.x + pageLeft;
            page.y = alloc.y + pageTop;
            page.height = pageHeight;
            page.width = pageWidth;
            String sC = Integer.toString(pageCount);

            for (int i = 0; i < pageCount; i++) {
                paintPageFrame(g, page);
                paintHeader(g, i, page);
                paintFooter(g, i, page);

                //g.setColor(Color.blue);
                //String sN = Integer.toString(i + 1);
                //String pageStr = "Page: " + sN;
                //pageStr += " of " + sC;
                //g.drawString(pageStr,
                //        page.x + page.width - 100,
                //        page.y + page.height - 3);
                page.y += pageGap + pageHeight;
            }
            super.paint(g, a); /* Draw text */

            g.setColor(Color.gray);

        }

        /**
         * Fills background of pages
         *
         * @param g Graphics
         * @param a Shape
         */
        protected void paintBackground(Graphics g, Shape a) {
            Rectangle alloc = (a instanceof Rectangle) ? (Rectangle) a : a.getBounds();

            g.setColor(new Color(0xEE, 0xEE, 0xEE));
            g.fillRect(alloc.x, alloc.y, alloc.width, alloc.height);
        }

        protected void paintHeader(Graphics g, int pageIndex, Rectangle page) {
            Graphics2D g2d = (Graphics2D) g;
            if (header != null) {
                AffineTransform old = g2d.getTransform();
                g2d.translate(page.x + pageMargins.left,
                        page.y + pageMargins.top - headerHeightDefault - MIN_GAP_HEADER_PAGE);
                boolean isCaretVisible = header.getCaret().isVisible();
                boolean isCaretSelectionVisible = header.getCaret().isSelectionVisible();
                header.getCaret().setVisible(false);
                header.getCaret().setSelectionVisible(false);
                header.paint(g2d);
                header.getCaret().setVisible(isCaretVisible);
                header.getCaret().setSelectionVisible(isCaretSelectionVisible);
                g2d.setColor(Color.lightGray);
                g2d.draw(new Rectangle(-1, -1, header.getWidth() + 1, header.getHeight() + 1));
                g2d.setTransform(old);
            }
        }

        protected void paintFooter(Graphics g, int pageIndex, Rectangle page) {
            Graphics2D g2d = (Graphics2D) g;
            if (footer != null) {
                AffineTransform old = g2d.getTransform();
                g2d.translate(page.x + pageMargins.left,
                        page.y + (pageHeight - pageMargins.bottom) + MIN_GAP_HEADER_PAGE);
                boolean isCaretVisible = footer.getCaret().isVisible();
                boolean isCaretSelectionVisible = footer.getCaret().isSelectionVisible();
                footer.getCaret().setVisible(false);
                footer.getCaret().setSelectionVisible(false);
                footer.paint(g2d);
                footer.getCaret().setVisible(isCaretVisible);
                footer.getCaret().setSelectionVisible(isCaretSelectionVisible);
                g2d.setColor(Color.lightGray);
                g2d.draw(new Rectangle(-1, -1, footer.getWidth() + 1, footer.getHeight() + 1));
                g2d.setTransform(old);
            }
        }

        /**
         * Paints frame for specified page
         *
         * @param g Graphics
         * @param page Shape page rectangle
         * @param container Rectangle
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

    /**
     * Represents multipage paragraph.
     *
     * @author Stanislav Lapitsky
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
            performMultiPageLayout(targetSpan, axis, offsets, spans);
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
            int headerHeight = getHeaderHeight();
            int footerHeight = getFooterHeight();

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

        protected int getHeaderHeight() {
            JTextComponent text = (JTextComponent) getContainer();
            if (text != null && text instanceof JEditorPane && ((JEditorPane) text).getEditorKit() instanceof MyPageableEditorKit) {
                MyPageableEditorKit kit = (MyPageableEditorKit) ((JEditorPane) text).getEditorKit();
                if (kit.getHeader() != null) {
                    return kit.getHeader().getHeight();
                }
            }
            return 0;
        }

        protected int getFooterHeight() {
            JTextComponent text = (JTextComponent) getContainer();
            if (text != null && ((JEditorPane) text).getEditorKit() instanceof MyPageableEditorKit) {
                MyPageableEditorKit kit = (MyPageableEditorKit) ((JEditorPane) text).getEditorKit();
                if (kit.getFooter() != null) {
                    return kit.getFooter().getHeight();
                }
            }
            return 0;
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
}
