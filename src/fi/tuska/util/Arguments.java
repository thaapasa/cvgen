package fi.tuska.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tuukka Haapasalo
 * @created Jan 17, 2005
 * 
 * $Id: Arguments.java,v 1.2 2008-09-08 14:39:14 tuska Exp $
 */
public class Arguments {

    private static final String DEFAULT_KEY = "DEFAULT_KEY";

    public Map<String, String> arguments;

    public Arguments(String[] args) {
        arguments = new HashMap<String, String>();
        parseArguments(args);
    }

    private void parseArguments(String[] args) {
        String key = DEFAULT_KEY;
        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];
            if (arg.length() >= 2 && arg.substring(0, 2).equals("--")) {
                key = arg.substring(2);
            } else {
                arguments.put(key, arg);
            }
        }
    }

    public String getArgument(String key) {
        return (String) arguments.get(key);
    }

    public boolean hasArgument(String key) {
        return getArgument(key) != null;
    }

    public boolean hasDefault() {
        return hasArgument(DEFAULT_KEY);
    }

    public String getDefaultArgument() {
        return getArgument(DEFAULT_KEY);
    }

}