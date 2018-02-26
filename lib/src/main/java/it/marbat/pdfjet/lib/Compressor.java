package it.marbat.pdfjet.lib;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;


@SuppressWarnings("WeakerAccess")
class Compressor extends Deflater {

	ByteArrayOutputStream bos = null;


    public Compressor(byte[] image) {

    	setInput(image);
        finish();
    	
    	bos = new ByteArrayOutputStream(image.length);

        byte[] buf = new byte[2048];
        while (!finished()) {
            int count = deflate(buf);
            bos.write(buf, 0, count);
        }
        
    }


    public byte[] getCompressedData() {
        return bos.toByteArray();
    }
        
}
