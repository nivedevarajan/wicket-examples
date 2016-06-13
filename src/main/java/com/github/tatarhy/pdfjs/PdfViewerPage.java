package com.github.tatarhy.pdfjs;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;

/**
 * Pdf viewer using pdf.js
 */
public class PdfViewerPage extends WebPage {
    public PdfViewerPage() {
        WebMarkupContainer viewer = new WebMarkupContainer("viewer");
        viewer.setVersioned(false);
        add(viewer);

        PdfView pdfView = new PdfView("pdf", "/license.pdf");
        add(pdfView);
        viewer.add(new PdfIncrementLink("next", pdfView, 1));
        viewer.add(new PdfIncrementLink("prev", pdfView, -1));
        viewer.add(new PdfPageLabel("label"));
    }
}
