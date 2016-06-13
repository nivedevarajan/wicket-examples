package com.github.tatarhy.pdfjs;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;

/**
 * Pdf increment link.
 */
public class PdfIncrementLink extends AjaxFallbackLink {
    private int increment;
    private PdfPageInfo pageInfo;
    private PdfView pdfView;

    public PdfIncrementLink(String id, PdfView pdfView, int increment) {
        super(id);
        this.pageInfo = PdfPageInfo.of(1, 1);
        this.increment = increment;
        this.pdfView = pdfView;

        setOutputMarkupId(true);
        setAutoEnable(true);

        add(newPdfComponentBehavior());
    }

    private PdfComponentBehavior newPdfComponentBehavior() {
        return new PdfComponentBehavior() {
            @Override
            public void onPdfRenderEvent(PdfRenderEvent event) {
                PdfIncrementLink.this.setPageInfo(event.getPageInfo());

                event.getTarget().add(PdfIncrementLink.this);
            }
        };
    }

    @Override
    public void onClick(AjaxRequestTarget target) {
        target.appendJavaScript(pdfView.getPageIncrementScript(increment));
    }


    @Override
    public boolean linksTo(final Page page) {
        return ((increment < 0) && pageInfo.isFirst()) || ((increment > 0) && pageInfo.isLast());
    }

    private void setPageInfo(PdfPageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
}
