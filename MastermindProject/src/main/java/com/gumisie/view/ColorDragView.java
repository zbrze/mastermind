package com.gumisie.view;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

/**
 * A view which holds a draggable image associated with a particular color
 */
public class ColorDragView extends ImageView {

    private Color color;
    private Image pin;

    public ColorDragView() {
        this.setCursor(Cursor.HAND);

        //init the drag functionality
        this.setOnDragDetected(e -> {

            //put the image onto the dragboard
            Dragboard db = this.startDragAndDrop(TransferMode.ANY);
            ClipboardContent content = new ClipboardContent();
            db.setDragView(this.snapshot(null, null), e.getX(), e.getY());
            content.putImage(this.getImage());
            content.putString(String.format("#%02X%02X%02X",
                    (int)(color.getRed() * 255),
                    (int)(color.getGreen() * 255),
                    (int)(color.getBlue() * 255)));
            db.setContent(content);

            e.consume();
        });

    }


    public void setPin(Color color, Image pin){
        this.color = color;
        this.pin = pin;
        this.setImage(pin);
    }


    public Color getColor() {
        return color;
    }

    public Image getPin() {
        return pin;
    }

}
