package com.mikhailkarpov.javafx.config;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;

public class StageReadyEvent extends ApplicationEvent {

    public StageReadyEvent(Stage stage) {
        super(stage);
    }

    public Stage getStage() {
        return (Stage) getSource();
    }
}