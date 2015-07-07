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
import com.alee.extended.breadcrumb.WebBreadcrumb;
import com.alee.extended.breadcrumb.WebBreadcrumbButton;
import com.alee.extended.breadcrumb.WebBreadcrumbToggleButton;
import com.alee.extended.button.WebSplitButton;
import com.alee.extended.image.DisplayType;
import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.layout.TableLayout;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.extended.tab.DocumentData;
import com.alee.extended.tab.WebDocumentPane;
import com.alee.global.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebList;
import com.alee.laf.list.WebListModel;
import com.alee.laf.menu.WebCheckBoxMenuItem;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.eclipse.swt.internal.win32.OS;
import untiword.components.UWDocumentElement;
import untiword.utils.Resources;
import untiword.components.UWEditor;
import untiword.components.UWListView;
import untiword.components.docx.DocxDocument;
import untiword.controller.ClientController;
import untiword.events.CreateDocumentListener;
import untiword.events.ListDocumentEvent;
import untiword.events.ListDocumentListener;
import untiword.events.NotFoundDocumentEvent;
import untiword.events.NotFoundDocumentListener;
import untiword.events.RenameDocumentEvent;
import untiword.events.RenameDocumentListener;
import untiword.model.DocumentIDsAndNames;

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
    //private static JList docsList;
    private UWListView docListView;
    private WebListModel docsListViewModel;
//    private WebList docsList;
//    private WebListModel docsListModel;
//    private JList documentList;
    private JScrollPane documentListScrollPane;
    //private JButton createButton;
    private WebButton createButton;
    private JButton openButton;
    private JLabel openLabel;
    private JLabel createLabel;
    private JLabel orLabel;
    //private JTextField docNameTextField;
    private WebTextField docNameTextField;
    private JScrollPane scrollPane;

    private Resources resources;

    private String Server;
    private String Po;
    private ClientController clientController;
    private boolean isConnect = false;
    private ActionListener breadcrumbAction;
    
    private int documentCount = 1;
    
    public UWGuiNew() {
        initComponents();
        createClient();
    }

    private void initComponents() {
        // Init variable
        resources = new Resources();
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
                    clientController.sendMessage(clientController.createControlMessage("getdoclist", 0, ""));
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
                    Logger.getLogger(UWGuiNew.class.getName()).log(Level.SEVERE, null, ex);
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
        addOpenNew(openPane);
        centerPane.add(openPane, "OpenCard");

        /* Create GUI for edit here */
        addEditor(editPane);
        centerPane.add(editPane, "EditCard");

        getContentPane().add(centerPane, java.awt.BorderLayout.CENTER);

        // Pack
        this.pack();
        this.setSize(800, 600);
    }

    private void createClient() {
        clientController = new ClientController();
        clientController.addNotFoundListener(new NotFoundDocumentListener() {

            @Override
            public void NotFoundDocument(NotFoundDocumentEvent event) {
                docsListViewModel.removeAllElements();
                //docsListViewModel.addElement("There are no documents on the server.");
                docsListViewModel.addElement(new UWDocumentElement(resources.loadIcon("resources/document-add.png"), "New Document"));
            }
        });

        clientController.addRenameDocumentListener(new RenameDocumentListener() {

            @Override
            public void RenameDocument(RenameDocumentEvent event) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

        clientController.addListDocumentListener(new ListDocumentListener() {

            @Override
            public void ListDocument(ListDocumentEvent event) {
                
                docsListViewModel.removeAllElements();
                docsListViewModel.addElement(new UWDocumentElement(resources.loadIcon("resources/document-add.png"), "New Document"));
                
                String[] docs = event.DocumentNameList();
                for (String doc : docs) {
                    docsListViewModel.addElement(new UWDocumentElement(resources.loadIcon("resources/compose.png"), doc));
                }
            }
        });

        clientController.setCreateDocumentListener(new CreateDocumentListener() {

            @Override
            public void getUWPanel(UWEditor editor) {
                editBreadcrumb.setSelected(true);
                String num = "" + editor.getNum();
                docmentPane.openDocument(new DocumentData(num, editor.getDocumentName(), editor));

                CardLayout cl = (CardLayout) (centerPane.getLayout());
                cl.show(centerPane, "EditCard");
            }
        });
    }

    private void addOpenNew(JPanel panel) {
        double size[][]
                = {{10, TableLayout.FILL, 10},
                {10, 65, 10, TableLayout.FILL, 10}};

        double sizeAccount[][] = {{TableLayout.FILL, 5, 50, 5},
        {5, 17, 17, 17, 5}
        };

        TableLayout layout = new TableLayout(size);
        panel.setLayout(layout);

        WebPanel accountPanel = new WebPanel();
        accountPanel.setUndecorated(true);
        accountPanel.setLayout(new TableLayout(sizeAccount));
        //ret.setRound ( StyleConstants.largeRound );

        ImageIcon i1 = resources.loadIcon("resources/account.jpg");
        WebDecoratedImage img1 = new WebDecoratedImage(i1);
        img1.setPreferredSize(new Dimension(50, 50));

        WebLabel userNameLabel = new WebLabel("Not Login");
        userNameLabel.setHorizontalAlignment(WebLabel.RIGHT);
        userNameLabel.setFontSize(20);
        userNameLabel.setBoldFont();

        WebLabel emailLabel = new WebLabel("");
        emailLabel.setHorizontalAlignment(WebLabel.RIGHT);

        accountPanel.add(img1, "2, 1, 2, 3");
        accountPanel.add(userNameLabel, "0, 1, 0, 2");
        accountPanel.add(emailLabel, "0, 3");

        docsListViewModel = new WebListModel<UWDocumentElement>();
        //docsListViewModel.add(new UWDocumentElement(resources.loadIcon("resources/document-add.png"), "New Document"));
        //docsListViewModel.add(new UWDocumentElement(resources.loadIcon("resources/compose.png"), "Thanh"));
        docListView = new UWListView(docsListViewModel);
        docListView.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    openDocument();
                }
            }
        });
        WebScrollPane scrolllist = new WebScrollPane(docListView);

        panel.add(accountPanel, "1, 1");
        panel.add(scrolllist, "1, 3");
    }

