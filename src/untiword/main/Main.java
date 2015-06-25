/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.main;

import com.alee.laf.WebLookAndFeel;
import javax.swing.SwingUtilities;
import untiword.gui.JFWGui;
import untiword.gui.WordGui;

/**
 *
 * @author NThanh
 */
public class Main {

    public static void main ( final String[] args )
    {
        SwingUtilities.invokeLater ( new Runnable ()
        {
            @Override
            public void run ()
            {
                // Installing WebLaF
                WebLookAndFeel.install ();
                //WebLookAndFeel.setDecorateFrames ( true );

                // Initializing editor dialog
                JFWGui gui = new JFWGui();
                gui.setLocationRelativeTo(null);
                gui.setVisible(true);
                
                //
            }
        } );
    }
}
