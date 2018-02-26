package it.marbat.pdfjet.lib;

import java.util.ArrayList;
import java.util.List;

/**
 *  Please see Example_45
 */
@SuppressWarnings({"unused", "SameParameterValue"})
public class PlainText implements Drawable {

    private Font font;
    private String[] textLines;
    private float fontSize;
    private float x;
    private float y;
    private float w = 500f;
    private float leading;
    private int backgroundColor = Color.white;
    private int borderColor = Color.white;
    private int textColor = Color.black;
    private List<float[]> endOfLinePoints = null;

    private String language = null;
    private String altDescription = null;
    private String actualText = null;


    public PlainText(Font font, String[] textLines) {
        this.font = font;
        this.fontSize = font.getSize();
        this.textLines = textLines;
        this.endOfLinePoints = new ArrayList<>();
        StringBuilder buf = new StringBuilder();
        for (String str : textLines) {
            buf.append(str);
            buf.append(' ');
        }
        this.altDescription = buf.toString();
        this.actualText = buf.toString();
    }


    public PlainText setFontSize(float fontSize) {
        this.fontSize = fontSize;
        return this;
    }


    public PlainText setLocation(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }


    public PlainText setWidth(float w) {
        this.w = w;
        return this;
    }


    public PlainText setLeading(float leading) {
        this.leading = leading;
        return this;
    }


    public PlainText setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }


    public PlainText setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        return this;
    }


    public PlainText setTextColor(int textColor) {
        this.textColor = textColor;
        return this;
    }


    public List<float[]> getEndOfLinePoints() {
        return endOfLinePoints;
    }


    /**
     *  Draws this PlainText on the specified page.
     *
     *  @param page the page to draw this PlainText on.
     *  @return x and y coordinates of the bottom right corner of this component.
     *  @throws Exception exception
     */
    public float[] drawOn(Page page) throws Exception {
        float originalSize = font.getSize();
        font.setSize(fontSize);
        float y_text = y + font.getAscent();

        page.addBMC(StructElem.SPAN, language, Single.space, Single.space);
        page.setBrushColor(backgroundColor);
        leading = font.getBodyHeight();
        float h = font.getBodyHeight() * textLines.length;
        page.fillRect(x, y, w, h);
        page.setPenColor(borderColor);
        page.setPenWidth(0f);
        page.drawRect(x, y, w, h);
        page.addEMC();

        page.addBMC(StructElem.SPAN, language, altDescription, actualText);
        page.setTextStart();
        page.setTextFont(font);
        page.setBrushColor(textColor);
        page.setTextLeading(leading);
        page.setTextLocation(x, y_text);
        for (String str : textLines) {
            if (font.skew15) {
                setTextSkew(page, 0.26f, x, y_text);
            }
            page.println(str);
            endOfLinePoints.add(new float[] { x + font.stringWidth(str), y_text });
            y_text += leading;
        }
        page.setTextEnd();
        page.addEMC();

        font.setSize(originalSize);

        return new float[] { x + w, y + h };
    }


    private void setTextSkew(
            Page page, float skew, float x, float y) throws Exception {
        page.append(1f);
        page.append(' ');
        page.append(0f);
        page.append(' ');
        page.append(skew);
        page.append(' ');
        page.append(1f);
        page.append(' ');
        page.append(x);
        page.append(' ');
        page.append(page.height - y);
        page.append(" Tm\n");
    }

}   // End of PlainText.java
