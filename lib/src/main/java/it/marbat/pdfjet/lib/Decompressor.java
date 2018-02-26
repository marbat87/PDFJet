package it.marbat.pdfjet.lib;

import java.io.ByteArrayOutputStream;
import java.util.zip.Inflater;


@SuppressWarnings("WeakerAccess")
class Decompressor extends Inflater {

    private ByteArrayOutputStream bos = null;

    
    public Decompressor(byte[] data) throws Exception {
    	super.setInput(data);
        bos = new ByteArrayOutputStream(data.length);

	    byte[] buf = new byte[2048];
	    while (!super.finished()) {
	        int count = super.inflate(buf);
	        bos.write(buf, 0, count);
	    }
    }
    
    
    public byte[] getDecompressedData() {
    	return bos.toByteArray();
    }

}
