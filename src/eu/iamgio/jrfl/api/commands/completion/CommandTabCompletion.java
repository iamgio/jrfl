package eu.iamgio.jrfl.api.commands.completion;

import eu.iamgio.jrfl.api.commands.Commands;

/**
 * Completion which contains loaded commands
 * @author Gio
 */
public class CommandTabCompletion implements TabCompletion {

    @Override
    public void onTab(String[] args) {
        TabCompletion.auto(Commands.getCommands()).onTab(args);
    }
}
