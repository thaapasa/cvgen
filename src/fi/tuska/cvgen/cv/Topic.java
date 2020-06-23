package fi.tuska.cvgen.cv;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Tuukka Haapasalo
 * @created Jan 19, 2005
 * 
 * $Id: Topic.java,v 1.8 2011-01-04 09:47:18 tuska Exp $
 */
public class Topic extends Base {

    private List<Section> sections;
    private boolean brief;
    private boolean wide;

    public Topic(Node node, String langCode) {
        super(langCode);
        this.sections = new ArrayList<Section>();
        this.brief = true;
        this.wide = false;
        readAttributes(node.getAttributes());
        readSections(node.getChildNodes());
    }

    public Topic(Topic o) {
        super(o);
        this.sections = new ArrayList<Section>();
        this.brief = o.brief;
        this.wide = o.wide;
    }

    public List<Section> getSections() {
        return sections;
    }

    public int getSectionCount() {
        return sections.size();
    }

    public Section[] getSections(boolean full) {
        List<Section> selected = new ArrayList<Section>();
        for (Section section : sections) {
            if (full || (!full && section.isBrief()))
                selected.add(section);
        }
        return selected.toArray(new Section[0]);
    }

    public boolean isBrief() {
        return brief;
    }

    public boolean isWide() {
        return wide;
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    @Override
    protected boolean setAttribute(String key, String value) {
        if (super.setAttribute(key, value))
            return true;
        if ("brief".equals(key)) {
            brief = parseBoolean(value);
        } else if ("wide".equals(key)) {
            wide = parseBoolean(value);
        } else {
            System.err.println("Invalid key " + key + " in Topic");
            return false;
        }
        return true;
    }

    @Override
    public String format(boolean full, int indent, boolean first) {
        String res = indent(indent) + "(Topic " + getName();
        if (sections.size() > 0) {
            res += linefeed;
            Section[] sections = getSections(full);
            for (int i = 0; i < sections.length; ++i) {
                if (i != 0)
                    res += linefeed;
                res += ((Section) sections[i]).format(full, indent + 1, i == 0);
            }
        }
        res += ")";
        return res;
    }

    private void readSections(NodeList nodes) {
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            if ("section".equals(node.getNodeName()))
                addSection(new Section(nodes.item(i), getAcceptLang()));
        }
    }

}