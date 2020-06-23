package fi.tuska.cvgen.text;

import java.util.ArrayList;
import java.util.List;

import fi.tuska.cvgen.cv.Table;
import fi.tuska.util.Utils;

/**
 * @author Tuukka Haapasalo
 * @created Jan 20, 2005
 * 
 * $Id: Item.java,v 1.8 2011-01-04 09:47:18 tuska Exp $
 */
public class Item extends fi.tuska.cvgen.cv.Item {

    public Item(fi.tuska.cvgen.cv.Item item) {
        super(item);
    }

    private String formatTable(boolean full, int indent) {
        Table table = getTable();
        int[] widths = new int[table.getWidth()];

        int total = 0;
        for (int i = 0; i < table.getWidth(); ++i) {
            widths[i] = table.getMaxWidth(i);
            total += widths[i];
        }
        int usable = table.getWidthInChars() - indent;
        if (total < usable) {
            int add = (usable - total) / (table.getWidth() - 1);
            for (int i = 0; i < table.getWidth() - 1; ++i) {
                widths[i] += add;
            }
        }

        StringBuffer res = new StringBuffer();
        for (int y = 0; y < table.getHeight(); ++y) {
            if (y != 0)
                res.append(linefeed + indent(indent));

            for (int x = 0; x < table.getWidth(); ++x) {
                Table.Cell cell = table.getCell(x, y);
                String contents = cell.getName();
                res.append(contents);
                if (contents.length() < widths[x] && x != table.getWidth() - 1)
                    res.append(indent(widths[x] - contents.length()));
            }
        }

        return res.toString();
    }

    @Override
    public String format(boolean full, int indent, int columns, boolean firstItem) {
        if (getTable() != null)
            return formatTable(full, indent);
        List<String> parts = new ArrayList<String>();
        if (getHeader() != null)
            parts.add(getHeader());
        if (getValue() != null)
            parts.add(getValue());

        return ((!isWide() && (full || !isCompact()) && getName() != null) ? getName() + ": "
            : "")
            + Utils.implode(parts, ", ");
    }

}