/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.components;

import javax.swing.ImageIcon;

/**
 *
 * @author NThanh
 */
public class UWDocumentElement {

    /**
     * Cached element thumbnail icon for enabled state.
     */
    private ImageIcon enabledThumbnail = null;

    /**
     * Cached element thumbnail icon for disabled state.
     */
    private ImageIcon disabledThumbnail = null;
    
    private String documentName = "";

    /**
     * Constructs element without file.
     */
    public UWDocumentElement() {
        super();
    }

    /**
     * Constructs element with specified file.
     * @param icon
     * @param name
     */
    public UWDocumentElement(ImageIcon icon, String name) {
        super();
        this.enabledThumbnail = icon;
        this.documentName = name;
    }
    
    public String getDocumentName() {
        return this.documentName;
    }
    
    public void setDocumentName(String name) {
        this.documentName = name;
    }

    /**
     * Returns cached element thumbnail icon for enabled state.
     *
     * @return cached element thumbnail icon for enabled state
     */
    public ImageIcon getEnabledThumbnail() {
        return enabledThumbnail;
    }

    /**
     * Sets cached element thumbnail icon for enabled state.
     *
     * @param enabledThumbnail new cached element thumbnail icon for enabled
     * state
     */
    public void setEnabledThumbnail(final ImageIcon enabledThumbnail) {
        this.enabledThumbnail = enabledThumbnail;
    }

    /**
     * Returns cached element thumbnail icon for disabled state.
     *
     * @return cached element thumbnail icon for disabled state
     */
    public ImageIcon getDisabledThumbnail() {
        return disabledThumbnail;
    }

    /**
     * Sets cached element thumbnail icon for disabled state.
     *
     * @param disabledThumbnail new cached element thumbnail icon for disabled
     * state
     */
    public void setDisabledThumbnail(final ImageIcon disabledThumbnail) {
        this.disabledThumbnail = disabledThumbnail;
    }
}
