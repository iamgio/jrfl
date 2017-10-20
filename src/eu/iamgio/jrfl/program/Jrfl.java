package eu.iamgio.jrfl.program;

import eu.iamgio.jrfl.Console;
import eu.iamgio.jrfl.Logger;
import eu.iamgio.jrfl.api.JrflPlugin;
import eu.iamgio.jrfl.api.colors.Colors;
import eu.iamgio.jrfl.api.commands.Commands;
import eu.iamgio.jrfl.api.configuration.Configuration;
import eu.iamgio.jrfl.api.configuration.SubFolder;
import eu.iamgio.jrfl.plugins.DefaultPluginLoader;
import eu.iamgio.jrfl.plugins.Plugins;
import eu.iamgio.jrfl.program.commands.*;
import eu.iamgio.jrfl.program.configuration.configs.Cache;
import eu.iamgio.jrfl.program.configuration.configs.PreferencesConfig;
import eu.iamgio.jrfl.program.configuration.configs.StyleConfig;
import eu.iamgio.jrfl.program.configuration.folders.LogsFolder;
import eu.iamgio.jrfl.program.configuration.folders.PluginsFolder;
import eu.iamgio.jrfl.program.configuration.folders.StylesFolder;
import eu.iamgio.jrfl.program.configuration.styles.Style;
import eu.iamgio.jrfl.program.nodes.Nodes;
import eu.iamgio.libfx.api.CSS;
import eu.iamgio.libfx.api.FXML;
import eu.iamgio.libfx.api.elements.SimpleStage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

/**
 * Main class
 * @author Gio
 * @since 17/05/2017
 */
public class Jrfl extends Application {

    public static final String VERSION = "1.0.0";
    public static final File FOLDER = new File(System.getProperty("user.home") + File.separator + ".jrfl");

    public static SubFolder stylesFolder, pluginsFolder, logsFolder;
    public static Configuration cache,
                                preferences,
                                style;

    public static Style ACTIVE_STYLE;

    private static ExternalClassesSystem externalClassesSystem;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initConfigurations();
        setup(primaryStage);
        new Style(cache.get("selected-style")).setActive();

        HBox.setHgrow(Nodes.PATH_LABEL, Priority.NEVER);
        HBox.setHgrow(Nodes.TEXTFIELD, Priority.ALWAYS);

        Colors.init();

        Console console = Console.getConsole();
        console.setFolder(new File(System.getProperty("user.home")));
        console.sendMessage(preferences.get("messages.join") + "\n§riAmGio (c) 2017");

        Commands.registerCommands(
                new HelpCommand(), new PluginsCommand(), new SetStyleCommand(), new ColorsCommand(), new VariablesCommand());

        for(File file : pluginsFolder.getFile().listFiles()) {
            if(file.getName().endsWith(".jar")) {
                DefaultPluginLoader loader = new DefaultPluginLoader(file);
                loader.enablePlugin();
            }
        }

        console.getLogger().log(Logger.TIME_PREFIX() + "[System] JRFL opened");

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    Plugins.getPlugins().forEach(JrflPlugin::onShutdown);
                    console.getLogger().log(Logger.TIME_PREFIX() + "[System] JRFL closed");
                }));

        Commands.init();
    }

    private void initConfigurations() {
        if(!FOLDER.exists()) FOLDER.mkdir();
        stylesFolder = new StylesFolder();
        pluginsFolder = new PluginsFolder();
        logsFolder = new LogsFolder();

        cache = new Cache();
        preferences = new PreferencesConfig();
        style = new StyleConfig();
    }

    private void setup(Stage primaryStage) {
        Pane root = (Pane) FXML.load(getClass(), "/assets/scenes/MainScene.fxml");
        root.setPrefSize(preferences.getInt("resolution.width"), preferences.getInt("resolution.height"));
        Scene scene = new Scene(root, preferences.getInt("resolution.width"), preferences.getInt("resolution.height"));

        CSS.load(getClass(), scene, "/assets/stylesheets/styles.css");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setId("scrollpane");
        root.getChildren().add(scrollPane);

        initBoxes(root, scene);

        SimpleStage stage = new SimpleStage(primaryStage);
        stage.setIcon(getClass(), "/assets/icon.png");
        stage.show(scene, "JRFL v" + VERSION, preferences.getBoolean("resizable"));
    }

    private void initBoxes(Pane root, Scene scene) {
        VBox vBox = Nodes.TEXT_VBOX;
        HBox hBox = Nodes.TEXTBAR_HBOX;
        ScrollPane scrollPane = Nodes.SCROLL_PANE;

        vBox.setTranslateY(10);
        vBox.setTranslateX(10);
        vBox.prefWidthProperty().bind(scene.widthProperty().subtract(10));
        vBox.prefHeightProperty().bind(scene.heightProperty().subtract(20).subtract(preferences.getDouble("textbar-height")));
        vBox.setSpacing((preferences.getDouble("messages-spacing-level") - 1)*10-5);


        hBox.setPrefWidth(scene.getWidth());
        hBox.setPrefHeight(preferences.getDouble("textbar-height"));
        hBox.setTranslateY(scene.getHeight() - hBox.getPrefHeight());
        hBox.setSpacing(20);

        root.widthProperty().addListener(o -> hBox.setPrefWidth(root.getWidth()));
        root.heightProperty().addListener(o -> hBox.setTranslateY(scene.getHeight() - Nodes.TEXTBAR_HBOX.getPrefHeight()));

        scrollPane.setContent(vBox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setTranslateX(vBox.getTranslateX());
        scrollPane.setTranslateY(vBox.getTranslateY());
        scrollPane.maxWidthProperty().bind(vBox.prefWidthProperty());
        scrollPane.prefWidthProperty().bind(vBox.prefWidthProperty());
        scrollPane.prefHeightProperty().bind(vBox.prefHeightProperty());
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
    }

    /**
     * @return Current external-classes system
     */
    public static ExternalClassesSystem getExternalClassesSystem() {
        if(externalClassesSystem == null) externalClassesSystem = new ExternalClassesSystem();
        return externalClassesSystem;
    }

    public static void main(String...args) {
        launch(args);
    }
}
