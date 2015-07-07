/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.gui.client;

import Model.Account.FacebookUser;
import Server.BusinessLogicLayer.AccountBlo;
import View.Account.FBLoginJFrame;
import View.Account.FBLoginJFrameEventListener;
import com.alee.extended.breadcrumb.WebBreadcrumb;
import com.alee.extended.breadcrumb.WebBreadcrumbToggleButton;
import com.alee.extended.button.WebSplitButton;
import com.alee.extended.layout.TableLayout;
import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.list.CheckBoxListModel;
import com.alee.extended.list.WebCheckBoxList;
import com.alee.extended.panel.CenterPanel;
import com.alee.extended.image.WebDecoratedImage;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.extended.panel.WebButtonGroup;
import com.alee.extended.tab.DocumentData;
import com.alee.extended.tab.WebDocumentPane;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.button.WebToggleButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebListModel;
import com.alee.laf.menu.WebCheckBoxMenuItem;
import com.alee.laf.menu.WebMenu;
import com.alee.laf.menu.WebMenuBar;
import com.alee.laf.menu.WebMenuItem;
import com.alee.laf.menu.WebPopupMenu;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.rootpane.WebDialog;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebPasswordField;
import com.alee.laf.text.WebTextField;
import com.alee.laf.toolbar.ToolbarStyle;
import com.alee.laf.toolbar.WebToolBar;
import com.alee.managers.hotkey.Hotkey;
import com.alee.managers.hotkey.HotkeyManager;
import com.alee.managers.notification.NotificationManager;
import com.alee.utils.SwingUtils;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
<<<<<<< .merge_file_a02296
import java.util.HashMap;
=======
import java.net.URL;
import java.util.ArrayList;
>>>>>>> .merge_file_a19344
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;
import untiword.components.UWDocumentElement;
import untiword.utils.Resources;
import untiword.components.UWEditor;
import untiword.components.UWListView;
import untiword.components.docx.DocxDocument;
import untiword.controller.ClientController;
import untiword.events.AuthorizationListener;
import untiword.events.ListDocumentEvent;
import untiword.events.NotFoundDocumentEvent;
import untiword.events.RenameDocumentEvent;
import untiword.events.ReturnDataListener;
import untiword.model.DocumentIDsAndNames;

/**
 *
 * @author NThanh
 */
public class UWGuiNew extends javax.swing.JFrame {

    private FacebookUser _fBUser;
<<<<<<< .merge_file_a02296
    private WebButton _loginFBbtn;
    private WebButton _registerBtn;
    private FBLoginJFrame _fBLoginJFrame;
    private ImageIcon _i1;
    private WebLabel _userNameLabel;
    private WebLabel _emailLabel;
    private HashMap<String, String> _loginUsers;
=======
    WebButton _loginFBbtn;
    WebButton _registerBtn;
    WebButton _loginBtn;
    FBLoginJFrame _fBLoginJFrame;
>>>>>>> .merge_file_a19344

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
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
    }
<<<<<<< .merge_file_a02296
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {                                   
        // TODO add your handling code here:
        if(_fBUser != null)
        {
             clientController.logout(_fBUser.getAccessToken());
        }    
    }            
=======
    public boolean isConnectInternet() throws IOException{
        HttpURLConnection connection = (HttpURLConnection) new URL("https://www.google.com/?gws_rd=ssl").openConnection();
        connection.setRequestMethod("HEAD");
        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            return false;
        }
        return true;
    }
