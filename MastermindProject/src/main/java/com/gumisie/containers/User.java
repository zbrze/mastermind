package com.gumisie.containers;

import javafx.beans.property.SimpleStringProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class User {

    private final SimpleStringProperty email = new SimpleStringProperty();
    private final SimpleStringProperty firstName = new SimpleStringProperty();
    private final SimpleStringProperty lastName = new SimpleStringProperty();
    private final SimpleStringProperty passwordHash = new SimpleStringProperty();
    private String salt;

    public User() {
        //required by Hibernate
    }

    public User(String email, String firstName, String lastName, String passwordHash, String salt) {
        setEmail(email);
        setFirstName(firstName);
        setLastName(lastName);
        setPasswordHash(passwordHash);
        this.salt = salt;
    }

    @Id
    @Column
    public String getEmail() {
        return email.get();
    }

    @Transient
    public SimpleStringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    @Column
    public String getFirstName() {
        return firstName.get();
    }

    @Transient
    public SimpleStringProperty firstNameProperty() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    @Column
    public String getLastName() {
        return lastName.get();
    }

    @Transient
    public SimpleStringProperty lastNameProperty() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    @Column
    public String getPasswordHash() {
        return passwordHash.get();
    }

    @Transient
    public SimpleStringProperty passwordHashProperty() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash.set(passwordHash);
    }

    @Column
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public String toString() {
        return "User{" +
                "email=" + getEmail() +
                ", firstName=" + getFirstName() +
                ", lastName=" + getLastName() +
                ", password=" + getPasswordHash() +
                '}';
    }
}
