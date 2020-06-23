package fi.tuska.cvgen.latex;

/**
 * @author Tuukka Haapasalo
 * @created Jan 24, 2005
 * 
 * $Id: Personal.java,v 1.5 2011-01-04 09:47:18 tuska Exp $
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
        StringBuffer res = new StringBuffer();

        fi.tuska.cvgen.cv.Item[] items = getItems(full);
        for (int i = 0; i < items.length; ++i) {
            res.append(items[i].format(full, indent, 1, i == 0)).append(linefeed);
        }

        return res.toString();
    }
}