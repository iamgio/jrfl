package eu.iamgio.jrfl.program.configuration.configs;

import eu.iamgio.jrfl.api.configuration.Configuration;
import eu.iamgio.jrfl.program.Jrfl;

/**
 * Represents the <tt>preferences.jpref</tt> configuration
 * @author Gio
 */
public class PreferencesConfig extends Configuration {

    public PreferencesConfig() {
        super(INTERNAL_ASSETS_PATH + "preferences.jpref", "preferences.jpref", Jrfl.class);
        store();
    }
}
