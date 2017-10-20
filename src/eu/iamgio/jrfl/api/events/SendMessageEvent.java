package eu.iamgio.jrfl.api.events;

import eu.iamgio.customevents.api.Cancellable;
import eu.iamgio.customevents.api.Event;

/**
 * @author Gio
 */
public class SendMessageEvent extends Event implements Cancellable {

    private String text;
    private Source source;

    private boolean cancelled;

    public SendMessageEvent(String text, Source source) {
        this.text = text;
        this.source = source;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    public enum Source {
        COMMAND_ECHO, OTHER
    }

    /**
     * @return Message text
     */
    public String getText() {
        return text;
    }

    /**
     * @return Source type
     */
    public Source getSource() {
        return source;
    }
}
