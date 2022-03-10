package com.gumisie.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * A single slot which accept an image and a color value (as a RGB String)
 * If a user drags a ColorDragView, they may drop that view into this slot
 */
public class ColorSlotView extends AnchorPane {

    private final ImageView bgImageView;
    private final ImageView fgImageView;

    private Image image = null;
    private Image highlightedImage = null;
    private Image disabledImage = null;

    private Color color = null;

    public ColorSlotView() {

        //create background image holder
        bgImageView = new ImageView();
        this.getChildren().add(bgImageView);
        setTopAnchor(bgImageView, 0.0);
        setBottomAnchor(bgImageView, 0.0);
        setRightAnchor(bgImageView, 0.0);
        setLeftAnchor(bgImageView, 0.0);
        initDropFunctionality(bgImageView);

        //create foreground image holder
        fgImageView = new ImageView();
        this.getChildren().add(fgImageView);
        setTopAnchor(fgImageView, 0.0);
        setBottomAnchor(fgImageView, 0.0);
        setLeftAnchor(fgImageView, 0.0);
        setRightAnchor(fgImageView, 0.0);
        initDropFunctionality(fgImageView);

        this.disabledProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (disabledImage != null) bgImageView.setImage(disabledImage);
            } else {
                if (image != null) bgImageView.setImage(image);
            }
        });
    }

    /**
     * Sets the background of the slot (the image displayed underneath the dropped image)
     *
     * @param regular     The image which will be used as background for this view
     * @param highlighted The image which will be used when a user is hovering over this slot with a droppable payload
     * @param disabled    The image which will be used when this slot is disabled
     */
    public void setBackground(Image regular, Image highlighted, Image disabled) {
        this.image = regular;
        this.highlightedImage = highlighted;
        this.disabledImage = disabled;

        if (isDisabled()) {
            bgImageView.setImage(disabled);
        } else {
            bgImageView.setImage(regular);
        }
    }

    /**
     * @return the color slotted into this view, or null if empty
     */
    public Color getColor() {
        return color;
    }

    /**
     * Initializes the desired drag&drop behavior for a single view controlled by this slot
     *
     * @param view This slot's view, which will have it's drag&drop event handlers set
     */
    private void initDropFunctionality(ImageView view) {
        //init the drop functionality
        view.setOnDragOver(event -> {
            //allow for drop actions on this node
            if (!isDisabled()) {
                if (event.getGestureSource() != view && event.getDragboard().hasImage() && event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            }
        });

        view.setOnDragEntered(event -> {
            if (!isDisabled()) {
                if (event.getGestureSource() != view && event.getDragboard().hasImage() && event.getDragboard().hasString()) {
                    if (highlightedImage != null) this.bgImageView.setImage(highlightedImage);
                }

                event.consume();
            }
        });

        view.setOnDragExited(event -> {
            if (!isDisabled()) {
                if (image != null) this.bgImageView.setImage(image);

                event.consume();
            }
        });

        view.setOnDragDropped(event -> {
            if (!isDisabled()) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasImage()) {
                    fgImageView.setImage(db.getImage());
                    String colorRgb = db.getString();
                    double r = Integer.parseInt(colorRgb.substring(1, 3), 16);
                    double g = Integer.parseInt(colorRgb.substring(3, 5), 16);
                    double b = Integer.parseInt(colorRgb.substring(5, 7), 16);
                    color = Color.color(r / 255.0, g / 255.0, b / 255.0);
                    success = true;
                }

                //notify the source that the drag & drop was finished
                event.setDropCompleted(success);

                event.consume();
            }
        });
    }
}
