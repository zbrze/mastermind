package com.gumisie.containers;

public class ScoreRecord<T> {

    private final String firstName;
    private final String lastName;
    private final T score;

    public ScoreRecord(String firstName, String lastName, T score) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.score = score;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public T getScore() {
        return score;
    }

}
