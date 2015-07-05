/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.model;

/**
 *
 * @author NThanh
 */
public class DocumentIDsAndNames {

    private final int num;
    private final String name;

    public DocumentIDsAndNames(String ob) {
        String[] broken = ob.split("~");
        this.num = Integer.parseInt(broken[0]);
        this.name = broken[1];
    }

    /**
     * Getter for the docID
     *
     * @return num
     */
    public int getNum() {
        return this.num;
    }

    /**
     * toString() method is overriden to diplay JList correctly
     * @return 
     */
    @Override
    public String toString() {
        return name;
    }
}
