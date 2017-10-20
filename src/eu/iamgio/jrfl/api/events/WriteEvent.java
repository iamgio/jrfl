package eu.iamgio.jrfl.api.events;

import eu.iamgio.customevents.api.Event;

/**
 * @author Gio
 */
public class WriteEvent extends Event {

    private String text;

    public WriteEvent(String text) {
        this.text = text;
    }

    /**
     * @return Message text
     */
    public String getText() {
        return text;
    }
}
