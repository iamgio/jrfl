package eu.iamgio.jrfl;

import com.sun.istack.internal.Nullable;
import eu.iamgio.customevents.api.Cancellable;
import eu.iamgio.customevents.api.Events;
import eu.iamgio.jrfl.api.Parameter;
import eu.iamgio.jrfl.api.colors.Colors;
import eu.iamgio.jrfl.api.commands.Command;
import eu.iamgio.jrfl.api.commands.Commands;
import eu.iamgio.jrfl.api.commands.completion.FileTabCompletion;
import eu.iamgio.jrfl.api.components.ConsoleComponent;
import eu.iamgio.jrfl.api.configuration.Formatter;
import eu.iamgio.jrfl.api.events.EditMessageEvent;
import eu.iamgio.jrfl.api.events.SendMessageEvent;
import eu.iamgio.jrfl.exceptions.JrflException;
import eu.iamgio.jrfl.program.Jrfl;
import eu.iamgio.jrfl.program.nodes.JrflWrittenText;
import eu.iamgio.jrfl.program.nodes.Nodes;
import eu.iamgio.libfx.api.JavaFX;
import eu.iamgio.libfx.api.tasks.DelayerTask;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the visual console
 * @author Gio
 */
public class Console {

    private File folder;
    private List<String> writtenCommands;
    private List<Parameter> parameters;

    private int COMMAND_SWITCH_INDEX = 0;

    private static Console instance;

    public static Console getConsole() {
        if(instance == null) instance = new Console();
        return instance;
    }

    private Console() {
        writtenCommands = new ArrayList<>();
    }

    /**
     * @return Scene root
     */
    public Pane getPane() {
        return ((Pane) JavaFX.getRoot());
    }

    /**
     * @return Logger
     */
    public Logger getLogger() {
        return new Logger();
    }

    /**
     * Dispatches a command
     * @param command Command
     */
    public void dispatchCommand(String command) {
        if(sendMessage(FileTabCompletion.trasformedPath(command), false, true, SendMessageEvent.Source.COMMAND_ECHO)) {
            addWrittenCommand(command);
            Commands.invoke(command);
            Command cmd = Commands.byName(command);
            if(cmd == null) {
                Console.getConsole().getLogger().log(Logger.TIME_PREFIX() + command);
            }
            else {
                Console.getConsole().getLogger().log(Logger.TIME_PREFIX() + "[Command] " +
                        (cmd.isLogged() ? cmd.getText() : "[nolog] " + cmd.getName())
                );
            }
        }
    }

    /**
     * @return Sent nodes (messages, components...)
     */
    public List<Node> getNodes() {
        return Nodes.TEXT_VBOX.getChildren();
    }

    /**
     * Adds a written command
     * @param command Written command
     */
    private void addWrittenCommand(String command) {
        if(writtenCommands.size() == 0 || !writtenCommands.get(writtenCommands.size()-1).equals(command)) {
            writtenCommands.add(command);
            COMMAND_SWITCH_INDEX = writtenCommands.size();
        }
    }

