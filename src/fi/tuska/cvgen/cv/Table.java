package fi.tuska.cvgen.cv;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Tuukka Haapasalo
 * @created Jan 24, 2005
 * 
 * $Id: Table.java,v 1.6 2011-01-04 09:47:18 tuska Exp $
 */
public class Table extends Base {

    private int width;
    private List<List<Cell>> rows;
    private int widthInChars;
    private double widthInCm;
    private int widthInPixel;
    private final Cell emptyCell = new Cell("", "", false);

    public Table(Node node, String acceptLang) {
        super(acceptLang);
        this.rows = new ArrayList<List<Cell>>();
        this.width = 0;
        this.widthInChars = rowWidth;
        this.widthInCm = 11;
        this.widthInPixel = 400;
        readAttributes(node.getAttributes());
        readRows(node.getChildNodes());
    }

    public int getMaxWidth(int col) {
        if (col < 0 || col >= getWidth())
            return 0;
        int max = 0;
        for (int y = 0; y < getHeight(); ++y) {
            int cur = getCell(col, y).getName().length();
            if (cur > max)
                max = cur;
        }
        return max;
    }

    private void readRows(NodeList nodes) {
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            if ("tr".equals(node.getNodeName())) {
                List<Cell> row = new ArrayList<Cell>();
                readRow(row, node.getChildNodes());
                rows.add(row);
            }
        }
    }

    private void readRow(List<Cell> row, NodeList nodes) {
        int cur = 0;
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            if ("td".equals(node.getNodeName())) {
                Cell cell = new Cell(node, getAcceptLang());
                if (cell.isAcceptLanguage()) {
                    cur++;
                    row.add(cell);
                    if (cur > width)
                        width = cur;
                }
            }
        }
    }

    public class Cell extends Base {
        private boolean header;

        public Cell(Node node, String acceptLang) {
            super(acceptLang, node.getFirstChild() != null ? node.getFirstChild().getNodeValue()
                : null);
            this.header = false;
            readAttributes(node.getAttributes());
        }

        private Cell(String acceptLang, String name, boolean header) {
            super(acceptLang, name);
            this.header = header;
        }

        public boolean isHeader() {
            return header;
        }

        @Override
        public String getName() {
            String name = super.getName();
            return name != null ? name : "";
        }

        @Override
        public String format(boolean full, int indent, boolean firstItem) {
            return "(Cell " + getName() + (header ? " (header)" : "") + ")";
        }

        @Override
        protected boolean setAttribute(String key, String value) {
            if (super.setAttribute(key, value))
                return true;
            if ("header".equals(key))
                header = parseBoolean(value);
            else {
                return false;
            }
            return true;
        }

    }

    public Cell getCell(int x, int y) {
        if (y < 0 || y >= getHeight())
            return null;
        List<Cell> row = rows.get(y);

        if (x < 0 || x >= getWidth())
            return null;
        if (x >= row.size())
            return emptyCell;

        return (Cell) row.get(x);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return rows.size();
    }

    @Override
    public String format(boolean full, int indent, boolean firstItem) {
        String res = indent(indent) + "(Table";
        if (getHeight() > 0 && getWidth() > 0) {
            for (int y = 0; y < getHeight(); ++y) {
                res += linefeed + indent(indent) + " (row" + linefeed;
                for (int x = 0; x < getWidth(); ++x) {
                    if (x != 0)
                        res += linefeed;
                    res += indent(indent + 2) + getCell(x, y).format(full, indent, x == 0 && y == 0);
                }
                res += ")";
            }
        }
        res += ")";
        return res;
    }

    @Override
    protected boolean setAttribute(String key, String value) {
        if ("widthInChars".equals(key))
            widthInChars = Integer.parseInt(value);
        else if ("widthInCm".equals(key))
            widthInCm = Double.parseDouble(value);
        else if ("widthInPixel".equals(key))
            widthInPixel = Integer.parseInt(value);
        else
            return false;

        return true;
    }

    public int getWidthInChars() {
        return widthInChars;
    }

    public double getWidthInCm() {
        return widthInCm;
    }

    public int getWidthInPixel() {
        return widthInPixel;
    }
}