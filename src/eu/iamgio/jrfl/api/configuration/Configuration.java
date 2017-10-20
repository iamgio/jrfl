package eu.iamgio.jrfl.api.configuration;

import eu.iamgio.jrfl.program.configuration.Inheritance;
import eu.iamgio.jrfl.exceptions.JrflException;
import eu.iamgio.jrfl.program.Jrfl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

/**
 * Extendible class to create configurations in the <tt>.jrfl</tt> folder
 * @author Gio
 */
public class Configuration {

    public Properties properties;
    private File out;

    protected static final String INTERNAL_ASSETS_PATH = "/assets/configs/";

    private Formatter formatter;

    public Configuration(String in, String out, Class<?> clazz) {
        this.properties = new Properties();
        this.out = new File(Jrfl.FOLDER, out);
        this.formatter = Formatter.getInstance();
        try {
            if(!exists())
                Files.copy(
                    clazz.getResourceAsStream(in),
                    this.out.toPath(), StandardCopyOption.REPLACE_EXISTING
                );
            properties.load(new FileInputStream(this.out));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public Configuration(File file) {
        this.formatter = Formatter.getInstance();
        this.properties = new Properties();
        try {
            properties.load(new FileInputStream(file));
            this.out = file;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return properties.toString();
    }

    /**
     * @return <tt>true</tt> if the file exists
     */
    public boolean exists() {
        return out.exists();
    }

    /**
     * Sets a key of the property file to the value
     * @param key Key
     * @param value Value
     * @return This
     */
    public Configuration set(String key, String value) {
        properties.setProperty(key, value);
        return this;
    }

    /**
     * @param key Key
     * @return Corresponding value
     */
    public String get(String key) {
        String value = properties.getProperty(key);
        if(value == null) {
            throw new JrflException("Not existing property: " + key);
        }
        if(value.startsWith("inherit")) {
            return Inheritance.inherit(this, key);
        }
        return formatter.format(value);
    }

    /**
     * @param key Key
     * @return Corresponding value as int
     */
    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    /**
     * @param key Key
     * @return Corresponding value as double
     */
    public double getDouble(String key) {
        return Double.parseDouble(get(key));
    }

    /**
     * @param key Key
     * @return Corresponding value as boolean
     */
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    /**
     * Exports the file
     * @param overwrite <tt>true</tt> if an overwrite is wanted (if exists)
     */
    public void store(boolean overwrite) {
        try {
            if(!exists() || overwrite)
                properties.store(new FileOutputStream(out), "");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <tt>overwrite = false</tt>
     * @see Configuration#store(boolean)
     */
    public void store() {
        store(false);
    }
}
