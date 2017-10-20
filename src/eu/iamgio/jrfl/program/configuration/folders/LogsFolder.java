package eu.iamgio.jrfl.program.configuration.folders;

import eu.iamgio.jrfl.api.configuration.SubFolder;

/**
 * Represents the <tt>logs</tt> folder
 * @author Gio
 */
public class LogsFolder extends SubFolder {

    public LogsFolder() {
        super("logs");
        mkdir();
    }
}
