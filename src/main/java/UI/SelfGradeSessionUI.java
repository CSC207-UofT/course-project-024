package UI;

import Sessions.SessionController;
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

/**
 * User interface for sessions that use a self-grade feature
 */
public class SelfGradeSessionUI extends StudySessionUI {

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

        StackPane center = getFlippableFlashcardFront(e -> setFirstFlipScene(window));
        HBox bottom = getBottomBarFront();

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

        StackPane center = getFlashcardBack();
        HBox bottom = getBottomBarBackInteractable(window);

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

        StackPane center = getFlippableFlashcardBack(e -> setFlippedFrontScene(window));
        setupFlashcardView(window, layout, center);
    }

    /**
     * Sets the current scene for when the user requests a card's front to be shown after having already viewed it once.
     * Allows the user to request to view the next card.
     */
    public void setFlippedFrontScene(Stage window) {
        BorderPane layout = new BorderPane();

        StackPane center = getFlippableFlashcardFront(e -> setFlippedBackScene(window));
        setupFlashcardView(window, layout, center);
    }

    /**
     * Set the scene to the given layout and centered content.
     * @param window the current window
     * @param layout the outermost layout
     * @param center the centermost layout (the flashcard)
     */
    private void setupFlashcardView(Stage window, BorderPane layout, StackPane center) {
        StackPane right = getRightBar(e -> setNewCardScene(window));
        Region left = new Region();
        left.prefWidthProperty().bind(right.widthProperty());
        HBox bottom = getBottomBarBackDisabled();

        layout.setCenter(center);
        layout.setRight(right);
        layout.setLeft(left);
        layout.setBottom(bottom);
        Scene flippedBackScene = new Scene(layout, WINDOW_LENGTH, WINDOW_HEIGHT);
        window.setScene(flippedBackScene);
    }

    /**
     * Return an HBox which contains the bottom bar for when the front of a flashcard is being shown.
     * @return an HBox
     */
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

    /**
     * Return an HBox containing an interactable bottom bar for when the back of a flashcard is being shown.
     * @param window The window for the session
     * @return an HBox
     */
    private HBox getBottomBarBackInteractable(Stage window) {
        Button yesBtn = getButton("YES");
        yesBtn.setOnMouseClicked(e -> {
            sessionController.postAnswerUpdate(true);
            setFlippedBackScene(window);
        });
        Button noBtn = getButton("NO");
        noBtn.setOnMouseClicked(e -> {
            sessionController.postAnswerUpdate(false);
            setFlippedBackScene(window);
        });
        return getLabeledBottomBar(yesBtn, noBtn);
    }

    /**
     * Return an HBox containing an uninteractable bottom bar for when the back of a flashcard is being shown.
     * @return an HBox
     */
    private HBox getBottomBarBackDisabled() {
        Button yesBtn = getButton("YES");
        yesBtn.setDisable(true);
        Button noBtn = getButton("NO");
        noBtn.setDisable(true);
        return getLabeledBottomBar(yesBtn, noBtn);
    }

    /**
     * Return an HBox with prebuilt user prompts and the given buttons.
     * @param yesBtn a button representing a positive user response
     * @param noBtn a button representing a negative user response
     * @return an HBox
     */
    private HBox getLabeledBottomBar(Button yesBtn, Button noBtn) {
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(10);
        bottom.setPadding(new Insets(10, 10, 40, 10));
        Label bottomLabel = new Label("Did you guess the back correctly?");
        bottomLabel.setFont(Font.font(20));
        bottom.getChildren().addAll(bottomLabel, yesBtn, noBtn);
        return bottom;
    }

    /**
     * Return a StackPane containing the right bar with a button
     * @param e the event to trigger when the button in the right bar is pressed
     * @return a StackPane
     */
    private StackPane getRightBar(EventHandler<MouseEvent> e) {
        StackPane rightBar = new StackPane();
        rightBar.setPadding(new Insets(0, 40, 0, 0));
        Button nextBtn = getButton("Get another card");
        nextBtn.setOnMouseClicked(e);
        rightBar.getChildren().add(nextBtn);
        return rightBar;
    }

    /**
     * Return a Button
     * @param btnText the text on the button
     * @return a Button
     */
    private Button getButton(String btnText) {
        Button btn = new Button(btnText);
        btn.setMinSize(50, 50);
        return btn;
    }
}

