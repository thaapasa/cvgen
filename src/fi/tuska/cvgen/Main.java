package fi.tuska.cvgen;

import java.io.File;

/**
 * @author Tuukka Haapasalo
 * @created Jan 19, 2005
 * 
 * $Id: Main.java,v 1.4 2008-09-09 09:37:07 tuska Exp $
 */
public class Main {

    private File source;
    private String langCode;
    private String langName;

    public Main(String file, String langCode, String langName) {
        this.source = new File(file);
        this.langCode = langCode;
        this.langName = langName;

        if (!source.exists())
            throw new IllegalArgumentException("Specified source file (" + source.getName()
                + ") does not exist");

        generate();
    }

    private void generate() {
        String basename = source.getPath();
        if (basename.toLowerCase().endsWith(".xml"))
            basename = basename.substring(0, basename.length() - 4);
        if (new Generator(source, basename, langCode, langName).generate())
            System.out.println("CVs generated successfully");
        else
            System.out.println("Errors occured, CV's not generated successfully");
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out
                .println("Usage: java fi.tuska.cvgen.Main xml-file language-code language-name");
            return;
        }
        try {
            new Main(args[0], args[1], args[2]);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("Error in program execution, exiting");
            System.exit(1);
        }

    }

}