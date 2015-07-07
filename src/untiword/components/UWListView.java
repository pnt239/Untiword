/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.components;

import com.alee.laf.list.WebList;
import javax.swing.ListModel;

/**
 *
 * @author NThanh
 */
public class UWListView extends WebList {
    public UWListView() {
        super();
        
        initialize();
    }
    
    public UWListView(ListModel model) {
        super(model);
        
        initialize();
    }
    
    private void initialize() {
        // Standard settings
        setLayoutOrientation ( WebList.HORIZONTAL_WRAP );
        setVisibleRowCount ( 0 );

        // Files list renderer
        setCellRenderer ( new UWListViewCellRenderer ( this ) );
    }
}
