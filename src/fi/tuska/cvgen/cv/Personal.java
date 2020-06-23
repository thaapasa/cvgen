package fi.tuska.cvgen.cv;

import org.w3c.dom.Node;

/**
 * @author Tuukka Haapasalo
 * @created Jan 24, 2005
 * 
 * $Id: Personal.java,v 1.6 2011-01-04 09:47:17 tuska Exp $
 */
public class Personal extends Section {

    public Personal(Node node, String acceptLang) {
        super(node, acceptLang);
    }

    public Personal(Personal o) {
        super(o);
    }

    @Override
    public String format(boolean full, int indent, boolean firstItem) {
        String res = indent(indent) + "(Personal, " + getItemCount() + " items";
        if (getItemCount() > 0) {
            res += linefeed;
            boolean first = true;
            for (Item item : getItems()) {
                if (!first)
                    res += linefeed;
                res += item.format(full, indent + 2, first);
                first = false;
            }
        }
        res += ")";
        return res;
    }
}