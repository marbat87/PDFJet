package it.marbat.pdfjet.lib;


/**
 *  Used to create PDF annotation objects.
 *
 *
 */
@SuppressWarnings("WeakerAccess")
class Annotation {

    protected int objNumber;
    protected String uri;
    protected String key;
    protected float x1;
    protected float y1;
    protected float x2;
    protected float y2;

    protected String language = null;
    protected String altDescription = null;
    protected String actualText = null;

    protected FileAttachment fileAttachment = null;


    /**
     *  This class is used to create annotation objects.
     *
     *  @param uri the URI string.
     *  @param key the destination name.
     *  @param x1 the x coordinate of the top left corner.
     *  @param y1 the y coordinate of the top left corner.
     *  @param x2 the x coordinate of the bottom right corner.
     *  @param y2 the y coordinate of the bottom right corner.
     *
     */
    protected Annotation(
            String uri,
            String key,
            float x1,
            float y1,
            float x2,
            float y2,
            String language,
            String altDescription,
            String actualText) {
        this.uri = uri;
        this.key = key;
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.language = language;
        this.altDescription = (altDescription == null) ? uri : altDescription;
        this.actualText = (actualText == null) ? uri : actualText;
    }

}
