package eu.iamgio.jrfl.api.configuration;

import eu.iamgio.jrfl.program.Jrfl;

import java.io.File;

/**
 * Extendible class to create folders in the <tt>.jrfl</tt> folder
 * @author Gio
 */
public class SubFolder {

    private File folder;

    public SubFolder(String name) {
        this.folder = new File(Jrfl.FOLDER, name);
    }

    @Override
    public String toString() {
        return folder.toString();
    }

    /**
     * @return The folder as <tt>File</tt>
     */
    public File getFile() {
        return folder;
    }

    /**
     * Creates the directory
     * @param overwrite <tt>true</tt> if an overwrite is wanted (if exists)
     */
    public void mkdir(boolean overwrite) {
        if(!folder.exists() || overwrite) folder.mkdir();
    }

    /**
     * <tt>overwrite = false</tt>
     * @see SubFolder#mkdir(boolean)
     */
    public void mkdir() {
        mkdir(false);
    }
}
