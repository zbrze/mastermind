package com.gumisie.presenter;

import com.gumisie.containers.ColorPin;
import com.gumisie.containers.Difficulty;
import com.gumisie.models.GameModel;
import com.gumisie.view.ColorDragView;
import com.gumisie.view.SlotRowView;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GamePresenter {


    //for draggable window
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    Pane pane;

    @FXML
    GridPane gridPane1;

    @FXML
    GridPane gridPane2;

    @FXML
    ImageView but, resetBut;

    @FXML
    Button exit, minimize;

    @FXML
    AnchorPane topPane;

    //numer obecnie aktywnego rzędu (licząc od zera)
    private int currentRow;
    //wielkość slotów/pinów
    private int slotSize = 40;

    private GameModel gameModel;

    //wczytanie obrazków dla ruchomych pinów
    //(wczytać wszystkie dostępne, ilość wykorzystanych do danej gry podawana przy inicjalizacji)
    private List<SlotRowView> slotRowViews = new ArrayList<SlotRowView>();
    private List<ColorPin> colorPins = List.of(new ColorPin(Color.RED, "/img/rt1.png", slotSize),
                                              new ColorPin(Color.YELLOW, "/img/rt2.png", slotSize),
                                              new ColorPin(Color.GREEN, "/img/rt3.png", slotSize),
                                              new ColorPin(Color.CYAN, "/img/rt4.png", slotSize),
                                              new ColorPin(Color.DARKVIOLET, "/img/rt5.png", slotSize),
                                              new ColorPin(Color.ORANGE, "/img/rt6.png", slotSize),
                                              new ColorPin(Color.HOTPINK, "/img/rt7.png", slotSize),
                                              new ColorPin(Color.BLUE, "/img/rt8.png", slotSize));

    public void init(GameModel model){

        //wczytuje obrazki używane jako tła do slotów (wielkość slotSize x slotSize)
        Image slotBg = new Image("/img/slot.png", slotSize, slotSize, false, true);
        Image slotBgHighlighted = new Image("/img/slot_highlighted.png", slotSize, slotSize, false, true);
        Image indicatorEmpty = new Image("/img/gray_indicator.png", slotSize, slotSize, false, true);
        Image indicatorPartiallyCorrect = new Image("/img/white_indicator_textured_1.png", slotSize, slotSize, false, true);
        Image indicatorFullyCorrect = new Image("/img/black_indicator_textured_1.png", slotSize, slotSize, false, true);
        Image slotDiabled = new Image("/img/slot_disabled.png", slotSize, slotSize, false, true);
        this.gameModel = model;

        Difficulty difficulty = model.getDifficulty();

        if(difficulty.toInt() == 0){
            pane.setPrefSize(415, 635);
            topPane.setPrefWidth(415);
            gridPane1.setPrefSize(260, 580);
            gridPane2.setPrefHeight(275);
            resetBut.setLayoutY(385);
            but.setLayoutY(455);
            minimize.setLayoutX(307);
            exit.setLayoutX(361);
        }
        else if(difficulty.toInt() == 1){
            pane.setPrefSize(500, 770);
            topPane.setPrefWidth(500);
            gridPane1.setPrefSize(345, 660);
            gridPane2.setPrefHeight(500);
            resetBut.setLayoutY(555);
            but.setLayoutY(625);
            minimize.setLayoutX(392);
            exit.setLayoutX(446);
        }
        else if(difficulty.toInt() == 2){
            pane.setPrefSize(530, 710);
            topPane.setPrefWidth(530);
            gridPane1.setPrefSize(375, 660);
            gridPane2.setPrefHeight(500);
            resetBut.setLayoutY(555);
            but.setLayoutY(625);
            minimize.setLayoutX(415);
            exit.setLayoutX(469);
        }

        currentRow = 0;
        for(int i=0; i<difficulty.getRows(); i++){
            //tworzy nowy rząd
            SlotRowView slotRowView = new SlotRowView();

            //rzędy poza pierwszym domyślnie wyłączone
            if(i>0){
                slotRowView.setDisable(true);
            }

            //ustawia tła slotów
            slotRowView.setSlotImage(slotBg, slotBgHighlighted, slotDiabled);
            //ustawia obrazki wskaźników z prawej strony
            slotRowView.setIndicatorImages(indicatorEmpty, indicatorFullyCorrect, indicatorPartiallyCorrect);
            //ustawia ilość kolorowych slotów/wskaźników w tym rzędzie
            slotRowView.setColorCount(difficulty.getColumns());
            //dodaje rząd do listy
            slotRowViews.add(i, slotRowView);
            //dodaje rząd do grida
            gridPane1.add(slotRowView, 0, i);
        }

        //ustawiam przeciągalne widoki dla każdego koloru z dostarczonej konfiguracji
        for(int i=0;i<difficulty.getColors();i++){
            ColorPin color = colorPins.get(i);
            ColorDragView colorDragView = new ColorDragView();
            colorDragView.setPin(color.getColor(), color.getPin());
            gridPane2.add(colorDragView, 0, i);
        }

        but.setOnMouseClicked(e -> submitCode(difficulty.getRows(), difficulty.getColumns()));
        resetBut.setOnMouseClicked(e -> restart());

        //minimalizacja okna
        minimize.setOnAction(e -> {
            ((Stage)((Button)e.getSource()).getScene().getWindow()).setIconified(true);
        });

        //wyjście
        exit.setOnAction(e -> {
            final Node source = (Node) e.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        });

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
    }

    //wyłącza aktywny rząd i uaktywnia następny
    //obecnie zapętla się - po ostatnim przeskakuje do pierwszego
    //(dla prezentacji, żeby nie restartować programu po przejściu do końca)
    //+ustawia wskaźniki poprawności na losową wartość w pełni i częściowo poprawnych

    private void nextRow(int rows, int columns, int fullyCorrect, int partiallyCorrect) {
        slotRowViews.get(currentRow).setDisable(true);
        slotRowViews.get(currentRow).setCorrect(fullyCorrect, partiallyCorrect);
        currentRow++;
        if(currentRow<=rows)
            slotRowViews.get(currentRow).setDisable(false);

    }


    private void restart() {
        gameModel.restart();
    }

    public void submitCode(int rows, int columns){
        SlotRowView slot = slotRowViews.get(currentRow);
        if(slot.isFullyFilled()){
            if(this.gameModel.makeMove(slot.getCode(), currentRow)) {
                System.out.println("Game won");
                //wyświetl okno z informacją o wygranej
            }
            if(currentRow != rows) {
                int[] correctness = this.gameModel.getCodeCorrectness(slot.getCode());
                this.nextRow(rows, columns, correctness[0], correctness[1]);
            }
            else
                gameModel.createGame(false);
        }
    }
}
