package eu.iamgio.jrfl.program.configuration.folders;

import eu.iamgio.jrfl.api.configuration.SubFolder;

/**
 * Represents the <tt>styles</tt> folder
 * @author Gio
 */
public class StylesFolder extends SubFolder {

    public StylesFolder() {
        super("styles");
        mkdir();
    }
}
