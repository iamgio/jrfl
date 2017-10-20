package eu.iamgio.jrfl.api.components;

import eu.iamgio.jrfl.program.nodes.JrflNode;
import eu.iamgio.jrfl.program.nodes.Nodes;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.Arrays;
import java.util.List;

/**
 * Component that generates a editable text area
 * @author Gio
 */
public class EditorComponent implements ConsoleComponent {

    private String text;
    private TextArea area;

    public EditorComponent(String text) {
        this.text = text;
        node();
    }

    public EditorComponent(List<String> lines) {
        text = "";
        for(String line : lines) {
            text += line + "\n";
        }

        node();
    }

    private void node() {
        new JrflNode<TextArea>("editor") {

            @Override
            protected TextArea node() {
                TextArea n = new TextArea(text);
                n.setMinHeight(Nodes.TEXT_VBOX.getPrefHeight()/1.5);
                area = n;
                return n;
            }
        };
    }

    @Override
    public void show() {
        Nodes.TEXT_VBOX.getChildren().add(area);
        VBox.setVgrow(area, Priority.NEVER);
    }

    @Override
    public void remove() {
        Nodes.TEXT_VBOX.getChildren().remove(area);
    }

    /**
     * @return Updated text
     */
    public String getText() {
        return area.getText();
    }

    /**
     * @return Lines of the text
     */
    public List<String> getLines() {
        return Arrays.asList(area.getText().split("\\n"));
    }

    /**
     * Adds a combination to the editor
     * @param combination Combination
     * @param runnable Runnable to be run
     */
    public void addKeyCombination(KeyCodeCombination combination, Runnable runnable) {
        area.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            if(combination.match(e)) runnable.run();
        });
    }

    /**
     * Sets the text area editable
     * @param editable Editable property
     */
    public void setEditable(boolean editable) {
        area.setEditable(editable);
    }

    /**
     * @return Raw text area
     */
    public TextArea getTextArea() {
        return area;
    }
}
