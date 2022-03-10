package com.gumisie;

import com.gumisie.config.NavigatorConfig;
import com.gumisie.containers.Difficulty;
import com.gumisie.containers.User;
import com.gumisie.models.GameModel;
import com.gumisie.models.MenuModel;
import com.gumisie.models.UserModel;
import com.gumisie.persistence.interfaces.IUserDao;
import com.gumisie.service.AppNavigator;
import com.gumisie.service.PasswordService;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * JavaFX application start point
 */
public class JavaFXLauncher extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void start (Stage primaryStage) {
        //DI Initialization
        //inject the primary scene
        NavigatorConfig.setPrimaryStage(primaryStage);
        //create the injection context
        context = new SpringApplicationBuilder(Main.class).run();
        //inject model factories
        context.getBean(GameModel.Factory.class);
        context.getBean(MenuModel.Factory.class);
        context.getBean(UserModel.Factory.class);

        //display the initial scene
        primaryStage.getIcons().add(new Image("/img/icon.png"));
        primaryStage.setTitle("Mastermind");
        primaryStage.setMaximized(false);
        primaryStage.setResizable(false);

        //start the application (display the menu)
        MenuModel menuModel = MenuModel.Factory.createInstance(Difficulty.fromInt(1), null);
        AppNavigator appNavigator = context.getBean(AppNavigator.class);
        appNavigator.showMenu(menuModel);
    }


    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }

}
