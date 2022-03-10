package com.gumisie.presenter;

import com.gumisie.models.UserModel;
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

public class RegisterPresenter {

    //for draggable window
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    Button exit, minimize, signButton;

    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    AnchorPane topPane;

    @FXML
    Label wrongData; //dodać tekst w przypadku błędnych danych rejestracji

    private Stage dialogStage;
    private UserModel model;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
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

        signButton.disableProperty().bind(
                Bindings.isEmpty(passwordTextField.textProperty())
                        .or(Bindings.isEmpty(confirmPasswordField.textProperty()))
                        .or(Bindings.isEmpty(emailTextField.textProperty()))
                        .or(Bindings.isEmpty(firstNameTextField.textProperty()))
                        .or(Bindings.isEmpty(lastNameTextField.textProperty()))
        );

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

        model.userExistsProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                //user exists
                wrongData.setText("This email is already used, try to log in");
            } else {
                wrongData.setText("");
            }
        });

        model.getUserObjectProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                this.dialogStage.close();
            }
        });
    }

    @FXML
    private void handleRegisterAction(){
        createUser();
    }

    private void createUser() {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();

        if (passwordTextField.getText().equals(confirmPasswordField.getText())) {
            model.createUser(email, firstName, lastName, password);
        } else {
            wrongData.setText("Passwords don't match");
            confirmPasswordField.clear();
        }
    }

}