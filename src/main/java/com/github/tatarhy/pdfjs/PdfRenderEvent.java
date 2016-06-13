package com.github.tatarhy.pdfjs;

import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * Pdf render event
 */
public class PdfRenderEvent {
    private final AjaxRequestTarget target;
    private final PdfPageInfo pageInfo;

    public PdfRenderEvent(AjaxRequestTarget target, PdfPageInfo pageInfo) {
        this.target = target;
        this.pageInfo = pageInfo;
    }

    public AjaxRequestTarget getTarget() {
        return target;
    }

    public PdfPageInfo getPageInfo() {
        return pageInfo;
    }
}
