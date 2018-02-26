package it.marbat.pdfjet.lib;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Used to create Java or .NET objects that represent the objects in PDF document.
 * See the PDF specification for more information.
 */
@SuppressWarnings({"WeakerAccess", "SameParameterValue", "unused"})
public class PDFobj {

    protected int number;           // The object number
    protected int offset;           // The object offset
    protected List<String> dict;
    protected int stream_offset;
    protected byte[] stream;        // The compressed stream
    protected byte[] data;          // The decompressed data


    /**
     * Used to create Java or .NET objects that represent the objects in PDF document.
     * See the PDF specification for more information.
     * Also see Example_19.
     *
     * @param offset the object offset in the offsets table.
     */
    public PDFobj(int offset) {
        this.offset = offset;
        this.dict = new ArrayList<>();
    }


    protected PDFobj() {
        this.dict = new ArrayList<>();
    }


    public int getNumber() {
        return this.number;
    }


    /**
     * Returns the object dictionary.
     *
     * @return the object dictionary.
     */
    public List<String> getDict() {
        return this.dict;
    }


    /**
     * Returns the uncompressed stream data.
     *
     * @return the uncompressed stream data.
     */
    public byte[] getData() {
        return this.data;
    }


    protected void setStream(byte[] pdf, int length) {
        stream = new byte[length];
        System.arraycopy(pdf, this.stream_offset, stream, 0, length);
    }


    protected void setStream(byte[] stream) {
        this.stream = stream;
    }


    protected void setNumber(int number) {
        this.number = number;
    }


    /**
     * Returns the dictionary value for the specified key.
     *
     * @param key the specified key.
     * @return the value.
     */
    public String getValue(String key) {
        for (int i = 0; i < dict.size(); i++) {
            String token = dict.get(i);
            if (token.equals(key)) {
                return dict.get(i + 1);
            }
        }
        return "";
    }


