package eu.iamgio.jrfl.program.configuration.styles;

import com.sun.istack.internal.NotNull;
import eu.iamgio.jrfl.program.Jrfl;
import eu.iamgio.jrfl.api.configuration.Configuration;
import eu.iamgio.jrfl.program.nodes.JrflLabel;
import eu.iamgio.jrfl.program.nodes.JrflRectangle;
import eu.iamgio.jrfl.program.nodes.JrflTextField;
import eu.iamgio.jrfl.program.nodes.Nodes;
import eu.iamgio.libfx.api.JavaFX;
import javafx.scene.layout.Pane;

import java.io.File;

/**
 * Abstracts a <tt>*.jstyle</tt> file
 * @author Gio
 */
public class Style {

    private String name;
    private File file;
    private Configuration configuration;

    public Style(@NotNull String name) {
        this.name = name;
        this.file = new File(Jrfl.stylesFolder.getFile(), name);
        this.configuration = new Configuration(file);
    }

    /**
     * Sets the style active
     * If <tt>name</tt> is null, the style will be temporary
     */
    public void setActive() {
        Jrfl.ACTIVE_STYLE = this;
        Jrfl.cache.set("selected-style", name);
        Jrfl.cache.store(true);

        Pane pane = ((Pane) JavaFX.getRoot());
        pane.getChildrenUnmodifiable().forEach(n -> n.setStyle(""));
        Nodes.TEXT_VBOX.getChildrenUnmodifiable().forEach(n -> n.setStyle(""));
        pane.setStyle("-fx-background-color: " + configuration.get("background.color"));

        new JrflRectangle(); new JrflLabel(); new JrflTextField();
    }

    /**
     * @return Style name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Style file
     */
    public File getFile() {
        return file;
    }

    /**
     * @return Style configuration
     */
    public Configuration getConfiguration() {
        return configuration;
    }
}
