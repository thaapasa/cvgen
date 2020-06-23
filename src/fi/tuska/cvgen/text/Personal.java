package fi.tuska.cvgen.text;

import fi.tuska.util.Utils;

/**
 * @author Tuukka Haapasalo
 * @created Jan 24, 2005
 * 
 * $Id: Personal.java,v 1.4 2011-01-04 09:47:18 tuska Exp $
 */
public class Personal extends fi.tuska.cvgen.cv.Personal {

    public Personal(fi.tuska.cvgen.cv.Personal personal) {
        super(personal);
        for (fi.tuska.cvgen.cv.Item item : personal.getItems()) {
            addItem(new Item(item));
        }
    }

    @Override
    public String format(boolean full, int indent, boolean firstItem) {
        String res = "";
        fi.tuska.cvgen.cv.Item[] items = getItems(full);
        for (int i = 0; i < items.length; ++i) {
            res += Utils.wrapLines(items[i].format(full, i == 0), rowWidth, "", linefeed);
        }
        if (!res.equals(""))
            res += linefeed;

        return res;
    }

}