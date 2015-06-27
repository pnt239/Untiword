/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import com.alee.extended.breadcrumb.WebBreadcrumb;
import com.alee.extended.breadcrumb.WebBreadcrumbToggleButton;
import com.alee.extended.panel.GroupPanel;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebList;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextField;
import com.alee.utils.SwingUtils;
import java.awt.BorderLayout;
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
    private static WebPanel contentPanel;

    static void setup() {
        JFrame jFrame = new JFrame();
        WebBreadcrumb breadcrumb1 = new WebBreadcrumb(true);
        fillBreadcrumb(breadcrumb1);
        GroupPanel steps = new GroupPanel(3, false, breadcrumb1);

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(steps, BorderLayout.LINE_START);

        contentPanel = getPreview();

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

    public static WebPanel getPreview() {
        // Editable list
        WebPanel ret = new WebPanel(true);
        ret.setUndecorated(false);
        ret.setLayout(new BorderLayout());
        ret.setMargin(50);
        ret.setRound(StyleConstants.largeRound);

        WebList editableList = new WebList(createSampleData());
        editableList.setVisibleRowCount(10);
        editableList.setSelectedIndex(0);
        editableList.setEditable(true);
        WebScrollPane list = new WebScrollPane(editableList);
        list.setMaximumSize(new Dimension(200, 600));
        final WebPanel panel1 = new WebPanel(true);
        panel1.setPaintFocus(true);
        panel1.setMargin(10);
        panel1.add(new WebLabel("Select From List Files", WebLabel.CENTER), BorderLayout.NORTH);
        panel1.add(list, BorderLayout.CENTER);

        final WebPanel panel2 = new WebPanel(true);
        panel2.setPaintFocus(true);
        panel2.setMargin(10);
        panel2.add(new WebLabel("Create New Doc", WebLabel.CENTER), BorderLayout.NORTH);
        
        WebTextField txtFileName = new WebTextField(15);
        txtFileName.setHideInputPromptOnFocus(false);
        txtFileName.setInputPrompt("Enter text...");
        txtFileName.setInputPromptFont(txtFileName.getFont().deriveFont(Font.ITALIC));
        txtFileName.setMargin(5);        
        
        panel2.add(new WebButton("Create"), BorderLayout.SOUTH);
        panel2.add(txtFileName,BorderLayout.CENTER);

        ret.add(new GroupPanel(4, false, panel1, new WebLabel("OR", WebLabel.CENTER), panel2));
        return ret;
    }

    private static String[] createSampleData() {
        return new String[]{"Editable element 1", "Editable element 2", "Editable element 3", "Editable element 4", "Editable element 5",
            "Editable element 6"};
    }
}
