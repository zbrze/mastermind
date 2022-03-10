package com.gumisie.models;

import com.gumisie.service.AppNavigator;
import com.gumisie.containers.Difficulty;
import com.gumisie.containers.User;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

public class MenuModel {

    private final SimpleObjectProperty<User> user;
    private Difficulty difficulty;
    private final AppNavigator navigator;

    private MenuModel(AppNavigator navigator, Difficulty difficulty, User user){
        this.navigator = navigator;
        this.difficulty = difficulty;
        this.user = new SimpleObjectProperty<>(user);
    }

    public void startGame(){
        navigator.startNewGame(GameModel.Factory.createInstance(difficulty, user.get()));
    }

    public void showScoreboard(){
        navigator.showScoreBoard();
    }

    public void showStats(){
        //TODO show stats screen
        throw new RuntimeException("Not implmented");
    }

    public void register(){
        UserModel userModel = UserModel.Factory.createInstance();
        userModel.getUserObjectProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                this.setUser(newValue);
            }
        });
        navigator.showRegisterDialog(userModel);
    }

    public void login(){
        UserModel userModel = UserModel.Factory.createInstance();
        userModel.getUserObjectProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                this.setUser(newValue);
            }
        });
        navigator.showLoginDialog(userModel);
    }


    public void askForLogin(){
        UserModel userModel = UserModel.Factory.createInstance();
        userModel.getUserObjectProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                this.setUser(newValue);
            }
        });
        navigator.showLoginOrRegister(userModel);
    }


    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public User getUser() {
        return user.get();
    }

    public SimpleObjectProperty<User> userProperty() {
        return user;
    }

    public void setUser(User user) {
        this.user.set(user);
    }

    @Component
    public static class Factory{

        private static AppNavigator navigator;

        public Factory(AppNavigator navigator) {
            Factory.navigator = navigator;
        }

        public static MenuModel createInstance(Difficulty difficulty, User user){
            return new MenuModel(navigator, difficulty, user);
        }

    }

}
