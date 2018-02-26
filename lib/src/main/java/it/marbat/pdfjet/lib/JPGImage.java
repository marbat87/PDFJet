package it.marbat.pdfjet.lib;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


/**
 * Used to embed JPG images in the PDF document.
 */
@SuppressWarnings({"unused", "WeakerAccess", "ResultOfMethodCallIgnored"})
class JPGImage {

    static final char M_SOF0 = (char) 0x00C0;  // Start Of Frame N
    static final char M_SOF1 = (char) 0x00C1;  // N indicates which compression process
    static final char M_SOF2 = (char) 0x00C2;  // Only SOF0-SOF2 are now in common use
    static final char M_SOF3 = (char) 0x00C3;
    static final char M_SOF5 = (char) 0x00C5;  // NB: codes C4 and CC are NOT SOF markers
    static final char M_SOF6 = (char) 0x00C6;
    static final char M_SOF7 = (char) 0x00C7;
    static final char M_SOF9 = (char) 0x00C9;
    static final char M_SOF10 = (char) 0x00CA;
    static final char M_SOF11 = (char) 0x00CB;
    static final char M_SOF13 = (char) 0x00CD;
    static final char M_SOF14 = (char) 0x00CE;
    static final char M_SOF15 = (char) 0x00CF;

    int width;
    int height;
    long size;
    int colorComponents;
    byte[] data;

    private InputStream stream;


    public JPGImage(InputStream inputStream) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[2048];
        int count;
        while ((count = inputStream.read(buf, 0, buf.length)) > 0) {
            baos.write(buf, 0, count);
        }
        inputStream.close();
        data = baos.toByteArray();
        readJPGImage(new ByteArrayInputStream(data));
    }


    protected InputStream getInputStream() {
        return this.stream;
    }


    protected int getWidth() {
        return this.width;
    }


    protected int getHeight() {
        return this.height;
    }


    protected long getFileSize() {
        return this.size;
    }


    protected int getColorComponents() {
        return this.colorComponents;
    }


    protected byte[] getData() {
        return this.data;
    }


    private void readJPGImage(InputStream is) throws Exception {
        char ch1 = (char) is.read();
        char ch2 = (char) is.read();
        size += 2;
        if (ch1 == 0x00FF && ch2 == 0x00D8) {
            boolean foundSOFn = false;
            while (true) {
                char ch = nextMarker(is);
                switch (ch) {
                    // Note that marker codes 0xC4, 0xC8, 0xCC are not,
                    // and must not be treated as SOFn. C4 in particular
                    // is actually DHT.
                    case M_SOF0:    // Baseline
                    case M_SOF1:    // Extended sequential, Huffman
                    case M_SOF2:    // Progressive, Huffman
                    case M_SOF3:    // Lossless, Huffman
                    case M_SOF5:    // Differential sequential, Huffman
                    case M_SOF6:    // Differential progressive, Huffman
                    case M_SOF7:    // Differential lossless, Huffman
                    case M_SOF9:    // Extended sequential, arithmetic
                    case M_SOF10:   // Progressive, arithmetic
                    case M_SOF11:   // Lossless, arithmetic
                    case M_SOF13:   // Differential sequential, arithmetic
                    case M_SOF14:   // Differential progressive, arithmetic
                    case M_SOF15:   // Differential lossless, arithmetic
                        // Skip 3 bytes to get to the image height and width
                        is.read();
                        is.read();
                        is.read();
                        size += 3;
                        height = readTwoBytes(is);
                        width = readTwoBytes(is);
                        colorComponents = is.read();
                        size++;
                        foundSOFn = true;
                        break;

                    default:
                        skipVariable(is);
                        break;
                }

                if (foundSOFn) {
                    while (is.read() != -1) {
                        size++;
                    }
                    break;
                }
            }
        } else {
            throw new Exception();
        }
// System.out.println("size == " + size);
    }


    private int readTwoBytes(InputStream is) throws Exception {
        int value = is.read();
        value <<= 8;
        value |= is.read();
        size += 2;
        return value;
    }


    // Find the next JPEG marker and return its marker code.
    // We expect at least one FF byte, possibly more if the compressor
    // used FFs to pad the file.
    // There could also be non-FF garbage between markers. The treatment
    // of such garbage is unspecified; we choose to skip over it but
    // emit a warning msg.
    // NB: this routine must not be used after seeing SOS marker, since
    // it will not deal correctly with FF/00 sequences in the compressed
    // image data...
    private char nextMarker(InputStream is) throws Exception {
        int discarded_bytes = 0;
        char ch;

        // Find 0xFF byte; count and skip any non-FFs.
        ch = (char) is.read();
        size++;
        while (ch != 0x00FF) {
            discarded_bytes++;
            ch = (char) is.read();
            size++;
        }

        // Get marker code byte, swallowing any duplicate FF bytes.
        // Extra FFs are legal as pad bytes, so don't count them in discarded_bytes.
        do {
            ch = (char) is.read();
            size++;
        } while (ch == 0x00FF);

        if (discarded_bytes != 0) {
            throw new Exception();
        }

        return ch;
    }


    // Most types of marker are followed by a variable-length parameter
    // segment. This routine skips over the parameters for any marker we
    // don't otherwise want to process.
    // Note that we MUST skip the parameter segment explicitly in order
    // not to be fooled by 0xFF bytes that might appear within the
    // parameter segment such bytes do NOT introduce new markers.
    private void skipVariable(InputStream is) throws Exception {
        // Get the marker parameter length count
        int length = readTwoBytes(is);

        // Length includes itself, so must be at least 2
        if (length < 2) {
            throw new Exception();
        }
        length -= 2;

        // Skip over the remaining bytes
        while (length > 0) {
            is.read();
            size++;
            length--;
        }
    }

}   // End of JPGImage.java
