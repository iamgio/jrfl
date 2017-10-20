package eu.iamgio.jrfl.program.commands;

import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.api.commands.Command;
import eu.iamgio.jrfl.api.commands.completion.FileTabCompletion;
import eu.iamgio.jrfl.program.Jrfl;
import eu.iamgio.jrfl.program.configuration.styles.Style;

/**
 * @author Gio
 */
public class SetStyleCommand extends Command {

    public SetStyleCommand() {
        super("setstyle", "[Native] Sets the active style from a .jstyle file", "setstyle <%style>");
        setArgCompletion(0, new FileTabCompletion(".jstyle", Jrfl.stylesFolder.getFile()));
    }

    @Override
    public void onCommand(String[] args) {
        Console console = Console.getConsole();
        if(args.length == 1) {
            if(args[0].endsWith(".jstyle") && FileTabCompletion.file(Jrfl.stylesFolder.getFile(), args[0]).exists()) {
                new Style(FileTabCompletion.trasformedPath(args[0])).setActive();
                console.sendMessage("The style was applied. A restart of the software is suggested");
            }
            else console.sendMessage("§cInvalid style");
        }
        else console.sendMessage("§7Invalid syntax. Usage: §f" + getUsage());
    }
}