//    private void addOpenNewBK(JPanel panel) {
//        panel.setLayout(new javax.swing.BoxLayout(openPane, javax.swing.BoxLayout.Y_AXIS));
//
//        WebPanel ret = new WebPanel(true);
//        ret.setUndecorated(false);
//        ret.setLayout(new BorderLayout());
//        ret.setMargin(50);
//        ret.setRound(StyleConstants.largeRound);
//
//        final WebPanel panel2 = new WebPanel(true);
//        panel2.setPaintFocus(true);
//        panel2.setMargin(10);
//        panel2.add(new WebLabel("Create New Doc", WebLabel.CENTER), BorderLayout.NORTH);
//
//        docNameTextField = new WebTextField(15);
//        docNameTextField.setHideInputPromptOnFocus(false);
//        docNameTextField.setInputPrompt("Enter Document Name...");
//        docNameTextField.setInputPromptFont(docNameTextField.getFont().deriveFont(Font.ITALIC));
//        docNameTextField.setMargin(5);
//
//        docsListModel = new WebListModel<String>();
//        //docsListModel.add("There are no documents on the server." );
//        docsList = new WebList(docsListModel);
//        docsList.setVisibleRowCount(10);
//        docsList.setEditable(false);
//        docsList.setSelectedIndex(0);
//        docsList.setMinimumSize(new Dimension(0, 200));
//        WebScrollPane scrolllist = new WebScrollPane(docsList);
//
//        scrolllist.setMaximumSize(new Dimension(200, 600));
//        final WebPanel panel1 = new WebPanel(true);
//        panel1.setPaintFocus(true);
//        panel1.setMargin(10);
//        panel1.add(new WebLabel("Select From List Files", WebLabel.CENTER), BorderLayout.NORTH);
//        panel1.add(scrolllist, BorderLayout.CENTER);
//
//        docNameTextField.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ae) {
//                String newName = docNameTextField.getText();
//                docNameTextField.setText("");
//                if (newName.contains("|") || newName.contains("~")) {
//                    WebOptionPane.showMessageDialog(null, "pipes and tildas cannot be used in document names", "Error", WebOptionPane.ERROR_MESSAGE);
//                    //WebOptionPane.showMessageDialog(editor.getTabbedPane(), "pipes and tildas cannot be used in document names");
//                    return;
//                }
//                clientController.sendMessage(clientController.createControlMessage("requestNew",
//                        -1, newName));
//            }
//        });
//
//        createButton = new WebButton("Create");
//        createButton.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent ae) {
//                String newName = docNameTextField.getText();
//                docNameTextField.setText("");
//                if (newName.equals("Enter Document Name") || newName.equals("")) {
//                    newName = "New Document";
//                }
//                clientController.sendMessage(clientController.createControlMessage("requestNew",
//                        -1, newName));
//
//            }
//        });
//
//        docsList.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent evt) {
//                if (evt.getClickCount() == 2) {
//                    openDocument();
//                }
//            }
//        });
//
//        panel2.add(createButton, BorderLayout.SOUTH);
//        panel2.add(docNameTextField, BorderLayout.CENTER);
//
//        ret.add(new GroupPanel(4, false, panel1, new WebLabel("OR", WebLabel.CENTER), panel2));
//
//        panel.add(filler3);
//        panel.add(ret);
//        panel.add(filler4);
//    }

    private void addEditor(JPanel panel) {
        panel.setLayout(new BorderLayout());

        editorTabPan = new JPanel();
        editorTabPan.setLayout(new BorderLayout());

        editorMenuBar = new WebMenuBar();

        fileMenu = new WebMenu("File");

        shareMenuItem = new WebMenuItem("Share...", resources.loadIcon("resources/share.png"));
        fileMenu.add(shareMenuItem);
        fileMenu.addSeparator();

        newMenuItem = new WebMenuItem("New", resources.loadIcon("resources/new.png"));
        fileMenu.add(newMenuItem);

        openMenuItem = new WebMenuItem("Open", resources.loadIcon("resources/open.png"));
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

        printMenuItem = new WebMenuItem("Print", resources.loadIcon("resources/print.png"));
        fileMenu.add(printMenuItem);

        editorMenuBar.add(fileMenu);

        editMenu = new WebMenu("Edit");

        undoMenuItem = new WebMenuItem("Undo", resources.loadIcon("resources/undo.png"));
        editMenu.add(undoMenuItem);

        redoMenuItem = new WebMenuItem("Redo", resources.loadIcon("resources/redo.png"));
        editMenu.add(redoMenuItem);
        editMenu.addSeparator();

        cutMenuItem = new WebMenuItem("Cut", resources.loadIcon("resources/cut.png"));
        editMenu.add(cutMenuItem);

        copyMenuItem = new WebMenuItem("Copy", resources.loadIcon("resources/copy.png"));
        editMenu.add(copyMenuItem);

        pasteMenuItem = new WebMenuItem("Paste", resources.loadIcon("resources/paste.png"));
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

        printTbButton = new WebButton("", resources.loadIcon("resources/print_mini.png"));
        printTbButton.setFocusable(false);
        printTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        printTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editorToolBar.add(printTbButton);

        undoTbButton = new WebButton("", resources.loadIcon("resources/undo_mini.png"));
        undoTbButton.setFocusable(false);
        undoTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        undoTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editorToolBar.add(undoTbButton);

        redoTbButton = new WebButton("", resources.loadIcon("resources/redo_mini.png"));
        redoTbButton.setFocusable(false);
        redoTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        redoTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editorToolBar.add(redoTbButton);
        editorToolBar.addSeparator();

        zoomTbComboBox = new WebComboBox();
        zoomTbComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Arial", "Tahoma", "Consolas", "Linh tinh"}));
        zoomTbComboBox.setEditable(true);
        editorToolBar.add(zoomTbComboBox);
        editorToolBar.addSeparator();

        styleTbComboBox = new WebComboBox();
        styleTbComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"9", "12", "14", "20"}));
        editorToolBar.add(styleTbComboBox);
        editorToolBar.addSeparator();

        fontTbComboBox = new WebComboBox();
        fontTbComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Heading 1", "Heading 2", "Heading 3", "Heading 4"}));
        editorToolBar.add(fontTbComboBox);
        editorToolBar.addSeparator();

        fotnSizeTbComboBox = new WebComboBox();
        fotnSizeTbComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"100%", "125%", "150%", "200%"}));
        zoomTbComboBox.setEditable(true);
        editorToolBar.add(fotnSizeTbComboBox);
        editorToolBar.addSeparator();

        boldTbButton = new WebToggleButton("", resources.loadIcon("resources/bold.png"));
        boldTbButton.setFocusable(false);
        boldTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        boldTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        italicTbButton = new WebToggleButton("", resources.loadIcon("resources/italic.png"));
        italicTbButton.setFocusable(false);
        italicTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        italicTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        underlineTbButton = new WebToggleButton("", resources.loadIcon("resources/underline.png"));
        underlineTbButton.setFocusable(false);
        underlineTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        underlineTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        fontStyleTbGroup = new WebButtonGroup(true, boldTbButton, italicTbButton, underlineTbButton);
        fontStyleTbGroup.setButtonsDrawFocus(false);
        editorToolBar.add(fontStyleTbGroup);

        colorTbButton = new WebButton("A");
        editorToolBar.add(colorTbButton);
        editorToolBar.addSeparator();

        linkTbButton = new WebButton("", resources.loadIcon("resources/share.png"));
        linkTbButton.setFocusable(false);
        linkTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        linkTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editorToolBar.add(linkTbButton);

        commentTbButton = new WebButton("", resources.loadIcon("resources/comment.png"));
        commentTbButton.setFocusable(false);
        commentTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        commentTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editorToolBar.add(commentTbButton);
        editorToolBar.addSeparator();

        leftTbButton = new WebToggleButton("", resources.loadIcon("resources/left.png"));
        leftTbButton.setSelected(true);
        leftTbButton.setFocusable(false);
        leftTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        leftTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        centerTbButton = new WebToggleButton("", resources.loadIcon("resources/center.png"));
        centerTbButton.setFocusable(false);
        centerTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        centerTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        rightTbButton = new WebToggleButton("", resources.loadIcon("resources/right.png"));
        rightTbButton.setFocusable(false);
        rightTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        rightTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        justifyTbButton = new WebToggleButton("", resources.loadIcon("resources/justify.png"));
        justifyTbButton.setFocusable(false);
        justifyTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        justifyTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        alignTbGroup = new WebButtonGroup(true, leftTbButton, centerTbButton, rightTbButton, justifyTbButton);
        alignTbGroup.setButtonsDrawFocus(false);
        editorToolBar.add(alignTbGroup);
        editorToolBar.addSeparator();

        lineSpaceTbButton = new WebComboBox();
        lineSpaceTbButton.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"1", "1.15", "1.5", "2.0"}));
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

        decIndTbButton = new WebButton("", resources.loadIcon("resources/increase.png"));
        decIndTbButton.setFocusable(false);
        decIndTbButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        decIndTbButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        editorToolBar.add(decIndTbButton);
        editorToolBar.addSeparator();

        incIndTbButton = new WebButton("", resources.loadIcon("resources/decrease.png"));
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

        //UWEditor editor = new UWEditor(1, "Thanh", clientController);
        //docmentPane.openDocument(new DocumentData("id", "title", editor));
        editorTabPan.add(docmentPane, java.awt.BorderLayout.CENTER);

        panel.add(editorTabPan, java.awt.BorderLayout.CENTER);

    }

    private void openDocument() {
        int idxSelected = (int) docListView.getSelectedIndex();
        if (idxSelected == 0) {
            createDocument();
            return;
        } else {
            idxSelected--;
        }
        
        String nameId = clientController.getDocumentIdAndNames().get(idxSelected);
        DocumentIDsAndNames docNameID = new DocumentIDsAndNames(nameId);
        int num = docNameID.getNum();
        String name = docNameID.toString();

        UWEditor newDocWindow = new UWEditor(num, name, clientController);
        newDocWindow.setVisible(true);
        clientController.getDocIDtoDocPanel().put(newDocWindow.getNum(), newDocWindow);

        String numString = "" + num;
        docmentPane.openDocument(new DocumentData(numString, name, newDocWindow));

        CardLayout cl = (CardLayout) (centerPane.getLayout());
        cl.show(centerPane, "EditCard");
        editBreadcrumb.setSelected(true);

        clientController.getUser().addDocument(num, (DocxDocument) newDocWindow.getTextPane().getDocument());
        clientController.sendMessage(clientController.createControlMessage("load", num, name));
    }

    private void createDocument() {
        String newName = "Document" + documentCount;//docNameTextField.getText();
        //docNameTextField.setText("");
        documentCount++;
        if (newName.equals("Enter Document Name") || newName.equals("")) {
            newName = "New Document";
        }
        clientController.sendMessage(clientController.createControlMessage("requestNew",
                -1, newName));
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
                                        Server = serverAddr.getText();
                                        Po = serverPort.getText();
                                        //Server = "127.0.0.1";
                                        //Po = "8000";
                                        clientController.connectToServer(Server, Po);
                                        
                                        CardLayout cl = (CardLayout) (centerPane.getLayout());
                                        cl.show(centerPane, "OpenCard");
                                        openBreadcrumb.setSelected(true);
                                    } catch (IOException ex) {
                                        Logger.getLogger(UWGuiNew.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    loginFBbtn.setText("Log in as " + _fBUser.getName());

                                    isConnect = true;
                                    //clientController.authorize(_fBUser.getAccessToken());
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
