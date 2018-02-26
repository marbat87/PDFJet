package it.marbat.pdfjet.lib;


/**
 *  Please see Example_45
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class Field {

    float x;
    String[] values;
    String[] altDescription;
    String[] actualText;
    boolean format = false;


    public Field(float x, String[] values) {
        this(x, values, false);
    }

    public Field(float x, String[] values, boolean format) {
        this.x = x;
        this.values = values;
        this.format = format;
        if (values != null) {
            this.altDescription = new String[values.length];
            this.actualText     = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                this.altDescription[i] = values[i];
                this.actualText[i]     = values[i];
            }
        }
    }


    public Field setAltDescription(String altDescription) {
        this.altDescription[0] = altDescription;
        return this;
    }


    public Field setActualText(String actualText) {
        this.actualText[0] = actualText;
        return this;
    }

}
