/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import java.io.FileOutputStream;

/**
 *
 * @author Untitled25364
 */
public class Word {
    public static void main(String[] args){
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph p1 = doc.createParagraph();
        XWPFRun r1 = p1.createRun();
        
        r1.setText("New text");
        try{
            FileOutputStream fo = new FileOutputStream("Test.docx");
            doc.write(fo);
            fo.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
