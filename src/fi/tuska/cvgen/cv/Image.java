package fi.tuska.cvgen.cv;

import org.w3c.dom.Node;

/**
 * @author Tuukka Haapasalo
 * @created Jan 25, 2005
 * 
 * $Id: Image.java,v 1.6 2011-01-04 09:47:17 tuska Exp $
 */
public class Image extends Base {

    private int width;
    private int height;
    private String source;
    private String alt;
    private String target = null;
    
    public Image(Node node, String langCode) {
        super(langCode);
        readAttributes(node.getAttributes());
    }

    @Override
    public String format(boolean full, int indent, boolean first) {
        return "(Image \"" + source + "\", " + width + "x" + height + ")";
    }

    @Override
    protected boolean setAttribute(String key, String value) {
        if ("width".equals(key))
            width = Integer.parseInt(value);
        else if ("height".equals(key))
            height = Integer.parseInt(value);
        else if ("source".equals(key))
            source = value;
        else if ("target".equals(key)) 
            target = value;
        else if ("alt".equals(key)) {
            alt = value;
            return false;
        }
        return true;
    }

    public int getHeight() {
        return height;
    }

    public String getSource() {
        return source;
    }

    public int getWidth() {
        return width;
    }

    public String getTarget() {
        return target;
    }
    
    public String getAlternative() {
        return alt;
    }
}