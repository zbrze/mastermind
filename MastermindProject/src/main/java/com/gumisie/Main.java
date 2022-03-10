package com.gumisie;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static javafx.application.Application.launch;


/**
 * Main Spring application start point
 */
@SpringBootApplication
public class Main {

    public static void main (String[] args){
        launch(JavaFXLauncher.class, args);
    }

}

