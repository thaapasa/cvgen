package fi.tuska.cvgen.text;

import fi.tuska.cvgen.cv.Constants;
import fi.tuska.util.CalendarUtils;

/**
 * @author Tuukka Haapasalo
 * @created Jan 20, 2005
 * 
 * $Id: CV.java,v 1.11 2011-01-04 09:47:18 tuska Exp $
 */
public class CV extends fi.tuska.cvgen.cv.CV implements Constants {

    public CV(fi.tuska.cvgen.cv.CV cv) {
        super(cv);
        setPersonal(new Personal(cv.getPersonal()));
        for (fi.tuska.cvgen.cv.Topic topic : cv.getTopics()) {
            addTopic(new Topic(topic));
        }
        for (String hyph : cv.getHyphenations()) {
            addHyphenation(hyph);
        }
    }

    protected String padToRight(String start, String text) {
        if (start.length() + 1 + text.length() > rowWidth)
            System.err.println("Warning: overfull row");

        String res = start + " ";
        res += indent(rowWidth - (res.length() + text.length()));
        res += text;
        return res;
    }

    @Override
    public String format(boolean full, int indent, boolean firstItem) {
        String res = "";

        String line1 = "CURRICULUM VITAE";
        String line2 = formatDate(CalendarUtils.getCalendar().getTime());

        if (getPersonal() != null) {
            String personal = getPersonal().format(full, indent, true);
            String[] lines = personal.split(linefeed);

            if (lines.length < 2) {
                res += padToRight("", line1) + linefeed;
                res += padToRight("", line2) + linefeed;
                res += personal;
            } else {
                res += padToRight(lines[0], line1) + linefeed;
                res += padToRight(lines[1], line2) + linefeed;
                for (int i = 2; i < lines.length; ++i)
                    res += lines[i] + linefeed;
            }

        } else {
            res += padToRight("", line1) + linefeed;
            res += padToRight("", line2) + linefeed;
        }

        if (getTopicCount() > 0) {
            res += linefeed;
            boolean first = true;
            for (fi.tuska.cvgen.cv.Topic topic : getTopics(full)) {
                res += topic.format(full, indent, first);
                first = false;
            }
        }
        return res;
    }
}