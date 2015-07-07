/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Account;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

import static javafx.concurrent.Worker.State.FAILED;

/**
 *
 * @author Lilium Aikia
 */
public class SimpleSwingBrowser extends JPanel 
{    
    private SimpleSwingBrowserEventListener _simpleSwingBrowserEventListener;
    public void setSimpleSwingBrowserEventListener(SimpleSwingBrowserEventListener listener)
    {
        _simpleSwingBrowserEventListener = listener;
    }
    
    private final JFXPanel _jfxPanel = new JFXPanel();
    private WebEngine _engine;
    
    private final JLabel _lblStatus = new JLabel();

//    private final JButton _btnGo = new JButton("Go");
//    private final JTextField _txtURL = new JTextField();
    private final JProgressBar _progressBar = new JProgressBar();

    public SimpleSwingBrowser() {
        super(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        createScene();

//        ActionListener al = (ActionEvent e) -> {
//            loadURL(_txtURL.getText());
//        };
//
//        _btnGo.addActionListener(al);
//        _txtURL.addActionListener(al);

        _progressBar.setPreferredSize(new Dimension(150, 18));
        _progressBar.setStringPainted(true);

        JPanel topBar = new JPanel(new BorderLayout(5, 0));
        topBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
//        topBar.add(_txtURL, BorderLayout.CENTER);
//        topBar.add(_btnGo, BorderLayout.EAST);

        JPanel statusBar = new JPanel(new BorderLayout(5, 0));
        statusBar.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
        statusBar.add(_lblStatus, BorderLayout.CENTER);
        statusBar.add(_progressBar, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);
        add(_jfxPanel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        setPreferredSize(new Dimension(1024, 600));
    }

    private void createScene() {

        Platform.runLater(() -> {
            WebView view = new WebView();
            _engine = view.getEngine();
            
//            _engine.titleProperty().addListener((ObservableValue<? extends String> observable, String oldValue, final String newValue) -> {
//                SwingUtilities.invokeLater(() -> {
//                    SimpleSwingBrowser.this.setTitle(newValue);
//                });
//            });
            
            _engine.setOnStatusChanged((final WebEvent<String> event) -> {
                SwingUtilities.invokeLater(() -> {
                    _lblStatus.setText(event.getData());
                });
            });
            
            _engine.locationProperty().addListener((ObservableValue<? extends String> ov, String oldValue, final String newValue) -> {
                SwingUtilities.invokeLater(() -> {
//                    _txtURL.setText(newValue);
                    if(_simpleSwingBrowserEventListener != null)
                    {
                        _simpleSwingBrowserEventListener.locationChanged();
                    }
                });
            });
            
            _engine.getLoadWorker().workDoneProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, final Number newValue) -> {
                SwingUtilities.invokeLater(() -> {
                    _progressBar.setValue(newValue.intValue());
                });
            });
            
            _engine.getLoadWorker()
                    .exceptionProperty()
                    .addListener((ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) -> {
                        if (_engine.getLoadWorker().getState() == FAILED) {
                            SwingUtilities.invokeLater(() -> {
                                JOptionPane.showMessageDialog(
                                        this,
                                        (value != null)
                                                ? _engine.getLocation() + "\n" + value.getMessage()
                                                : _engine.getLocation() + "\nUnexpected error.",
                                        "Loading error...",
                                        JOptionPane.ERROR_MESSAGE);
                            });
                        }
            });
            
            _jfxPanel.setScene(new Scene(view));
        });
    }

    public void loadURL(final String url) 
    {
        Platform.runLater(() -> {
            String tmp = toURL(url);
            
            if (tmp == null) {
                tmp = toURL("http://" + url);
            }
            
            _engine.load(tmp);
        });
    }
    
    public String getURL()
    {
        return _engine.getLocation();
    }

    private static String toURL(String str) {
        try {
            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
            return null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimpleSwingBrowser browser = new SimpleSwingBrowser();
            browser.setVisible(true);
            browser.loadURL("http://oracle.com");
        });
    }
}
