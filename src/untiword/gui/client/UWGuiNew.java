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
import com.alee.extended.breadcrumb.WebBreadcrumbToggleButton;
import com.alee.extended.panel.GroupPanel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.alee.utils.SwingUtils;
import java.awt.BorderLayout;
import static java.awt.BorderLayout.SOUTH;
import java.awt.CardLayout;
import java.awt.Color;
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
import static untiword.gui.client.UWGui.second;

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
    
    private String Server;
    private String Po;
    private Editor mainEditor;
    private boolean isConnect = false;
    private ActionListener breadcrumbAction ;
            

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
        centerPane.add(editPane, "EditCard");

        getContentPane().add(centerPane, java.awt.BorderLayout.CENTER);

        // Pack
        this.pack();
        this.setSize(800, 600);
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
