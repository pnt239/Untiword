/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import java.io.FileOutputStream;
/**
 *
 * @author Untitled25364
 */
public class WordModel {
    public WordModel(String name){
        XWPFDocument doc = new XWPFDocument();
        try{
            String[] splitName = name.split("\\.");
            FileOutputStream fo;
            if(splitName[splitName.length - 1].equals("doc") || 
                    splitName[splitName.length - 1].equals("docx"))
            {
                fo = new FileOutputStream(name);
            } else {
                fo = new FileOutputStream(name + ".docx");
            }            
            doc.write(fo);
            fo.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
}
