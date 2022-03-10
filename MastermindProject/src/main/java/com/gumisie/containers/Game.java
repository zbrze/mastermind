package com.gumisie.containers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.persistence.*;

@Entity
public class Game {

    private final SimpleIntegerProperty id = new SimpleIntegerProperty();
    private User user;
    private final SimpleIntegerProperty difficulty = new SimpleIntegerProperty();
    private final SimpleBooleanProperty isWon = new SimpleBooleanProperty(false);
    private final SimpleLongProperty time = new SimpleLongProperty();
    private final SimpleLongProperty score = new SimpleLongProperty(0);


    public Game() {
        //required by Hibernate
    }

    public Game(User user, int difficulty, boolean isWon, long time, long score){
        setUser(user);
        setDifficulty(difficulty);
        setScore(score);
        setTime(time);
        setIsWon(isWon);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    public int getId() {
        return id.get();
    }

    @Transient
    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column
    public int getDifficulty() {
        return difficulty.get();
    }

    @Transient
    public SimpleIntegerProperty difficultyProperty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty.set(difficulty);
    }

    @Column
    public boolean isIsWon() {
        return isWon.get();
    }

    @Transient
    public SimpleBooleanProperty isWonProperty() {
        return isWon;
    }

    public void setIsWon(boolean isWon) {
        this.isWon.set(isWon);
    }

    @Column
    public long getTime() {
        return time.get();
    }

    @Transient
    public SimpleLongProperty timeProperty() {
        return time;
    }

    public void setTime(long time) {
        this.time.set(time);
    }

    @Column
    public long getScore() {
        return score.get();
    }

    @Transient
    public SimpleLongProperty scoreProperty() {
        return score;
    }

    public void setScore(long score) {
        this.score.set(score);
    }

}