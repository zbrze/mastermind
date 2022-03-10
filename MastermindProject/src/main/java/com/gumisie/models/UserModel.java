package com.gumisie.models;

import com.gumisie.service.AppNavigator;
import com.gumisie.containers.User;
import com.gumisie.persistence.interfaces.IUserDao;
import com.gumisie.service.PasswordService;
import javafx.beans.property.*;
import org.springframework.stereotype.Component;

public class UserModel {

    private final ObjectProperty<User> userObjectProperty = new SimpleObjectProperty<>(null);
    private final StringProperty username = new SimpleStringProperty("");
    private final BooleanProperty userExists = new SimpleBooleanProperty(false);
    private final BooleanProperty wrongPassword = new SimpleBooleanProperty(false);

    private final AppNavigator navigator;
    private final IUserDao userDao;

    private UserModel(AppNavigator navigator, IUserDao userDao){
        this.navigator = navigator;
        this.userDao = userDao;
    }
    public UserModel newUser(){
        return this;
    }

    public ObjectProperty<User> getUserObjectProperty(){
        return userObjectProperty;
    }

    public StringProperty usernameProperty(){
        return username;
    }

    /**
     * Creates a user with the given credentials if the user doesn't already exist
     *
     * @param email email
     * @param firstName first name
     * @param lastName last name
     * @param plainPassword password as plaintext
     */
    public boolean createUser(String email, String firstName, String lastName, String plainPassword) {
        findUserByMail(email);
        if (userExists.get()) return false; //user already exists, don't create a new one

        String salt = PasswordService.generateSalt();
        String passwordHash = PasswordService.hash(plainPassword, salt);
        User user = new User(email, firstName, lastName, passwordHash, salt);
        userDao.save(user);
        userObjectProperty.set(user);
        return true;
    }

    /**
     * Finds if a user with the given mail exists
     * Also sets the userExists flag accordingly
     *
     * @param email email
     * @return the found user or null, if no user uses this email
     */
    public User findUserByMail(String email){
        User user = userDao.getByEmail(email);
        userExists.setValue(user != null);
        return user;
    }

    /**
     * Authenticates the user with the given credentials
     * Sets the userExists and wrongPassword flags accordingly
     * If the authentication was successful, the authenticated user is saved in userObjectProperty
     *
     * @param email email
     * @param password password as plaintext
     * @return true if authentication was susccesful
     */
    public boolean authenticateUser(String email, String password){
        User user = findUserByMail(email);
        if (user != null){
            if (PasswordService.authenticate(password, user.getSalt(), user.getPasswordHash())){
                wrongPassword.setValue(false);
                userObjectProperty.set(user);
                return true;
            } else {
                wrongPassword.setValue(true);
                return false;
            }
        }
        return false; //user not found
    }

    public void showRegisterDialog(){
        navigator.showRegisterDialog(this);
    }

    public BooleanProperty userExistsProperty() {
        return userExists;
    }

    public BooleanProperty wrongPasswordProperty() {
        return wrongPassword;
    }

    public User getUser() {
        return this.userObjectProperty.getValue();
    }

    public void setNull() {
        this.userExists.setValue(false);
        this.username.setValue(null);
        this.userObjectProperty.set(new User());
    }

    @Component
    public static class Factory{

        private static AppNavigator navigator;
        private static IUserDao userDao;

        public Factory(AppNavigator navigator, IUserDao userDao) {
            Factory.navigator = navigator;
            Factory.userDao = userDao;
        }

        public static UserModel createInstance(){
            return new UserModel(navigator, userDao);
        }

    }

}