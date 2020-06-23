package fi.tuska.cvgen.text;

import fi.tuska.util.Utils;

/**
 * @author Tuukka Haapasalo
 * @created Jan 20, 2005
 * 
 * $Id: Section.java,v 1.8 2011-01-04 09:47:18 tuska Exp $
 */
public class Section extends fi.tuska.cvgen.cv.Section {

    public Section(fi.tuska.cvgen.cv.Section section) {
        super(section);
        for (fi.tuska.cvgen.cv.Item item : section.getItems()) {
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