package eu.iamgio.jrfl.api;

/**
 * Represents a place to save keys and values
 * @author Gio
 */
public class Parameter {

    private String key, value;

    public Parameter(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @return Key
     */
    public String getKey() {
        return key;
    }

    /**
     * @return Value
     */
    public String getValue() {
        return value;
    }
}
