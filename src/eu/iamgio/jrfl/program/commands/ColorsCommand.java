package eu.iamgio.jrfl.program.commands;

import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.api.colors.Colors;
import eu.iamgio.jrfl.api.commands.Command;

/**
 * @author Gio
 */
public class ColorsCommand extends Command {

    public ColorsCommand() {
        super("colors", "[Native] Shows the avaible colors", "colors");
    }

    @Override
    public void onCommand(String[] args) {
        Console console = Console.getConsole();
        console.sendMessage("Color char: " + Colors.COLOR_CHAR, false);
        String text = "";
        int c = 0;
        for(String key : Colors.getColors().keySet()) {
            c++;
            text += "   " + key + key.replace(Colors.COLOR_CHAR, "");
            if(c%10 == 0) text += "\n";
        }
        console.sendMessage(text);
    }
}
