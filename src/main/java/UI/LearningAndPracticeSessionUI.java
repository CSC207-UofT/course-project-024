package UI;

import Flashcards.FlashcardDTO;
import Sessions.SessionController;
import Sessions.SessionInteractor;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*; // Panes, etc.

public class LearningAndPracticeSessionUI extends StudySessionUI {

    SessionController sessionController = new SessionController();

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle(APPLICATION_TITLE);

        setNewCardScene(window);

        window.show();
    }

    /**
     * Sets the current scene for when the user requests a new card to be shown.
     * This scene shows the front of the requested flashcard and allows the user to request to view the back.
     */
    public void setNewCardScene(Stage window) {
        flashcard = sessionController.getNextCard();
        BorderPane layout = new BorderPane();

        BorderPane top = getTopBar();
        StackPane center = getFlippableFlashcardFront(e -> setFirstFlipScene(window));
        HBox bottom = getBottomBarFront();

        layout.setTop(top);
        layout.setCenter(center);
        layout.setBottom(bottom);

        Scene newCardScene = new Scene(layout, WINDOW_LENGTH, WINDOW_HEIGHT);
        window.setScene(newCardScene);
    }

    /**
     * Sets the current scene for when the user requests a card to be flipped to the back for the first time.
     * This scene shows the back of the requested flashcard and allows the user to self-select if their guess of the
     * back was correct, and when selected, changes the scene to setFlippedBackScene().
     */
    public void setFirstFlipScene(Stage window) {
        BorderPane layout = new BorderPane();

        BorderPane top = getTopBar();
        StackPane center = getFlashcardBack();
        HBox bottom = getBottomBarBackInteractable(window);

        layout.setTop(top);
        layout.setCenter(center);
        layout.setBottom(bottom);
        Scene firstFlipScene = new Scene(layout, WINDOW_LENGTH, WINDOW_HEIGHT);
        window.setScene(firstFlipScene);
    }

    /**
     * Sets the current scene for when the user requests a card's back to be shown after having already viewed it once.
     * Allows the user to request to view the next card.
     */
    public void setFlippedBackScene(Stage window) {
        BorderPane layout = new BorderPane();

        BorderPane top = getTopBar();
        StackPane center = getFlippableFlashcardBack(e -> setFlippedFrontScene(window));
        // TODO: implement
        StackPane right = getRightBar(e -> setNewCardScene(window));
        Region left = new Region();
        left.prefWidthProperty().bind(right.widthProperty());
        HBox bottom = getBottomBarBackDisabled();


        layout.setTop(top);
        layout.setCenter(center);
        layout.setRight(right);
        layout.setLeft(left);
        layout.setBottom(bottom);
        Scene flippedBackScene = new Scene(layout, WINDOW_LENGTH, WINDOW_HEIGHT);
        window.setScene(flippedBackScene);
    }

    /**
     * Sets the current scene for when the user requests a card's front to be shown after having already viewed it once.
     * Allows the user to request to view the next card.
     */
    public void setFlippedFrontScene(Stage window) {
        BorderPane layout = new BorderPane();

        BorderPane top = getTopBar();
        StackPane center = getFlippableFlashcardFront(e -> setFlippedBackScene(window));
        // TODO: implement
        StackPane right = getRightBar(e -> setNewCardScene(window));
        Region left = new Region();
        left.prefWidthProperty().bind(right.widthProperty());
        HBox bottom = getBottomBarBackDisabled();

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

    private HBox getBottomBarBackInteractable(Stage window) {
        Button yesBtn = getButton("YES");
        yesBtn.setOnMouseClicked(e -> {
            // TODO: handle YES
            sessionController.postAnswerUpdate(true);
            setFlippedBackScene(window);
        });
        Button noBtn = getButton("NO");
        noBtn.setOnMouseClicked(e -> {
            // TODO: handle NO
            sessionController.postAnswerUpdate(false);
            setFlippedBackScene(window);
        });
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(10);
        bottom.setPadding(new Insets(10, 10, 40, 10));
        Label bottomLabel = new Label("Did you guess the back correctly?");
        bottomLabel.setFont(Font.font(20));
        bottom.getChildren().addAll(bottomLabel, yesBtn, noBtn);
        return bottom;
    }

    private HBox getBottomBarBackDisabled() {
        Button yesBtn = getButton("YES");
        yesBtn.setDisable(true);
        Button noBtn = getButton("NO");
        noBtn.setDisable(true);
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(10);
        bottom.setPadding(new Insets(10, 10, 40, 10));
        Label bottomLabel = new Label("Did you guess the back correctly?");
        bottomLabel.setFont(Font.font(20));
        bottom.getChildren().addAll(bottomLabel, yesBtn, noBtn);
        return bottom;
    }

    private StackPane getRightBar(EventHandler<MouseEvent> e) {
        StackPane rightBar = new StackPane();
        rightBar.setPadding(new Insets(0, 40, 0, 0));
        Button nextBtn = getButton("Get another card");
        nextBtn.setOnMouseClicked(e);
        rightBar.getChildren().add(nextBtn);
        return rightBar;
    }

    private Button getButton(String btnText) {
        Button btn = new Button(btnText);
        btn.setMinSize(50, 50);
        return btn;
    }

    // TODO: DO NOT RUN! JavaFX requires use of modules, so for now, run main from Main.java until properly implemented
    public static void main(String[] args) {
        launch(args);
    }
}

