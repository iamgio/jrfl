package eu.iamgio.jrfl.api.commands.completion;

import eu.iamgio.jrfl.plugins.Plugins;

import java.util.ArrayList;
import java.util.List;

/**
 * Completion that contains loaded plugins
 * @author Gio
 */
public class PluginsTabCompletion implements TabCompletion {

    @Override
    public void onTab(String[] args) {
        List<String> names = new ArrayList<>();
        Plugins.getPlugins().forEach(p -> names.add(p.getName()));
        TabCompletion.auto(names).onTab(args);
    }
}
