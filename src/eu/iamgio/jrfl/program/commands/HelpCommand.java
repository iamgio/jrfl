package eu.iamgio.jrfl.program.commands;

import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.api.commands.Alias;
import eu.iamgio.jrfl.api.commands.Command;
import eu.iamgio.jrfl.api.commands.Commands;
import eu.iamgio.jrfl.api.commands.completion.CommandTabCompletion;

/**
 * @author Gio
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        super("help", "Shows the help", "help [%command]");
        setArgCompletion(0, new CommandTabCompletion());
    }

    @Override
    public void onCommand(String[] args) {
        Console console = Console.getConsole();
        if(args.length == 0) {
            console.sendMessage("List of commands:");
            for(Command command : Commands.getCommands()) {
                if(!(command instanceof HelpCommand)) {
                    console.sendMessage("   " + command.getName() + " §r- " + command.getDescription());
                }
            }
        }
        else {
            Command command = Commands.byName(args[0]);
            if(command != null) {
                console.sendMessage("§2Name: §a" + command.getName() +
                        "\n§2Description: §a" + command.getDescription() +
                        "\n§2Usage: §a" + command.getUsage() +
                                (command instanceof Alias ? "\n§2Alias of: §a" + ((Alias) command).getOriginalCommand().getName() : "") +
                        "\n§a" + (command instanceof Alias ?
                            ((Alias) command).getOriginalCommand().getClass().getName() : command.getClass().getName()));
            }
            else console.sendMessage("§cInvalid command");
        }
    }
}
