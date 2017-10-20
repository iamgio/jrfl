package eu.iamgio.jrfl.program.configuration;

import eu.iamgio.jrfl.api.configuration.Configuration;

import java.util.Properties;

/**
 * Used by the style configurations to parse the <tt>inherit</tt> keyword
 * @author Gio
 */
public class Inheritance {

    public static String inherit(Configuration configuration, String key) {
        Properties properties = configuration.properties;
        String value = properties.get(key).toString();
        return properties.getProperty(value.replace("inherit ", ""));
    }
}
