package eu.iamgio.jrfl.api.events;

import eu.iamgio.customevents.api.Cancellable;
import eu.iamgio.customevents.api.Event;
import eu.iamgio.jrfl.api.commands.Command;

/**
 * @author Gio
 */
public class CommandEvent extends Event implements Cancellable {

    private Command command;

    private boolean cancelled;

    public CommandEvent(Command command) {
        this.command = command;
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
     * @return Command
     */
    public Command getCommand() {
        return command;
    }
}
