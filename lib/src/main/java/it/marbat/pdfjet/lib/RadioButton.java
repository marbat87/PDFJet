package it.marbat.pdfjet.lib;


/**
 *  Creates a RadioButton, which can be set selected or unselected.
 *
 */
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class RadioButton implements Drawable {

    private boolean selected = false;
    private float x;
    private float y;
    private float r1;
    private float r2;
    private float penWidth;
    private Font font = null;
    private String label = "";
    private String uri = null;

    private String language = null;
    private String altDescription = Single.space;
    private String actualText = Single.space;


    /**
     *  Creates a RadioButton that is not selected.
     *
     */
    public RadioButton(Font font, String label) {
        this.font = font;
        this.label = label;
    }


    /**
     *  Sets the font size to use for this text line.
     *
     *  @param fontSize the fontSize to use.
     *  @return this RadioButton.
     */
    public RadioButton setFontSize(float fontSize) {
        this.font.setSize(fontSize);
        return this;
    }


    /**
     *  Set the x,y position on the Page.
     *
     *  @param x the x coordinate on the Page.
     *  @param y the y coordinate on the Page.
     *  @return this RadioButton.
     */
    public RadioButton setPosition(float x, float y) {
        return setLocation(x, y);
    }


    /**
     *  Set the x,y location on the Page.
     *
     *  @param x the x coordinate on the Page.
     *  @param y the y coordinate on the Page.
     *  @return this RadioButton.
     */
    public RadioButton setLocation(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }


    /**
     *  Sets the URI for the "click text line" action.
     *
     *  @param uri the URI.
     *  @return this RadioButton.
     */
    public RadioButton setURIAction(String uri) {
        this.uri = uri;
        return this;
    }


    /**
     *  Selects or deselects this radio button.
     *
     *  @param selected the selection flag.
     *  @return this RadioButton.
     */
    public RadioButton select(boolean selected) {
        this.selected = selected;
        return this;
    }


    /**
     *  Sets the alternate description of this radio button.
     *
     *  @param altDescription the alternate description of the radio button.
     *  @return this RadioButton.
     */
    public RadioButton setAltDescription(String altDescription) {
        this.altDescription = altDescription;
        return this;
    }


    /**
     *  Sets the actual text for this radio button.
     *
     *  @param actualText the actual text for the radio button.
     *  @return this RadioButton.
     */
    public RadioButton setActualText(String actualText) {
        this.actualText = actualText;
        return this;
    }


    /**
     *  Draws this RadioButton on the specified Page.
     *
     *  @param page the Page where the RadioButton is to be drawn.
     *  @return x and y coordinates of the bottom right corner of this component.
     *  @throws Exception exception
     */
    public float[] drawOn(Page page) throws Exception {
        page.addBMC(StructElem.SPAN, language, altDescription, actualText);

        this.r1 = font.getAscent()/2;
        this.r2 = r1/2;
        this.penWidth = r1/10;

        float y_box = y - font.getAscent();
        page.setPenWidth(1f);
        page.setPenColor(Color.black);
        page.setLinePattern("[] 0");
        page.setBrushColor(Color.black);
        page.drawCircle(x + r1, y_box + r1, r1);
        
        if (this.selected) {
            page.drawCircle(x + r1, y_box + r1, r2, Operation.FILL);
        }

        if (uri != null) {
            page.setBrushColor(Color.blue);
        }
        page.drawString(font, label, x + 3*r1, y);
        page.setPenWidth(0f);
        page.setBrushColor(Color.black);

        page.addEMC();

        if (uri != null) {
            // Please note: The font descent is a negative number.
            page.addAnnotation(new Annotation(
                    uri,
                    null,
                    x + 3*r1,
                    page.height - y,
                    x + 3*r1 + font.stringWidth(label),
                    page.height - (y - font.getAscent()),
                    language,
                    altDescription,
                    actualText));
        }

        return new float[] { x + 6*r1 + font.stringWidth(label), y + font.getDescent() };
    }

}   // End of RadioButton.java
