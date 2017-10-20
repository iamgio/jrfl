package eu.iamgio.jrfl;

import eu.iamgio.jrfl.program.Jrfl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Represents the system which manages log files
 * @author Gio
 */
public class Logger {

    private File file;

    Logger() {
        Calendar calendar = Calendar.getInstance();
        file = new File(Jrfl.logsFolder.getFile(),
                calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + ".txt");
        if(!file.exists()) {
            try {
                file.createNewFile();
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Logs a certain text into the current file
     * @param text Text to be logged
     */
    public void log(String text) {
        try {
            List<String> lines = Files.readAllLines(file.toPath());
            lines.add(text);
            Files.write(file.toPath(), lines);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Time prefix (HH:mm:ss)
     */
    public static String TIME_PREFIX() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return "[" + format.format(date) + "] ";
    }
}
