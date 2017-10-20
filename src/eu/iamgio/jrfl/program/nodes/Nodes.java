package eu.iamgio.jrfl.program.nodes;

import eu.iamgio.libfx.api.JavaFX;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * Static class which contains the main nodes
 * @author Gio
 */
public final class Nodes {

    private static boolean initialized = false;

    public static VBox TEXT_VBOX;
    public static HBox TEXTBAR_HBOX;
    public static Rectangle TEXTBAR_RECTANGLE;
    public static Label PATH_LABEL;
    public static TextField TEXTFIELD;
    public static ScrollPane SCROLL_PANE;

    static {
        if(!initialized) {
            TEXT_VBOX = (VBox) JavaFX.fromId("text_vbox");
            TEXTBAR_HBOX = (HBox) JavaFX.fromId("textbar_hbox");
            TEXTBAR_RECTANGLE = (Rectangle) JavaFX.fromId("textbar_rectangle");
            for(Node n : TEXTBAR_HBOX.getChildrenUnmodifiable()) {
                if(n.getId() != null) {
                    if(n.getId().equals("path_label")) PATH_LABEL = (Label) n;
                    if(n.getId().equals("textfield")) TEXTFIELD = (TextField) n;
                }
            }
            SCROLL_PANE = (ScrollPane) JavaFX.fromId("scrollpane");
            initialized = true;
        }
    }
}
