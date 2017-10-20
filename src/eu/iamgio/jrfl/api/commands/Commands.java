package eu.iamgio.jrfl.api.commands;

import eu.iamgio.customevents.api.Events;
import eu.iamgio.customevents.api.Listener;
import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.api.commands.completion.InitialCommandTabCompletion;
import eu.iamgio.jrfl.api.configuration.Formatter;
import eu.iamgio.jrfl.api.events.CommandEvent;
import eu.iamgio.jrfl.exceptions.JrflException;
import eu.iamgio.jrfl.program.Jrfl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Static class that represents commands
 * @author Gio
 */
public class Commands implements Listener {

    private static List<Command> commands = new ArrayList<>();
    private static boolean initialized = false;

    private Commands(){}

    static {
        commands = sort(commands);
    }

    @SuppressWarnings("deprecation")
    public static void init() {
        if(initialized) throw new JrflException("Commands already initialized");
        if(commands.size() > 0) {
            commands.get(commands.size() - 1).setArgCompletion(-1, new InitialCommandTabCompletion());
        }
        initialized = true;
    }

    /**
     * @param name Command name
     * @return Corresponding command
     */
    public static Command byName(String name) {
        for(Command command : commands) {
            if(command.getName().equals(name)) return command;
        }
        return null;
    }

    /**
     * Sorts a list based on <tt>toString()</tt> methods
     * @param list Unsorted list
     * @param <T> List type
     * @return Sorted list
     */
    private static <T> List<T> sort(List<T> list) {
        List<T> sorted = new ArrayList<>();
        List<String> names = new ArrayList<>();
        list.forEach(c -> names.add(c.toString()));
        Collections.sort(names);
        for(String name : names) {
            T t = null;
            for(T c : list) {
                if(c.toString().equals(name)) {
                    t = c;
                    break;
                }
            }
            sorted.add(t);
        }
        return sorted;
    }

    /**
     * Invokes a command
     * @param text Command full text
     */
    public static void invoke(String text) {
        String name = text.split(" ")[0];
        text = Formatter.getInstance().format(text);
        Command command = byName(name);
        if(command == null) {
            if(Jrfl.preferences.getBoolean("messages.unknown-cmd.enable")) {
                Console.getConsole().sendMessage(Jrfl.preferences.get("messages.unknown-cmd.message"));
            }
            return;
        }
        command.setText(text);
        String[] parts = text.split(" ");
        String[] args = new String[parts.length-1];
        for(int i = 0; i<args.length; i++) {
            args[i] = parts[i+1];
        }
        CommandEvent event = new CommandEvent(command);
        Events.getManager().callEvent(event);
        if(!event.isCancelled()) {
            command.onCommand(args);
        }
    }

    /**
     * Registers a new command
     * @param command Command
     */
    public static void registerCommand(Command command) {
        if(initialized) throw new JrflException("Commands must be registered at the launch of the plugin");
        if(!commands.contains(command)) commands.add(command);
    }

    /**
     * Registers new commands
     * @param commands Commands
     */
    public static void registerCommands(Command...commands) {
        for(Command command : commands) {
            registerCommand(command);
        }
    }

    /**
     * @return Commands
     */
    public static List<Command> getCommands() {
        return commands;
    }
}
