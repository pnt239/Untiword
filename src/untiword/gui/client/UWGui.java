/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.gui.client;

import Controller.AccountController.AccountController;
import Controller.MessageHandlingThread;
import Model.Account.FacebookUser;
import Model.ServerRequestDQ;
import Model.UserDQ;
import View.Account.FBLoginJFrame;
import View.Account.FBLoginJFrameEventListener;
import View.ButtonTabComponent;
import View.DocumentSelectionPanel;
import View.Editor;
import View.NewDocPanel;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserCommandEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserListener;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowOpeningEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;
import com.alee.extended.breadcrumb.WebBreadcrumb;
import com.alee.extended.breadcrumb.WebBreadcrumbToggleButton;
import com.alee.extended.panel.GroupPanel;
import com.alee.global.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.text.WebTextField;
import com.alee.utils.SwingUtils;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.Painter;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Untitled25364
 */
public class UWGui {

    private static FacebookUser _fBUser;
    private static AccountController _accountController;
    private static boolean isLogin = false;
    private static boolean isConnect = false;
    private static JPanel bottomPanel;

    private static JPanel mainPanel;

    public UWGui() {
        setupGui();
    }

    private static void setupGui() {
        JFrame jFrame = new JFrame();
        WebBreadcrumb breadcrumb1 = new WebBreadcrumb(true);
        fillBreadcrumb(breadcrumb1);
        GroupPanel steps = new GroupPanel(3, false, breadcrumb1);

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(steps, BorderLayout.LINE_START);

        WebPanel contentPanel = createConnectForm();

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(contentPanel);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);

        jFrame.add(mainPanel);

        jFrame.setSize(800, 600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                UWGui main = new UWGui();
            }
        });

    }

    private static void fillBreadcrumb(WebBreadcrumb b) {
        
    first.setSelected (true);
        first.addActionListener (firstActionListener);
        second.addActionListener(secondActionListener);
        third.addActionListener(thirdActionListener);

        b.add(first);
        b.add(second);
        b.add(third);
        SwingUtils.groupButtons(b);
    }

    static WebBreadcrumbToggleButton first = new WebBreadcrumbToggleButton("Connect to Server");   
    static WebBreadcrumbToggleButton second = new WebBreadcrumbToggleButton("Select Document");
    static WebBreadcrumbToggleButton third = new WebBreadcrumbToggleButton("Edit");

    private static ActionListener firstActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!isConnect) {
                first.setSelected(true);
            }
        }
    };
    private static ActionListener secondActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isConnect) {
                second.setSelected(isConnect);
                
            }
        }
    };
    private static ActionListener thirdActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (isConnect) {
                third.setSelected(isConnect);
            }
        }
    };
    
    private static void setFBLoginJFrameEventListener(FBLoginJFrame fBLoginJFrame, WebButton loginFBbtn)
    {
        if(fBLoginJFrame != null)
        {
            try
            {
                FBLoginJFrameEventListener fBLoginJFrameEventListener = new FBLoginJFrameEventListener() {

                    @Override
                    public void loginSuccess() 
                    {
                        if(fBLoginJFrame.getLoginSuccess())
                        {
                            if(loginFBbtn != null)
                            {
                                _fBUser = fBLoginJFrame.getUser();
                                if(_fBUser != null)
                                {
                                    fBLoginJFrame.close();
                                    loginFBbtn.setText("Log in as " + _fBUser.getName());
                                    isLogin = true;
                                }                             
                            }                           
                        }
                    }
                };
                fBLoginJFrame.setFBLoginJFrameEventListener(fBLoginJFrameEventListener);
            }
            catch(Exception e)
            {
                
            }
        }
    }

    private static WebPanel createConnectForm() {
        WebPanel panel = new WebPanel();
        panel.setUndecorated(false);
        panel.setLayout(new BorderLayout());
        panel.setMargin(210);
        panel.setRound(StyleConstants.largeRound);

        final WebTextField ServerAddr = new WebTextField(15);
        ServerAddr.setMaximumSize(new Dimension(200, 200));
        ServerAddr.setInputPrompt("Server Address:");
        ServerAddr.setHideInputPromptOnFocus(false);
        ServerAddr.setInputPromptFont(ServerAddr.getFont().deriveFont(Font.ITALIC));

        // Password field input prompt
        final WebTextField Port = new WebTextField(15);
        Port.setInputPrompt("Port:");
        Port.setHideInputPromptOnFocus(false);
        Port.setMaximumSize(new Dimension(200, 200));
        WebLabel label = new WebLabel("Connect to Server");

        WebButton loginFBbtn = new WebButton("Login with Facebook");
        loginFBbtn.setMoveIconOnPress(false);
        loginFBbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //Hào viết login facebook ở đây.
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

        WebButton button = new WebButton("Connect");
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (isLogin) {
                    try {
                        String Server = ServerAddr.getText();
                        String Po = Port.getText();
                        Editor mainEditor = new Editor(Server, Po);
                        
                    } catch (IOException ex) {
                        Logger.getLogger(UWGui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else{
                    
                }
            }

        });

        GroupPanel p = new GroupPanel(3, false, label, ServerAddr, Port,
                new GroupPanel(2, new GroupPanel(loginFBbtn), button));

        panel.add(p);

        return panel;
    }

}
