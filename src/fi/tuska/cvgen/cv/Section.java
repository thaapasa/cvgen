package fi.tuska.cvgen.cv;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Tuukka Haapasalo
 * @created Jan 20, 2005
 * 
 * $Id: Section.java,v 1.11 2011-01-04 09:47:17 tuska Exp $
 */
public class Section extends Base {

    private boolean brief;
    private List<Item> items;

    public Section(Node node, String acceptLang) {
        super(acceptLang);
        this.items = new ArrayList<Item>();
        this.brief = true;
        readAttributes(node.getAttributes());
        readItems(node.getChildNodes());
    }

    public Section(Section o) {
        super(o);
        this.items = new ArrayList<Item>();
        this.brief = o.brief;
    }

    @Override
    public String format(boolean full, int indent, boolean first) {
        String res = indent(indent) + "(Section \"" + getName() + "\"";
        if (items.size() > 0) {
            res += linefeed;
            boolean tFirst = true;
            for (Item i : items) {
                if (!tFirst)
                    res += linefeed;
                res += i.format(full, indent + 1, tFirst);
                tFirst = false;
            }
        }
        res += ")";
        return res;
    }

    @Override
    protected boolean setAttribute(String key, String value) {
        if (super.setAttribute(key, value))
            return true;
        else if ("brief".equals(key))
            brief = parseBoolean(value);
        else {
            System.err.println("Invalid key " + key + " in Section");
            return false;
        }
        return true;
    }

    private void readItems(NodeList nodes) {
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            if ("item".equals(node.getNodeName()))
                addItem(new Item(nodes.item(i), getAcceptLang()));
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean isBrief() {
        return brief;
    }

    public Item[] getItems(boolean full) {
        List<Item> selected = new ArrayList<Item>();
        for (Item item : items) {
            if (item.isAcceptLanguage()
                && ((full && item.isFull()) || (!full && item.isBrief())))
                selected.add(item);
        }
        return selected.toArray(new Item[0]);
    }

    public List<Item> getItems() {
        return items;
    }

    public int getItemCount() {
        return items.size();
    }

}