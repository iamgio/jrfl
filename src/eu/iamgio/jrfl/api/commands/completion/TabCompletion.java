package eu.iamgio.jrfl.api.commands.completion;

import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.program.nodes.Nodes;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface used by commands to define auto-completions
 * @author Gio
 */
public interface TabCompletion {

    TextField TEXTFIELD = Nodes.TEXTFIELD;

    /**
     * Method called when 'TAB' is pressed in the textfield
     */
    void onTab(String[] args);

    /**
     * @param possibilities Possibilities
     * @param <T> Generic type.
     * @return Auto-generated completion based on <tt>toString()</tt>s of <tt>T</tt>
     */
    static <T> TabCompletion auto(List<T> possibilities) {
        return args -> {
            String last = args[args.length-1];
            List<T> respected = new ArrayList<>();
            possibilities.forEach(p -> {
                if(p.toString().toLowerCase().startsWith(last.toLowerCase())) {
                    respected.add(p);
                }
            });
            if(respected.size() > 1) {
                String text = "";
                for(T r : respected) {
                    text += r.toString() + "   ";
                }
                Console.getConsole().sendMessage(text);
            }
            else if(respected.size() == 1) {
                String text = "";
                for(int i = 0; i<args.length-1; i++) {
                    text += args[i] + " ";
                }
                TEXTFIELD.setText(text + respected.get(0).toString() + " ");
                TEXTFIELD.positionCaret(TEXTFIELD.getText().length());
            }
        };
    }
}
