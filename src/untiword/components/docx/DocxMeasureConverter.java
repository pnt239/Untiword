/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package untiword.components.docx;

import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 *
 * @author NThanh
 */
public class DocxMeasureConverter {
    
    public static BigInteger inchToDxa(float inch) {
        return BigDecimal.valueOf(inch).multiply(BigDecimal.valueOf(1440)).toBigInteger();
    }
    
    public static float dxaToInch(BigInteger dxa) {
        BigDecimal fdxa = new BigDecimal(dxa);
        BigDecimal result = fdxa.divide(BigDecimal.valueOf(1440), 10, RoundingMode.HALF_UP);
        return result.floatValue();
    }
    
    public static int dxaToPixel(BigInteger dxa) {
        return (int)(dxaToInch(dxa) * (float)getDPI());
    }
    
    public static int getDPI() {
          return GraphicsEnvironment.isHeadless() ? 96 :
                    Toolkit.getDefaultToolkit().getScreenResolution();
    }
    
    public static int pointToPixel(int point) {
        float inch = point * 1.0F / 72.0F;
        return Math.round(inch * getDPI());
    }
}
