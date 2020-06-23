package fi.tuska.cvgen.latex;

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
        double width = table.getWidthInCm() / (double) table.getWidth();

        buf.append("\\begin{tabular}[h]{@{}"
            + indent(table.getWidth(), "p{" + formatDouble(width) + "cm}") + "}");

        for (int y = 0; y < table.getHeight(); ++y) {
            if (y != 0)
                buf.append(indent(indent));
            for (int x = 0; x < table.getWidth(); ++x) {
                Table.Cell cell = table.getCell(x, y);
                buf.append(cell.isHeader() ? "\\textbf{" + cell.getName() + "}" : cell.getName());
                buf.append((x != table.getWidth() - 1) ? " & " : "\\\\\n");

            }
        }

        buf.append("\\end{tabular}\\\\\n");
        return buf.toString();
    }

    @Override
    public String formatValue(boolean full) {
        String value = getValue();
        if (value != null && TYPE_EMAIL.equals(getType())) {
            value = "\\email{" + value + "}";
        }
        if (getHeader() != null) {
            value = "\\textbf{" + getHeader() + "}" + (value != null ? ", " + value : "");
        }
        if (value == null)
            value = "";
        value = value.replaceAll("[.][.][.]", "\\\\ldots");
        return value;
    }

    @Override
    public String format(boolean full, int indent, int columns, boolean firstItem) {
        // System.out.println("Pim: " + this);
        if (getTable() != null)
            return columns > 1 ? (getName() != null ? getName() : "") + " & "
                + formatTable(full, indent) : formatTable(full, indent);

        StringBuffer res = new StringBuffer();
        res.append(indent(indent));

        if (isWide())
            res.append("\\multicolumn{2}{l}{");

        if ((full || !isCompact()) && getName() != null)
            res.append(getName() + (columns < 2 ? ": " : ""));

        if (columns > 1 && !isWide())
            res.append(" & ");

        String value = formatValue(full);
        String[] values = value.split("\n");
        for (int i = 0; i < values.length; ++i) {
            if (i != 0) {
                res.append("\n");
                res.append(indent(indent));
                if (isWide())
                    res.append("\\multicolumn{2}{l}{");
                if (columns > 1 && !isWide())
                    res.append(" & ");
            }
            res.append(values[i]);
            if (isWide())
                res.append("}");
            res.append("\\\\*");
        }

        return res.toString();
    }

    @Override
    public String toString() {
        return "Value: " + getValue();
    }

}