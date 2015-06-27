package View;

import com.alee.extended.panel.GroupPanel;
import com.alee.global.StyleConstants;
import com.alee.laf.button.WebButton;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebList;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.laf.panel.WebPanel;
import com.alee.laf.scroll.WebScrollPane;
import com.alee.laf.text.WebTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import untiword.gui.client.WordGui;

/**
 * 
 * DocumentSelectionPanel is a class to represent the document selection menu.
 * It allows the user to either load a document from the server or create a new
 * document.
 * 
 */

public class DocumentSelectionPanel {

    private static final long serialVersionUID = 1L;
    //private static JList docsList;
    private static WebList docsList;
    //private JButton createButton;
    private WebButton createButton;
    private JButton openButton;
    private JLabel openLabel;
    private JLabel createLabel;
    private JLabel orLabel;
    //private JTextField docNameTextField;
    private WebTextField docNameTextField;
    private JScrollPane scrollPane;
    
    public WebPanel getPreview() {
        // Editable list
        WebPanel ret = new WebPanel(true);
        ret.setUndecorated(false);
        ret.setLayout(new BorderLayout());
        ret.setMargin(50);
        ret.setRound(StyleConstants.largeRound);

        WebList editableList = new WebList();
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
        
        docNameTextField  = new WebTextField(15);
        docNameTextField.setHideInputPromptOnFocus(false);
        docNameTextField.setInputPrompt("Enter Document Name...");
        docNameTextField.setInputPromptFont(docNameTextField.getFont().deriveFont(Font.ITALIC));
        docNameTextField.setMargin(5);        
        
        createButton = new WebButton("Create");        
        panel2.add(createButton, BorderLayout.SOUTH);
        panel2.add(docNameTextField,BorderLayout.CENTER);

        ret.add(new GroupPanel(4, false, panel1, new WebLabel("OR", WebLabel.CENTER), panel2));
        return ret;
    }

    /**
     * creates a new DocumentSelectionPanel. Will display the list of document
     * from the server that can be selected on one side (or a message that there
     * are no documents on the server). On the other side there will be a text
     * field to create a new document with the given name (or a variation of the
     * name if its already taken, ie. Niki-3 is Niki is already taken).
     * 
     * @param list
     *            list of documents on the server
     * @param editor
     *            the editor this selectionPanel is in.
     */
    public WebPanel getDocumentSelectionPanel(String[] list, final Editor editor) {
        
        WebPanel ret = new WebPanel(true);
        ret.setUndecorated(false);
        ret.setLayout(new BorderLayout());
        ret.setMargin(50);
        ret.setRound(StyleConstants.largeRound);

        final WebPanel panel2 = new WebPanel(true);
        panel2.setPaintFocus(true);
        panel2.setMargin(10);
        panel2.add(new WebLabel("Create New Doc", WebLabel.CENTER), BorderLayout.NORTH);
        
        docNameTextField  = new WebTextField(15);
        docNameTextField.setHideInputPromptOnFocus(false);
        docNameTextField.setInputPrompt("Enter Document Name...");
        docNameTextField.setInputPromptFont(docNameTextField.getFont().deriveFont(Font.ITALIC));
        docNameTextField.setMargin(5);        
        



        if (list != null) {
            ArrayList<DocumentIDsAndNames> newList = new ArrayList<DocumentIDsAndNames>();
            for (String item : list) {
                newList.add(new DocumentIDsAndNames(item));
            }
            docsList = new WebList(newList.toArray());
        } else {
            String[] blank = { "There are no documents on the server." };
            docsList = new WebList(blank);
        }
        docsList.setVisibleRowCount(10);
        docsList.setEditable(false);
        docsList.setSelectedIndex(0);
        docsList.setEditable(true);
        docsList.setMinimumSize(new Dimension(0, 200));
        WebScrollPane scrolllist = new WebScrollPane(docsList);
        
        
        scrolllist.setMaximumSize(new Dimension(200, 600));
        final WebPanel panel1 = new WebPanel(true);
        panel1.setPaintFocus(true);
        panel1.setMargin(10);
        panel1.add(new WebLabel("Select From List Files", WebLabel.CENTER), BorderLayout.NORTH);
        panel1.add(scrolllist, BorderLayout.CENTER);

        
        docNameTextField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String newName = docNameTextField.getText();
                docNameTextField.setText("");
                if(newName.contains("|") || newName.contains("~")){
                    WebOptionPane.showMessageDialog ( null, "pipes and tildas cannot be used in document names", "Error", WebOptionPane.ERROR_MESSAGE );
                    //WebOptionPane.showMessageDialog(editor.getTabbedPane(), "pipes and tildas cannot be used in document names");
                    return;
                }
                editor.sendMessage(editor.createControlMessage("requestNew",
                        -1, newName));
            }
        });

        createButton = new WebButton("Create");    
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String newName = docNameTextField.getText();
                docNameTextField.setText("");
                if (newName.equals("Enter Document Name") || newName.equals("")) {
                    newName = "New Document";
                }
                editor.sendMessage(editor.createControlMessage("requestNew",
                        -1, newName));

            }
        });        

        docsList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    openDocument(editor);
                }
            }
        });

            
        panel2.add(createButton, BorderLayout.SOUTH);
        panel2.add(docNameTextField,BorderLayout.CENTER);

        ret.add(new GroupPanel(4, false, panel1, new WebLabel("OR", WebLabel.CENTER), panel2));
        
        return ret;
    }

    
    private void openDocument(Editor editor) {

        if (docsList.getSelectedValue().equals(
                "There are no documents on the server.")) {
            return;
        }
        int num = ((DocumentIDsAndNames) docsList.getSelectedValue()).getNum();
        String name = docsList.getSelectedValue().toString();

        if (editor.getDocIDtoDocPanel().containsKey(num)) {
            JOptionPane.showMessageDialog(editor.getTabbedPane(),
                    "That document is already open!");
            return;
        }

        editor.getUser().addDocument(num);
        WordGui newDoc = new WordGui(num, name, editor);
        newDoc.setLocationRelativeTo(null);
        newDoc.setTitle(name);
        newDoc.setVisible(true);
//        editor.getTabbedPane().add(name, newDoc);
        editor.getDocIDtoDocPanel().put(num, newDoc);
        editor.sendMessage(editor.createControlMessage("load", num, name));
//        editor.getTabbedPane().remove(editor.getTabbedPane().getTabCount() - 2);
//        editor.getTabbedPane().add("+", new NewDocPanel(editor));
//        editor.initTabComponent(editor.getTabbedPane().getTabCount() - 2);
//        editor.getTabbedPane().setSelectedIndex(editor.getTabbedPane().getTabCount() - 2);
    }

    /**
     * Wrapper class for displaying elements in the JList.
     * 
     */
    class DocumentIDsAndNames extends Object {
        private final int num;
        private final String name;

        public DocumentIDsAndNames(String ob) {
            String[] broken = ob.split("~");
            this.num = Integer.parseInt(broken[0]);
            this.name = broken[1];
        }

        /**
         * Getter for the docID
         * @return num
         */
        public int getNum() {
            return this.num;
        }

        /**
         * toString() method is overriden to diplay JList correctly
         */
        @Override
        public String toString() {
            return name;
        }
    }
}
