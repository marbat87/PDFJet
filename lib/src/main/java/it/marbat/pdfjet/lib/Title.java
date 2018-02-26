package it.marbat.pdfjet.lib;


/**
 * Please see Example_51 and Example_52
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Title {

    public TextLine prefix = null;
    public TextLine text = null;


    public Title(Font font, String title, float x, float y) {
        this.prefix = new TextLine(font);
        this.text = new TextLine(font, title);
        this.prefix.setLocation(x, y);
        this.text.setLocation(x, y);
    }


    public Title setPrefix(String text) {
        prefix.setText(text);
        return this;
    }


    public Title setOffset(float offset) {
        text.setLocation(text.x + offset, text.y);
        return this;
    }


    public void drawOn(Page page) throws Exception {
        if (!prefix.str.equals("")) {
            prefix.drawOn(page);
        }
        text.drawOn(page);
    }

}
