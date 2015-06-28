/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.utils;

import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 *
 * @author NThanh
 */
public class Resources {

    private static final Map<String, ImageIcon> iconsCache = new HashMap<String, ImageIcon>();

    /**
     * Returns icon loaded from example icons package.
     *
     * @param path path to the icon inside icons package
     * @return loaded icon
     */
    public ImageIcon loadIcon(String path) {
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource(path));
        
        String key = path;
        if (!iconsCache.containsKey(key)) {
            iconsCache.put(key, icon);
        }
        return iconsCache.get(key);
    }
}
