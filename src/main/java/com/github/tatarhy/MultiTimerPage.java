package com.github.tatarhy;

import org.apache.wicket.ajax.AbstractAjaxTimerBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.util.time.Duration;

/**
 * Cannot add multiple AjaxTimerBehavior. It only added the second behavior. Something wrong?
 */
public class MultiTimerPage extends WebPage {
    public MultiTimerPage() {
        add(new AbstractAjaxTimerBehavior(Duration.seconds(15)) {
            @Override
            protected void onTimer(AjaxRequestTarget target) {
                target.appendJavaScript("console.log('fired first');");
            }
        });

        add(new AbstractAjaxTimerBehavior(Duration.seconds(20)) {
            @Override
            protected void onTimer(AjaxRequestTarget target) {
                target.appendJavaScript("console.log('fired second');");
            }
        });
    }
}
