package com.gumisie.config;

import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * A class holding the Spring DI configuration for the AppNavigator
 */
@Configuration
@ComponentScan(basePackages = "com.gumisie")
public class NavigatorConfig {

    private static Stage primaryStage;

    public static void setPrimaryStage(Stage primaryStage) {
        NavigatorConfig.primaryStage = primaryStage;
    }

    @Bean
    @Qualifier("primaryStage")
    public Stage getPrimaryStage(){
        if (primaryStage == null) {
            throw new RuntimeException("Primary stage was not set with NavigatorConfig.setPrimaryStage");
        }
        return primaryStage;
    }

}
