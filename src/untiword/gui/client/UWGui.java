/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.gui.client;
import Controller.MessageHandlingThread;
import Model.ServerRequestDQ;
import Model.UserDQ;
import View.ButtonTabComponent;
import View.DocumentSelectionPanel;
import View.NewDocPanel;
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
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
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
    
    private static boolean isLogin = false;
    private static boolean isConnect = false;
    private static JPanel bottomPanel;
    
    private static JPanel mainPanel;
    private static void setupGui(){
        JFrame jFrame = new JFrame();
        WebBreadcrumb breadcrumb1 = new WebBreadcrumb ( true );
        fillBreadcrumb ( breadcrumb1 );
        GroupPanel steps = new GroupPanel (3, false, breadcrumb1);
        
        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(steps, BorderLayout.LINE_END);           

        WebPanel contentPanel = createConnectForm();

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(contentPanel);
        mainPanel.add(steps, BorderLayout.PAGE_END);
        
        jFrame.add(mainPanel);
        
        jFrame.setSize(800, 600);        
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
        
        
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater (new Runnable ()
        {
            @Override
            public void run ()
            {
                setupGui();
            }
        } );
        
    }
    
    private static void fillBreadcrumb ( WebBreadcrumb b )
    {
        // Sample breadcrumb data        
        WebBreadcrumbToggleButton first = new WebBreadcrumbToggleButton("Connect to Server");
        first.setSelected(true);
        
        WebBreadcrumbToggleButton second = new WebBreadcrumbToggleButton("Login & Select Document");
        second.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isConnect){
                    
                }
            }
        });
        
        WebBreadcrumbToggleButton third = new WebBreadcrumbToggleButton("Edit");        
        third.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(isLogin){
                    
                }
            }
        });
        
        
        b.add ( first );
        b.add ( second );
        b.add ( third );        
        SwingUtils.groupButtons ( b );
    }
    
    private static WebPanel createConnectForm ()
    {
        WebPanel panel = new WebPanel ();
        panel.setUndecorated ( false );
        panel.setLayout ( new BorderLayout () );
        panel.setMargin(210);
        panel.setRound ( StyleConstants.largeRound );
        
        final WebTextField ServerAddr = new WebTextField( 15 );
        ServerAddr.setMaximumSize(new Dimension(200, 200));
        ServerAddr.setInputPrompt ( "Server Address:" );
        ServerAddr.setHideInputPromptOnFocus ( false );
        ServerAddr.setInputPromptFont ( ServerAddr.getFont ().deriveFont ( Font.ITALIC ) );

        // Password field input prompt
        final WebTextField Port = new WebTextField ( 15 );
        Port.setInputPrompt ( "Port:" );
        Port.setHideInputPromptOnFocus ( false );
        Port.setMaximumSize(new Dimension(200, 200));
        WebLabel label = new WebLabel ( "Connect to Server");
        
        WebButton loginFBbtn = new WebButton ( "Login with Facebook");
        loginFBbtn.setMoveIconOnPress ( false );        
        
        WebButton button = new WebButton("Connect");
        button.addActionListener(new ActionListener() {
                
            @Override
            public void actionPerformed(ActionEvent e) {
                
                    String Server = ServerAddr.getText();
                    String Po = Port.getText();
                
            }
            
        });
        
        GroupPanel p = new GroupPanel(3,false,label, ServerAddr, Port, 
                new GroupPanel ( 2, new GroupPanel (loginFBbtn  ),  button) );

        panel.add ( p);
        
        return panel;
    }
    

}






