package eu.iamgio.jrfl.api;

import eu.iamgio.jrfl.plugins.PluginLoader;

import java.util.List;

/**
 * Class that must be extended by the main class of a plugin
 * @author Gio
 */
public class JrflPlugin {

    private PluginLoader loader;
    private String name, version, description;
    private List<Parameter> parameters;

    public JrflPlugin(String name, String version) {
        this.name = name;
        this.version = version;
        this.description = "No description provided";
    }

    public JrflPlugin(String name, String version, String description) {
        this.name = name;
        this.version = version;
        this.description = description;
    }

    @Override
    public String toString() {
        return name + " (" + version + ")";
    }

    public void onLoad(){}
    public void onActivate(){}
    public void onShutdown(){}

    /**
     * @return Plugin loader
     */
    public PluginLoader getLoader() {
        return loader;
    }

    /**
     * @param loader New loader
     */
    public void setLoader(PluginLoader loader) {
        this.loader = loader;
    }

    /**
     * @return Plugin name
     */
    public String getName() {
        return name;
    }

    /**
     * @return Plugin version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return Plugin description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return Plugin parameters
     */
    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * @param key Parameter key
     * @return Parameter by key
     */
    public Parameter getParameter(String key) {
        for(Parameter parameter : parameters) {
            if(parameter.getKey().equals(key)) {
                return parameter;
            }
        }
        return null;
    }
}
