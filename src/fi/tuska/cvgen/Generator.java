package fi.tuska.cvgen;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import fi.tuska.cvgen.cv.CV;

/**
 * @author Tuukka Haapasalo
 * @created Jan 19, 2005
 * 
 * $Id: Generator.java,v 1.11 2008-09-09 09:37:07 tuska Exp $
 */
public class Generator {

    private String basename;
    private File source;

    private boolean generateText = true;
    private boolean generateHTML = true;
    private boolean generateLaTeX = true;
    private String langCode;
    private String langName;

    public Generator(File source, String basename, String langCode, String langName) {
        this.source = source;
        this.basename = basename;
        this.langCode = langCode;
        this.langName = langName;
    }

    public boolean generate() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(source);

            CV cv = readDocument(doc);
            // System.out.println(cv.format());
            generateCVs(cv);
            return true;
        } catch (ParserConfigurationException pce) {
            System.err.println("Parser configuration error: " + pce);
        } catch (SAXException se) {
            System.err.println("SAX error: " + se);
        } catch (IOException ioe) {
            System.err.println("I/O error: " + ioe);
        }

        return false;
    }

    private void generateCVs(CV base) {
        String cvName = basename + "-" + langCode;
        String cvDetName = cvName + "-detailed";
        if (generateText) {
            fi.tuska.cvgen.text.CV cv = new fi.tuska.cvgen.text.CV(base);
            cv.generate(true, cvDetName + ".txt", langCode);
            cv.generate(false, cvName + ".txt", langCode);
        }
        if (generateLaTeX) {
            fi.tuska.cvgen.latex.CV cv = new fi.tuska.cvgen.latex.CV(base, langName);
            cv.generate(true, cvDetName + ".tex", langCode);
            cv.generate(false, cvName + ".tex", langCode);
        }
        if (generateHTML) {
            fi.tuska.cvgen.html.CV cv = new fi.tuska.cvgen.html.CV(base);
            cv.generate(true, cvDetName + ".html", langCode);
            cv.generate(false, cvName + ".html", langCode);
        }
    }

    private CV readDocument(Document doc) {
        return new CV(doc.getFirstChild(), langCode);
    }

    public boolean isGenerateHTML() {
        return generateHTML;
    }

    public void setGenerateHTML(boolean generateHTML) {
        this.generateHTML = generateHTML;
    }

    public boolean isGenerateLaTeX() {
        return generateLaTeX;
    }

    public void setGenerateLaTeX(boolean generateLaTeX) {
        this.generateLaTeX = generateLaTeX;
    }

    public boolean isGenerateText() {
        return generateText;
    }

    public void setGenerateText(boolean generateText) {
        this.generateText = generateText;
    }
}