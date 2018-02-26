package it.marbat.pdfjet.lib;


/**
 *  Used to specify PDF structure element objects.
 *
 *
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class StructElem {
    public static final String SPAN = "Span";
    public static final String P = "P";
    public static final String LINK = "Link";

    protected int objNumber;
    protected String structure = null;
    protected int pageObjNumber;
    protected int mcid = 0;
    protected String language = null;
    protected String altDescription = null;
    protected String actualText = null;
    protected Annotation annotation = null;
}
