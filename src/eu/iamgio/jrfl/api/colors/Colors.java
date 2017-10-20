package eu.iamgio.jrfl.api.colors;

import eu.iamgio.jrfl.exceptions.JrflColorException;
import eu.iamgio.jrfl.program.Jrfl;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.HashMap;

/**
 * Static class used to manage colors
 * @author Gio
 */
public final class Colors {

    private Colors(){}

    private static HashMap<String, String> colors;

    public static final String COLOR_CHAR = "§";

    static {
        if(colors == null) {
            colors = new HashMap<>();

            for(Object key : Jrfl.ACTIVE_STYLE.getConfiguration().properties.keySet()) {
                if(key.toString().startsWith("colors.")) {
                    defineColor(key.toString().replace("colors.", "").charAt(0),
                            Jrfl.ACTIVE_STYLE.getConfiguration().get(key.toString()));
                }
            }
        }
    }

    /**
     * Empty method used to call the static initializer
     */
    public static void init() {}

    /**
     * @return All colors
     */
    public static HashMap<String, String> getColors() {
        return colors;
    }

    /**
     * @param character Color char (ex: 6, a...). Do not include the prefix
     * @return <tt>true</tt> if the color is valid
     */
    public static boolean isValid(char character) {
        return colors.containsKey(COLOR_CHAR + character);
    }

    /**
     * Creates a new color
     * @param character Color char (ex: 6, a...). Do not include the prefix
     * @param paint Color paint in HEX (#00000)
     */
    public static void defineColor(char character, String paint) {
        colors.put(COLOR_CHAR + character, paint);
    }

    /**
     * @param text Text
     * @return Parsed text
     */
    public static TextFlow parse(String text) throws JrflColorException {
        boolean startsWithColor = text.startsWith(COLOR_CHAR);
        TextFlow flow = new TextFlow();
        String[] parts = text.split(COLOR_CHAR);
        for(int i = 0; i<parts.length; i++) {
            String part = parts[i];
            if(part.length() > 0) {
                String value = part.charAt(0) + "";
                if(startsWithColor || i > 0) part = part.substring(1);
                if(!(!startsWithColor && i == 0) && !isValid(value.charAt(0))) throw new JrflColorException(value.charAt(0));
                Paint paint = Paint.valueOf(!startsWithColor && i == 0 ? Jrfl.ACTIVE_STYLE.getConfiguration().get("text.color") : colors.get(COLOR_CHAR + value));
                Text label = new Text(part);
                label.setFill(paint);
                flow.getChildren().add(label);
            }
        }
        return flow;
    }

    /**
     * @param flow <tt>TextFlow</tt> to check
     * @return <tt>true</tt> if the flow is empty
     */
    public static boolean isEmpty(TextFlow flow) {
        String text = "";
        for(Node t : flow.getChildren()) {
            text += ((Text) t).getText();
        }
        return text.isEmpty();
    }
}