    /**
     * Adds a key combination
     * @param combination Combination
     * @param runnable Runnable to be run
     */
    public void addKeyCombination(KeyCodeCombination combination, Runnable runnable) {
        getPane().getScene().addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            if(combination.match(e)) runnable.run();
        });
    }

    /**
     * Removes texts
     */
    public void clear() {
        Nodes.TEXT_VBOX.getChildren().clear();
        writtenCommands.clear();
        COMMAND_SWITCH_INDEX = 0;
    }

    /**
     * @return Current folder
     */
    public File getFolder() {
        return folder;
    }

    /**
     * Sets the current folder
     * @param folder Folder
     */
    public void setFolder(File folder) {
        if(folder.isFile()) {
            throw new JrflException("A folder is required");
        }
        this.folder = folder;
        Nodes.PATH_LABEL.setText("  " + folder.getAbsolutePath() +
                (folder.getAbsolutePath().endsWith(File.separator) ? "" : File.separator) + " >");
    }

    private boolean sendMessage(String message, boolean parseColors, boolean format, SendMessageEvent.Source source, @Nullable Integer position) {
        if(message.contains("\n")) {
            String[] parts = message.split("\\n");
            int c = 0;
            for(String s : parts) {
                if(!sendMessage(s, parseColors, format, source, null)) c++;
            }
            scrollDown();
            return c != parts.length;
        }
        message = (format ? Formatter.getInstance().format(message) : message) + "\n";
        String original = null;
        if(position != null) {
            if(!(Nodes.TEXT_VBOX.getChildren().get(position) instanceof TextFlow)) {
                throw new JrflException("Cannot edit " + Nodes.TEXT_VBOX.getChildren().get(position).getClass().getName());
            }
            original = "";
            TextFlow f = (TextFlow) Nodes.TEXT_VBOX.getChildren().get(position);
            for(Node label : f.getChildren()) {
                original += ((Label) label).getText();
            }
        }
        Text text = new Text(message);
        text.setFill(Paint.valueOf(Jrfl.ACTIVE_STYLE.getConfiguration().get("text.color")));
        TextFlow flow = parseColors ? Colors.parse(message) : new TextFlow(text);
        if(Colors.isEmpty(flow)) return false;
        if(source == SendMessageEvent.Source.COMMAND_ECHO) text.opacityProperty().setValue(0.5);
        Cancellable event;
        if(position == null) {
            Nodes.TEXT_VBOX.getChildren().add(flow);
            event = new SendMessageEvent(message.replace("\n", ""), source);
            Events.getManager().callEvent((SendMessageEvent) event);
        }
        else {
            event = new EditMessageEvent(position, original.replace("\n", ""), message.replace("\n", ""));
            Events.getManager().callEvent((EditMessageEvent) event);
        }
        if(event.isCancelled()) {
            if(position == null) {
                Nodes.TEXT_VBOX.getChildren().remove(flow);
            }
        }
        else {
            if(position == null) {
                new JrflWrittenText();
            }
            else {
                Nodes.TEXT_VBOX.getChildren().set(position, flow);
                new JrflWrittenText(flow);
            }
        }
        scrollDown();
        return true;
    }

    /**
     * Sends a message to the console
     * @param message Message
     * @param parseColors <tt>true</tt> to change color chars into colors
     * @param format <tt>true</tt> to format using variables
     * @param source Message source
     * @return <tt>true</tt> if the message was sent successfully
     */
    public boolean sendMessage(String message, boolean parseColors, boolean format, SendMessageEvent.Source source) {
        return sendMessage(message, parseColors, format, source, null);
    }

    /**
     * <tt>source = OTHER</tt>
     * @see Console#sendMessage(String, boolean, boolean, SendMessageEvent.Source)
     */
    public boolean sendMessage(String message, boolean parseColors, boolean format) {
        return sendMessage(message, parseColors, format, SendMessageEvent.Source.OTHER, null);
    }

    /**
     * <tt>format = true </tt>
     * <tt>source = OTHER</tt>
     * @see Console#sendMessage(String, boolean, boolean, SendMessageEvent.Source)
     */
    public boolean sendMessage(String message, boolean parseColors) {
        return sendMessage(message, parseColors, true, SendMessageEvent.Source.OTHER, null);
    }

    /**
     * <tt>parseColors = true </tt>
     * <tt>format = true </tt>
     * <tt>source = OTHER</tt>
     * @see Console#sendMessage(String, boolean, boolean, SendMessageEvent.Source)
     */
    public boolean sendMessage(String message) {
        return sendMessage(message, true, true, SendMessageEvent.Source.OTHER, null);
    }

    /**
     * Edits a message
     * @param index Message index
     * @param message Edited message
     * @throws JrflException If the message contains multiple lines
     */
    public void editMessage(int index, String message) throws JrflException {
        if(message.contains("\n")) {
            throw new JrflException("Edited messages cannot contain multiple lines");
        }
        sendMessage(message, true, true, SendMessageEvent.Source.OTHER, index);
    }

    /**
     * Shows the component
     * @param component Component
     */
    public void showComponent(ConsoleComponent component) {
        component.show();
        scrollDown();
    }

    /**
     * Removes the component
     * @param component Component
     */
    public void removeComponent(ConsoleComponent component) {
        component.remove();
    }

    /**
     * @return Text in the textfield
     */
    public String getText() {
        return Nodes.TEXTFIELD.getText();
    }

    /**
     * Sets the text in the textfield
     * @param text Text
     */
    public void setText(String text) {
        Nodes.TEXTFIELD.setText(text);
    }

    /**
     * Scrolls the messages box to the max value
     */
    public void scrollDown() {
        new DelayerTask().run(() -> Nodes.SCROLL_PANE.setVvalue(Integer.MAX_VALUE), Duration.millis(20));
    }

    /**
     * @return Global parameters
     */
    public List<Parameter> getGlobalParameters() {
        return parameters;
    }

    /**
     * @param key Global parameter key
     * @return Global parameter by key
     */
    public Parameter getGlobalParameter(String key) {
        for(Parameter parameter : parameters) {
            if(parameter.getKey().equals(key)) {
                return parameter;
            }
        }
        return null;
    }

    /**
     * Sets the previous command into the textfield
     * @return <tt>true</tt> if the switch happened
     */
    public boolean switchCommandUp() {
        try {
            COMMAND_SWITCH_INDEX--;
            Nodes.TEXTFIELD.setText(writtenCommands.get(COMMAND_SWITCH_INDEX));
            Nodes.TEXTFIELD.positionCaret(Nodes.TEXTFIELD.getText().length());
            return true;
        }
        catch(Exception e) {
            COMMAND_SWITCH_INDEX++;
            return false;
        }
    }

    /**
     * Sets the next command into the textfield
     * @return <tt>true</tt> if the switch happened
     */
    public boolean switchCommandDown() {
        try {
            COMMAND_SWITCH_INDEX++;
            Nodes.TEXTFIELD.setText(writtenCommands.get(COMMAND_SWITCH_INDEX));
            Nodes.TEXTFIELD.positionCaret(Nodes.TEXTFIELD.getText().length());
            return true;
        }
        catch(Exception e) {
            Nodes.TEXTFIELD.setText("");
            if(COMMAND_SWITCH_INDEX != writtenCommands.size()) COMMAND_SWITCH_INDEX--;
            return false;
        }
    }
}
