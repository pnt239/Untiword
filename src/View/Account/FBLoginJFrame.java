/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Account;

import Controller.AccountController.AccountController;
import Controller.AccountController.FacebookController;
import Model.Account.FacebookUser;
import chrriis.dj.nativeswing.NativeSwing;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserCommandEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserListener;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserNavigationEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowOpeningEvent;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserWindowWillOpenEvent;
import com.restfb.types.User;
import java.awt.BorderLayout;
import static java.lang.System.out;
import java.net.MalformedURLException;
import org.openide.util.Lookup;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lilium Aikia
 */
public class FBLoginJFrame extends javax.swing.JFrame {

    private FBLoginJFrameEventListener _fbBLoginJFrameEventListener;
    private boolean _loginSuccess;
    private Browser _browser;
    private AccountController _accountController;
    
    public void close()
    {
        this.setVisible(false);
        this.dispose();
    }
    
    public boolean getLoginSuccess()
    {
        return _loginSuccess;
    }
    
    public FacebookUser getUser()
    {
        FacebookUser user = null;
        
        try
        {
            String url = _browser.getResourceLocation();
            String at = _accountController.getFacebookController().parseUserAccessTokenUrl(url);
            user = _accountController.getFacebookController().getUser(at);
//            out.print(url + "\n");
//            out.print(_browser.getHtmlContent() + "\n");
//            out.print(at + "\n");
//            out.print(user.getName() + "\n" + user.getEmail() + "\n");
//            List<User> friends = _accountController.getFacebookController().getUserFriends(user);
//            out.print(friends.size());
//            for(User friend : friends)
//            {
//                out.print(friend.getName() + "\n");
//            }
        }
        catch(Exception e)
        {
            
        }      
        
        return user;
    }
    
    public void setFBLoginJFrameEventListener(FBLoginJFrameEventListener fBLoginJFrameEventListener)
    {
        if(fBLoginJFrameEventListener != null)
        {
            try
            {
                _fbBLoginJFrameEventListener = fBLoginJFrameEventListener;
            }
            catch (Exception e)
            {
                
            }
        }
    }
    
    private void setWebBrowserListener()
    {
        if(_browser != null)
        {
            WebBrowserListener webBrowserListener = new WebBrowserListener() {
                private int _locationChanged = 0;
                
                @Override
                public void windowWillOpen(WebBrowserWindowWillOpenEvent wbwwoe) {                   
                }

                @Override
                public void windowOpening(WebBrowserWindowOpeningEvent wbwoe) {                    
                }

                @Override
                public void windowClosing(WebBrowserEvent wbe) {                 
                }

                @Override
                public void locationChanging(WebBrowserNavigationEvent wbne) {
                }

                @Override
                public void locationChanged(WebBrowserNavigationEvent wbne) {
                    _locationChanged++;
                    if(_locationChanged == 2)
                    {
                        _loginSuccess = true;
                        if(_fbBLoginJFrameEventListener != null)
                        {
                            _fbBLoginJFrameEventListener.loginSuccess();
                        }
                    }
                }

                @Override
                public void locationChangeCanceled(WebBrowserNavigationEvent wbne) {
                }

                @Override
                public void loadingProgressChanged(WebBrowserEvent wbe) {
                }

                @Override
                public void titleChanged(WebBrowserEvent wbe) {
                }

                @Override
                public void statusChanged(WebBrowserEvent wbe) {
                }

                @Override
                public void commandReceived(WebBrowserCommandEvent wbce) {
                }
            };
            _browser.getWebBrowser().addWebBrowserListener(webBrowserListener);
        }
    }
    
    /**
     * Creates new form NewJFrame
     * @throws java.net.MalformedURLException
     */
    public FBLoginJFrame() throws MalformedURLException {
        initComponents();      
        _accountController = new AccountController();
        NativeSwing.initialize();
        setLayout(new BorderLayout());
        BrowserProvider bp = Lookup.getDefault().lookup(BrowserProvider.class);
        if (bp!=null){
            _browser = bp.createBrowser();
            add(_browser.getBrowserComponent(), BorderLayout.CENTER);
            
            setWebBrowserListener();
            _browser.clearSessionCookies();
            _browser.getWebBrowser().setMenuBarVisible(false);
            _browser.getWebBrowser().setButtonBarVisible(false);
            _browser.getWebBrowser().setLocationBarVisible(false);
            
            
            FacebookController fc = new FacebookController();
            _browser.browseTo(new URL(fc.getFbUserAccessTokenUrl()));
            //out.print(fc.getFbUserAccessTokenUrl());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 829, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 476, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FBLoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FBLoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FBLoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FBLoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new FBLoginJFrame().setVisible(true);
            } catch (MalformedURLException ex) {
                Logger.getLogger(FBLoginJFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
