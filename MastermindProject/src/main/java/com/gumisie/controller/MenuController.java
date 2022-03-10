package com.gumisie.controller;

import com.gumisie.containers.Difficulty;
import com.gumisie.containers.User;
import com.gumisie.models.MenuModel;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuController {

    //for draggable window
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    Button exit, minimize, logoutButton, loginButton, registerButton;

    @FXML
    ToggleGroup difficultyGroup;

    @FXML
    RadioButton easyRadioButton, normalRadioButton, hardRadioButton;

    @FXML
    Label welcomeUser;

    @FXML
    AnchorPane topPane;

    int difficultyInt;

    private MenuModel menuModel;

    public void init(MenuModel menuModel){
        this.menuModel = menuModel;

        //zczytuję domyślną trudność (normal)
        changeDifficulty();

        //minimalizacja okna
        minimize.setOnAction(e -> {
            ((Stage)((Button)e.getSource()).getScene().getWindow()).setIconified(true);
        });

        //wyjście
        exit.setOnAction(e -> {
            System.exit(0);
        });

        logoutButton.disableProperty().bind(
                Bindings.isNull(
                        menuModel.userProperty()
                ));

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

        menuModel.userProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null) {
                welcomeUser.setText(newValue.getFirstName());
                setWelcomeUser(newValue);
            }
        });
    }

    //obsługa przycisków w menu
    @FXML
    public void startGamePressed(){
        if(menuModel.getUser() != null){
            menuModel.startGame();
        } else {
           menuModel.askForLogin();
        }
    }

    @FXML
    public void scoreBoardPressed(){
        menuModel.showScoreboard();
    }

    @FXML
    public void statsPressed(){
        menuModel.showStats();
    }

    @FXML
    public void loginPressed(){
        menuModel.login();
    }

    @FXML
    public void registerPressed(){
            menuModel.register();
    }

    //obsługa przycisków trudności
    @FXML
    public void changeDifficulty(){
        if(easyRadioButton.isSelected()) {
            difficultyInt = 0;
            menuModel.setDifficulty(Difficulty.fromInt(0));
            System.out.println(0);
        }
        else if(normalRadioButton.isSelected()) {
            difficultyInt = 1;
            menuModel.setDifficulty(Difficulty.fromInt(1));
            System.out.println(1);
        }
        else if(hardRadioButton.isSelected()) {
            difficultyInt = 2;
            menuModel.setDifficulty(Difficulty.fromInt(2));
            System.out.println(2);
        }
    }

    @FXML
    public void logoutPressed(){
        System.out.println("Logging out");
        menuModel.setUser(null);
        welcomeUser.setText(null);
        logoutButton.setVisible(false);
        loginButton.setVisible(true);
        registerButton.setVisible(true);
    }

    public void setWelcomeUser(User user){
        System.out.println("Binding logged user");
        menuModel.setUser(user);

        logoutButton.setVisible(true);
        loginButton.setVisible(false);
        registerButton.setVisible(false);
    }

    public MenuModel getMenuModel(){return  menuModel;}

}
