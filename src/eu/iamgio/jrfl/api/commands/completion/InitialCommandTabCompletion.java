package eu.iamgio.jrfl.api.commands.completion;

import eu.iamgio.jrfl.api.commands.Commands;

/**
 * @author Gio
 * @deprecated Use {@link CommandTabCompletion}
 */
public class InitialCommandTabCompletion implements TabCompletion {

    @Override
    public void onTab(String[] args) {
        TabCompletion.auto(Commands.getCommands()).onTab(args);
    }
}
