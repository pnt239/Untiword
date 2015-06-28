/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.main;

import com.alee.laf.WebLookAndFeel;
import javax.swing.SwingUtilities;
import untiword.gui.client.DocumentGui;
import untiword.gui.client.UWGuiNew;
import untiword.gui.client.WordGui;

/**
 *
 * @author NThanh
 */
public class ClientMain {

    public static void main ( final String[] args )
    {
        SwingUtilities.invokeLater (new Runnable ()
        {
            @Override
            public void run ()
            {
                // Installing WebLaF
                WebLookAndFeel.install ();
                //WebLookAndFeel.setDecorateFrames ( true );

                // Initializing editor dialog
                UWGuiNew gui = new UWGuiNew();
                
                gui.setLocationRelativeTo(null);
                gui.setVisible(true);
            }
        } );
    }
}
