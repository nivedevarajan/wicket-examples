package com.github.tatarhy.pdfjs;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Label show current page number and page count. Looks like <pre>1/2</pre>.
 */
public class PdfPageLabel extends Label {
    public PdfPageLabel(String id) {
        super(id, new Model<>(PdfPageInfo.of(1, 1)));
        setOutputMarkupId(true);

        add(newPdfComponentBehavior());
    }

    private PdfComponentBehavior newPdfComponentBehavior() {
        return new PdfComponentBehavior() {
            @Override
            public void onPdfRenderEvent(PdfRenderEvent event) {
                // update page info.
                IModel<PdfPageInfo> model = (IModel<PdfPageInfo>) PdfPageLabel.this.getDefaultModel();
                model.setObject(event.getPageInfo());

                event.getTarget().add(PdfPageLabel.this);
            }
        };
    }


}
