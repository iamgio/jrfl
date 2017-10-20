package eu.iamgio.jrfl.program.nodes;

import javafx.scene.shape.Rectangle;

/**
 * Represents the bar behind the textfield
 * @author Gio
 */
public class JrflRectangle extends JrflNode<Rectangle> {

    @Override
    protected Rectangle node() {
        return Nodes.TEXTBAR_RECTANGLE;
    }

    public JrflRectangle() {
        super("textbar");
        n.setHeight(Nodes.TEXTBAR_HBOX.getPrefHeight());

        n.widthProperty().bind(pane.widthProperty());
        n.translateYProperty().bind(Nodes.TEXTBAR_HBOX.translateYProperty());
    }
}
