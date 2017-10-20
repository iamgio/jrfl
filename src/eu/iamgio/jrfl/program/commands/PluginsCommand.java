package eu.iamgio.jrfl.program.commands;

import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.api.JrflPlugin;
import eu.iamgio.jrfl.api.commands.Command;
import eu.iamgio.jrfl.api.commands.completion.PluginsTabCompletion;
import eu.iamgio.jrfl.plugins.Plugins;

/**
 * @author Gio
 */
public class PluginsCommand extends Command {

    public PluginsCommand() {
        super("plugins", "[Native] Shows the loaded plugins", "plugins [%plugin]");
        setArgCompletion(0, new PluginsTabCompletion());
    }

    @Override
    public void onCommand(String[] args) {
        Console console = Console.getConsole();
        if(args.length == 1) {
            JrflPlugin plugin = Plugins.byName(args[0]);
            if(plugin == null) {
                console.sendMessage("§cInvalid plugin");
                return;
            }
            console.sendMessage("§2Name: §a" + plugin.getName() +
                    "\n§2Version: §a" + plugin.getVersion() +
                    "\n§2Description: §a" + plugin.getDescription() +
                    "\n§a" + plugin.getClass().getName());
        }
        else {
            console.sendMessage("Plugins (" + Plugins.getPlugins().size() + "): ");
            for(JrflPlugin plugin : Plugins.getPlugins()) {
                console.sendMessage("   " + plugin.toString() + " - " + plugin.getDescription(), false);
            }
        }
    }
}
