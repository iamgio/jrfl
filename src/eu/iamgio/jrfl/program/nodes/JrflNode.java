package eu.iamgio.jrfl.program.nodes;

import eu.iamgio.jrfl.api.configuration.Configuration;
import eu.iamgio.jrfl.exceptions.JrflException;
import eu.iamgio.jrfl.program.Jrfl;
import eu.iamgio.libfx.api.JavaFX;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;

/**
 * Class used to style nodes using styles
 * @author Gio
 */
public abstract class JrflNode<T extends Node> {

    protected final Configuration CONFIGURATION = Jrfl.ACTIVE_STYLE.getConfiguration();

    protected T n;
    protected Pane pane;
    private String id;

    protected abstract T node();

    protected JrflNode(String id) {
        this.id = id;
        pane = (Pane) JavaFX.getRoot();
        n = node();
        styleAll();
    }

    protected JrflNode(String id, T node) {
        this.id = id;
        pane = (Pane) JavaFX.getRoot();
        n = node;
        styleAll();
    }

    private void styleAll() {
        String[] styles = {
                "color", "font", "size",
                "fill", "bgcolor",
                "stroke-color", "stroke-size", "border-size", "border-color", "opacity"
        };
        String regex = "";
        for(String style : styles) {
            regex += style + "|";
        }
        regex = regex.substring(0, regex.length()-1);
        if(n instanceof Label || n instanceof TextField || n instanceof TextFlow || n instanceof TextArea) {
            style("text-fill", styles[0], "#000");
            style("font-family", styles[1], Font.getDefault().getName());
            style("font-size", styles[2], Font.getDefault().getSize() + "");
        }
        else {
            style("background-color", styles[0], "#FFF");
            style("fill", styles[0], "#000");
        }
        if(n instanceof TextArea) {
            style("background-color", styles[4], "#FFF");
        }
        style("stroke", styles[5], "#000");
        style("stroke-width", styles[6], "0");
        style("border-width", styles[7], "0");
        style("border-color", styles[8], "#000");
        style("opacity", styles[9], "1");

        for(Object key : Jrfl.ACTIVE_STYLE.getConfiguration().properties.keySet()) {
            if(key.toString().startsWith(id + ".")) {
                String attr = key.toString().split("\\.")[1];
                if(!attr.matches(regex)) {
                    n.setStyle(
                            n.getStyle() + attr + ": " + Jrfl.ACTIVE_STYLE.getConfiguration().get(key.toString()) + "; ");
                }
            }
        }
    }

    protected void style(String style, String key, String alt) {
        String x = style.equals("font-family") ? "\"" : "";
        n.setStyle((n.getStyle() == null ? "" : n.getStyle()) +
                "-fx-" + style + ": " + x + (alt == null ? get(key) : get(key, alt)) + x + "; ");
    }

    public String get(String type) {
        try {
            return CONFIGURATION.get(id + "." + type);
        }
        catch(JrflException e) {
            throw new JrflException("The style parameter " + id + "." + type + " is necessary");
        }
    }

    public String get(String type, String alt) {
        try {
            return CONFIGURATION.get(id + "." + type);
        }
        catch(JrflException e) {
            return alt;
        }
    }
}
