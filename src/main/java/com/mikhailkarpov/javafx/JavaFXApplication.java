package com.mikhailkarpov.javafx;

import com.mikhailkarpov.javafx.config.StageReadyEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

public class JavaFXApplication extends Application {

    private static final Logger log = LoggerFactory.getLogger(JavaFXApplication.class);

    private ConfigurableApplicationContext applicationContext;
    private Stage splash;

    @Override
    public void init() {
        Platform.runLater(this::showSplash);
        this.applicationContext = buildAndRunContext();
        Platform.runLater(this::closeSplash);
    }

    @Override
    public void start(Stage stage) {
        this.applicationContext.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
        log.info("Application stopped");
    }

    private ConfigurableApplicationContext buildAndRunContext() {
        String[] args = getParameters().getRaw().stream().toArray(String[]::new);

        return new SpringApplicationBuilder(Launcher.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

    private void showSplash() {
        try {
            log.info("Loading splash");
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/splash.fxml"));
            Scene scene = new Scene(root, Color.TRANSPARENT);

            this.splash = new Stage(StageStyle.TRANSPARENT);
            this.splash.setScene(scene);
            this.splash.setTitle("Loading...");
            this.splash.show();

        } catch (IOException e) {
            log.error("Failed to load splash screen: {}", e.getMessage());
        }
    }

    private void closeSplash() {
        if (this.splash != null) {
            log.info("Closing splash screen");
            this.splash.close();
        }
    }
}
