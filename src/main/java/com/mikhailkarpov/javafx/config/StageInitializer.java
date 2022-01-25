package com.mikhailkarpov.javafx.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {

    private static final Logger log = LoggerFactory.getLogger(StageInitializer.class);

    @Value("classpath:/fxml/main.fxml")
    private Resource resource;

    private final ApplicationContext applicationContext;
    private final ApplicationProperties applicationProperties;

    public StageInitializer(ApplicationContext applicationContext, ApplicationProperties applicationProperties) {
        this.applicationContext = applicationContext;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(this.resource.getURL());
            fxmlLoader.setControllerFactory(this.applicationContext::getBean);

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Stage stage = event.getStage();
            stage.setScene(scene);
            stage.setTitle(this.applicationProperties.getTitle());
            stage.show();

        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
