package it.marbat.pdfjet.lib;

import java.util.ArrayList;
import java.util.List;


/**
 * Container for drawable objects that can be drawn on a page as part of Optional Content Group. 
 * Please see the PDF specification and Example_30 for more details.
 *
 * @author Mark Paxton
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class OptionalContentGroup {

    protected String name;
    protected int ocgNumber;
    protected int objNumber;
    protected boolean visible;
    protected boolean printable;
    protected boolean exportable;
    private List<Drawable> components;

    public OptionalContentGroup(String name) {
        this.name = name;
        this.components = new ArrayList<>();
    }

    public void add(Drawable d) {
        components.add(d);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setPrintable(boolean printable) {
        this.printable = printable;
    }

    public void setExportable(boolean exportable) {
        this.exportable = exportable;
    }

    public void drawOn(Page p) throws Exception {
        if (!components.isEmpty()) {
            p.pdf.groups.add(this);
            ocgNumber = p.pdf.groups.size();

            p.pdf.newobj();
            p.pdf.append("<<\n");
            p.pdf.append("/Type /OCG\n");
            p.pdf.append("/Name (" + name + ")\n");
            p.pdf.append("/Usage <<\n");
            if (visible) {
                p.pdf.append("/View << /ViewState /ON >>\n");
            }
            else {
                p.pdf.append("/View << /ViewState /OFF >>\n");
            }
            if (printable) {                
                p.pdf.append("/Print << /PrintState /ON >>\n");
            }
            else {
                p.pdf.append("/Print << /PrintState /OFF >>\n");                
            }
            if (exportable) {                                
                p.pdf.append("/Export << /ExportState /ON >>\n");
            }
            else {
                p.pdf.append("/Export << /ExportState /OFF >>\n");                
            }
            p.pdf.append(">>\n");
            p.pdf.append(">>\n");            
            p.pdf.endobj();

            objNumber = p.pdf.objNumber;

            p.append("/OC /OC");
            p.append(ocgNumber);
            p.append(" BDC\n");
            for (Drawable component : components) {
                component.drawOn(p);
            }
            p.append("\nEMC\n");
        }
    }

}   // End of OptionalContentGroup.java
