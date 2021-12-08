package UI;

import Sessions.SessionController;
import Sessions.TestSessionDTO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*; // Panes, etc.
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

/**
 * User interface for test sessions
 */
public class TestSessionUI extends StudySessionUI {

    SessionController sessionController = new SessionController();
    boolean wasCorrect;

    private final TextField userInputField = new TextField();

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle(APPLICATION_TITLE);

        setNewCardScene(window);

        window.show();
    }

    /**
     * Sets the current scene for when the user requests a new card to be shown in a StudySession.
     * This scene shows the front of the requested flashcard and allows the user to request to view the back.
     */
    public void setNewCardScene(Stage window) {
        flashcard = sessionController.getNextCard();

        BorderPane top = getTopBar();
        StackPane center = getFlashcardFront();
        StackPane left = getLeftBar();
        StackPane right = getGenericRightBar(
                "Confirm guess",
                e -> {
                    wasCorrect = userInputField.getText().equals(flashcard.getBack());
                    sessionController.postAnswerUpdate(wasCorrect);
                    setBackScene(window);
                }
        );
        right.prefWidthProperty().bind(left.widthProperty());
        HBox bottom = getBottomBarFirst();

        Scene scene = getScene(top, center, left, right, bottom);
        window.setScene(scene);
    }

    /**
     * Get a scene with the given layout
     * @param top the top bar
     * @param center the center element
     * @param left the left bar
     * @param right the right bar
     * @param bottom the bottom bar
     * @return a Scene
     */
    private Scene getScene(BorderPane top, StackPane center, StackPane left, StackPane right, HBox bottom) {
        BorderPane layout = new BorderPane();
        layout.setTop(top);
        layout.setCenter(center);
        layout.setRight(right);
        layout.setLeft(left);
        layout.setBottom(bottom);
        return new Scene(layout, WINDOW_LENGTH, WINDOW_HEIGHT);
    }

    /**
     * Sets the current scene for when the user requests a card's back.
     * Allows the user to request to view the next card.
     */
    public void setBackScene(Stage window) {
        BorderPane top = getTopBar();
        StackPane center = getFlippableFlashcardBack(e -> setFlippedFrontScene(window));
        StackPane left = getLeftBar();
        StackPane right = getRightBar(window);
        right.prefWidthProperty().bind(left.widthProperty());
        HBox bottom = getBottomBarShowResult();

        Scene scene = getScene(top, center, left, right, bottom);
        window.setScene(scene);
    }

    /**
     * Sets the current scene for when the user requests a card's front to be shown after having already viewed it once.
     * Allows the user to request to view the next card.
     */
    public void setFlippedFrontScene(Stage window) {
        BorderPane top = getTopBar();
        StackPane center = getFlippableFlashcardFront(e -> setBackScene(window));
        StackPane left = getLeftBar();
        StackPane right = getRightBar(window);
        right.prefWidthProperty().bind(left.widthProperty());
        HBox bottom = getBottomBarShowResult();

        Scene scene = getScene(top, center, left, right, bottom);
        window.setScene(scene);
    }

    /**
     * Display a pop-up with the user's performance at the test.
     */
    public void displayEndOfTestResults() {
        TestSessionDTO testSessionDTO = (TestSessionDTO) sessionController.getCurrentSession();
        int numCorrect = testSessionDTO.getNumCorrect();
        int totalCards = testSessionDTO.getCardsSeen();
        int percentage = (int) (((float) numCorrect * 100 / totalCards));
        displayAlertBox(
                "Good job! You got " + numCorrect + " / " + totalCards + " cards correct! " +
                        "That's " + percentage + "%!"
        );
    }

    /**
     * Return a BorderPane that holds important information meant to be displayed at the top of the window.
     * @return a BorderPane
     */
    private BorderPane getTopBar() {
        TestSessionDTO testSessionDTO = (TestSessionDTO) sessionController.getCurrentSession();
        BorderPane top = new BorderPane();
        HBox midBox = new HBox();
        midBox.setSpacing(10);
        midBox.setPadding(new Insets(10,10,10,10));
        midBox.setAlignment(Pos.CENTER);
        Label cardCountLabel = new Label("Card: " + testSessionDTO.getCardsSeen() + " / " + testSessionDTO.getLength());
        cardCountLabel.setFont(Font.font(20));
        midBox.getChildren().add(cardCountLabel);
        top.setCenter(midBox);
        return top;
    }

    /**
     * Return an HBox containing the bottom bar displayed after the user requests a new card.
     * @return an HBox
     */
    private HBox getBottomBarFirst() {
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(10);
        bottom.setPadding(new Insets(10, 10, 40, 10));
        Label bottomLabel = new Label("What is written on the back?");
        bottomLabel.setFont(Font.font(20));
        bottom.getChildren().addAll(bottomLabel, userInputField);
        return bottom;
    }

    /**
     * Return an HBox containing the bottom bar displayed after the user inputs a guess.
     * @return an HBox
     */
    private HBox getBottomBarShowResult() {
        HBox bottom = new HBox();
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(10);
        bottom.setPadding(new Insets(10, 10, 40, 10));
        Label bottomLabel = new Label();
        if (wasCorrect) {
            bottomLabel.setText("You guessed right!");
        } else {
            bottomLabel.setText("Sorry, that's not it. Better luck next time!");
        }
        bottomLabel.setFont(Font.font(20));
        bottom.getChildren().addAll(bottomLabel);
        return bottom;
    }

    /**
     * Return a generic right bar containing a button with the given text and which fires the given event.
     * @param btnText the text on the button
     * @param e the event to be executed when the button is clicked
     * @return a StackPane
     */
    private StackPane getGenericRightBar(String btnText, EventHandler<MouseEvent> e) {
        StackPane rightBar = new StackPane();
        rightBar.setPadding(new Insets(0, 40, 0, 0));
        Button rightBtn = getButton(btnText);
        rightBtn.setOnMouseClicked(e);
        rightBar.getChildren().add(rightBtn);
        return rightBar;
    }

    /**
     * Return a StackPane containing the left bar tracking the user's progress in the test.
     * @return a StackPane
     */
    private StackPane getLeftBar() {
        TestSessionDTO testSessionDTO = (TestSessionDTO) sessionController.getCurrentSession();
        StackPane leftBar = new StackPane();
        leftBar.setPadding(new Insets(0, 0, 0, 30));
        Label numCorrectLabel = new Label("Correct guesses: " + testSessionDTO.getNumCorrect() + " / " +
                testSessionDTO.getLength());
        numCorrectLabel.setFont(Font.font(15));
        leftBar.getChildren().add(numCorrectLabel);
        return leftBar;
    }

    /**
     * Get a button
     * @return a Button
     */
    private Button getButton(String btnText) {
        Button btn = new Button(btnText);
        btn.setMinSize(50, 50);
        return btn;
    }

    /**
     * Display a closeable popup window about the test being finished.
     */
    private void displayAlertBox(String msg) {
        Stage alertWindow = new Stage();
        alertWindow.setTitle("Test Finished");
        alertWindow.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label(msg);
        Button closeBtn = new Button("Close");
        closeBtn.setOnMouseClicked(e -> alertWindow.close());

        VBox box = new VBox();
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);

        box.getChildren().addAll(label, closeBtn);

        Scene scene = new Scene(box,500, 100);
        alertWindow.setScene(scene);

        alertWindow.showAndWait();
    }

    /**
     * Return a StackPane containing the right bar with a button
     * @param window the current window
     * @return a StackPane
     */
    private StackPane getRightBar(Stage window) {
        TestSessionDTO testSessionDTO = (TestSessionDTO) sessionController.getCurrentSession();
        StackPane rightBar;
        if (testSessionDTO.getCardsSeen() == testSessionDTO.getLength()) {
            rightBar = getGenericRightBar("Finish", e -> {
                displayEndOfTestResults();
                window.close();
            });
        } else {
            rightBar = getGenericRightBar("Get another card", e -> setNewCardScene(window));
        }
        return rightBar;
    }
}

