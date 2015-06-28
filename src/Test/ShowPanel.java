/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import com.alee.extended.breadcrumb.WebBreadcrumb;
import com.alee.extended.breadcrumb.WebBreadcrumbToggleButton;
import com.alee.extended.panel.GroupPanel;
import com.alee.extended.panel.GroupingType;
import com.alee.extended.tab.DocumentData;
import com.alee.extended.tab.WebDocumentPane;
import com.alee.global.StyleConstants;
import com.alee.laf.WebLookAndFeel;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebList;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextField;
import com.alee.laf.text.WebTextPane;
import com.alee.utils.SwingUtils;
import com.alee.utils.TextUtils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import untiword.gui.client.UWGui;

/**
 *
 * @author Untitled25364
 */
public class ShowPanel {

    private static JPanel bottomPanel;
    private static JPanel mainPanel;
    private static GroupPanel contentPanel;

    static void setup() {
        JFrame jFrame = new JFrame();
        WebBreadcrumb breadcrumb1 = new WebBreadcrumb(true);
        fillBreadcrumb(breadcrumb1);
        GroupPanel steps = new GroupPanel(3, false, breadcrumb1);

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(steps, BorderLayout.LINE_START);

        contentPanel = (GroupPanel) getPreview();

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.PAGE_END);

        jFrame.add(mainPanel);

        jFrame.setSize(800, 600);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                WebLookAndFeel.install();
                setup();
            }
        });

    }

    private static void fillBreadcrumb(WebBreadcrumb b) {

        first.setSelected(true);
        first.addActionListener(firstActionListener);
        second.addActionListener(secondActionListener);
        third.addActionListener(thirdActionListener);

        b.add(first);
        b.add(second);
        b.add(third);
        SwingUtils.groupButtons(b);
    }

    static WebBreadcrumbToggleButton first = new WebBreadcrumbToggleButton("Connect to Server");
    static WebBreadcrumbToggleButton second = new WebBreadcrumbToggleButton("Select Document");
    static WebBreadcrumbToggleButton third = new WebBreadcrumbToggleButton("Edit");

    private static ActionListener firstActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            e.getID();
        }
    };
    private static ActionListener secondActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            e.getID();
        }
    };
    private static ActionListener thirdActionListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            e.getID();
        }
    };

    public static Component getPreview ()
    {
        final WebLabel title = new WebLabel ( "You can drag, close and split tabs in this document pane" );

        final WebDocumentPane pane = new WebDocumentPane ();
        pane.setUndecorated ( false );
        addDocuments ( pane );

        final WebButton add = new WebButton ( new ActionListener ()
        {
            @Override
            public void actionPerformed ( final ActionEvent e )
            {
                addDocuments ( pane );
            }
        } );
        final WebButton clear = new WebButton (  new ActionListener ()
        {
            @Override
            public void actionPerformed ( final ActionEvent e )
            {
                pane.closeAll ();
            }
        } );
        final WebButton restore = new WebButton ( new ActionListener ()
        {
            @Override
            public void actionPerformed ( final ActionEvent e )
            {
                pane.closeAll ();
                addDocuments ( pane );
            }
        } );

        final GroupPanel titlePanel = new GroupPanel ( GroupingType.fillFirst, 5, title, add, clear, restore );
        return new GroupPanel ( GroupingType.fillLast, 10, false, titlePanel, pane ).setMargin ( 10 );
    }

    /**
     * Adds a few documents into the document pane.
     *
     * @param pane document pane
     */
    static void addDocuments ( final WebDocumentPane pane )
    {
        JFrame temp = new JFrame();
        temp.add(new WebLabel ("AAAAAAAAAAAAAAAAAA"));
        temp.add(new WebTextPane());
        WebPanel a = new WebPanel().add(temp);
        pane.openDocument ( new DocumentData ( TextUtils.generateId (),  "Excel doc" , a  ) );
       pane.openDocument ( new DocumentData ( TextUtils.generateId (),  "Excel doc" , new WebLabel () ) );
       pane.openDocument ( new DocumentData ( TextUtils.generateId (),  "Excel doc" , new WebLabel () ) );
       pane.openDocument ( new DocumentData ( TextUtils.generateId (),  "Excel doc" , new WebLabel () ) );
       pane.openDocument ( new DocumentData ( TextUtils.generateId (),  "Excel doc" , new WebLabel () ) );
       
       
    }
}
