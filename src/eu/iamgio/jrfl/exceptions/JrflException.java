package eu.iamgio.jrfl.exceptions;

import eu.iamgio.jrfl.Console;

/**
 * @author Gio
 */
public class JrflException extends RuntimeException {

    private String message;

    public JrflException(String message) {
        super(message);
        this.message = message;
    }

    public JrflException() {
        this("An error occurred");
    }

    /**
     * Prints the message in the console
     */
    public void print() {
        Console.getConsole().sendMessage("§c" + message);
    }
}
