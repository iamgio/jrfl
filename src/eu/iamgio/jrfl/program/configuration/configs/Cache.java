package eu.iamgio.jrfl.program.configuration.configs;

import eu.iamgio.jrfl.api.configuration.Configuration;
import eu.iamgio.jrfl.program.Jrfl;

/**
 * Represents the <tt>cache.properties</tt> file
 * @author Gio
 */
public class Cache extends Configuration {

    public Cache() {
        super(INTERNAL_ASSETS_PATH + "cache.properties", "cache.properties", Jrfl.class);
    }
}
