package fi.tuska.cvgen.cv;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * @author Tuukka Haapasalo
 * @created Jan 19, 2005
 * 
 * $Id: Base.java,v 1.10 2011-01-04 09:47:17 tuska Exp $
 */
public abstract class Base implements Constants {

    private String name;

    private static final DateFormat dateFormat = new SimpleDateFormat("d.M.yyyy");
    private static final NumberFormat doubleFormat = new DecimalFormat("#.##");
    protected static final String linefeed = System.getProperty("line.separator");

    /**
     * Which language we are generating (command line parameter). Used to
     * select proper name attribute.
     */
    private String acceptLang;

    /** The language for which this element is valid (from the XML file). */
    private String langCode;

    protected Base(String acceptLang) {
        this.acceptLang = acceptLang;
    }

    protected Base(String acceptLang, String name) {
        this.acceptLang = acceptLang;
        this.name = name;
    }

    protected Base(Base o) {
        this.name = o.name;
        this.acceptLang = o.acceptLang;
        this.langCode = o.langCode;
    }

    protected String getAcceptLang() {
        return acceptLang;
    }

    public String getName() {
        return name;
    }

    protected String formatDate(Date date) {
        return date != null ? dateFormat.format(date) : null;
    }

    protected String formatDouble(double number) {
        return doubleFormat.format(number);
    }

    protected Date parseDate(String date) {
        try {
            return date != null ? dateFormat.parse(date) : null;
        } catch (ParseException pe) {
            System.err.println("Invalid date: " + date);
            return null;
        }
    }

    protected boolean parseBoolean(String value) {
        if ("true".equals(value))
            return true;
        if ("false".equals(value))
            return false;
        System.err.println("Invalid boolean value: " + value);
        return false;
    }

    protected String indent(int indent) {
        return indent(indent, ' ');
    }

    protected String indent(int indent, char character) {
        StringBuffer ind = new StringBuffer();
        for (int i = 0; i < indent; ++i) {
            ind.append(character);
        }
        return ind.toString();
    }

    protected String indent(int indent, String character) {
        StringBuffer ind = new StringBuffer();
        for (int i = 0; i < indent; ++i) {
            ind.append(character);
        }
        return ind.toString();
    }

    public final String format(boolean full, boolean first) {
        return format(full, 0, first);
    }

    public abstract String format(boolean full, int indent, boolean first);

    protected void readAttributes(NamedNodeMap attributes) {
        for (int i = 0; i < attributes.getLength(); ++i) {
            Node attr = attributes.item(i);
            setAttribute(attr.getNodeName(), attr.getNodeValue());
        }
    }

    protected String getLangCode() {
        return langCode;
    }

    protected boolean setAttribute(String key, String value) {
        String nameKey = "name" + acceptLang;
        if ("name".equals(key)) {
            name = value;
        } else if (key != null && key.startsWith("name")) {
            if (nameKey.equals(key))
                name = value;
        } else if ("language".equals(key)) {
            langCode = value;
        } else
            return false;
        return true;
    }

    public boolean isAcceptLanguage() {
        if (langCode == null || acceptLang == null)
            return true;
        return langCode.equals(acceptLang);
    }

    public boolean generate(boolean full, String filename, String langCode) {
        try {
            FileWriter writer = new FileWriter(filename);
            PrintWriter printer = new PrintWriter(writer);
            printer.print(format(full, true));
            printer.flush();
            printer.close();
            writer.close();
            return true;
        } catch (IOException ioe) {
            System.err.println("Error in writing CV to file " + filename);
        }
        return false;
    }

}
