package it.marbat.pdfjet.lib;

/**
 * Interface that is required for components that can be drawn on a PDF page as part of Optional Content Group. 
 *
 * @author Mark Paxton
 */
@SuppressWarnings("UnnecessaryInterfaceModifier")
public interface Drawable {

    /**
     *  Draw the component implementing this interface on the PDF page.
     *
     *  @param page the page to draw on.
     *  @return x and y coordinates of the bottom right corner of this component.
     *  @throws Exception exception
     */
    public float[] drawOn(Page page) throws Exception;

}
