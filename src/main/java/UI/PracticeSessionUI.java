package UI;

import Flashcards.FlashcardDTO;
import Sessions.SessionController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*; // Panes, etc.

public class PracticeSessionUI extends StudySessionUI {

    SessionController sessionController = new SessionController();
    FlashcardDTO flashcard = sessionController.getNextCard();

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle(APPLICATION_TITLE);

        setFrontScene(window);

        window.show();
    }

    /**
     * Sets the current scene for when the user requests a card's back.
     * Allows the user to request to view the next card.
     */
    public void setBackScene(Stage window) {
        BorderPane layout = new BorderPane();

        BorderPane top = getTopBar();
        StackPane center = getFlippableFlashcardBack(e -> setFrontScene(window));
        // TODO: implement
        StackPane right = getRightBar(window);
        Region left = new Region();
        left.prefWidthProperty().bind(right.widthProperty());
        HBox bottom = getBottomBarBack();


        layout.setTop(top);
        layout.setCenter(center);
        layout.setRight(right);
        layout.setLeft(left);
        layout.setBottom(bottom);
        Scene flippedBackScene = new Scene(layout, WINDOW_LENGTH, WINDOW_HEIGHT);
        window.setScene(flippedBackScene);
    }

    /**
     * Sets the current scene for when the user requests a card's front to be shown.
     * Allows the user to request to view the next card.
     */
    public void setFrontScene(Stage window) {
        BorderPane layout = new BorderPane();

        BorderPane top = getTopBar();
        StackPane center = getFlippableFlashcardFront(e -> setBackScene(window));
        // TODO: implement
        StackPane right = getRightBar(window);
        Region left = new Region();
        left.prefWidthProperty().bind(right.widthProperty());
        HBox bottom = getBottomBarFront();

        layout.setTop(top);
        layout.setCenter(center);
        layout.setRight(right);
        layout.setLeft(left);
        layout.setBottom(bottom);
        Scene flippedFrontScene = new Scene(layout, WINDOW_LENGTH, WINDOW_HEIGHT);
        window.setScene(flippedFrontScene);
    }

    private BorderPane getTopBar() {
        BorderPane top = new BorderPane();
        HBox leftBox = new HBox();
        HBox rightBox = new HBox();
        leftBox.setSpacing(10);
        rightBox.setSpacing(10);
        leftBox.setPadding(new Insets(10, 10, 20, 10));
        rightBox.setPadding(new Insets(10, 10, 20, 10));
        Button exitBtn = new Button("Exit Session");
        // TODO: implement
        exitBtn.setOnMouseClicked(e -> System.out.println("Exiting session..."));
        Button logoutBtn = new Button("Logout");
        // TODO: implement
        logoutBtn.setOnMouseClicked(e -> System.out.println("Logging out..."));
        leftBox.getChildren().add(exitBtn);
        rightBox.getChildren().add(logoutBtn);
        top.setLeft(leftBox);
        top.setRight(rightBox);
        return top;
    }

    private HBox getBottomBarFront() {
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(10);
        bottom.setPadding(new Insets(10, 10, 40, 10));
        Label bottomLabel = new Label("Click the flashcard to see the back!");
        bottomLabel.setFont(Font.font(20));
        bottom.getChildren().add(bottomLabel);
        return bottom;
    }

    private HBox getBottomBarBack() {
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(10);
        bottom.setPadding(new Insets(10, 10, 40, 10));
        Label bottomLabel = new Label("Click the flashcard to see the front!");
        bottomLabel.setFont(Font.font(20));
        bottom.getChildren().add(bottomLabel);
        return bottom;
    }

    private StackPane getRightBar(Stage window) {
        StackPane rightBar = new StackPane();
        rightBar.setPadding(new Insets(0, 40, 0, 0));
        Button nextBtn = getButton();
        nextBtn.setOnMouseClicked(e -> {
            flashcard = sessionController.getNextCard();
            setFrontScene(window);
        });
        rightBar.getChildren().add(nextBtn);
        return rightBar;
    }

    private Button getButton() {
        Button btn = new Button("Get another card");
        btn.setMinSize(50, 50);
        return btn;
    }

    // TODO: DO NOT RUN! JavaFX requires use of modules, so for now, run main from Main.java until properly implemented
    public static void main(String[] args) {
        launch(args);
    }
}

