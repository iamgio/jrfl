package eu.iamgio.jrfl.api.commands;

/**
 * Represents a command alias
 * @author Gio
 */
public class Alias extends Command {

    private Command command;

    Alias(String name, Command command) {
        super(name, command.getDescription(), command.getUsage().replace(command.getName(), name));
        this.command = command;
    }

    /**
     * @return Original command
     */
    public Command getOriginalCommand() {
        return command;
    }

    @Override
    public void onCommand(String[] args) {
        command.onCommand(args);
    }
}
