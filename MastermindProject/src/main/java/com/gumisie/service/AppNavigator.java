package com.gumisie.service;

import com.gumisie.controller.MenuController;
import com.gumisie.models.GameModel;
import com.gumisie.models.MenuModel;
import com.gumisie.models.UserModel;
import com.gumisie.presenter.GamePresenter;
import com.gumisie.presenter.LoginPresenter;
import com.gumisie.presenter.RegisterPresenter;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AppNavigator {

    private final Stage primaryStage;

    public AppNavigator(@Qualifier("primaryStage") Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void showMenu(MenuModel menuModel) {
        try {
            this.primaryStage.setTitle("My second JavaFX app");
            this.primaryStage.initStyle(StageStyle.UNDECORATED);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MenuView.fxml"));
            Parent root = loader.load();

            // set initial data into controller
            MenuController menuController = loader.getController();
            menuController.init(menuModel);

            // add layout to a scene and show them all
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            // don't do this in common apps
            e.printStackTrace();
        }

    }
    public void showRegisterDialog(UserModel userModel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegisterView.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit transaction");
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            AnchorPane page = loader.load();
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            scene.getStylesheets().addAll(this.getClass().getResource("/css/registerstyle.css").toExternalForm());
            RegisterPresenter registerPresenter = loader.getController();
            registerPresenter.init(userModel);
            registerPresenter.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void showLoginDialog(UserModel userModel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit transaction");
            dialogStage.initStyle(StageStyle.UNDECORATED);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            BorderPane page = loader.load();
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            scene.getStylesheets().addAll(this.getClass().getResource("/css/loginstyle.css").toExternalForm());
            LoginPresenter loginPresenter = loader.getController();
            loginPresenter.init(userModel);
            loginPresenter.setDialogStage(dialogStage);
            dialogStage.showAndWait();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void startNewGame(GameModel gameModel) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GameView.fxml"));
            Parent root = loader.load();

            GamePresenter gamePresenter = loader.getController();
            gamePresenter.init(gameModel);

            Scene scene = new Scene(root);
            scene.getStylesheets().addAll(this.getClass().getResource("/css/gamestyle.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void showLoginOrRegister(UserModel userModel){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);
        window.setMinHeight(200);
        Label label = new Label();
        label.setText("You need to be logged");
        Button loginButton = new Button("Log In");
        loginButton.setOnAction(x -> {
            this.showLoginDialog(userModel);
            final Node source = (Node) x.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });

        Button registerButton = new Button("Register");
        registerButton.setOnAction(x -> {
            this.showRegisterDialog(userModel);
            final Node source = (Node) x.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, loginButton, registerButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

    public void showScoreBoard() {
    }
}