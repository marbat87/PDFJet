package it.marbat.pdfjet.lib;


/**
 *  Used to create rectangular boxes on a page.
 *  Also used to for layout purposes. See the placeIn method in the Image and TextLine classes.
 *
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class Box implements Drawable {

    protected float x;
    protected float y;

    private float w;
    private float h;

    private int color = Color.black;

    private float width = 0.3f;
    private String pattern = "[] 0";
    private boolean fill_shape = false;
    
    private String language = null;
    private String altDescription = Single.space;
    private String actualText = Single.space;

    protected String uri = null;
    protected String key = null;


    /**
     *  The default constructor.
     *
     */
    public Box() {
    }


    /**
     *  Creates a box object.
     *
     *  @param x the x coordinate of the top left corner of this box when drawn on the page.
     *  @param y the y coordinate of the top left corner of this box when drawn on the page.
     *  @param w the width of this box.
     *  @param h the height of this box.
     */
    public Box(double x, double y, double w, double h) {
        this.x = (float) x;
        this.y = (float) y;
        this.w = (float) w;
        this.h = (float) h;
    }


    /**
     *  Creates a box object.
     *
     *  @param x the x coordinate of the top left corner of this box when drawn on the page.
     *  @param y the y coordinate of the top left corner of this box when drawn on the page.
     *  @param w the width of this box.
     *  @param h the height of this box.
     */
    public Box(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }


    /**
     *  Sets the position of this box on the page.
     *
     *  @param x the x coordinate of the top left corner of this box when drawn on the page.
     *  @param y the y coordinate of the top left corner of this box when drawn on the page.
     */
    public Box setPosition(double x, double y) {
        return setLocation((float) x, (float) y);
    }


    /**
     *  Sets the position of this box on the page.
     *
     *  @param x the x coordinate of the top left corner of this box when drawn on the page.
     *  @param y the y coordinate of the top left corner of this box when drawn on the page.
     */
    public Box setPosition(float x, float y) {
        return setLocation(x, y);
    }


    /**
     *  Sets the location of this box on the page.
     *
     *  @param x the x coordinate of the top left corner of this box when drawn on the page.
     *  @param y the y coordinate of the top left corner of this box when drawn on the page.
     */
    public Box setLocation(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }


    /**
     *  Sets the size of this box.
     *
     *  @param w the width of this box.
     *  @param h the height of this box.
     */
    public void setSize(double w, double h) {
        this.w = (float) w;
        this.h = (float) h;
    }


    /**
     *  Sets the size of this box.
     *
     *  @param w the width of this box.
     *  @param h the height of this box.
     */
    public void setSize(float w, float h) {
        this.w = w;
        this.h = h;
    }


    /**
     *  Sets the color for this box.
     *
     *  @param color the color specified as an integer.
     */
    public void setColor(int color) {
        this.color = color;
    }


    /**
     *  Sets the width of this line.
     *
     *  @param width the width.
     */
    public void setLineWidth(double width) {
        this.width = (float) width;
    }


    /**
     *  Sets the width of this line.
     *
     *  @param width the width.
     */
    public void setLineWidth(float width) {
        this.width = width;
    }
    

    /**
     *  Sets the URI for the "click box" action.
     *
     *  @param uri the URI
     */
    public void setURIAction(String uri) {
        this.uri = uri;
    }


    /**
     *  Sets the destination key for the action.
     *
     *  @param key the destination name.
     */
    public void setGoToAction(String key) {
        this.key = key;
    }


    /**
     *  Sets the alternate description of this box.
     *
     *  @param altDescription the alternate description of the box.
     *  @return this Box.
     */
    public Box setAltDescription(String altDescription) {
        this.altDescription = altDescription;
        return this;
    }


    /**
     *  Sets the actual text for this box.
     *
     *  @param actualText the actual text for the box.
     *  @return this Box.
     */
    public Box setActualText(String actualText) {
        this.actualText = actualText;
        return this;
    }


    /**
     *  The line dash pattern controls the pattern of dashes and gaps used to stroke paths.
     *  It is specified by a dash array and a dash phase.
     *  The elements of the dash array are positive numbers that specify the lengths of
     *  alternating dashes and gaps.
     *  The dash phase specifies the distance into the dash pattern at which to start the dash.
     *  The elements of both the dash array and the dash phase are expressed in user space units.
     *  <pre>
     *  Examples of line dash patterns:
     *
     *      "[Array] Phase"     Appearance          Description
     *      _______________     _________________   ____________________________________
     *
     *      "[] 0"              -----------------   Solid line
     *      "[3] 0"             ---   ---   ---     3 units on, 3 units off, ...
     *      "[2] 1"             -  --  --  --  --   1 on, 2 off, 2 on, 2 off, ...
     *      "[2 1] 0"           -- -- -- -- -- --   2 on, 1 off, 2 on, 1 off, ...
     *      "[3 5] 6"             ---     ---       2 off, 3 on, 5 off, 3 on, 5 off, ...
     *      "[2 3] 11"          -   --   --   --    1 on, 3 off, 2 on, 3 off, 2 on, ...
     *  </pre>
     *
     *  @param pattern the line dash pattern.
     */
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }


    /**
     *  Sets the private fill_shape variable.
     *  If the value of fill_shape is true - the box is filled with the current brush color.
     *
     *  @param fill_shape the value used to set the private fill_shape variable.
     */
    public void setFillShape(boolean fill_shape) {
        this.fill_shape = fill_shape;
    }


    /**
     *  Places this box in the another box.
     *
     *  @param box the other box.
     *  @param x_offset the x offset from the top left corner of the box.
     *  @param y_offset the y offset from the top left corner of the box.
     */
    public void placeIn(
            Box box,
            double x_offset,
            double y_offset) throws Exception {
        placeIn(box, (float) x_offset, (float) y_offset);
    }


    /**
     *  Places this box in the another box.
     *
     *  @param box the other box.
     *  @param x_offset the x offset from the top left corner of the box.
     *  @param y_offset the y offset from the top left corner of the box.
     */
    public void placeIn(
            Box box,
            float x_offset,
            float y_offset) throws Exception {
        this.x = box.x + x_offset;
        this.y = box.y + y_offset;
    }


    /**
     *  Scales this box by the spacified factor.
     *
     *  @param factor the factor used to scale the box.
     */
    public void scaleBy(double factor) throws Exception {
        scaleBy((float) factor);
    }


    /**
     *  Scales this box by the spacified factor.
     *
     *  @param factor the factor used to scale the box.
     */
    public void scaleBy(float factor) throws Exception {
        this.x *= factor;
        this.y *= factor;
    }


    /**
     *  Draws this box on the specified page.
     *
     *  @param page the page to draw this box on.
     *  @return x and y coordinates of the bottom right corner of this component.
     *  @throws Exception exception
     */
    public float[] drawOn(Page page) throws Exception {
        page.addBMC(StructElem.SPAN, language, altDescription, actualText);
        page.setPenWidth(width);
        page.setLinePattern(pattern);
        if (fill_shape) {
            page.setBrushColor(color);
        }
        else {
            page.setPenColor(color);
        }
        page.moveTo(x, y);
        page.lineTo(x + w, y);
        page.lineTo(x + w, y + h);
        page.lineTo(x, y + h);
        if (fill_shape) {
            page.fillPath();
        }
        else {
            page.closePath();
        }
        page.addEMC();

        if (uri != null || key != null) {
            page.addAnnotation(new Annotation(
                    uri,
                    key,    // The destination name
                    x,
                    page.height - y,
                    x + w,
                    page.height - (y + h),
                    language,
                    altDescription,
                    actualText));
        }

        return new float[] {x + w, y + h};
    }

}   // End of Box.java
