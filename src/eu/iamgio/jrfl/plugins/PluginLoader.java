package eu.iamgio.jrfl.plugins;

import eu.iamgio.jrfl.api.commands.Command;
import eu.iamgio.jrfl.api.commands.Commands;
import eu.iamgio.jrfl.exceptions.JrflException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author Gio
 */
public abstract class PluginLoader {

    private File file;
    private Properties properties;

    protected URLClassLoader loader;
    protected Class<?> main;

    public PluginLoader(File file) {
        this.file = file;
        this.properties = getJplugin();
        registerCommands();
    }

    /**
     * @return .jplugin file
     */
    @SuppressWarnings("deprecation")
    private Properties getJplugin() {
        if(properties == null) {
            JarFile jar;
            Properties properties = new Properties();
            try {
                jar = new JarFile(file);
                JarEntry entry = jar.getJarEntry(".jplugin");
                if(entry == null) {
                    throw new JrflException("Cannot find .jplugin");
                }
                properties.load(jar.getInputStream(entry));

                loader = new URLClassLoader(new URL[] {file.toURL()}, getClass().getClassLoader());
                try {
                    main = Class.forName(properties.getProperty("main"), true, loader);
                }
                catch(ClassNotFoundException e) {
                    throw new JrflException("Cannot find main class " + properties.getProperty("main"));
                }
                return properties;
            }
            catch(IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        else return properties;
    }

    /**
     * Registers commands from the .jplugin file
     */
    private void registerCommands() {
        Set<Object> keys = properties.keySet();
        if(keys.contains("commands-package")) {
            String pack = properties.getProperty("commands-package") + ".";
            for(Object name : keys) {
                if(!name.toString().matches("main|commands-package")) {
                    try {
                        Class<?> clazz = Class.forName(pack + properties.getProperty(name.toString()), true, loader);
                        Command command = (Command) clazz.newInstance();
                        Commands.registerCommand(command);
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Disables the plugin
     */
    public abstract void enablePlugin();
}
