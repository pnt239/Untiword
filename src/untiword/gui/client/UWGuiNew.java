/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.gui.client;

import Controller.AccountController.AccountController;
import Model.Account.FacebookUser;
import View.Account.FBLoginJFrame;
import View.Account.FBLoginJFrameEventListener;
import View.Editor;
import com.alee.extended.breadcrumb.WebBreadcrumb;
import com.alee.extended.breadcrumb.WebBreadcrumbButton;
import com.alee.extended.breadcrumb.WebBreadcrumbToggleButton;
import com.alee.extended.button.WebSplitButton;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.extended.tab.DocumentData;
import com.alee.extended.tab.WebDocumentPane;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.WebCheckBoxMenuItem;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.hotkey.Hotkey;
import com.alee.utils.SwingUtils;
import java.awt.BorderLayout;
import static java.awt.BorderLayout.SOUTH;
import java.awt.CardLayout;
import java.awt.CheckboxMenuItem;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import untiword.components.UWEditor;

/**
 *
 * @author NThanh
 */
public class UWGuiNew extends javax.swing.JFrame {

    private FacebookUser _fBUser;
    private AccountController _accountController;

    private WebPanel bottomPane;
    private WebBreadcrumb breadcrumb;
    private WebBreadcrumbToggleButton loginBreadcrumb;
    private WebBreadcrumbToggleButton openBreadcrumb;
    private WebBreadcrumbToggleButton editBreadcrumb;
    private JPanel centerPane;
    private JPanel loginPane;
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private JPanel loginFormPan;
    private WebTextField serverAddr;
    private WebTextField serverPort;
    private JPanel openPane;
    private JPanel editPane;
    private JPanel editorTabPan;
    private WebMenuBar editorMenuBar;
    private WebMenu fileMenu;
    private WebMenu editMenu;
    private WebMenu insertMenu;
    private WebMenu viewMenu;
    private WebMenu formatMenu;
    private WebMenu toolMenu;
    private WebMenu tableMenu;
    private WebMenu helpMenu;
    private WebMenu modeSubMenu;
    private WebMenuItem shareMenuItem;
    private WebMenuItem newMenuItem;
    private WebMenuItem openMenuItem;
    private WebMenuItem renameMenuItem;
    private WebMenuItem saveasMenuItem;
    private WebMenuItem removeMenuItem;
    private WebMenuItem historyMenuItem;
    private WebMenuItem downloadMenuItem;
    private WebMenuItem pageSetupMenuItem;
    private WebMenuItem printPreviewMenuItem;
    private WebMenuItem printMenuItem;
    private WebMenuItem undoMenuItem;
    private WebMenuItem redoMenuItem;
    private WebMenuItem cutMenuItem;
    private WebMenuItem copyMenuItem;
    private WebMenuItem pasteMenuItem;
    private WebMenuItem selectMenuItem;
    private WebMenuItem findMenuItem;
    private WebCheckBoxMenuItem showRulerMenuItem;
    private WebMenuItem imageInsMenuItem;
    private WebMenuItem linkInsMenuItem;
    private WebMenuItem equationInsMenuItem;
    private WebMenuItem tableInsMenuItem;
    private WebMenuItem commentInsMenuItem;
    private WebMenuItem footnoteInsMenuItem;
    private WebMenuItem symbolInsMenuItem;
    private WebMenuItem lineInsMenuItem;
    private WebMenuItem pageNumInsMenuItem;
    private WebMenuItem pageBreakInsMenuItem;
    private WebMenuItem headerInsMenuItem;
    private WebMenuItem footerInsMenuItem;
    private WebMenuItem contentHelpMenuItem;
    private WebMenuItem aboutHelpMenuItem;
    private WebToolBar editorToolBar;
    private WebButton printTbButton;
    private WebButton undoTbButton;
    private WebButton redoTbButton;
    private WebComboBox zoomTbComboBox;
    private WebComboBox styleTbComboBox;
    private WebComboBox fontTbComboBox;
    private WebComboBox fotnSizeTbComboBox;
    private WebToggleButton boldTbButton;
    private WebToggleButton italicTbButton;
    private WebToggleButton underlineTbButton;
    private WebButtonGroup fontStyleTbGroup;
    private WebButton colorTbButton;
    private WebButton linkTbButton;
    private WebButton commentTbButton;
    private WebToggleButton leftTbButton;
    private WebToggleButton centerTbButton;
    private WebToggleButton rightTbButton;
    private WebToggleButton justifyTbButton;
    private WebButtonGroup alignTbGroup;
    private WebComboBox lineSpaceTbButton;
    private WebSplitButton numberTbButton;
    private WebSplitButton bulletTbButton;
    private WebButton decIndTbButton;
    private WebButton incIndTbButton;
    private WebButton clearTbButton;
    private WebDocumentPane docmentPane;

