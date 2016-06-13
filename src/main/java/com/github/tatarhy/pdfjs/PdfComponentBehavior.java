package com.github.tatarhy.pdfjs;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.event.IEvent;

/**
 * Behavior handle PdfRenderEvent from PdfView.
 */
public abstract class PdfComponentBehavior extends Behavior {
    @Override
    public void onEvent(Component component, IEvent<?> event) {
        super.onEvent(component, event);
        Object payload = event.getPayload();
        if (payload instanceof PdfRenderEvent) {
            PdfRenderEvent pdfRenderEvent = (PdfRenderEvent) payload;
            onPdfRenderEvent(pdfRenderEvent);
        }
    }

    /**
     * Handler called when caught pdf render event.
     *
     * @param event event object.
     */
    protected abstract void onPdfRenderEvent(PdfRenderEvent event);
}
