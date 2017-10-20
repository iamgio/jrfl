package eu.iamgio.jrfl.plugins;

import eu.iamgio.jrfl.api.JrflPlugin;
import eu.iamgio.jrfl.exceptions.JrflException;

import java.io.File;
import java.lang.reflect.Method;

/**
 * Represents the system that loads plugins
 * @author Gio
 */
public class DefaultPluginLoader extends PluginLoader {

    private static final String LOAD_METHOD = "onLoad";

    public DefaultPluginLoader(File file) {
        super(file);
    }

    /**
     * Enables the plugin
     */
    @Override
    @SuppressWarnings("unchecked")
    public void enablePlugin() {
        try {
            Method method = main.getMethod(LOAD_METHOD);
            Object instance;
            try {
                instance = main.newInstance();
            }
            catch(Exception e) {
                throw new JrflException("Cannot instantiate " + main.getName());
            }
            try {
                method.invoke(instance);
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            JrflPlugin plugin = (JrflPlugin) instance;
            plugin.setLoader(this);
            plugin.onActivate();
            Plugins.plugins.add(plugin);
        }
        catch(NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
