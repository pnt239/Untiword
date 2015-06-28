/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Account.Browsers;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Lilium Aikia
 */
@ServiceProvider(service = BrowserProvider.class)
public class Win64BrowserProvider implements BrowserProvider 
{
    private boolean isInitialized;

    @Override
    public Browser createBrowser() {
        initialize();
        return new Win64Browser();
    }

    private synchronized void initialize() {
        if (!isInitialized) {
            NativeInterface.open();
            isInitialized = true;
        }
    }
    
}
