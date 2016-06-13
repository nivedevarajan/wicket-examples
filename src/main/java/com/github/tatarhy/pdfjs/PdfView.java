package com.github.tatarhy.pdfjs;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;
import org.apache.wicket.event.Broadcast;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

/**
 * Canvas component that display pdf. You should mark up canvas tag like below:
 * <p>
 * <pre>
 *     <canvas wicket:id="yourId"></canvas>
 * </pre>
 */
public class PdfView extends WebComponent {

    public PdfView(String id, String url) {
        super(id);

        setOutputMarkupId(true);

        add(newAbstractDefaultAjaxBehavior(url));
    }

    private AbstractDefaultAjaxBehavior newAbstractDefaultAjaxBehavior(String url) {
        return new AbstractDefaultAjaxBehavior() {

            @Override
            public void renderHead(Component component, IHeaderResponse response) {
                super.renderHead(component, response);

                String markupId = component.getMarkupId();

                CharSequence callbackScript = getCallbackFunction(
                        CallbackParameter.explicit("pagePosition"),
                        CallbackParameter.explicit("pageCount"));

                CharSequence viewerScript = "var " + markupId + " = new PdfView('"
                        + markupId + "', '" + url + "', " + callbackScript
                        + ");";

                response.render(JavaScriptHeaderItem.forUrl("pdf.js"));
                response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(PdfView.class, "PdfView.js")));
                response.render(JavaScriptHeaderItem.forScript(viewerScript, markupId + "Script"));
                response.render(OnDomReadyHeaderItem.forScript(markupId + ".init()"));
            }


            @Override
            protected void respond(AjaxRequestTarget target) {
                int pagePosition = getComponent().getRequest().getRequestParameters().getParameterValue("pagePosition").toInt();
                int pageCount = getComponent().getRequest().getRequestParameters().getParameterValue("pageCount").toInt();

                PdfPageInfo pageInfo = PdfPageInfo.of(pagePosition, pageCount);
                // Send event to siblings to update them.
                getComponent().send(getComponent().getParent(), Broadcast.BREADTH, new PdfRenderEvent(target, pageInfo));
            }
        };
    }

    /**
     * Gets JavaScript code to increment page.
     *
     * @param increment increment
     * @return JavaScript code
     */
    public CharSequence getPageIncrementScript(int increment) {
        return getMarkupId() + ".increment(" + increment + ")";
    }
}