package fi.tuska.cvgen.html;

import fi.tuska.cvgen.cv.Table;

/**
 * @author Tuukka Haapasalo
 * @created Jan 20, 2005
 * 
 * $Id: Item.java,v 1.11 2011-01-04 09:47:18 tuska Exp $
 */
public class Item extends fi.tuska.cvgen.cv.Item {

    public Item(fi.tuska.cvgen.cv.Item item) {
        super(item);
    }

    private String formatTable(boolean full, int indent) {
        StringBuffer buf = new StringBuffer();

        Table table = getTable();

        buf.append(indent(indent) + "<table class=\"item\">"
            + linefeed);

        for (int y = 0; y < table.getHeight(); ++y) {
            buf.append(indent(indent) + "<tr>" + linefeed);
            for (int x = 0; x < table.getWidth(); ++x) {
                buf.append(indent(indent) + "<td>");
                Table.Cell cell = table.getCell(x, y);
                buf.append(cell.isHeader() ? "<b>" + cell.getName() + "</b>" : cell.getName());
                buf.append("</td>" + linefeed);

            }
            buf.append(indent(indent) + "</tr>" + linefeed);
        }

        buf.append(indent(indent) + "</table>" + linefeed);
        return buf.toString();
    }

    @Override
    public String formatValue(boolean full) {
        String value = getValue();
        if (value != null && TYPE_EMAIL.equals(getType())) {
            value = "<a href=\"mailto:" + value + "\">" + value + "</a>";
        }
        if (getHeader() != null) {
            value = "<b>" + getHeader() + "</b>" + (value != null ? ", " + value : "");
        }
        if (value == null || value.equals(""))
            value = "&nbsp;";
        return value;
    }

    @Override
    public String format(boolean full, int indent, int columns, boolean firstItem) {
        String res = indent(indent) + "<tr>" + linefeed;

        String value = "";
        if (getTable() != null) {
            value = formatTable(full, indent);
        } else {
            value = formatValue(full);
        }

        // Wide items always use entire width
        if (columns > 1 && !isWide()) {
            res += indent(indent) + "<td class=\"key\">"
                + (getName() != null ? getName() : "&nbsp;") + "</td>" + linefeed;
            res += indent(indent) + "<td class=\"value\" colspan=\"2\">" + value + "</td>"
                + linefeed;
        } else {
            res += indent(indent) + "<td class=\"value\" colspan=\"3\">" + value + "</td>"
                + linefeed;
        }
        res += indent(indent) + "</tr>" + linefeed;

        return res;
    }
}