/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.main;

import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBElement;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.vml.CTFill;
import org.docx4j.wml.CTBackground;
import org.docx4j.wml.ObjectFactory;

/**
 *
 * @author NThanh
 */
public class TestDocx {

    static String DOCX_OUT;

    public static void main(String[] args) throws Exception {

        // The image to add
        imageFile = new File("E:\\Images\\11214129_1844073689150265_5364286538807002334_n.jpg");

        // Save it to
        DOCX_OUT = "D:\\OUT_BackgroundImage.docx";

        TestDocx sample = new TestDocx();
        sample.addBackground();
    }

    static ObjectFactory factory = Context.getWmlObjectFactory();
    static File imageFile;

    private byte[] image;
    private WordprocessingMLPackage wordMLPackage;

    public void addBackground() throws Exception {

        image = this.getImage();

        wordMLPackage = WordprocessingMLPackage.createPackage();

        BinaryPartAbstractImage imagePartBG = BinaryPartAbstractImage.createImagePart(wordMLPackage, image);

        wordMLPackage.getMainDocumentPart().getJaxbElement().setBackground(
                createBackground(
                        imagePartBG.getRelLast().getId()));

        wordMLPackage.getMainDocumentPart().addParagraphOfText("To see your background, go to 'Web layout' or 'Full screen reading' document view!");

        File f = new File(DOCX_OUT);
        wordMLPackage.save(f);

    }

    private static CTBackground createBackground(String rId) {

        org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();

        CTBackground background = wmlObjectFactory.createCTBackground();
        background.setColor("FFFFFF");
        org.docx4j.vml.ObjectFactory vmlObjectFactory = new org.docx4j.vml.ObjectFactory();
        // Create object for background (wrapped in JAXBElement)
        org.docx4j.vml.CTBackground background2 = vmlObjectFactory
                .createCTBackground();
        JAXBElement<org.docx4j.vml.CTBackground> backgroundWrapped = vmlObjectFactory
                .createBackground(background2);
        background.getAnyAndAny().add(backgroundWrapped);
        background2.setTargetscreensize("1024,768");
        background2.setVmlId("_x0000_s1025");
        background2.setBwmode(org.docx4j.vml.officedrawing.STBWMode.WHITE);
        // Create object for fill
        CTFill fill = vmlObjectFactory.createCTFill();
        background2.setFill(fill);
        fill.setTitle("Alien 1");
        fill.setId(rId);
        fill.setType(org.docx4j.vml.STFillType.FRAME);
        fill.setRecolor(org.docx4j.vml.STTrueFalse.T);

        return background;
    }

    private byte[] getImage() throws IOException {

        // Our utility method wants that as a byte array
        java.io.InputStream is = new java.io.FileInputStream(imageFile);
        long length = imageFile.length();
	    // You cannot create an array using a long type.
        // It needs to be an int type.
        if (length > Integer.MAX_VALUE) {
            System.out.println("File too large!!");
        }
        byte[] bytes = new byte[(int) length];
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }
        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            System.out.println("Could not completely read file " + imageFile.getName());
        }
        is.close();

        return bytes;
    }

}
