package eu.iamgio.jrfl.api.events;

import eu.iamgio.customevents.api.Cancellable;
import eu.iamgio.customevents.api.Event;

/**
 * @author Gio
 */
public class EditMessageEvent extends Event implements Cancellable {

    private int index;
    private String originalText, text;

    private boolean cancelled;

    public EditMessageEvent(int index, String originalText, String text) {
        this.index = index;
        this.originalText = originalText;
        this.text = text;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * @return Message index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @return Original text
     */
    public String getOriginalText() {
        return originalText;
    }

    /**
     * @return Message text
     */
    public String getText() {
        return text;
    }
}
