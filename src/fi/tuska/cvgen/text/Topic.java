package fi.tuska.cvgen.text;

/**
 * @author Tuukka Haapasalo
 * @created Jan 20, 2005
 * 
 * $Id: Topic.java,v 1.7 2011-01-04 09:47:18 tuska Exp $
 */
public class Topic extends fi.tuska.cvgen.cv.Topic {

    public Topic(fi.tuska.cvgen.cv.Topic topic) {
        super(topic);
        for (fi.tuska.cvgen.cv.Section section : topic.getSections()) {
            addSection(new Section(section));
        }
    }

    @Override
    public String format(boolean full, int indent, boolean first) {
        String res = getName() + linefeed + indent(rowWidth, '-') + linefeed;

        fi.tuska.cvgen.cv.Section[] sections = getSections(full);
        for (int i = 0; i < sections.length; ++i) {
            res += sections[i].format(full, i == 0);
        }

        return res;
    }

}