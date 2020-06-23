package fi.tuska.cvgen.cv;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Tuukka Haapasalo
 * @created Jan 19, 2005
 * 
 * $Id: CV.java,v 1.13 2011-01-04 09:47:17 tuska Exp $
 */
public class CV extends Base {

    private static final String DEFAULT_IMAGE_KEY = "___def_img";
    private String author;
    private Date created;
    private Date modified;
    private List<Topic> topics;
    private List<String> hyphenation;
    private Personal personal;
    private String stylesheet;
    private Map<String, Image> images = new HashMap<String, Image>();

    private double textWidth;
    private double textHeight;
    private double topMargin;
    private double leftMargin;

    public CV(Node node, String langCode) {
        super(langCode);
        this.textWidth = 16;
        this.textHeight = 26;
        this.topMargin = 4;
        this.leftMargin = 4;
        this.topics = new ArrayList<Topic>();
        this.hyphenation = new ArrayList<String>();
        readAttributes(node.getAttributes());
        readTopics(node.getChildNodes());
        readHyphenation(node.getChildNodes());
        readPersonal(node.getChildNodes());
        readImage(node.getChildNodes());
    }

    public CV(CV o) {
        super(o);
        this.topics = new ArrayList<Topic>();
        this.hyphenation = new ArrayList<String>();
        this.author = o.author;
        this.created = o.created;
        this.modified = o.modified;
        this.stylesheet = o.stylesheet;
        // Copy images
        this.images = new HashMap<String, Image>(o.images);
        this.textWidth = o.textWidth;
        this.textHeight = o.textHeight;
        this.topMargin = o.topMargin;
        this.leftMargin = o.leftMargin;
    }

    @Override
    protected boolean setAttribute(String key, String value) {
        if ("author".equals(key))
            author = value;
        else if ("created".equals(key))
            created = parseDate(value);
        else if ("modified".equals(key))
            modified = parseDate(value);
        else if ("stylesheet".equals(key))
            stylesheet = value;
        else if ("textWidth".equals(key))
            textWidth = Double.parseDouble(value);
        else if ("textHeight".equals(key))
            textHeight = Double.parseDouble(value);
        else if ("topMargin".equals(key))
            topMargin = Double.parseDouble(value);
        else if ("leftMargin".equals(key))
            leftMargin = Double.parseDouble(value);
        else {
            System.err.println("Invalid key " + key + " in CV");
            return false;
        }
        return true;
    }

    public void addHyphenation(String hyphen) {
        hyphenation.add(hyphen);
    }

    public List<String> getHyphenations() {
        return hyphenation;
    }

    public int getHyphenationCount() {
        return hyphenation.size();
    }

    public void addTopic(Topic topic) {
        topics.add(topic);
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public Topic[] getTopics(boolean full) {
        List<Topic> selected = new ArrayList<Topic>();
        for (Topic topic : topics) {
            if (full || (!full && topic.isBrief()))
                selected.add(topic);
        }

        return selected.toArray(new Topic[0]);
    }

    public int getTopicCount() {
        return topics.size();
    }

    public String getAuthor() {
        return author;
    }

    public Date getCreated() {
        return created;
    }

    public Date getModified() {
        return modified;
    }

    @Override
    public String format(boolean full, int indent, boolean firstItem) {
        StringBuffer res = new StringBuffer();
        res.append(indent(indent)).append("(CV");
        res.append(linefeed).append(indent(indent)).append(" (author ").append(author + ")");
        res.append(linefeed).append(indent(indent)).append(" (created ")
            .append(formatDate(created)).append(")");
        res.append(linefeed).append(indent(indent)).append(" (modified ")
            .append(formatDate(modified)).append(")");
        Image image = getImage(null);
        if (image != null) {
            res.append(linefeed).append(indent(indent)).append(" ")
                .append(image.format(full, false));
        }
        if (personal != null) {
            res.append(linefeed).append(indent(indent)).append(" ")
                .append(personal.format(full, indent, false));
        }
        if (topics.size() > 0) {
            res.append(linefeed).append(indent(indent)).append(" (Topics").append(linefeed);
            boolean tFirst = true;
            for (Topic t : topics) {
                if (!tFirst)
                    res.append(linefeed);
                res.append(t.format(full, indent + 2, tFirst));
                tFirst = false;
            }
            res.append(")");
        }
        res.append(")");
        return res.toString();
    }

    @Override
    public String toString() {
        return "CV of " + author + " (created on " + formatDate(created) + ", modified on "
            + formatDate(modified) + ")";
    }

    private void readTopics(NodeList nodes) {
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            if ("topic".equals(node.getNodeName()))
                addTopic(new Topic(node, getAcceptLang()));
        }
    }

    private void readHyphenation(NodeList nodes) {
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            if ("hyphen".equals(node.getNodeName()))
                addHyphenation(node.getFirstChild().getNodeValue());
        }
    }

    private void readImage(NodeList nodes) {
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            if ("image".equals(node.getNodeName()))
                setImage(new Image(node, getAcceptLang()));
        }
    }

    private void readPersonal(NodeList nodes) {
        for (int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            if ("personal".equals(node.getNodeName())) {
                setPersonal(new Personal(node, getAcceptLang()));
            }
        }
    }

    protected void setImage(Image image) {
        String target = image.getTarget();
        if (target == null)
            target = DEFAULT_IMAGE_KEY;
        images.put(target, image);
    }

    protected void setPersonal(Personal personal) {
        if (this.personal != null)
            System.err
                .println("Warning: multiple occurances of personal-section. Overriding previous section.");
        this.personal = personal;
    }

    public Personal getPersonal() {
        return personal;
    }

    public String getStylesheet() {
        return stylesheet;
    }

    public Image getImage(String target) {
        Image image = images.get(target);
        if (image != null)
            return image;
        return images.get(DEFAULT_IMAGE_KEY);
    }

    public double getLeftMargin() {
        return leftMargin;
    }

    public double getTextHeight() {
        return textHeight;
    }

    public double getTextWidth() {
        return textWidth;
    }

    public double getTopMargin() {
        return topMargin;
    }
}