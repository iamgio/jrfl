package eu.iamgio.jrfl.program.configuration.folders;

import eu.iamgio.jrfl.api.configuration.SubFolder;

/**
 * Represents the <tt>plugins</tt> folder
 * @author Gio
 */
public class PluginsFolder extends SubFolder {

    public PluginsFolder() {
        super("plugins");
        mkdir();
    }
}
