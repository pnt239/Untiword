/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Account;

import Controller.AccountController.FacebookController;
import Model.Account.FacebookUser;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lilium Aikia
 */
public class FBLoginJFrame extends javax.swing.JFrame {

    private FBLoginJFrameEventListener _fbBLoginJFrameEventListener;
    private boolean _loginSuccess;
    private SwingFXWebView _webBrowser;
    
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
            if(_webBrowser != null)
            {
                String url = _webBrowser.getUrl();
                String at = FacebookController.getInstance().parseUserAccessTokenUrl(url);
                user = FacebookController.getInstance().getUser(at);
            }       
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
        if(_webBrowser != null)
        {
            _webBrowser.setSwingFXWebViewEventListener(() -> {
                if(_webBrowser.getUrl().contains("access_token"))
                {
                    setVisible(false);
                    _loginSuccess = true;
                    if(_fbBLoginJFrameEventListener != null)
                    {
                        _fbBLoginJFrameEventListener.loginSuccess();
                    }            
                }
                else if(_webBrowser.getUrl().startsWith("www.localhost")
                        || _webBrowser.getUrl().startsWith("localhost")
                        || _webBrowser.getUrl().startsWith("https://www.localhost")
                        || _webBrowser.getUrl().startsWith("https://localhost")
                        || _webBrowser.getUrl().startsWith("http://www.localhost")
                        || _webBrowser.getUrl().startsWith("http://localhost"))
                {
                    setVisible(false);
                    _loginSuccess = false;
                    if(_fbBLoginJFrameEventListener != null)
                    {
                        _fbBLoginJFrameEventListener.loginFailed();
                    }
                }
            });
        }
    }
    
    /**
     * Creates new form NewJFrame
     * @throws java.net.MalformedURLException
     */
    public FBLoginJFrame() throws MalformedURLException {
        initComponents();
        setLayout(new BorderLayout());
        _webBrowser = new SwingFXWebView(FacebookController.getInstance().getFbUserAccessTokenUrl());
        if(_webBrowser != null)
        {
            setWebBrowserListener();
            add(_webBrowser, BorderLayout.CENTER);
            _webBrowser.setPreferredSize(new Dimension(_webBrowser.getHeight(), _webBrowser.getWidth()));
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
        setTitle("Login Facebook");
        setPreferredSize(new java.awt.Dimension(800, 665));
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 625, Short.MAX_VALUE)
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
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FBLoginJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
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
