package com.github.tatarhy;

import com.github.tatarhy.pdfjs.PdfViewerPage;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends WebPage {
    private static final long serialVersionUID = 1L;

    public HomePage(final PageParameters parameters) {
        super(parameters);

        add(new Link("JsTimeoutPage") {
            @Override
            public void onClick() {
                setResponsePage(new JsTimeoutPage());
            }
        });

        add(new Link("MultiTimerPage") {
            @Override
            public void onClick() {
                setResponsePage(new MultiTimerPage());
            }
        });

        add(new Link("PdfViewerPage") {
            @Override
            public void onClick() {
                setResponsePage(new PdfViewerPage());
            }
        });
    }
}
