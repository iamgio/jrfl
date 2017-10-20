package eu.iamgio.jrfl.program;

import java.util.List;

/**
 * Used to load external classes to use between plugins without adding them as dependencies.
 * @author Gio
 */
public class ExternalClassesSystem {

    private List<Class<?>> classes;

    ExternalClassesSystem(){}

    /**
     * @return External classes
     */
    public List<Class<?>> getClasses() {
        return classes;
    }

    /**
     * @param className Complete name of the class
     * @return Class by name
     */
    public Class<?> byName(String className) {
        for(Class<?> clazz : classes) {
            if(clazz.getName().equals(className)) {
                return clazz;
            }
        }
        return null;
    }
}
