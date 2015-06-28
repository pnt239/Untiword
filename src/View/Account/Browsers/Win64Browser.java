/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Account.Browsers;

import chrriis.dj.nativeswing.NSComponentOptions;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.net.URL;
import javax.swing.JComponent;

/**
 *
 * @author Lilium Aikia
 */
public class Win64Browser implements Browser
{
    private JWebBrowser webBrowser;
    public Win64Browser() {

        //If not this, browser component creates exceptions when you move it around,
        //this flag is for the native peers to recreate in the new place:
        webBrowser = new JWebBrowser(NSComponentOptions.destroyOnFinalization());

    }

    @Override
    public JComponent getBrowserComponent() {
        return webBrowser;
    }

    @Override
    public void browseTo(URL url) {
        webBrowser.navigate(url.toString());
    }

    @Override
    public void dispose() {
        webBrowser.disposeNativePeer();
        webBrowser = null;
    }    

    @Override
    public JWebBrowser getWebBrowser() {
        return webBrowser;
    }

    @Override
    public String getResourceLocation() {
        return webBrowser.getResourceLocation();
    }

    @Override
    public String getHtmlContent() {
        return webBrowser.getHTMLContent();
    }

    @Override
    public void clearSessionCookies() {
        JWebBrowser.clearSessionCookies();
    }
}
