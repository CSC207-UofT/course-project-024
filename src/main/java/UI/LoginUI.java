package UI;

import Accounts.AccountController;
import Accounts.AccountDTO;
import Accounts.AccountInteractor;
import Database.DatabaseGateway;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginUI extends Application {

    DatabaseGateway gateway = new DatabaseGateway();

    public static final int WINDOW_LENGTH = 1000;
    public static final int WINDOW_HEIGHT = 600;
    public static final String APPLICATION_TITLE = "Flashcards";

    /**
     * Starts the login UI.
     * @param window A stage
     */
    @Override
    public void start(Stage window) throws Exception {
        window.setTitle(APPLICATION_TITLE);

        setLoginScene(window);

        window.show();
    }

    /**
     * Initializes the login window.
     * @param window A stage supplied by JavaFX.
     */
    private void setLoginScene(Stage window) {
        BorderPane layout = new BorderPane();

        VBox mainFieldsBox = createMainFieldsBox();

        Label openingText = new Label("Welcome!");

        openingText.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        Label openingText2 = new Label("Log in and start studying!");

        openingText2.setFont(Font.font("Verdana", 15));

        HBox usernameBox = createUsernameBox();

        Label usernameLbl = new Label("Username: ");

        TextField usernameField = createUsernameField();

        TextField passwordField = createPasswordField();

        usernameBox.getChildren().addAll(usernameLbl, usernameField);

        HBox passwordBox = createPasswordBox();

        Label passwordLbl = new Label("Password: ");

        passwordBox.getChildren().addAll(passwordLbl, passwordField);

        Button loginBtn = createButton("Log in", 100);

        Button createAccountBtn = createButton("Create a New Account");

        HBox buttonsBox = createButtonsBox();

        buttonsBox.getChildren().addAll(loginBtn, createAccountBtn);

        loginBtn.setOnMouseClicked(e -> {

            // TODO: Remove references to AccountInteractor and replace with AccountController

            // TODO: Retrieve AccountDTO from Database controller and replace this hack
            // boolean success = AccountController.login(AccountInteractor.createAccount("test", "test"), passwordField.getText());
            boolean success = gateway.authenticateAccount(usernameField.getText(), passwordField.getText());
            // Search database for any account that matches the inputted username. If that is success, then
            // login. Else, show Alert Box of no matching username. If that goes through, but the password
            // doesn't match, then send an Alert Box that the password is incorrect.
            // boolean success = AccountController.login();
            //TODO: If success, close window and launch main menu
            // If not success, show alert box of login failure
            // TODO: Alert box for not finding an account with that username
            if (success) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                AccountDTO currAccount = gateway.getAccountFromDB(username, password);
                System.out.println("Logged in");
                try {
                    // use the login function from AccountController. That will set the currentAccount
                    AccountController.login(currAccount, password);
                    new Main().start(new Stage());
                    window.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                displayAlertBox("Login Error", "Invalid credentials, please check your input and try again.");
            }
        });

        createAccountBtn.setOnMouseClicked(e -> {
            //TODO: Make sure that an account with that same username doesn't already exist, and that
            // the username and password fields are non-empty. If so, create an AlertBox

            if (usernameField.getText().isBlank() || passwordField.getText().isBlank()) {
                displayAlertBox("Invalid Credentials", "Error: Cannot create an account with a blank username or password.");
            } else {
                if (!gateway.duplicateAccount(usernameField.getText())) {
                    AccountDTO newAccount = AccountController.createAccount(usernameField.getText(), passwordField.getText());
                    gateway.addAccountToDB(usernameField.getText(), passwordField.getText());
                    AccountController.login(newAccount, passwordField.getText());
                } else {
                    displayAlertBox("Duplicate Username", "Error: An account with that username already exists.");
                }
            }


        });

        mainFieldsBox.getChildren().addAll(openingText, openingText2, usernameBox, passwordBox, buttonsBox);

        layout.setCenter(mainFieldsBox);

        Scene loginScene = new Scene(layout, WINDOW_LENGTH, WINDOW_HEIGHT);

        window.setScene(loginScene);

    }

    /**
     * Helper method that creates the HBox where the buttons go.
     * @return an HBox
     */
    private HBox createButtonsBox() {
        HBox buttonsBox = new HBox();

        buttonsBox.setAlignment(Pos.CENTER);

        buttonsBox.setSpacing(10);
        return buttonsBox;
    }

    /**
     * Helper method that creates and returns a button.
     * @param text The text within the button
     * @param minWidth The minimum width of the button
     * @return The created button
     */
    private Button createButton(String text, int minWidth) {
        Button button = new Button(text);
        button.setMinWidth(minWidth);
        return button;
    }

    /**
     * Helper method that creates and returns a button
     * @param text The text within the button
     * @return The created button
     */
    private Button createButton(String text) {
        return new Button(text);
    }

    /**
     * Helper method that creates the main VBox for the login UI.
     * @return A centered VBox
     */
    private VBox createMainFieldsBox() {
        VBox mainFieldsBox = new VBox(20);
        mainFieldsBox.setAlignment(Pos.CENTER);
        return mainFieldsBox;
    }

    /**
     * A helper method that creates the HBox for the password field.
     * @return A centered HBox
     */
    private HBox createPasswordBox() {
        HBox passwordBox = new HBox();
        passwordBox.setAlignment(Pos.CENTER);
        passwordBox.setSpacing(10);
        return passwordBox;
    }

    /**
     * A helper method that creates the password text field.
     * @return A TextField that asks for a password.
     */
    private TextField createPasswordField() {
        TextField passwordField = new TextField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPadding(new Insets(5));
        return passwordField;
    }

    /**
     * A helper method that creates and returns a box for the username.
     * @return A centered HBox for the username field
     */
    private HBox createUsernameBox() {
        HBox usernameBox = new HBox();
        usernameBox.setAlignment(Pos.CENTER);
        usernameBox.setSpacing(10);
        return usernameBox;
    }

    /**
     * A helper method that creates and returns a username text field.
     * @return A TextField that asks for a username.
     */
    private TextField createUsernameField() {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setPadding(new Insets(5));
        return usernameField;
    }

    /**
     * Displays an alert box with the given title and message.
     * @param title The title of the window of the alert box
     * @param alertMessage The string message to be shown
     */
    private void displayAlertBox(String title, String alertMessage) {
        Stage window = new Stage();

        window.setTitle(title);

        window.initModality(Modality.APPLICATION_MODAL);

        Label msg = new Label(alertMessage);

        Button confirmationBtn = new Button("OK");

        confirmationBtn.setOnMouseClicked(e -> window.close());

        VBox layout = new VBox();

        layout.setSpacing(10);

        layout.getChildren().addAll(msg, confirmationBtn);

        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 500, 100);

        window.setScene(scene);

        window.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
