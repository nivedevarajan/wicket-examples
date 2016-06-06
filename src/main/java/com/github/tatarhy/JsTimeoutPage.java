package com.github.tatarhy;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.WebPage;

import java.time.Duration;

/**
 * Timer events using javascript setTimeout() function
 */
public class JsTimeoutPage extends WebPage {
    public JsTimeoutPage() {
        add(new AbstractTimerCallbackBehavior(Duration.ofSeconds(10L)) {
            @Override
            protected void respond(AjaxRequestTarget target) {
                target.appendJavaScript("console.log('fire first event at ' + new Date().toString());");
            }
        });

        add(new AbstractTimerCallbackBehavior(Duration.ofSeconds(20L)) {
            @Override
            protected void respond(AjaxRequestTarget target) {
                target.appendJavaScript("console.log('fire second event at ' + new Date().toString());");
            }
        });
    }

    private static abstract class AbstractTimerCallbackBehavior extends AbstractDefaultAjaxBehavior {
        private Duration duration;

        public AbstractTimerCallbackBehavior(Duration duration) {
            this.duration = duration;
        }

        @Override
        public void renderHead(Component component, IHeaderResponse response) {
            super.renderHead(component, response);

            CharSequence timerScript = "setTimeout(function() {"
                    + getCallbackScript()
                    + "},"
                    + duration.toMillis()
                    + ")";

            response.render(OnDomReadyHeaderItem.forScript(timerScript));
        }
    }
}
