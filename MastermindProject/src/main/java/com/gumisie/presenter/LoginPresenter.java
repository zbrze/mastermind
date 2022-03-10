package com.gumisie.presenter;

import com.gumisie.models.UserModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

public class LoginPresenter {

    //for draggable window
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    Button exit, minimize, toRegister, signButton;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    AnchorPane topPane;

    @FXML
    Label wrongData; //dodać tekst w przypadku błędnych danych logowania

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    private UserModel model;

    public void init(UserModel model){
        this.model = model;

        //minimalizacja okna
        minimize.setOnAction(e -> {
            ((Stage)((Button)e.getSource()).getScene().getWindow()).setIconified(true);
        });

        //wyjście
        exit.setOnAction(e -> {
            final Node source = (Node) e.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });


        //draggable window
        topPane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        topPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        toRegister.setOnAction(e -> {
            final Node source = (Node) e.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();

            Platform.runLater(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                stage.close();
            });

            model.showRegisterDialog();
        });

        signButton.disableProperty().bind(
                Bindings.isEmpty(passwordTextField.textProperty())
                        .or(Bindings.isEmpty(emailTextField.textProperty()))
        );

        model.wrongPasswordProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue){
                //wrong password
                System.out.println("Wrong password");
                wrongData.setText("Wrong password");
            } else {
                wrongData.setText("");
            }
        });

        model.getUserObjectProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                //user has been set
                this.dialogStage.close();
            }
        });
    }

    @FXML
    private void handleLoginAction(){
        authenticateUser();
    }


    private void authenticateUser() {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

         model.authenticateUser(email, password);
    }

}
