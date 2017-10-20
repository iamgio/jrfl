package eu.iamgio.jrfl.api.configuration;

import eu.iamgio.jrfl.program.Jrfl;
import javafx.beans.value.ObservableValue;

import java.io.File;
import java.util.HashMap;

/**
 * Class that can create variables used by configurations and commands
 * @author Gio
 */
public class Formatter {

    private static Formatter instance;
    public static Formatter getInstance() {
        if(instance == null) instance = new Formatter();
        return instance;
    }

    private HashMap<String, String> nativeVariables = new HashMap<>();
    private HashMap<String, String> additionalVariables = new HashMap<>();

    private Formatter() {
        instance = this;
        nativeVariables.put("{jrflhome}", System.getProperty("user.home") + File.separator + ".jrfl");
        nativeVariables.put("{home}", System.getProperty("user.home") + File.separator);
        nativeVariables.put("{desktop}", System.getProperty("user.home") + File.separator + "Desktop");
        nativeVariables.put("{user}", System.getProperty("user.name"));
        nativeVariables.put("{version}", Jrfl.VERSION);
    }

    /**
     * Adds a variable to the properties reader
     * @param var Variable
     * @param val Value to be replaced with
     */
    public void setVariable(String var, String val) {
        additionalVariables.put(var, val);
    }

    /**
     * The variable will be automatically updated when the value changes
     * @see Formatter#setVariable(String, String)
     * @param var Variable
     * @param observable Observable property
     */
    public void setObservableVariable(String var, ObservableValue<?> observable) {
        setVariable(var, observable.getValue().toString());
        observable.addListener(o -> {
            if(additionalVariables.containsKey(var)) {
                setVariable(var, observable.getValue().toString());
            }
        });
    }

    /**
     * Removes a variable from the properties reader
     * @param var Variable
     */
    public void removeVariable(String var) {
        additionalVariables.remove(var);
    }

    /**
     * @return Variables and values
     */
    public HashMap<String, String> getVariables() {
        HashMap<String, String> all = new HashMap<>();
        all.putAll(nativeVariables); all.putAll(additionalVariables);
        return all;
    }

    /**
     * @param s String to be formatted
     * @return Formatted string
     */
    public String format(String s) {
        HashMap<String, String> variables = getVariables();
        for(String var : variables.keySet()) {
            s = s.replace(var, variables.get(var));
        }
        return s;
    }
}