>>>>>>> .merge_file_a19344

    private void initComponents(){
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

        ActionListener getDocActionListener = (ActionEvent e) -> {
            if (isConnect) {
                clientController.sendMessage(clientController.createControlMessage("getdoclist", 0, ""));
                System.out.println(e.getActionCommand());
            }
        };

        breadcrumbAction = (ActionEvent e) -> {
            CardLayout cl = (CardLayout) (centerPane.getLayout());
            WebBreadcrumbToggleButton button = (WebBreadcrumbToggleButton) e.getSource();
            cl.show(centerPane, button.getName());
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

        _loginFBbtn = new WebButton("Login with Facebook");
        _loginFBbtn.setBounds(50, 115, 180, 30);
        _loginFBbtn.addActionListener((ActionEvent e) -> {
            try {
                _fBLoginJFrame = new FBLoginJFrame();
                _fBLoginJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                _fBLoginJFrame.setVisible(true);
                setFBLoginJFrameEventListener(_fBLoginJFrame, _loginFBbtn);
            } catch (MalformedURLException ex) {
                Logger.getLogger(UWGuiNew.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        _registerBtn = new WebButton("Register");
<<<<<<< .merge_file_a02296
        _registerBtn.setBounds(230, 115, 100, 30);
        _registerBtn.addActionListener((ActionEvent e) -> {
            boolean decorateFrames = WebLookAndFeel.isDecorateDialogs ();
            WebLookAndFeel.setDecorateDialogs ( true );
            
            RegisterDialog registerDialog = new RegisterDialog();
            registerDialog.pack();
            registerDialog.setLocationRelativeTo(null);
            registerDialog.setVisible(true);
            
            WebLookAndFeel.setDecorateDialogs ( decorateFrames );
=======
        _registerBtn.setBounds(160, 115, 100, 30);
        _registerBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean decorateFrames = WebLookAndFeel.isDecorateDialogs ();
                WebLookAndFeel.setDecorateDialogs ( true );
                
                RegisterDialog registerDialog = new RegisterDialog();
                registerDialog.pack();
                registerDialog.setLocationRelativeTo(null);
                registerDialog.setVisible(true);
                
                WebLookAndFeel.setDecorateDialogs ( decorateFrames );
            }
>>>>>>> .merge_file_a19344
        });
        
        _loginBtn = new WebButton("Login");
        _loginBtn.setBounds(50, 115, 100, 30);
        _loginBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean decorateFrames = WebLookAndFeel.isDecorateDialogs ();
                WebLookAndFeel.setDecorateDialogs ( true );
                
                LoginDialog loginDialog = new LoginDialog();
                loginDialog.pack();
                loginDialog.setLocationRelativeTo(null);
                loginDialog.setVisible(true);
                
                WebLookAndFeel.setDecorateDialogs ( decorateFrames );
            }
        });

        loginFormPan.add(label);
        loginFormPan.add(serverAddr);
        loginFormPan.add(serverPort);
        try {
            if(!isConnectInternet()){
                loginFormPan.add(_registerBtn);
                loginFormPan.add(_loginBtn);
            } else {
                loginFormPan.add(_loginFBbtn);
            }
        } catch (IOException ex) {
            loginFormPan.add(_registerBtn);
            loginFormPan.add(_loginBtn);
        }


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
        clientController.addNotFoundListener((NotFoundDocumentEvent event) -> {
            docsListViewModel.removeAllElements();
            //docsListViewModel.addElement("There are no documents on the server.");
            docsListViewModel.addElement(new UWDocumentElement(resources.loadIcon("resources/document-add.png"), "New Document"));
        });

        clientController.addRenameDocumentListener((RenameDocumentEvent event) -> {
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        });

        clientController.addListDocumentListener((ListDocumentEvent event) -> {
            docsListViewModel.removeAllElements();
            docsListViewModel.addElement(new UWDocumentElement(resources.loadIcon("resources/document-add.png"), "New Document"));
            
            String[] docs = event.DocumentNameList();
            for (String doc : docs) {
                docsListViewModel.addElement(new UWDocumentElement(resources.loadIcon("resources/compose.png"), doc));
            }
        });

        clientController.setCreateDocumentListener((UWEditor editor) -> {
            editBreadcrumb.setSelected(true);
            String num = "" + editor.getNum();
            docmentPane.openDocument(new DocumentData(num, editor.getDocumentName(), editor));
            
            CardLayout cl = (CardLayout) (centerPane.getLayout());
            cl.show(centerPane, "EditCard");
        });
        
        clientController.setAuthorizationListener(new AuthorizationListener() {

            @Override
            public void authorizeSucess() 
            {
                try
                {                    
                    _userNameLabel.setText(_fBUser.getName());
                    _emailLabel.setText(_fBUser.getEmail());
//                    try 
//                    {
//                        String _URL = _fBUser.getPicture();
//                        URL url = new URL(_URL);
//                        BufferedImage img = ImageIO.read(url);
//                        _i1 = new ImageIcon(img);
//                    } catch (MalformedURLException ex) {
//                        Logger.getLogger(UWGuiNew.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (IOException ex) {
//                        Logger.getLogger(UWGuiNew.class.getName()).log(Level.SEVERE, null, ex);
//                    }     
                    
                    CardLayout cl = (CardLayout) (centerPane.getLayout());
                    cl.show(centerPane, "OpenCard");
                    openBreadcrumb.setSelected(true);
                    _loginFBbtn.setText("Log in as " + _fBUser.getName());                  
                    isConnect = true;
                    
                    clientController.requestLoginUsers();
                }
                catch(Exception e)
                {
                    System.out.println(e.toString());
                }             
            }

            @Override
            public void authorizeFailed() {
                System.out.println("Authorize failed!");
            }
        });
        
        clientController.setConnectionResultListener(() -> {
            try
            {
//                _fBLoginJFrame.close();
//                _fBLoginJFrame = new FBLoginJFrame();
//                _fBLoginJFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//                _fBLoginJFrame.setVisible(false);
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
            }
        });
        
        clientController.setReturnDataListener(new ReturnDataListener() {

            @Override
            public void loginUsersReturned(String data) 
            {
                String[] parts = data.split("[|]");
                if(parts.length % 2 == 0)
                {
                    _loginUsers = new HashMap<>(parts.length / 2);
                    for(int i=0; i<parts.length; i++)
                    {
                        _loginUsers.put(parts[i], parts[i+1]);
                        i++;
                    }
                }
                System.out.println("Reveiced login user request: " + data);
            }

            @Override
            public void documentsReturned(String data) {
                
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

        
        _i1 = resources.loadIcon("resources/account.jpg");
        WebDecoratedImage img1 = new WebDecoratedImage(_i1);
        img1.setPreferredSize(new Dimension(50, 50));

        _userNameLabel = new WebLabel("Not login");
        _userNameLabel.setHorizontalAlignment(WebLabel.RIGHT);
        _userNameLabel.setFontSize(20);
        _userNameLabel.setBoldFont();

        _emailLabel = new WebLabel("");
        _emailLabel.setHorizontalAlignment(WebLabel.RIGHT);

        accountPanel.add(img1, "2, 1, 2, 3");
        accountPanel.add(_userNameLabel, "0, 1, 0, 2");
        accountPanel.add(_emailLabel, "0, 3");

        docsListViewModel = new WebListModel<>();
        //docsListViewModel.add(new UWDocumentElement(resources.loadIcon("resources/document-add.png"), "New Document"));
        //docsListViewModel.add(new UWDocumentElement(resources.loadIcon("resources/compose.png"), "Thanh"));
        docListView = new UWListView(docsListViewModel);
        docListView.addMouseListener(new MouseAdapter() {
            @Override
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
    
    public Component getShareComponent(){
        
        //Left Check list view
        final CheckBoxListModel model = new CheckBoxListModel ();    
        
        //Set Element
        if(_loginUsers != null)
        {
             String[] keys = _loginUsers.keySet().toArray(new String[_loginUsers.keySet().size()]);
            for (int i=0; i<keys.length; i++) 
            {
                model.addCheckBoxElement (_loginUsers.get(keys[i]), false);
            }
        }
       
        WebCheckBoxList leftWebCheckBoxList = new WebCheckBoxList ( model );
        leftWebCheckBoxList.setVisibleRowCount ( 4 );
        leftWebCheckBoxList.setSelectedIndex ( 0 );
        leftWebCheckBoxList.setEditable ( false );        
        
        final WebPanel panel1 = new WebPanel ( true );
        panel1.setPaintFocus ( true );        
        panel1.setMargin ( 10 );
        panel1.add ( new WebLabel ( "People to share", WebLabel.CENTER ), BorderLayout.NORTH );
        panel1.add ( new GroupPanel ( new WebScrollPane ( leftWebCheckBoxList )) , BorderLayout.CENTER );
        
 
        

        //Right panel
        final CheckBoxListModel modelRight = new CheckBoxListModel ();        
        
        //modelRight.addCheckBoxElement ( "Element which" );        
        WebCheckBoxList rightWebCheckBoxList = new WebCheckBoxList ( modelRight );
        rightWebCheckBoxList.setVisibleRowCount ( 4 );
        rightWebCheckBoxList.setSelectedIndex ( 0 );
        rightWebCheckBoxList.setEditable ( false );        
        
        final WebPanel panel2 = new WebPanel ( true );        
        panel2.setPaintFocus ( true );       
        panel2.setMargin ( 10 );
        panel2.add ( new WebLabel ( "Shared people", WebLabel.CENTER ), BorderLayout.NORTH );
        panel2.add ( new GroupPanel ( new WebScrollPane ( rightWebCheckBoxList )), BorderLayout.CENTER );
        
        //Middle buttons
        WebButton addButton = new WebButton(">");
        addButton.addActionListener((ActionEvent e) -> {
            List<Object> elements = model.getCheckedValues();
            for(int i = 0; i < model.size(); i++){
                for (Object element : elements) {
                    if (model.get(i).getUserObject().equals(element)) {
                        modelRight.addCheckBoxElement(element);
                        model.remove(i);
                    }
                }
            }
        });
        WebButton removeButton = new WebButton("<");
        removeButton.addActionListener((ActionEvent e) -> {
            List<Object> elements = modelRight.getCheckedValues();
            for(int i = 0; i < modelRight.size(); i++){
                for (Object element : elements) {
                    if (modelRight.get(i).getUserObject().equals(element)) {
                        model.addCheckBoxElement(element);
                        modelRight.remove(i);
                    }
                }
            }
        });
        GroupPanel middleButtons = new GroupPanel(false,addButton,removeButton );       
        
        SwingUtils.equalizeComponentsWidths(panel1,panel2);
        
        return new GroupPanel ( 3, panel1, middleButtons, panel2 );
        
    }
    
    ActionListener shareActionListener = (ActionEvent e) -> 
    {
        clientController.requestLoginUsers();
        final WebPopOver popOver = new WebPopOver();
        popOver.setCloseOnFocusLoss ( true );
        popOver.setMargin ( 10 );
        popOver.setLayout ( new VerticalFlowLayout () );
        final WebLabel titleLabel = new WebLabel ( "Share", WebLabel.CENTER );
        final WebButton closeButton = new WebButton ( resources.loadIcon( "resources/cross.png" ), (final ActionEvent e1) -> {
            popOver.dispose ();
        }).setUndecorated ( true );
        popOver.add ( new GroupPanel ( GroupingType.fillFirstAndLast, 4, titleLabel, closeButton ).setMargin ( 0, 0, 10, 0 ) );
        popOver.add(getShareComponent());
        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x1 = (dim.width)/2;
        int y1 = (dim.height)/2;
        popOver.show(x1, y1);
    };

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
        shareMenuItem.addActionListener(shareActionListener);

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
        clientController.sendMessage(clientController.createControlMessage("requestNew", -1, newName));
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
                                if (_fBUser != null) 
                                {
                                    //fBLoginJFrame.close();
                                    try {
                                        Server = serverAddr.getText();
                                        Po = serverPort.getText();
                                        //Server = "127.0.0.1";
                                        //Po = "8000";
                                        clientController.connectToServer(Server, Po);
                                        clientController.authorize(_fBUser.getAccessToken()); 
                                        
//                                        CardLayout cl = (CardLayout) (centerPane.getLayout());
//                                        cl.show(centerPane, "OpenCard");
//                                        openBreadcrumb.setSelected(true);
                                    } catch (IOException ex) {
                                        Logger.getLogger(UWGuiNew.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    //loginFBbtn.setText("Log in as " + _fBUser.getName());
                                    //isConnect = true;                                                                   
                                }
                            }
                        }
                    }

                    @Override
                    public void loginFailed() {
                        System.out.println("Login Failed!");
                    }
                };
                fBLoginJFrame.setFBLoginJFrameEventListener(fBLoginJFrameEventListener);
            } catch (Exception e) {

            }
        }
    }
    
    private class RegisterDialog extends WebDialog
    {
        public RegisterDialog ()
        {
            super ();
            this.setTitle("Register");            
            setDefaultCloseOperation ( WebDialog.DISPOSE_ON_CLOSE );
            setResizable ( false );
            setModal ( true );

            TableLayout layout = new TableLayout ( new double[][]{ { TableLayout.PREFERRED, TableLayout.FILL },                
                    { TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED,TableLayout.PREFERRED } } );
            layout.setHGap ( 5 );
            layout.setVGap ( 5 );
            WebPanel content = new WebPanel ( layout );
            content.setMargin ( 15, 30, 15, 30 );
<<<<<<< .merge_file_a02296
            content.setOpaque ( false );          
=======
            content.setOpaque ( false );
>>>>>>> .merge_file_a19344
            
            content.add (new WebLabel ( "Email", WebLabel.TRAILING ), "0,1" );
            WebTextField wtfEmail = new WebTextField ( 15 );
            content.add ( wtfEmail, "1,1" );
            
            content.add (new WebLabel ( "Password", WebLabel.TRAILING ), "0,2" );
            WebPasswordField wtfPassword = new WebPasswordField ( 15 );
            content.add ( wtfPassword, "1,2" );

            WebButton register = new WebButton ( "Register" );
            WebButton cancel = new WebButton ( "Cancel" );
            ActionListener listener = (ActionEvent e) -> {
                try {
                    setVisible ( false );
                    try {
                        int result = AccountBlo.getInstance().register("", wtfPassword.getText(), wtfEmail.getText());
                        if(result == -1)
                        {
                            NotificationManager.showNotification ( "This email is invalid!");
                        }
                        else
                        {
                            NotificationManager.showNotification ( "Register success!" );
                        }
                    } catch (XPathExpressionException ex) {
                        Logger.getLogger(UWGuiNew.class.getName()).log(Level.SEVERE, null, ex);
                    }                   
                } catch (ParserConfigurationException | SAXException | IOException | TransformerException ex) {
                    Logger.getLogger(UWGuiNew.class.getName()).log(Level.SEVERE, null, ex);
                }
            };
            register.addActionListener ( listener );
            cancel.addActionListener ( listener );
            content.add ( new CenterPanel ( new GroupPanel ( 5, register, cancel ) ), "0,3,1,2" );
            SwingUtils.equalizeComponentsWidths ( register, cancel );

            add ( content );
            
            registerHotkeys(register);
        }
        
        private void registerHotkeys(WebButton register)
        {
            HotkeyManager.registerHotkey ( this, register, Hotkey.ESCAPE );
            HotkeyManager.registerHotkey ( this, register, Hotkey.ENTER );
        }
    }
    
    private class LoginDialog extends WebDialog
    {
        public LoginDialog ()
        {
            super ();
            this.setTitle("Login");
            setIconImages ( WebLookAndFeel.getImages () );
            setDefaultCloseOperation ( WebDialog.DISPOSE_ON_CLOSE );
            setResizable ( false );
            setModal ( true );

            TableLayout layout = new TableLayout ( new double[][]{ { TableLayout.PREFERRED, TableLayout.FILL },
                    { TableLayout.PREFERRED, TableLayout.PREFERRED, TableLayout.PREFERRED } } );
            layout.setHGap ( 5 );
            layout.setVGap ( 5 );
            WebPanel content = new WebPanel ( layout );
            content.setMargin ( 15, 30, 15, 30 );
            content.setOpaque ( false );

            content.add ( new WebLabel ( "Email", WebLabel.TRAILING ), "0,0" );
            content.add ( new WebTextField ( 15 ), "1,0" );

            content.add ( new WebLabel ( "Password", WebLabel.TRAILING ), "0,1" );
            content.add ( new WebPasswordField ( 15 ), "1,1" );

            WebButton login = new WebButton ( "Login" );
            WebButton cancel = new WebButton ( "Cancel" );
            ActionListener listener = new ActionListener ()
            {
                @Override
                public void actionPerformed ( ActionEvent e )
                {
                    setVisible ( false );
                }
            };
            login.addActionListener ( listener );
            cancel.addActionListener ( listener );
            content.add ( new CenterPanel ( new GroupPanel ( 5, login, cancel ) ), "0,2,1,2" );
            SwingUtils.equalizeComponentsWidths ( login, cancel );

            add ( content );

            HotkeyManager.registerHotkey ( this, login, Hotkey.ESCAPE );
            HotkeyManager.registerHotkey ( this, login, Hotkey.ENTER );
        }
    }
}