    private String Server;
    private String Po;
    private Editor mainEditor;
    private boolean isConnect = false;
    private ActionListener breadcrumbAction;

    public UWGuiNew() {
        initComponents();
    }

    private void initComponents() {
        // Init variable
        bottomPane = new WebPanel();
        breadcrumb = new WebBreadcrumb(true);
        loginBreadcrumb = new WebBreadcrumbToggleButton("Login");
        openBreadcrumb = new WebBreadcrumbToggleButton("Open and New");
        editBreadcrumb = new WebBreadcrumbToggleButton("Editor");
        centerPane = new JPanel();
        loginPane = new JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        loginFormPan = new JPanel();
        serverAddr = new WebTextField(15);
        serverPort = new WebTextField(15);
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        openPane = new JPanel();
        editPane = new JPanel();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));

        // Create control
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        // Bottom pane
        bottomPane.setLayout(new javax.swing.BoxLayout(bottomPane, javax.swing.BoxLayout.LINE_AXIS));
        bottomPane.setPaintSides(true, false, false, false);
        bottomPane.setUndecorated(false);
        bottomPane.setMargin(new Insets(3, 3, 3, 3));

        ActionListener getDocActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isConnect) {
                    mainEditor.sendMessage(mainEditor.createControlMessage("getdoclist", 0, ""));
                    System.out.println(e.getActionCommand());
                }
            }
        };

        breadcrumbAction = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) (centerPane.getLayout());
                WebBreadcrumbToggleButton button = (WebBreadcrumbToggleButton) e.getSource();
                cl.show(centerPane, button.getName());
            }
        };

        loginBreadcrumb.setSelected(true);
        loginBreadcrumb.setName("LoginCard");
        loginBreadcrumb.addActionListener(breadcrumbAction);

        openBreadcrumb.setName("OpenCard");
        openBreadcrumb.addActionListener(breadcrumbAction);

        openBreadcrumb.addActionListener(getDocActionListener);

        editBreadcrumb.setName("EditCard");
        editBreadcrumb.addActionListener(breadcrumbAction);

        breadcrumb.add(loginBreadcrumb);
        breadcrumb.add(openBreadcrumb);
        breadcrumb.add(editBreadcrumb);
        SwingUtils.groupButtons(breadcrumb);

        GroupPanel steps = new GroupPanel(3, false, breadcrumb);
        bottomPane.add(steps, BorderLayout.LINE_START);

        getContentPane().add(bottomPane, java.awt.BorderLayout.SOUTH);

        // Center pane
        centerPane.setLayout(new java.awt.CardLayout());

        loginPane.setLayout(new javax.swing.BoxLayout(loginPane, javax.swing.BoxLayout.Y_AXIS));
        loginPane.add(filler1);

        loginFormPan.setLayout(null);
        loginFormPan.setMaximumSize(new java.awt.Dimension(400, 155));
        loginFormPan.setMinimumSize(new java.awt.Dimension(400, 155));
        loginFormPan.setPreferredSize(new java.awt.Dimension(400, 155));
        //loginFormPan.setBackground(Color.red);

        WebLabel label = new WebLabel("Connect to Server");
        label.setBounds(50, 10, 300, 25);

        serverAddr.setInputPrompt("Server Address:");
        serverAddr.setHideInputPromptOnFocus(false);
        serverAddr.setInputPromptFont(serverAddr.getFont().deriveFont(Font.ITALIC));
        serverAddr.setBounds(50, 45, 300, 25);

        serverPort.setInputPrompt("Port:");
        serverPort.setHideInputPromptOnFocus(false);
        serverPort.setBounds(50, 80, 300, 25);

        WebButton loginFBbtn = new WebButton("Login with Facebook");
        loginFBbtn.setBounds(100, 115, 200, 30);

        loginFBbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                FBLoginJFrame fBLoginJFrame;
                try {
                    fBLoginJFrame = new FBLoginJFrame();
                    fBLoginJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    fBLoginJFrame.setVisible(true);
                    setFBLoginJFrameEventListener(fBLoginJFrame, loginFBbtn);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(UWGui.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //WebButton button = new WebButton("Connect");

        loginFormPan.add(label);
        loginFormPan.add(serverAddr);
        loginFormPan.add(serverPort);
        loginFormPan.add(loginFBbtn);

        loginPane.add(loginFormPan);
        loginPane.add(filler2);

        centerPane.add(loginPane, "LoginCard");

        /* Create GUI for open here */
        openPane.setLayout(new javax.swing.BoxLayout(openPane, javax.swing.BoxLayout.Y_AXIS));
        centerPane.add(openPane, "OpenCard");

        /* Create GUI for edit here */
        addEditor(editPane);
        centerPane.add(editPane, "EditCard");

        getContentPane().add(centerPane, java.awt.BorderLayout.CENTER);

        // Pack
        this.pack();
        this.setSize(800, 600);
    }

    private void addEditor(JPanel panel) {
        panel.setLayout(new BorderLayout());

        editorTabPan = new JPanel();
        editorTabPan.setLayout(new BorderLayout());

        editorMenuBar = new WebMenuBar();

        fileMenu = new WebMenu("File");

        shareMenuItem = new WebMenuItem("Share...");
        fileMenu.add(shareMenuItem);
        fileMenu.addSeparator();

        newMenuItem = new WebMenuItem("New");
        fileMenu.add(newMenuItem);

        openMenuItem = new WebMenuItem("Open");
        fileMenu.add(openMenuItem);

        renameMenuItem = new WebMenuItem("Rename");
        fileMenu.add(renameMenuItem);

        saveasMenuItem = new WebMenuItem("Make a copy ...");
        fileMenu.add(saveasMenuItem);

        removeMenuItem = new WebMenuItem("Move to trash");
        fileMenu.add(removeMenuItem);
        fileMenu.addSeparator();

        historyMenuItem = new WebMenuItem("See revision history");
        fileMenu.add(historyMenuItem);
        fileMenu.addSeparator();

        downloadMenuItem = new WebMenuItem("Download as");
        fileMenu.add(downloadMenuItem);
        fileMenu.addSeparator();

        pageSetupMenuItem = new WebMenuItem("Page setup...");
        fileMenu.add(pageSetupMenuItem);

        printPreviewMenuItem = new WebMenuItem("Print preview");
        fileMenu.add(printPreviewMenuItem);

        printMenuItem = new WebMenuItem("Print");
        fileMenu.add(printMenuItem);

        editorMenuBar.add(fileMenu);

        editMenu = new WebMenu("Edit");

        undoMenuItem = new WebMenuItem("Undo");
        editMenu.add(undoMenuItem);

        redoMenuItem = new WebMenuItem("Redo");
        editMenu.add(redoMenuItem);
        editMenu.addSeparator();

        cutMenuItem = new WebMenuItem("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem = new WebMenuItem("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem = new WebMenuItem("Paste");
        editMenu.add(pasteMenuItem);
        editMenu.addSeparator();

        selectMenuItem = new WebMenuItem("Select all");
        editMenu.add(selectMenuItem);
        editMenu.addSeparator();

        findMenuItem = new WebMenuItem("Find and replace...");
        editMenu.add(findMenuItem);

        editorMenuBar.add(editMenu);

        viewMenu = new WebMenu("View");

        modeSubMenu = new WebMenu("Mode");
        modeSubMenu.addSeparator();

        viewMenu.add(modeSubMenu);
        viewMenu.addSeparator();

        showRulerMenuItem = new WebCheckBoxMenuItem("Show ruler");
        showRulerMenuItem.setSelected(true);
        viewMenu.add(showRulerMenuItem);

        editorMenuBar.add(viewMenu);

        insertMenu = new WebMenu("Insert");

        imageInsMenuItem = new WebMenuItem("Image...");
        insertMenu.add(imageInsMenuItem);

        linkInsMenuItem = new WebMenuItem("Link...");
        insertMenu.add(linkInsMenuItem);

        equationInsMenuItem = new WebMenuItem("Equation...");
        insertMenu.add(equationInsMenuItem);

        tableInsMenuItem = new WebMenuItem("Table...");
        insertMenu.add(tableInsMenuItem);
        insertMenu.addSeparator();

        commentInsMenuItem = new WebMenuItem("Comment");
        insertMenu.add(commentInsMenuItem);

        footnoteInsMenuItem = new WebMenuItem("Footnote");
        insertMenu.add(footnoteInsMenuItem);
        insertMenu.addSeparator();

        symbolInsMenuItem = new WebMenuItem("Special characters...");
        symbolInsMenuItem.setToolTipText("");
        insertMenu.add(symbolInsMenuItem);

        lineInsMenuItem = new WebMenuItem("Horizontal line");
        insertMenu.add(lineInsMenuItem);
        insertMenu.addSeparator();

        pageNumInsMenuItem = new WebMenuItem("Page number");
        insertMenu.add(pageNumInsMenuItem);
        insertMenu.addSeparator();

        pageBreakInsMenuItem = new WebMenuItem("Page break");
        insertMenu.add(pageBreakInsMenuItem);

        headerInsMenuItem = new WebMenuItem("Header");
        insertMenu.add(headerInsMenuItem);

        footerInsMenuItem = new WebMenuItem("Footer");
        insertMenu.add(footerInsMenuItem);

        editorMenuBar.add(insertMenu);

        formatMenu = new WebMenu("Format");
        editorMenuBar.add(formatMenu);

        toolMenu = new WebMenu("Tool");
        editorMenuBar.add(toolMenu);

        tableMenu = new WebMenu("Table");
        editorMenuBar.add(tableMenu);

        helpMenu = new WebMenu("Help");

        contentHelpMenuItem = new WebMenuItem("Help Content");
        helpMenu.add(contentHelpMenuItem);
        helpMenu.addSeparator();

        aboutHelpMenuItem = new WebMenuItem("About");
        helpMenu.add(aboutHelpMenuItem);

        editorMenuBar.add(helpMenu);

        panel.add(editorMenuBar, java.awt.BorderLayout.NORTH);

        editorToolBar = new WebToolBar();
        editorToolBar.setFloatable(false);
        editorToolBar.setToolbarStyle(ToolbarStyle.attached);
        editorToolBar.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        //editorToolBar.setRollover(true);

        printTbButton = new WebButton("Print");
        printTbButton.setFocusable(false);
        printTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        printTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editorToolBar.add(printTbButton);

        undoTbButton = new WebButton("Undo");
        undoTbButton.setFocusable(false);
        undoTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        undoTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editorToolBar.add(undoTbButton);

        redoTbButton = new WebButton("Redo");
        redoTbButton.setFocusable(false);
        redoTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        redoTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editorToolBar.add(redoTbButton);
        editorToolBar.addSeparator();

        zoomTbComboBox = new WebComboBox();
        zoomTbComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        zoomTbComboBox.setEditable(true);
        editorToolBar.add(zoomTbComboBox);
        editorToolBar.addSeparator();

        styleTbComboBox = new WebComboBox();
        styleTbComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        editorToolBar.add(styleTbComboBox);
        editorToolBar.addSeparator();

        fontTbComboBox = new WebComboBox();
        fontTbComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        editorToolBar.add(fontTbComboBox);
        editorToolBar.addSeparator();

        fotnSizeTbComboBox = new WebComboBox();
        fotnSizeTbComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        zoomTbComboBox.setEditable(true);
        editorToolBar.add(fotnSizeTbComboBox);
        editorToolBar.addSeparator();

        boldTbButton = new WebToggleButton("B");
        boldTbButton.setFocusable(false);
        boldTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        boldTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        italicTbButton = new WebToggleButton("I");
        italicTbButton.setFocusable(false);
        italicTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        italicTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        underlineTbButton = new WebToggleButton("U");
        underlineTbButton.setFocusable(false);
        underlineTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        underlineTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        fontStyleTbGroup = new WebButtonGroup(true, boldTbButton, italicTbButton, underlineTbButton);
        fontStyleTbGroup.setButtonsDrawFocus(false);
        editorToolBar.add(fontStyleTbGroup);

        colorTbButton = new WebButton("A");
        editorToolBar.add(colorTbButton);
        editorToolBar.addSeparator();

        linkTbButton = new WebButton("Link");
        linkTbButton.setFocusable(false);
        linkTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        linkTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editorToolBar.add(linkTbButton);

        commentTbButton = new WebButton("Comment");
        commentTbButton.setFocusable(false);
        commentTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        commentTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editorToolBar.add(commentTbButton);
        editorToolBar.addSeparator();

        leftTbButton = new WebToggleButton("Left");
        leftTbButton.setSelected(true);
        leftTbButton.setFocusable(false);
        leftTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        leftTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        centerTbButton = new WebToggleButton("Center");
        centerTbButton.setFocusable(false);
        centerTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        centerTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        rightTbButton = new WebToggleButton("Right");
        rightTbButton.setFocusable(false);
        rightTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rightTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        justifyTbButton = new WebToggleButton("Justify");
        justifyTbButton.setFocusable(false);
        justifyTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        justifyTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        alignTbGroup = new WebButtonGroup(true, leftTbButton, centerTbButton, rightTbButton, justifyTbButton);
        alignTbGroup.setButtonsDrawFocus(false);
        editorToolBar.add(alignTbGroup);
        editorToolBar.addSeparator();

        lineSpaceTbButton = new WebComboBox();
        lineSpaceTbButton.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Item 1", "Item 2", "Item 3", "Item 4"}));
        editorToolBar.add(lineSpaceTbButton);
        editorToolBar.addSeparator();

        numberTbButton = new WebSplitButton();
        WebPopupMenu popupMenu = new WebPopupMenu();
        popupMenu.add(new WebMenuItem("Menu item 1", WebLookAndFeel.getIcon(16), Hotkey.ALT_X));
        popupMenu.add(new WebMenuItem("Menu item 2", Hotkey.D));
        popupMenu.addSeparator();
        popupMenu.add(new WebMenuItem("Menu item 3", Hotkey.ESCAPE));
        numberTbButton.setPopupMenu(popupMenu);
        editorToolBar.add(numberTbButton);

        editorToolBar.addSeparator();

        decIndTbButton = new WebButton("Inc");
        decIndTbButton.setFocusable(false);
        decIndTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        decIndTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editorToolBar.add(decIndTbButton);
        editorToolBar.addSeparator();

        incIndTbButton = new WebButton("Dec");
        incIndTbButton.setToolTipText("");
        incIndTbButton.setFocusable(false);
        incIndTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        incIndTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editorToolBar.add(incIndTbButton);
        editorToolBar.addSeparator();

        clearTbButton = new WebButton("Clear");
        clearTbButton.setFocusable(false);
        clearTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        clearTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editorToolBar.add(clearTbButton);

        editorTabPan.add(editorToolBar, java.awt.BorderLayout.NORTH);

        docmentPane = new WebDocumentPane();
        docmentPane.setUndecorated(false);

//        UWEditor editor = new UWEditor();
//        docmentPane.openDocument(new DocumentData("id", "title", editor));
//        editorTabPan.add(docmentPane, java.awt.BorderLayout.CENTER);

        panel.add(editorTabPan, java.awt.BorderLayout.CENTER);

    }

    private void setFBLoginJFrameEventListener(FBLoginJFrame fBLoginJFrame, WebButton loginFBbtn) {
        if (fBLoginJFrame != null) {
            try {
                FBLoginJFrameEventListener fBLoginJFrameEventListener = new FBLoginJFrameEventListener() {

                    @Override
                    public void loginSuccess() {
                        if (fBLoginJFrame.getLoginSuccess()) {
                            if (loginFBbtn != null) {
                                _fBUser = fBLoginJFrame.getUser();
                                if (_fBUser != null) {
                                    fBLoginJFrame.close();
                                    try {
//                                        Server = serverAddr.getText();
//                                        Po = serverPort.getText();
                                        Server = "127.0.0.1";
                                        Po = "8000";
                                        mainEditor = new Editor(Server, Po);
                                        mainEditor.listener = new EditorListener() {

                                            @Override
                                            public void panelCreated(WebPanel input) {

                                                openPane.removeAll();
                                                openPane.add(input);
                                                openPane.revalidate();
                                                openPane.repaint();

                                                openPane.add(filler3);

                                                openBreadcrumb.setSelected(true);
                                                openPane.add(filler4);

                                                CardLayout cl = (CardLayout) (centerPane.getLayout());
                                                cl.show(centerPane, "OpenCard");
                                            }
                                        };
                                        mainEditor.docListener = new DocumentsListener() {

                                            @Override
                                            public void getUWPanel(UWEditor editor) {
                                                editBreadcrumb.setSelected(true);
                                                docmentPane.openDocument(new DocumentData("id", "title", editor));
                                                editorTabPan.add(docmentPane, java.awt.BorderLayout.CENTER);
                                            }
                                        };
                                    } catch (IOException ex) {
                                        Logger.getLogger(UWGuiNew.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    loginFBbtn.setText("Log in as " + _fBUser.getName());

                                    isConnect = true;
                                }

                            }
                        }
                    }
                };
                fBLoginJFrame.setFBLoginJFrameEventListener(fBLoginJFrameEventListener);
            } catch (Exception e) {

            }
        }
    }
}
