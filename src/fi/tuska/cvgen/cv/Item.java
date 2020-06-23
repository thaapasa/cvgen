package fi.tuska.cvgen.cv;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Tuukka Haapasalo
 * @created Jan 20, 2005
 * 
 * $Id: Item.java,v 1.13 2011-01-04 09:47:17 tuska Exp $
 */
public class Item extends Base {

    public static final String TYPE_EMAIL = "email";

    private String value;
    /** Shown in detailed listings? Default: true. */
    private boolean full;
    /** Shown in brief listings? Default: true. */
    private boolean brief;
    /** Compact format? Default: false. */
    private boolean compact;
    /** Header part, shown before the textual value. */
    private String header;
    /** Type */
    private String type;
    /** Wide format, no name shown? Default: false. */
    private boolean wide;
    /** Render a table in place of this element */
    private Table table;

    public Item(Node node, String acceptLang) {
        super(acceptLang);
        full = true;
        brief = true;
        header = null;
        compact = false;
        type = null;
        readAttributes(node.getAttributes());
        readTable(node.getChildNodes());
        value = node.getFirstChild() != null ? node.getFirstChild().getNodeValue() : null;
    }

    public Item(Item o) {
        super(o);
        this.value = o.value;
        this.full = o.full;
        this.brief = o.brief;
        this.header = o.header;
        this.compact = o.compact;
        this.wide = o.wide;
        this.type = o.type;
        this.table = o.getTable();
    }

    @Override
    public String format(boolean full, int indent, boolean first) {
        return format(full, indent, 2, first);
    }

    public String format(boolean full, int indent, int columns, boolean first) {
        if (table != null)
            return table.format(full, indent, first);
        String res = indent(indent) + "(Item " + getName() + " = " + value + " ("
            + (header != null ? "Header: " + header + ") (" : "") + (full ? "f" : "")
            + (brief ? "b" : "") + (compact ? "c" : "") + ")";
        res += ")";
        return res;
    }

    public String formatValue(boolean full) {
        return value;
    }

    @Override
    protected boolean setAttribute(String key, String value) {
        if (super.setAttribute(key, value))
            return true;
        if ("full".equals(key)) {
            full = parseBoolean(value);
        } else if ("brief".equals(key)) {
            brief = parseBoolean(value);
        } else if ("header".equals(key)) {
            header = value;
        } else if ("wide".equals(key)) {
            wide = parseBoolean(value);
        } else if ("compact".equals(key)) {
            compact = parseBoolean(value);
        } else if ("type".equals(key)) {
            type = value;
        } else {
            System.err.println("Invalid key " + key + " in Item");
            return false;
        }
        return true;
    }

    private void readTable(NodeList nodes) {
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            if ("table".equals(node.getNodeName()))
                setTable(new Table(node, getAcceptLang()));
        }
    }

    public void setTable(Table table) {
        if (this.table != null)
            System.err.println("Warning: overriding already set table");
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    public String getType() {
        return type;
    }

    public boolean isBrief() {
        return brief;
    }

    public boolean isFull() {
        return full;
    }

    public boolean isWide() {
        return wide;
    }

    public String getHeader() {
        return header;
    }

    public String getValue() {
        return value;
    }

    public boolean isCompact() {
        return compact;
    }

}