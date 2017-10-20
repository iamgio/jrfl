package eu.iamgio.jrfl.program.nodes;

import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

/**
 * Represents a text printed in the console
 * @author Gio
 */
public class JrflWrittenText extends JrflNode<TextFlow> {

    @Override
    protected TextFlow node() {
        VBox messagesBox = Nodes.TEXT_VBOX;
        return (TextFlow) messagesBox.getChildren().get(messagesBox.getChildren().size()-1);
    }

    public JrflWrittenText() {
        super("text");
    }

    public JrflWrittenText(TextFlow flow) {
        super("text", flow);
    }
}
