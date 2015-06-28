/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Account.Browsers;

import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import java.net.URL;
import javax.swing.JComponent;

/**
 *
 * @author Lilium Aikia
 */
public interface Browser 
{
    public void clearSessionCookies();
    public JWebBrowser getWebBrowser();
    public JComponent getBrowserComponent();
    public void browseTo (URL url);
    public String getResourceLocation();
    public String getHtmlContent();
    public void dispose();
}
