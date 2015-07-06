/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Account;

import com.sun.javafx.application.PlatformImpl;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javax.swing.JButton;

/**
 *
 * @author Lilium Aikia
 */
public class SwingFXWebView extends javax.swing.JPanel
{
    private SwingFXWebViewEventListener _swingFXWebViewEventListener;
    private Stage _stage;  
    private WebView _browser;  
    private JFXPanel _jfxPanel;  
    private JButton _swingButton;  
    private WebEngine _webEngine;  
    
    public void close()
    {
        _stage.close();
    }
    
    public void setSwingFXWebViewEventListener(SwingFXWebViewEventListener eventListener)
    {
        _swingFXWebViewEventListener = eventListener;
    }

    /**
     * Creates new form SwingFXWebView
     * @param url
     */
    public SwingFXWebView(String url) {
        initComponents();
        
        _jfxPanel = new JFXPanel();  
        createScene(url);  
         
        setLayout(new BorderLayout());  
        add(_jfxPanel, BorderLayout.CENTER);
        _jfxPanel.setPreferredSize(new Dimension(_jfxPanel.getHeight(), _jfxPanel.getWidth()));
         
        _swingButton = new JButton();  
        _swingButton.addActionListener((ActionEvent e) -> {
            Platform.runLater(() -> {
                _webEngine.reload();
            });
        });  
        _swingButton.setText("Reload");  
         
        add(_swingButton, BorderLayout.SOUTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setAutoscrolls(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 229, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 184, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    private void createScene(String url) {  
        PlatformImpl.startup(() -> {
            _stage = new Stage();
            
            _stage.setTitle("Login Facebook");
            _stage.setResizable(true);
            
            Group root = new Group();
            Scene scene = new Scene(root,80,20);
            _stage.setScene(scene);
            
            // Set up the embedded browser:
            _browser = new WebView();
            _webEngine = _browser.getEngine();
            _webEngine.getLoadWorker().stateProperty().addListener(
                    (ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) ->
                    {
                        if (newValue == Worker.State.SUCCEEDED) 
                        {
                            _stage.setTitle(_webEngine.getLocation());
                            if(_swingFXWebViewEventListener != null)
                            {
                                _swingFXWebViewEventListener.locationChanged();
                            }
                        }
                    });
            
            
            ObservableList<Node> children = root.getChildren();
            children.add(_browser);
            
            _jfxPanel.setScene(scene); 
            _webEngine.load(url);
        });  
    }
    
    public void Load(String url)
    {
        if(_webEngine != null)
        {
            _webEngine.load(url);
        }     
    }
    
    public String getUrl()
    {
        return _webEngine.getLocation();
    }
}
