package it.marbat.pdfjet.lib;

/**
 *  Encapsulates the width and height of a component.
 */
@SuppressWarnings("unused")
public class Dimension {

    protected float w;
    protected float h;

    /**
     *  Constructor for creating dimension objects.
     *
     *  @param width the width.
     *  @param height the height.
     */
    public Dimension(float width, float height) {
        this.w = width;
        this.h = height;
    }


    public float getWidth() {
        return w;
    }


    public float getHeight() {
        return h;
    }

}   // End of Dimension.java

