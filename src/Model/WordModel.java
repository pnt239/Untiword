/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import java.io.FileNotFoundException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 *
 * @author Untitled25364
 */
public class WordModel {
    private XWPFDocument syncVer;
    private String name;
    private FileOutputStream fo;
    public WordModel(String name){
        syncVer = new XWPFDocument();        
        try{
            String[] splitName = name.split("\\.");            
            if(splitName[splitName.length - 1].equals("doc") || 
                    splitName[splitName.length - 1].equals("docx"))
            {
                this.name = name;
                fo = new FileOutputStream(name);
            } else {
                this.name = name + ".docx";
                fo = new FileOutputStream(name + ".docx");
            }            
            syncVer.write(fo);
            fo.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void updateWordDoc(String textString) throws FileNotFoundException, IOException{
        
        XWPFParagraph newParagraph = syncVer.createParagraph();
        newParagraph.createRun().setText(textString);
        fo = new FileOutputStream(name);        
        syncVer.write(fo);
        fo.close();
        
    }
    
    
}
