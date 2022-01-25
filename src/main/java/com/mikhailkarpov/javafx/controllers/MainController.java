package com.mikhailkarpov.javafx.controllers;

import com.mikhailkarpov.javafx.config.ApplicationProperties;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class MainController implements Initializable {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button exitButton;

    private final ApplicationProperties applicationProperties;

    @Autowired
    public MainController(ApplicationProperties applicationProperties) {
        this.applicationProperties = Objects.requireNonNull(applicationProperties);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.welcomeLabel.setText(this.applicationProperties.getWelcome());
        this.exitButton.setOnAction(e -> exit());
    }

    private void exit() {
        Stage stage = (Stage) this.exitButton.getScene().getWindow();
        stage.close();
    }
}
