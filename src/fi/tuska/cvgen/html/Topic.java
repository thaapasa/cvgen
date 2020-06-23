package fi.tuska.cvgen.html;

/**
 * @author Tuukka Haapasalo
 * @created Jan 20, 2005
 * 
 * $Id: Topic.java,v 1.6 2011-01-04 09:47:18 tuska Exp $
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
        StringBuffer buf = new StringBuffer();
        buf.append(indent(indent) + "<!-- " + getName() + "-->" + linefeed);

        buf.append(indent(indent) + "<tr>" + linefeed);
        buf.append(indent(indent) + "<td colspan=\"3\" class=\"header\"><h2>" + getName()
            + "</h2></td>" + linefeed);
        buf.append(indent(indent) + "</tr>" + linefeed);
        buf.append(linefeed);
        
        fi.tuska.cvgen.cv.Section[] sections = getSections(full);
        for (int i = 0; i < sections.length; ++i) {
            if (i != 0) {
                if (isWide()) {
                    buf.append("<tr><td class=\"value\" colspan=\"3\"><br></td></tr>" + linefeed);
                } else {
                    buf.append("<tr><td class=\"key\"><br></td></tr>" + linefeed);
                }
            }
            buf.append(sections[i].format(full, i == 0));
        }

        buf.append(indent(indent) + "<!-- / " + getName() + "-->" + linefeed);

        return buf.toString();
    }
}