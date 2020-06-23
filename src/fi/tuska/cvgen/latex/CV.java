package fi.tuska.cvgen.latex;

import fi.tuska.cvgen.cv.Image;

/**
 * @author Tuukka Haapasalo
 * @created Jan 20, 2005
 * 
 * $Id: CV.java,v 1.15 2011-01-04 09:47:18 tuska Exp $
 */
public class CV extends fi.tuska.cvgen.cv.CV {

    private static final String TARGET_NAME = "latex";
    private String langName;

    public CV(fi.tuska.cvgen.cv.CV cv, String langName) {
        super(cv);
        this.langName = langName;
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
        buf.append("\\documentclass[10pt,pdftex,notitlepage,").append(langName)
            .append("]{article}\n");
        buf.append("\\usepackage[latin1]{inputenc}\n");
        buf.append("\\usepackage{babel}\n");
        buf.append("\\usepackage{graphicx}\n");
        buf.append("\\usepackage[pdftex,a4paper,final,breaklinks,pdfpagelabels=false]{hyperref}\n");
        buf.append("\\usepackage{longtable}\n");
        buf.append("\n");
        buf.append("\\pagestyle{empty}\n");
        buf.append("\\setlength{\\parskip}{0 ex}\n");
        buf.append("\\setlength{\\parindent}{0 pt}\n");
        buf.append("\\setlength{\\headsep}{0 pt}\n");
        buf.append("\\setlength{\\headheight}{0 pt}\n");
        buf.append("\\setlength{\\topmargin}{0 pt}\n");
        buf.append("\\setlength{\\marginparwidth}{0 pt}\n");
        buf.append("\\setlength{\\oddsidemargin}{0 pt}\n");
        buf.append("\\setlength{\\evensidemargin}{0 pt}\n");
        buf.append("\\setlength{\\marginparsep}{0 pt}\n");
        double hoffset = getLeftMargin() - 2.54;
        double voffset = getTopMargin() - 2.54;
        // -2, -2,5
        buf.append("\\setlength{\\hoffset}{" + formatDouble(hoffset) + " cm}\n");
        buf.append("\\setlength{\\textwidth}{" + formatDouble(getTextWidth()) + " cm}\n");
        buf.append("\\setlength{\\voffset}{" + formatDouble(voffset) + " cm}\n");
        buf.append("\\setlength{\\textheight}{" + formatDouble(getTextHeight()) + " cm}\n");
        buf.append("\\newcommand*{\\email}[1]{\\href{mailto:#1}{#1}}\n");

        buf.append("\\hypersetup{colorlinks=false}\n");
        buf.append("\\hypersetup{pdftitle={Curriculum Vitae, " + getAuthor() + "}}\n");
        buf.append("\\hypersetup{pdfborder={0 0 0}}\n");
        buf.append("\\hypersetup{pdfdisplaydoctitle}\n");
        buf.append("\n");

        buf.append("\\newcommand*{\\topalign}[2]{\\begin{minipage}[t]{#1}\\vspace{0pt}#2\\end{minipage}}\n");
        buf.append("\n");

        buf.append("% CV created on " + formatDate(getCreated()) + "\n");
        buf.append("% CV modified on " + formatDate(getModified()) + "\n");
        buf.append("\n");
        buf.append("\\title{Curriculum Vitae}\n");
        buf.append("\\author{" + getAuthor() + "}\n");
        buf.append("\n");
        if (getHyphenationCount() > 0) {
            for (String hyph : getHyphenations()) {
                buf.append("\\hyphenation{").append(hyph).append("}\n");
            }
            buf.append("\n");
        }

        buf.append("\\begin{document}\n");
        buf.append("\\sffamily\n");

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

            buf.append("\\begin{longtable}{p{3cm}p{12cm}}\n");
            Image image = getImage(TARGET_NAME);
            if (image != null) {
                buf.append("\\topalign{3cm}{\\includegraphics[width=2.8cm]{" + image.getSource()
                    + "}} &\n");
            }

            buf.append("\\topalign{12cm}{\\begin{tabular*}{12cm}{@{}l@{\\extracolsep{\\fill}}r@{\\extracolsep{0pt}}}\n");
            for (int i = 0; i < items.length; ++i) {
                fi.tuska.cvgen.cv.Item item = items[i];
                String shown = "";
                if (item != null) {
                    if (!item.isCompact() && item.getName() != null && !"".equals(item.getName())) {
                        shown += item.getName() + ": ";
                    }
                    shown += item.formatValue(full);
                }
                buf.append(shown + " & ");
                if (i == 0)
                    buf.append("\\textbf{CURRICULUM VITAE}");
                else if (i == 1)
                    buf.append("\\today");
                buf.append("\\\\\n");
            }
            buf.append("\\end{tabular*}}\\\\\n");
            buf.append("\\end{longtable}\n");
        }

        buf.append("\\begin{longtable}[h]{p{3cm}p{12cm}}\n\n");

        boolean first = true;
        for (fi.tuska.cvgen.cv.Topic topic : getTopics(full)) {
            buf.append(topic.format(full, indent + 2, first));
            first = false;
        }

        buf.append("\\end{longtable}\n");
        buf.append("\\end{document}\n");
        return buf.toString();
    }
}