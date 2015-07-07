/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.components;

import com.alee.extended.layout.AbstractLayoutManager;
import com.alee.laf.label.WebLabel;
import com.alee.laf.list.WebListCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author NThanh
 */
public class UWListViewCellRenderer extends WebListCellRenderer {

    public static final Dimension iconCellSize = new Dimension(90, 90);
    public static final Insets iconCellMargin = new Insets(5, 5, 8, 5);

    /**
     * Image thumbnails size.
     */
    public static final int thumbSize = 64;

    /**
     * Image side length.
     */
    public static final int imageSide = 64;

    /**
     * Gap between renderer elements.
     */
    public static final int gap = 4;

    protected UWListView listView;

    /**
     * Thumbnail icon label.
     */
    protected WebLabel iconLabel;

    /**
     * File name label.
     */
    protected WebLabel nameLabel;

    public UWListViewCellRenderer(final UWListView listView) {
        super();

        this.listView = listView;

        iconLabel = new WebLabel();
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
        iconLabel.setPreferredSize(new Dimension(imageSide, imageSide));

        nameLabel = new WebLabel();
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.PLAIN));
        nameLabel.setForeground(Color.BLACK);
        nameLabel.setVerticalAlignment(JLabel.CENTER);

        setLayout(new ListViewCellLayout());
        add(iconLabel);
        add(nameLabel);

        nameLabel.setHorizontalAlignment(JLabel.CENTER);
        listView.setFixedCellWidth(iconCellSize.width);
        listView.setFixedCellHeight(iconCellSize.height);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent ( list, "", index, isSelected, cellHasFocus );
        
        final UWDocumentElement element = ( UWDocumentElement ) value;
        
        // Proper margin
        setMargin(iconCellMargin);
        
        // Renderer icon
        final ImageIcon thumbnail = element.getEnabledThumbnail();
        iconLabel.setIcon ( thumbnail );
        
        // Updating name elements
        nameLabel.setText ( element.getDocumentName());
        
        return this;
    }

    /**
     * Custom layout for file list cell element.
     */
    protected class ListViewCellLayout extends AbstractLayoutManager {

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return iconCellSize;
        }

        @Override
        public void layoutContainer(Container parent) {
            // Constants for futher layout calculations
            final boolean ltr = true;
            final Insets i = getInsets();
            final boolean hasName = nameLabel.getText() != null;

            // Updating elements visibility
            nameLabel.setVisible(hasName);

            // Top-middle icon
            final int cw = iconCellSize.width - i.left - i.right;
            iconLabel.setBounds(i.left + cw / 2 - 27, i.top, imageSide, imageSide);

            // Name element
            if (hasName) {
                final int ny = i.top + imageSide + gap;
                nameLabel.setBounds(i.left, ny, cw, iconCellSize.height - ny - i.bottom);
            }
        }

        /**
         * Returns description bounds for list cell.
         *
         * @return description bounds for list cell
         */
        public Rectangle getDescriptionBounds() {
            // Constants for futher size calculations
            final boolean ltr = true;
            final Insets i = getInsets();

            // Icon view
            final int ny = i.top + imageSide + gap;
            return new Rectangle(i.left, ny, iconCellSize.width - i.left - i.right, iconCellSize.height - ny - i.bottom);
        }
    }
}