    protected List<Integer> getObjectNumbers(String key) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < dict.size(); i++) {
            String token = dict.get(i);
            if (token.equals(key)) {
                String str = dict.get(++i);
                if (str.equals("[")) {
                    while (true) {
                        str = dict.get(++i);
                        if (str.equals("]")) {
                            break;
                        }
                        numbers.add(Integer.valueOf(str));
                        ++i;    // 0
                        ++i;    // R
                    }
                } else {
                    numbers.add(Integer.valueOf(str));
                }
                break;
            }
        }
        return numbers;
    }


    public void addContent(byte[] content, Map<Integer, PDFobj> objects) {
        PDFobj obj = new PDFobj();
        obj.setNumber(Collections.max(objects.keySet()) + 1);
        obj.setStream(content);
        objects.put(obj.getNumber(), obj);

        int index = -1;
        boolean single = false;
        for (int i = 0; i < dict.size(); i++) {
            if (dict.get(i).equals("/Contents")) {
                String str = dict.get(++i);
                if (str.equals("[")) {
                    while (true) {
                        str = dict.get(++i);
                        if (str.equals("]")) {
                            index = i;
                            break;
                        }
                        ++i;    // 0
                        ++i;    // R
                    }
                } else {
                    // Single content object
                    index = i;
                    single = true;
                }
                break;
            }
        }

        if (single) {
            dict.add(index, "[");
            dict.add(index + 4, "]");
            dict.add(index + 4, "R");
            dict.add(index + 4, "0");
            dict.add(index + 4, String.valueOf(obj.number));
        } else {
            dict.add(index, "R");
            dict.add(index, "0");
            dict.add(index, String.valueOf(obj.number));
        }
    }


    public float[] getPageSize() {
        for (int i = 0; i < dict.size(); i++) {
            if (dict.get(i).equals("/MediaBox")) {
                return new float[]{
                        Float.valueOf(dict.get(i + 4)),
                        Float.valueOf(dict.get(i + 5))};
            }
        }
        return Letter.PORTRAIT;
    }


    protected int getLength(List<PDFobj> objects) {
        for (int i = 0; i < dict.size(); i++) {
            String token = dict.get(i);
            if (token.equals("/Length")) {
                int number = Integer.valueOf(dict.get(i + 1));
                if (dict.get(i + 2).equals("0") &&
                        dict.get(i + 3).equals("R")) {
                    return getLength(objects, number);
                } else {
                    return number;
                }
            }
        }
        return 0;
    }


    protected int getLength(List<PDFobj> objects, int number) {
        for (PDFobj obj : objects) {
            if (obj.number == number) {
                return Integer.valueOf(obj.dict.get(3));
            }
        }
        return 0;
    }


    public PDFobj getContentsObject(Map<Integer, PDFobj> objects) {
        for (int i = 0; i < dict.size(); i++) {
            if (dict.get(i).equals("/Contents")) {
                if (dict.get(i + 1).equals("[")) {
                    return objects.get(Integer.valueOf(dict.get(i + 2)));
                }
                return objects.get(Integer.valueOf(dict.get(i + 1)));
            }
        }
        return null;
    }


    public PDFobj getResourcesObject(Map<Integer, PDFobj> objects) {
        for (int i = 0; i < dict.size(); i++) {
            if (dict.get(i).equals("/Resources")) {
                String token = dict.get(i + 1);
                if (token.equals("<<")) {
                    PDFobj obj = new PDFobj();
                    obj.dict.add("0");
                    obj.dict.add("0");
                    obj.dict.add("obj");
                    obj.dict.add(token);
                    int level = 1;
                    i++;
                    while (i < dict.size() && level > 0) {
                        token = dict.get(i);
                        obj.dict.add(token);
                        if (token.equals("<<")) {
                            level++;
                        } else if (token.equals(">>")) {
                            level--;
                        }
                        i++;
                    }
                    return obj;
                }
                return objects.get(Integer.valueOf(token));
            }
        }
        return null;
    }


    public Font addFontResource(CoreFont coreFont, Map<Integer, PDFobj> objects) {
        Font font = new Font(coreFont);
        font.fontID = font.name.replace('-', '_').toUpperCase();

        PDFobj obj = new PDFobj();
        obj.number = Collections.max(objects.keySet()) + 1;
        obj.dict.add("<<");
        obj.dict.add("/Type");
        obj.dict.add("/Font");
        obj.dict.add("/Subtype");
        obj.dict.add("/Type1");
        obj.dict.add("/BaseFont");
        obj.dict.add("/" + font.name);
        if (!font.name.equals("Symbol") && !font.name.equals("ZapfDingbats")) {
            obj.dict.add("/Encoding");
            obj.dict.add("/WinAnsiEncoding");
        }
        obj.dict.add(">>");

        objects.put(obj.number, obj);

        for (int i = 0; i < dict.size(); i++) {
            if (dict.get(i).equals("/Resources")) {
                String token = dict.get(++i);
                if (token.equals("<<")) {                       // Direct resources object
                    addFontResource(this, objects, font.fontID, obj.number);
                } else if (Character.isDigit(token.charAt(0))) {  // Indirect resources object
                    addFontResource(objects.get(Integer.valueOf(token)), objects, font.fontID, obj.number);
                }
            }
        }

        return font;
    }


    private void addFontResource(
            PDFobj obj, Map<Integer, PDFobj> objects, String fontID, int number) {
        for (int i = 0; i < obj.dict.size(); i++) {
            String token;
            if (obj.dict.get(i).equals("/Font")) {
                token = obj.dict.get(++i);
                if (token.equals("<<")) {
                    obj.dict.add(++i, "/" + fontID);
                    obj.dict.add(++i, String.valueOf(number));
                    obj.dict.add(++i, "0");
                    obj.dict.add(++i, "R");
                    break;
                } else if (Character.isDigit(token.charAt(0))) {
                    PDFobj o2 = objects.get(Integer.valueOf(token));
                    for (int j = 0; j < o2.dict.size(); j++) {
                        token = o2.dict.get(j);
                        if (token.equals("<<")) {
                            o2.dict.add(++j, "/" + fontID);
                            o2.dict.add(++j, String.valueOf(number));
                            o2.dict.add(++j, "0");
                            o2.dict.add(++j, "R");
                            break;
                        }
                    }
                }
            }
        }
    }

}
