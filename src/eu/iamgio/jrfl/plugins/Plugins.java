package eu.iamgio.jrfl.plugins;

import eu.iamgio.jrfl.api.JrflPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Static class which represents all the installed plugins
 * @author Gio
 */
public class Plugins {

    static List<JrflPlugin> plugins = new ArrayList<>();

    /**
     * @return Enabled plugins
     */
    public static List<JrflPlugin> getPlugins() {
        return plugins;
    }

    /**
     * @param name Plugin name
     * @return Corresponding plugin
     */
    public static JrflPlugin byName(String name) {
        for(JrflPlugin plugin : plugins) {
            if(plugin.getName().equals(name)) {
                return plugin;
            }
        }
        return null;
    }
}
