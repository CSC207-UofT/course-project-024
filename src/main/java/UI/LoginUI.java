package UI;

import Accounts.AccountDTO;
import Accounts.AccountInteractor;
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
import javafx.stage.Stage;

public class LoginUI extends Application {

    public static final int WINDOW_LENGTH = 1000;
    public static final int WINDOW_HEIGHT = 600;
    public static final String APPLICATION_TITLE = "Flashcards";

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle(APPLICATION_TITLE);

        setLoginScene(window);

        window.show();


    }

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
            boolean success = AccountInteractor.login(AccountInteractor.createAccount("test", "test"), passwordField.getText());

            //TODO: If success, close window and launch main menu
            // If not success, show alert box of login failure
            if (success) {
                System.out.println("Logged in");
            } else {
                System.out.println("Rejected");
            }
        });

        createAccountBtn.setOnMouseClicked(e -> {
            AccountDTO newAccount = AccountInteractor.createAccount(usernameField.getText(), passwordField.getText());

            //TODO: Make sure that an account with that same username doesn't already exist, and that
            // the username and password fields are non-empty

            AccountInteractor.login(newAccount, passwordField.getText());

            System.out.println("New account created");
        });

        mainFieldsBox.getChildren().addAll(openingText, openingText2, usernameBox, passwordBox, buttonsBox);

        layout.setCenter(mainFieldsBox);

        Scene loginScene = new Scene(layout, WINDOW_LENGTH, WINDOW_HEIGHT);

        window.setScene(loginScene);

    }

    private HBox createButtonsBox() {
        HBox buttonsBox = new HBox();

        buttonsBox.setAlignment(Pos.CENTER);

        buttonsBox.setSpacing(10);
        return buttonsBox;
    }

    private Button createButton(String text, int minWidth) {
        Button button = new Button(text);
        button.setMinWidth(minWidth);
        return button;
    }

    private Button createButton(String text) {
        return new Button(text);
    }

    private VBox createMainFieldsBox() {
        VBox mainFieldsBox = new VBox(20);
        mainFieldsBox.setAlignment(Pos.CENTER);
        return mainFieldsBox;
    }

    private HBox createPasswordBox() {
        HBox passwordBox = new HBox();
        passwordBox.setAlignment(Pos.CENTER);
        passwordBox.setSpacing(10);
        return passwordBox;
    }

    private TextField createPasswordField() {
        TextField passwordField = new TextField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPadding(new Insets(5));
        return passwordField;
    }

    private HBox createUsernameBox() {
        HBox usernameBox = new HBox();
        usernameBox.setAlignment(Pos.CENTER);
        usernameBox.setSpacing(10);
        return usernameBox;
    }

    private TextField createUsernameField() {
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setPadding(new Insets(5));
        return usernameField;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
