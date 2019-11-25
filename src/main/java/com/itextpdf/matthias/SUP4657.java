package com.itextpdf.matthias;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.io.IOException;

public class SUP4657 {
    public static int LEFT_DESC;
    public static int RIGHT_DESC;
    public static int LEFT_ASC;
    public static int RIGHT_ASC;
    public static float ANGLE = 135;

    public static void main(String[] args) throws IOException, DocumentException {
        PdfReader reader = new PdfReader("src/main/resources/in.pdf");
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("src/main/resources/result.pdf"));
        PdfContentByte over;
        over = stamper.getOverContent(1);

        //Set Line Width of ContentByte using SetLineWidth function of PdfContentByte
        //over.setLineWidth(1);
        over.resetRGBColorStroke();
        over.setRGBColorStroke(100, 0, 0);

        //Create Line using given parameters by using functions MoveTo anf LineTo of PdfContentByte
//        over.moveTo(20, 20);
//        over.lineTo(500, 500);
//        over.moveTo(20, 20);
//        over.lineTo(50, 20);
//        over.moveTo(20, 20);
//        over.lineTo(20, 50);


        //new SUP4657().drawArrow(over, 400, 400, 3f, true);
        new SUP4657().dynamicArrow(over, 100, 100, 123, 326, 1f);

        over.stroke();
        over.saveState();
        over.restoreState();
        stamper.close();
    }

    public void drawArrow(PdfContentByte pcb, float x, float y, float linewidth, boolean fromLeft) {
        float offset = linewidth / 2;
        pcb.setLineWidth(linewidth);

        pcb.moveTo(x, y);
        if (fromLeft) {
            pcb.lineTo(x - 100, y + 100);

            pcb.moveTo(x + offset, y);
            pcb.lineTo(x - 20, y);

            pcb.moveTo(x, y);
            pcb.lineTo(x, y + 20);

        } else {
            pcb.lineTo(x + 100, y + 100);

            pcb.moveTo(x, y);
            pcb.lineTo(x + 20, y);

            pcb.moveTo(x, y);
            pcb.lineTo(x, y + 20);
        }
    }

    public void dynamicArrow(PdfContentByte pcb, float xStart, float yStart, float xEnd, float yEnd, float lineWidth) {
        float offset = lineWidth / 2;

        double pythA, pythB, pythC;
        if (xEnd > xStart) {
            pythA = xEnd - xStart;
        } else {
            pythA = xStart - xEnd;
        }

        if (yEnd > yStart) {
            pythB = yEnd - yStart;
        } else {
            pythB = yStart - yEnd;
        }

        float lineLength = (float) Math.sqrt((Math.pow(xEnd - xStart, 2) + Math.pow(yEnd - yStart, 2)));
        float ratio = (lineLength / 10) / lineLength;
        double smallXLen = pythA * ratio;
        double smallYLen = pythB * ratio;

        double smallX = xEnd + smallXLen;
        double smallY = yEnd + smallYLen;


        System.out.printf("small x:[%.2f] || small y:[%.2f]%n", smallX, smallY);

        pythC = Math.sqrt(Math.pow(pythA, 2) + Math.pow(pythB, 2));
        double angle = Math.asin(pythB / pythC);

        System.out.println(Math.toDegrees(angle));
        pcb.setLineWidth(lineWidth);

        pcb.moveTo(xStart, yStart);
        pcb.lineTo(xEnd, yEnd);

        //this is correct, but needs origin translation
        float newX = (float) ((xEnd * Math.cos(ANGLE)) - (yEnd * Math.cos(ANGLE)));
        float newY = (float) ((yEnd * Math.cos(ANGLE)) + (xEnd * Math.cos(ANGLE)));

        lineLength = (float) Math.sqrt((Math.pow(xEnd - newX, 2) + Math.pow(yEnd - newY, 2)));

        float legA = xEnd > newX ? xEnd - newX : newX - xEnd;
        float legB = yEnd > newY ? yEnd - newY : newY - yEnd;

        ratio = 0.2f;

        smallXLen = legA * ratio;
        smallYLen = legB * ratio;

        pcb.moveTo(xEnd, yEnd);
        pcb.lineTo(xEnd - smallXLen, yEnd - smallYLen);


//        newX = (float) ((xEnd * Math.cos(315)) - (yEnd * Math.cos(315)));
//        newY = (float) ((yEnd * Math.cos(315)) + (xEnd * Math.cos(315)));
//
//        lineLength = (float) Math.sqrt((Math.pow(xEnd - newX, 2) + Math.pow(yEnd - newY, 2)));
//        legA = xEnd > newX ? xEnd - newX : newX - xEnd;
//        legB = yEnd > newY ? yEnd - newY : newY - yEnd;
//
//        //ratio = (lineLength / 10) / lineLength;
//
//        smallXLen = legA * ratio;
//        smallYLen = legB * ratio;
//        pcb.moveTo(xEnd, yEnd);
//        pcb.lineTo(xEnd - smallXLen, yEnd - smallYLen);

    }

    private void newTry(){

    }


}
