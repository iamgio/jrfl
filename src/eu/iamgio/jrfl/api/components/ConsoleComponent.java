package eu.iamgio.jrfl.api.components;

/**
 * Interface used to create custom components in the console
 * @author Gio
 */
public interface ConsoleComponent {

    /**
     * Shows the component in the scene
     */
    void show();

    /**
     * Removes the component from the scene
     */
    void remove();
}
