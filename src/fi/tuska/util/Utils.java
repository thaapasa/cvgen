package fi.tuska.util;

import java.util.Collection;

/**
 * Small utilities
 * 
 * @author Tuukka Haapasalo
 */
public class Utils {

    /**
     * Checks that the single lines in a string containing multiple lines do
     * not exceed a given maximum length. Wraps those single lines that are
     * longer.
     * 
     * @param src the source string
     * @param maxLength the maximum allowed length
     * @param prefix a prefix to prepend to each wrapped line
     * @param delimiter line delimiter (crlf, etc)
     * @return the formatted string
     */
    public static String wrapLines(String src, int maxLength, String prefix, String delimiter) {
        String[] lines = src.split(delimiter);
        String result = "";
        for (int i = 0; i < lines.length; i++) {
            String wrapped = wrapLine(lines[i], maxLength, prefix, delimiter);
            result += wrapped;
        }
        return result;
    }

    /**
     * Wraps a string to multiple lines if it's longer than a given maximum
     * length. Lines are wrapped by the CRLF line delimiter string.
     * 
     * @param src the source string to wrap
     * @param maxLength the maximum length of a line
     * @param prefix a prefix to prepend to all wrapped lines
     * @return the wrapped string
     */
    public static String wrapLine(String src, int maxLength, String prefix, String delimiter) {
        int allowed = Math.min(maxLength, src.length());
        String remaining = src;
        String data = "";
        while (remaining.length() > allowed) {
            String maxPart = remaining.substring(0, allowed);
            int pos = maxPart.lastIndexOf(' ');
            if (pos < 1)
                pos = Math.max(allowed, remaining.length());

            data += remaining.substring(0, pos).trim();
            data += delimiter + prefix;
            remaining = remaining.substring(pos + 1).trim();
            allowed = Math.min(maxLength - prefix.length(), src.length());
        }
        data += remaining + delimiter;

        return data;

    }

    /**
     * Changes the first letter to upper case
     * 
     * @param str the source string
     * @return the string with the first letter changed to upper case
     */
    public static String ucFirst(String str) {
        if (str.equals(""))
            return "";
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Changes the first letter of every word to upper case
     * 
     * @param str the source string
     * @return the string with the first letter of every word changed to upper
     * case
     */
    public static String ucWords(String str) {
        if (str.equals(""))
            return "";

        String res = "";
        String rest = str;
        int pos;
        while ((pos = rest.indexOf("-")) > 0) {
            res += ucFirst(rest.substring(0, ++pos));
            rest = rest.substring(pos);
        }
        if (!rest.equals(""))
            res += ucFirst(rest);

        return res;
    }

    public static String implode(Collection<?> parts, String delimiter) {
        return implode(parts.toArray(new String[0]), delimiter);
    }

    public static String implode(String[] parts, String delimiter) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < parts.length; ++i) {
            if (i != 0)
                buf.append(delimiter);
            buf.append(parts[i]);
        }
        return buf.toString();

    }

}