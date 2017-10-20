package eu.iamgio.jrfl.api.commands;

import eu.iamgio.jrfl.api.commands.completion.InitialCommandTabCompletion;
import eu.iamgio.jrfl.api.commands.completion.TabCompletion;
import eu.iamgio.jrfl.api.configuration.Formatter;
import eu.iamgio.jrfl.program.nodes.Nodes;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Class that must be extended by commands classes
 * @author Gio
 */
public abstract class Command {

    private String name, description, usage, text;
    private List<Alias> aliases = new ArrayList<>();
    private HashMap<Integer, TabCompletion> completions = new HashMap<>();
    private boolean log = true;

    protected Command(String name, String usage) {
        this.name = name;
        this.description = "No description provided";
        this.usage = usage;
    }

    protected Command(String name, String description, String usage) {
        this.name = name;
        this.description = description;
        this.usage = usage;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * @return Text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text
     * @param text Text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return Command name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Command description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Command usage
     */
    public String getUsage() {
        return usage;
    }

    public abstract void onCommand(String[] args);

    /**
     * @return <tt>true</tt> if the command is fully logged
     */
    public boolean isLogged() {
        return log;
    }

    /**
     * Sets if the command must be logged fully
     * @param log Log value
     */
    public void setLogged(boolean log) {
        this.log = log;
    }

    /**
     * Adds an alias
     * @param name Alias name
     */
    public void addAlias(String name) {
        Alias alias = new Alias(name, this);
        for(int arg : completions.keySet()) {
            alias.setArgCompletion(arg, completions.get(arg));
        }
        aliases.add(alias);
        Commands.registerCommand(alias);
    }

    /**
     * @return Command aliases
     */
    public List<Alias> getAliases() {
        return aliases;
    }

    /**
     * Adds an auto-completion to a certain argument - Must be added in decreasing order
     * @param index Arg index
     * @param completion Completion type
     */
    @SuppressWarnings("deprecation")
    protected void setArgCompletion(int index, TabCompletion completion) {
        completions.put(index, completion);
        Nodes.TEXTFIELD.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            if(e.getCode() == KeyCode.TAB) {
                String t = Nodes.TEXTFIELD.getText() + "\0";
                String[] args = Formatter.getInstance().format(t).split(" ");
                if(args.length == 1 && args[0].replace("\0", "").isEmpty() && completion instanceof InitialCommandTabCompletion) {
                    completion.onTab(new String[] {""});
                }
                else if(args.length > 0 && (args[0].equals(name) || args.length == 1)) {
                    if(args.length == index + 2) {
                        List<String> list = Arrays.asList(args);
                        list.set(list.size()-1, list.get(list.size()-1).replace("\0", ""));
                        completion.onTab(list.toArray(new String[list.size()]));
                    }
                }
            }
        });
    }
}
