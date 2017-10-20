package eu.iamgio.jrfl.api.commands.completion;

import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.api.commands.Commands;
import eu.iamgio.jrfl.program.Jrfl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Completion used to select files
 * @author Gio
 */
public class FileTabCompletion implements TabCompletion {

    private String extension;
    private File folder;
    private boolean onlyFolders = false;

    public FileTabCompletion(){}

    public FileTabCompletion(String extension) {
        this.extension = extension.startsWith(".") ? extension : "." + extension;
    }

    public FileTabCompletion(File folder) {
        this.folder = folder;
    }

    public FileTabCompletion(String extension, File folder) {
        this(extension);
        this.folder = folder;
    }

    public FileTabCompletion setOnlyFolders(boolean onlyFolders) {
        this.onlyFolders = onlyFolders;
        return this;
    }

    @Override
    public void onTab(String[] args) {
        if(Commands.byName(args[0]) == null) return;
        File folder;
        boolean multiFolder = false;
        if(this.folder == null) {
            if(!args[args.length - 1].contains("/")) {
                folder = Console.getConsole().getFolder();
            }
            else {
                String f = "";
                String[] parts = args[args.length - 1].split("/");
                if(!args[args.length - 1].endsWith("/")) {
                    parts[parts.length - 1] = null;
                }
                for(String part : parts) {
                    if(part != null) f += FileTabCompletion.trasformedPath(part) + "/";
                }
                folder = new File(Console.getConsole().getFolder(), f);
                multiFolder = true;
            }
        }
        else folder = this.folder;
        File[] listFiles = folder.listFiles();
        if(listFiles == null) return;
        List<File> files = Arrays.asList(listFiles);
        List<File> respected = new ArrayList<>();
        String last = args[args.length-1];
        if(multiFolder) {
            last += "\0";
            String[] parts = last.split("/");
            last = parts[parts.length-1].replace("\0", "");
        }
        for(File f : files) {
            if(f.getName().replace(" ", Jrfl.preferences.get("tab.space-replacer")).toLowerCase().startsWith(last.toLowerCase())) {
                if((extension != null && f.getName().endsWith(extension)) || extension == null) {
                    if((!f.isFile() && onlyFolders) || !onlyFolders) respected.add(f);
                }
            }
        }
        if(respected.size() > 1) {
            String text = "";
            for(File file : respected) {
                text += (file.isFile() ? "§a" : "§b") + file.getName() + "   ";
            }
            Console.getConsole().sendMessage(text);
        }
        else if(respected.size() == 1) {
            File f = respected.get(0);
            String s = args[args.length-1] + "\0";

            String text = "";
            for(int i = 0; i<args.length-1; i++) {
                text += args[i] + " ";
            }

            if(s.contains("/")) {
                String[] parts = s.split("/");
                for(int i = 0; i<parts.length-1; i++) {
                    text += parts[i].replace(" ", Jrfl.preferences.get("tab.space-replacer")) + "/";
                }
            }
            text += f.getName().replace(" ", Jrfl.preferences.get("tab.space-replacer"));
            text = text.replace("\0", "");
            TEXTFIELD.setText(text + (f.isFile() ? " " : "/"));
            TEXTFIELD.positionCaret(TEXTFIELD.getText().length());
        }
    }

    /**
     * Gets a file based on a relative path
     * @param path Relative path gained from the console
     * @return Corresponding file
     */
    public static File file(File folder, String path) {
        return new File(folder, trasformedPath(path));
    }

    /**
     * Trasforms the path respecting spacing rules
     * @param path Relative path gained from the console
     * @return Trasformed path
     */
    public static String trasformedPath(String path) {
        return path.replace(Jrfl.preferences.get("tab.space-replacer"), " ").replace("/", File.separator);
    }
}
