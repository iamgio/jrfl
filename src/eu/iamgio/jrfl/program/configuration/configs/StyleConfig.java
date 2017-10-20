package eu.iamgio.jrfl.program.configuration.configs;

import eu.iamgio.jrfl.api.configuration.Configuration;
import eu.iamgio.jrfl.program.Jrfl;

import java.io.File;

/**
 * Represents the <tt>default.jstyle</tt> style
 * @author Gio
 */
public class StyleConfig extends Configuration {

    public StyleConfig() {
        super(INTERNAL_ASSETS_PATH + "defaultstyle.jstyle", "styles" + File.separator + "default.jstyle", Jrfl.class);
    }
}
