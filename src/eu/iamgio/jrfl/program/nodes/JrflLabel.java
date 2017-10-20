package eu.iamgio.jrfl.program.nodes;

import com.sun.javafx.tk.Toolkit;
import javafx.geometry.Pos;
import javafx.scene.control.Label;

/**
 * Represents the path label
 * @author Gio
 */
public class JrflLabel extends JrflNode<Label> {

    @Override
    protected Label node() {
        return Nodes.PATH_LABEL;
    }

    public JrflLabel() {
        super("pathtext");
        n.setAlignment(Pos.CENTER_LEFT);
        pane.getScene().widthProperty().addListener(o ->
                n.setMinWidth(Toolkit.getToolkit().getFontLoader().computeStringWidth(n.getText(), n.getFont())));
    }
}
