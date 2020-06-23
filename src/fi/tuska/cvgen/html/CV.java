package fi.tuska.cvgen.html;

import fi.tuska.cvgen.cv.Image;
import fi.tuska.util.CalendarUtils;

/**
 * @author Tuukka Haapasalo
 * @created Jan 20, 2005
 * 
 * $Id: CV.java,v 1.14 2011-01-04 09:47:18 tuska Exp $
 */
public class CV extends fi.tuska.cvgen.cv.CV {
    private static final String TARGET_NAME = "html";

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

    @Override
    public String format(boolean full, int indent, boolean firstItem) {
        StringBuffer buf = new StringBuffer();

        buf.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">" + linefeed);
        buf.append("<html>").append(linefeed);
        buf.append("<head>").append(linefeed);
        buf.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-15\">"
            + linefeed);
        buf.append("<title>Curriculum Vitae, ").append(getAuthor()).append("</title>")
            .append(linefeed);
        if (getStylesheet() != null) {
            buf.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"")
                .append(getStylesheet()).append("\">").append(linefeed);
        }
        buf.append("</head>").append(linefeed);
        buf.append("<body>").append(linefeed);
        buf.append(linefeed);
        buf.append("<table class=\"main\"><tr><td>").append(linefeed);
        buf.append(linefeed);
        buf.append(linefeed);
        buf.append("<p><br></p>").append(linefeed);
        buf.append(linefeed);

        {
            fi.tuska.cvgen.cv.Item[] items = new fi.tuska.cvgen.cv.Item[0];
            if (getPersonal() != null) {
                items = getPersonal().getItems(full);
            }
            if (items.length < 2) {
                fi.tuska.cvgen.cv.Item[] newItems = new fi.tuska.cvgen.cv.Item[2];
                for (int i = 0; i < items.length; ++i)
                    newItems[i] = items[i];
                items = newItems;
            }

            buf.append("<table class=\"personal\">").append(linefeed);
            buf.append("<tr>").append(linefeed);
            buf.append("<td colspan=\"2\" class=\"right\"><h1>Curriculum Vitae</h1></td>");
            buf.append("</tr>").append(linefeed);

            buf.append("<tr>").append(linefeed);
            buf.append(
                "<td colspan=\"2\" class=\"rightHeader\"><h2>"
                    + formatDate(CalendarUtils.getCalendar().getTime())).append("</h2></td>")
                .append(linefeed);
            buf.append("</tr>").append(linefeed);

            buf.append("<tr>").append(linefeed);
            buf.append("<td class=\"toppad\">");
            buf.append("<table class=\"info\">").append(linefeed);
            for (int i = 0; i < items.length; ++i) {
                fi.tuska.cvgen.cv.Item item = items[i];
                String shown = "";
                if (item != null) {
                    if (!item.isCompact() && item.getName() != null && !"".equals(item.getName())) {
                        shown += item.getName() + ": ";
                    }
                    shown += item.formatValue(full);
                }
                buf.append("<tr><td class=\"personal\">").append(shown).append("</td></tr>")
                    .append(linefeed);
            }
            buf.append("</table>").append(linefeed);
            buf.append("</td>").append(linefeed);

            Image image = getImage(TARGET_NAME);
            if (image != null) {
                buf.append("<td class=\"right toppad\"><img src=\"").append(image.getSource())
                    .append("\" width=\"").append(image.getWidth()).append("\" height=\"")
                    .append(image.getHeight()).append("\" alt=\"").append(image.getAlternative())
                    .append("\"></td>");
            }

            buf.append("</tr>").append(linefeed);
            buf.append("</table>").append(linefeed);
            buf.append("<br>").append(linefeed);
        }

        buf.append("<table>").append(linefeed);

        // Output the normal topics

        if (getTopicCount() > 0) {
            boolean first = true;
            buf.append(linefeed);
            for (fi.tuska.cvgen.cv.Topic topic : getTopics(full)) {
                if (!first)
                    buf.append("<tr><td><br></td></tr>").append(linefeed);
                buf.append(topic.format(full, indent, first)).append(linefeed);
                first = false;
            }
        }

        buf.append("</table>").append(linefeed);
        buf.append(linefeed);
        buf.append("</td></tr>").append(linefeed);
        buf.append("</table>").append(linefeed);
        buf.append(linefeed);
        buf.append("</body>").append(linefeed);
        buf.append("</html>").append(linefeed);

        return buf.toString();
    }
}