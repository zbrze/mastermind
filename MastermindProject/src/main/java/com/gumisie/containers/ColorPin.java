package com.gumisie.containers;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class ColorPin {

    private final Color color;
    private final Image pin;

    public ColorPin(Color color, String url, int size){
        this.color = color;
        this.pin = new Image(url, size, size, false, true);
    }

    public Color getColor() {
        return color;
    }

    public Image getPin() {
        return pin;
    }
}
