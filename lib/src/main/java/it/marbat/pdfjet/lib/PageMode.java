package it.marbat.pdfjet.lib;


/**
 *  Used to specify the PDF page mode.
 *
 */
@SuppressWarnings("unused")
public class PageMode {
    public static final String USE_NONE = "UseNone";            // Neither document outline nor thumbnail images visible
    public static final String USE_OUTLINES = "UseOutlines";    // Document outline visible
    public static final String USE_THUMBS = "UseThumbs";        // Thumbnail images visible
    public static final String FULL_SCREEN = "FullScreen";      // Full-screen mode
    public static final String USE_OC = "UseOC";                // (PDF 1.5) Optional content group panel visible
    public static final String USE_ATTACHMENTS = "UseAttachements";
}
