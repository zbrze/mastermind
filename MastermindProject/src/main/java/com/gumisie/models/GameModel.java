package com.gumisie.models;

import com.gumisie.service.AppNavigator;
import com.gumisie.containers.Code;
import com.gumisie.containers.Difficulty;
import com.gumisie.containers.Game;
import com.gumisie.containers.User;
import com.gumisie.persistence.interfaces.IGameDao;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameModel {

    private Code code;
    private long startTime;
    private final AppNavigator navigator;
    private final IGameDao gameDAO;
    private final Difficulty difficulty;
    private final User user;
    private int round;
    private Game game;

    private GameModel(AppNavigator navigator, IGameDao gameDAO, Difficulty difficulty, User user){
        this.navigator = navigator;
        this.gameDAO = gameDAO;
        this.startTime = System.nanoTime();
        this.difficulty = difficulty;
        this.user = user;
        generateCode(difficulty.getColumns());
    }

    private void generateCode(int slotsNum){
        List<Color> colors = List.of(Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN ,Color.DARKVIOLET, Color.ORANGE, Color.HOTPINK, Color.BLUE);
        List<Color> codeColors = new ArrayList<>();
        for(int i=0;i<slotsNum;i++){
            int getColor = (int) (Math.random() *  slotsNum);
            System.out.println(getColor);
            codeColors.add(colors.get(getColor));
        }
        System.out.println(codeColors);
        this.code = new Code(codeColors);
    }

    public boolean makeMove(Code code, int row){
        if(isCodeCorrect(code)){
            System.out.println("You won!");
            round = row;
            createGame(true);
            return true;
        }
        else
            System.out.println("Try again");
            return false;
    }

    private boolean isCodeCorrect(Code submittedCode){
        for(int i =0;i< difficulty.getColumns();i++)
            if(!submittedCode.getColor(i).equals(this.code.getColor(i)))
                return false;
        return true;
    }

    public int[] getCodeCorrectness(Code submittedCode) {
        int partiallyCorrect = 0;
        int fullyCorrect = 0;
        List<Integer> indexesSubmitted = IntStream.range(0, difficulty.getColumns())
                .boxed()
                .collect(Collectors.toList());;
        List<Integer> indexesCode = IntStream.range(0, difficulty.getColumns())
                .boxed()
                .collect(Collectors.toList());;
        for(int i =0;i< difficulty.getColumns();i++){
            if(submittedCode.getColor(i).equals(this.code.getColor(i))){
                fullyCorrect ++;

                indexesSubmitted.set(i, -1);
                indexesCode.set(i, -1);
            }
        }

        for(Integer submitted: indexesSubmitted) {
            for (Integer code : indexesCode) {
                if (code != -1 && submitted != -1) {
                    if (submittedCode.getColor(submitted).equals(this.code.getColor(code))) {
                        partiallyCorrect++;
                        indexesCode.set(indexesCode.indexOf(code), -1);
                        indexesSubmitted.set(indexesSubmitted.indexOf(submitted), -1);
                        submitted = -1;
                    }
                }
            }
        }


        int correctness[] = {fullyCorrect, partiallyCorrect};

        return correctness;
    }
    //obliczanie punktów
    private long calculateScore(){
        long score = calculateTime()* (difficulty.toInt()+1)/(round+1);
        System.out.println(score);
        return score;
    }

    //obliczanie czasu w sekundach
    private long calculateTime(){
        long endTime = System.nanoTime();
        return (endTime - startTime)/1000000000;
    }

    public void createGame(boolean isWon){
        this.game = new Game(user, difficulty.toInt(), isWon, calculateTime(), calculateScore());
        gameDAO.save(game);
    }

    public void restart(){
        navigator.startNewGame(GameModel.Factory.createInstance(difficulty, user));
    }

    public void returnToMenu(){
        navigator.showMenu(MenuModel.Factory.createInstance(difficulty, user));
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }



    @Component
    public static class Factory{

        private static AppNavigator navigator;
        private static IGameDao gameDao;

        public Factory(AppNavigator navigator, IGameDao gameDao) {
            Factory.navigator = navigator;
            Factory.gameDao = gameDao;
        }

        public static GameModel createInstance(Difficulty difficulty, User user){
            return new GameModel(navigator, gameDao, difficulty, user);
        }

    }

}