package com.gumisie.view;

import com.gumisie.containers.Code;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class SlotRowView extends GridPane {

    private final HBox colorsContainer;
    private final GridPane indicatorsContainer;

    //slots images
    private Image slotImage;
    private Image highlightedImage;
    private Image disabledImage;

    //indicator images
    private Image emptyImage;
    private Image partiallyCorrectImage;
    private Image fullyCorrectImage;

    private List<ColorSlotView> slots;
    private List<ImageView> indicators;

    private int colorCount = 0;
    private int partiallyCorrect = 0;
    private int fullyCorrect = 0;

    public SlotRowView() {

        colorsContainer = new HBox();
        this.getChildren().add(colorsContainer);
        GridPane.setRowIndex(colorsContainer, 0);
        GridPane.setColumnIndex(colorsContainer, 0);
        GridPane.setColumnSpan(colorsContainer, 1);

        indicatorsContainer = new GridPane();
        indicatorsContainer.setPadding(new Insets(5, 0, 0, 5));
        this.getChildren().add(indicatorsContainer);
        GridPane.setRowIndex(indicatorsContainer, 0);
        GridPane.setColumnIndex(indicatorsContainer, 1);
        GridPane.setColumnSpan(indicatorsContainer, 1);

    }

    public void setSlotImage(Image slotImage, Image highlightedImage, Image disabledImage){
        this.slotImage = slotImage;
        this.highlightedImage = highlightedImage;
        this.disabledImage = disabledImage;

        updateSlots();
    }


    public void setIndicatorImages(Image emptyImage, Image fullyCorrectImage, Image partiallyCorrectImage){
        this.emptyImage = emptyImage;
        this.partiallyCorrectImage = partiallyCorrectImage;
        this.fullyCorrectImage = fullyCorrectImage;

        updateIndicators();
    }

    public void setCorrect(int fullyCorrect, int partiallyCorrect){
        this.fullyCorrect = fullyCorrect;
        this.partiallyCorrect = partiallyCorrect;

        updateIndicators();
    }

    public void setColorCount(int colorCount){
        this.colorCount = colorCount;
        colorsContainer.getChildren().clear();
        indicatorsContainer.getChildren().clear();

        slots = new ArrayList<>(colorCount);
        for (int i = 0; i < colorCount; i++){
            ColorSlotView colorSlot = new ColorSlotView();
            slots.add(colorSlot);
            colorsContainer.getChildren().add(colorSlot);
        }

        double rowSize = Math.ceil(Math.sqrt(colorCount));
        indicators = new ArrayList<>((int) (rowSize * rowSize));
        for (int row = 0; row < rowSize; row++){
            for (int column = 0; column < rowSize; column++){
                ImageView indicator = new ImageView();
                indicators.add(indicator);
                indicatorsContainer.getChildren().add(indicator);
                indicator.setFitWidth(20.0);
                indicator.setFitHeight(20.0);
                GridPane.setRowIndex(indicator, row);
                GridPane.setColumnIndex(indicator, column);
            }
        }

        updateSlots();
        updateIndicators();
    }

    /**
     * @return true if all slots in this row are filled
     */
    public boolean isFullyFilled(){
        if (slots == null) return true;
        for (ColorSlotView slot : slots){
            if (slot.getColor() == null) return false;
        }
        return true;
    }

    /**
     * @return a Code object representing the color combination inputted in this row
     */
    public Code getCode(){
        Code result = new Code(colorCount);
        for (int i = 0; i < colorCount; i++){
            result.putColor(i, slots.get(i).getColor());
        }
        return result;
    }

    private void updateIndicators(){
        if (indicators != null) {
            for (int i = 0; i < indicators.size(); i++) {
                Image image = null;
                if (i < fullyCorrect) {
                    image = fullyCorrectImage;
                } else if (i < fullyCorrect + partiallyCorrect) {
                    image = partiallyCorrectImage;
                } else if (i < colorCount){
                    image = emptyImage;
                }
                indicators.get(i).setImage(image);
            }
        }
    }

    private void updateSlots() {
        if (slots != null) {
            for (ColorSlotView slot : slots) {
                slot.setBackground(slotImage, highlightedImage, disabledImage);
            }
        }
    }

    public int getColorCount(){
        return colorCount;
    }

}
