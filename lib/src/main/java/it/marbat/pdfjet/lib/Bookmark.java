package it.marbat.pdfjet.lib;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Please see Example_51 and Example_52
 *
 */
@SuppressWarnings({"WeakerAccess", "unused", "FieldCanBeLocal"})
public class Bookmark {

    private int destNumber = 0;
    private Page page = null;
    private float y = 0f;
    private String key = null;
    private String title = null;
    private Bookmark parent = null;
    private Bookmark prev = null;
    private Bookmark next = null;
    private List<Bookmark> children = null;
    private Destination dest = null;

    protected int objNumber = 0;
    protected String prefix = null;


    public Bookmark(PDF pdf) {
        pdf.toc = this;
    }


    private Bookmark(Page page, float y, String key, String title) {
        this.page = page;
        this.y = y;
        this.key = key;
        this.title = title;
    }


    public Bookmark addBookmark(Page page, Title title) {
        return addBookmark(page, title.text.getY(), title.text.getText());
    }


    public Bookmark addBookmark(Page page, float y, String title) {
        Bookmark bm = this;
        while (bm.parent != null) {
            bm = bm.getParent();
        }
        String key = bm.next();

        Bookmark bookmark = new Bookmark(page, y, key, title.replaceAll("\\s+", " "));
        bookmark.parent = this;
        bookmark.dest = page.addDestination(key, y);
        if (children == null) {
            children = new ArrayList<>();
        } else {
            bookmark.prev = children.get(children.size() - 1);
            children.get(children.size() - 1).next = bookmark;
        }
        children.add(bookmark);
        return bookmark;
    }


    public String getDestKey() {
        return this.key;
    }


    public String getTitle() {
        return this.title;
    }


    public Bookmark getParent() {
        return this.parent;
    }


    public Bookmark autoNumber(TextLine text) {
        Bookmark bm = getPrevBookmark();
        if (bm == null) {
            bm = getParent();
            if (bm.prefix == null) {
                prefix = "1";
            } else {
                prefix = bm.prefix + ".1";
            }
        } else {
            if (bm.prefix == null) {
                if (bm.getParent().prefix == null) {
                    prefix = "1";
                } else {
                    prefix = bm.getParent().prefix + ".1";
                }
            } else {
                int index = bm.prefix.lastIndexOf('.');
                if (index == -1) {
                    prefix = String.valueOf(Integer.valueOf(bm.prefix) + 1);
                } else {
                    prefix = bm.prefix.substring(0, index) + ".";
                    prefix += String.valueOf(Integer.valueOf(bm.prefix.substring(index + 1)) + 1);
                }
            }
        }
        text.setText(prefix);
        title = prefix + " " + title;
        return this;
    }


    protected List<Bookmark> toArrayList() {
        int objNumber = 0;
        List<Bookmark> list = new ArrayList<>();
        Queue<Bookmark> queue = new LinkedList<>();
        queue.add(this);
        while (!queue.isEmpty()) {
            Bookmark bm = queue.poll();
            bm.objNumber = objNumber++;
            list.add(bm);
            if (bm.getChildren() != null) {
                queue.addAll(bm.getChildren());
            }
        }
        return list;
    }


    protected List<Bookmark> getChildren() {
        return this.children;
    }


    protected Bookmark getPrevBookmark() {
        return this.prev;
    }


    protected Bookmark getNextBookmark() {
        return this.next;
    }


    protected Bookmark getFirstChild() {
        return this.children.get(0);
    }


    protected Bookmark getLastChild() {
        return children.get(children.size() - 1);
    }


    protected Destination getDestination() {
        return this.dest;
    }


    private String next() {
        ++destNumber;
        return "dest#" + String.valueOf(destNumber);
    }

}   // End of Bookmark.java
