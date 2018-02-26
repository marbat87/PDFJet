package it.marbat.pdfjet.lib;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;


/**
 *  Used to embed file objects.
 *  The file objects must added to the PDF before drawing on the first page.
 *
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class EmbeddedFile {

    protected int objNumber = -1;
    protected String fileName = null;


    public EmbeddedFile(PDF pdf, String fileName, InputStream stream, boolean compress) throws Exception {
        this.fileName = fileName;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	byte[] buf = new byte[2048];
    	int number;
    	while ((number = stream.read(buf, 0, buf.length)) > 0) {
    		baos.write(buf, 0, number);
    	}
        stream.close();

        if (compress) {
            buf = baos.toByteArray();
            baos = new ByteArrayOutputStream();
            DeflaterOutputStream dos = new DeflaterOutputStream(baos, new Deflater());
  		    dos.write(buf, 0, buf.length);
            dos.finish();
        }

        pdf.newobj();
        pdf.append("<<\n");
        pdf.append("/Type /EmbeddedFile\n");
        if (compress) {
            pdf.append("/Filter /FlateDecode\n");
        }
        pdf.append("/Length ");
        pdf.append(baos.size());
        pdf.append("\n");
        pdf.append(">>\n");
        pdf.append("stream\n");
        pdf.append(baos);
        pdf.append("\nendstream\n");
        pdf.endobj();

        pdf.newobj();
        pdf.append("<<\n");
        pdf.append("/Type /Filespec\n");
        pdf.append("/F (");
        pdf.append(fileName);
        pdf.append(")\n");
        pdf.append("/EF <</F ");
        pdf.append(pdf.objNumber - 1);
        pdf.append(" 0 R>>\n");
        pdf.append(">>\n");
        pdf.endobj();

        this.objNumber = pdf.objNumber;

        pdf.embeddedFiles.add(this);
    }


    public String getFileName() {
        return fileName;
    }

}   // End of EmbeddedFile.java
