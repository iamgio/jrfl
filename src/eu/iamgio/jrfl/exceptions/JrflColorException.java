package eu.iamgio.jrfl.exceptions;

import eu.iamgio.jrfl.api.colors.Colors;

/**
 * Thrown if an error during a color parsing occurs
 * @author Gio
 */
public class JrflColorException extends JrflException {

    public JrflColorException(char value) {
        super("Invalid color: " + Colors.COLOR_CHAR + value);
    }
}
