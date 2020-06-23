package fi.tuska.cvgen.latex;

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
    public String format(boolean full, int indent, boolean firstItem) {
        String res = ""; 
        if (!firstItem) {
            if (full)
                res += indent(indent) + "\\\\\n";
            res += indent(indent) + "\\\\\n";
            res += "\n";
        }
        res += "% --- " + getName() + " ---\n";
        res += indent(indent) + "\\multicolumn{2}{l}{\\textbf{" + getName() + "}}\\\\*\n";
        res += indent(indent) + "\\hline\\nopagebreak\n";
        res += indent(indent) + "\\\\*\n";

        fi.tuska.cvgen.cv.Section[] sections = getSections(full);
        for (int i = 0; i < sections.length; ++i) {
            if (i != 0) {
                res += indent(indent) + (full ? "\\\\" : "") + "\n\n";
            }
            res += sections[i].format(full, indent, i == 0);
        }

        res += "% --- / " + getName() + " ---\n";
        res += "\n";

        return res;
    }
}