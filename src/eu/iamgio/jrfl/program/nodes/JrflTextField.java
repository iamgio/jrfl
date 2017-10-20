package eu.iamgio.jrfl.program.nodes;

import eu.iamgio.customevents.api.Events;
import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.api.events.WriteEvent;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Represents the main textfield
 * @author Gio
 */
public class JrflTextField extends JrflNode<TextField> {

    @Override
    protected TextField node() {
        return Nodes.TEXTFIELD;
    }

    public JrflTextField() {
        super("textfield");
        Console console = Console.getConsole();
        n.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if(e.getCode() == KeyCode.TAB) e.consume();
        });
        n.setAlignment(Pos.CENTER_LEFT);
        n.setStyle(n.getStyle() + "-fx-background-color: transparent;");

        n.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                if(!n.getText().replace(" ", "").equals("")) {
                    Events.getManager().callEvent(new WriteEvent(n.getText()));
                    console.dispatchCommand(n.getText());
                    n.setText("");
                }
            }
            else if(e.getCode() == KeyCode.UP) {
                console.switchCommandUp();
            }
            else if(e.getCode() == KeyCode.DOWN) {
                console.switchCommandDown();
            }
            else if(e.getCode() == KeyCode.ESCAPE) {
                n.setText("");
            }
        });
    }
}
