package it.marbat.pdfjet.lib;


/**
 *  Used to attach file objects.
 *
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class FileAttachment {

    protected int objNumber = -1;
    protected PDF pdf = null;
    protected EmbeddedFile embeddedFile = null;
    protected String icon = "PushPin";
    protected String title = "";
    protected String contents = "Right mouse click or double click on the icon to save the attached file.";
    protected float x = 0f;
    protected float y = 0f;
    protected float h = 24f;


    public FileAttachment(PDF pdf, EmbeddedFile file) {
        this.pdf = pdf;
        this.embeddedFile = file;
    }


    public void setLocation(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public void setIconPushPin() {
        this.icon = "PushPin";
    }


    public void setIconPaperclip() {
        this.icon = "Paperclip";
    }


    public void setIconSize(float height) {
        this.h = height;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setDescription(String description) {
        this.contents = description;
    }


    public void drawOn(Page page) throws Exception {
        Annotation annotation = new Annotation(
                null,
                null,
                x,
                page.height - y,
                x + h,
                page.height - (y + h),
                null,
                null,
                null);
        annotation.fileAttachment = this;
        page.annots.add(annotation);
    }

}   // End of FileAttachment.java
