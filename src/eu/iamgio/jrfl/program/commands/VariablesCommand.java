package eu.iamgio.jrfl.program.commands;

import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.api.commands.Command;
import eu.iamgio.jrfl.api.configuration.Formatter;

import java.util.HashMap;

/**
 * @author Gio
 */
public class VariablesCommand extends Command {

    public VariablesCommand() {
        super("variables", "[Native] Shows the registered variables with their corresponding values", "variables");
    }

    @Override
    public void onCommand(String[] args) {
        Console console = Console.getConsole();
        HashMap<String, String> variables = Formatter.getInstance().getVariables();
        console.sendMessage("Variables (" + variables.keySet().size() + "):");
        for(String key : variables.keySet()) {
            console.sendMessage("   " + key + " - " + variables.get(key), false, false);
        }
    }
}
