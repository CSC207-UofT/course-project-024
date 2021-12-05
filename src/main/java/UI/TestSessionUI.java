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

        BorderPane layout = new BorderPane();

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

        layout.setTop(top);
        layout.setCenter(center);
        layout.setRight(right);
        layout.setLeft(left);
        layout.setBottom(bottom);

        Scene newCardScene = new Scene(layout, WINDOW_LENGTH, WINDOW_HEIGHT);
        window.setScene(newCardScene);
    }

    /**
     * Sets the current scene for when the user requests a card's back.
     * Allows the user to request to view the next card.
     */
    public void setBackScene(Stage window) {
        BorderPane layout = new BorderPane();

        BorderPane top = getTopBar();
        StackPane center = getFlippableFlashcardBack(e -> setFlippedFrontScene(window));
        StackPane left = getLeftBar();
        StackPane right = getRightBar(window);
        right.prefWidthProperty().bind(left.widthProperty());
        HBox bottom = getBottomBarShowResult();


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
        StackPane center = getFlippableFlashcardFront(e -> setBackScene(window));
        StackPane left = getLeftBar();
        StackPane right = getRightBar(window);
        right.prefWidthProperty().bind(left.widthProperty());
        HBox bottom = getBottomBarShowResult();

        layout.setTop(top);
        layout.setCenter(center);
        layout.setRight(right);
        layout.setLeft(left);
        layout.setBottom(bottom);
        Scene flippedFrontScene = new Scene(layout, WINDOW_LENGTH, WINDOW_HEIGHT);
        window.setScene(flippedFrontScene);
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
                "Good job! You got" + numCorrect + " / " + totalCards + " cards correct! " +
                        "That's " + percentage + "%!"
        );
    }

    private BorderPane getTopBar() {
        TestSessionDTO testSessionDTO = (TestSessionDTO) sessionController.getCurrentSession();
        BorderPane top = new BorderPane();
        HBox leftBox = new HBox();
        HBox midBox = new HBox();
        HBox rightBox = new HBox();
        leftBox.setSpacing(10);
        midBox.setSpacing(10);
        rightBox.setSpacing(10);
        leftBox.setPadding(new Insets(10, 10, 20, 10));
        midBox.setPadding(new Insets(10,10,10,10));
        rightBox.setPadding(new Insets(10, 10, 20, 10));
        midBox.setAlignment(Pos.CENTER);
        if (rightBox.widthProperty().lessThanOrEqualTo(rightBox.widthProperty()).get()) {
            rightBox.prefWidthProperty().bind(leftBox.widthProperty());
        } else {
            leftBox.prefWidthProperty().bind(rightBox.widthProperty());
        }
        Button exitBtn = new Button("Exit Session");
        // TODO: implement
        exitBtn.setOnMouseClicked(e -> System.out.println("Exiting session..."));
        Button logoutBtn = new Button("Logout");
        // TODO: implement
        logoutBtn.setOnMouseClicked(e -> System.out.println("Logging out..."));
        // TODO: implement
        Label cardCountLabel = new Label("Card: " + testSessionDTO.getCardsSeen() + " / " + testSessionDTO.getLength());
        cardCountLabel.setFont(Font.font(20));
        leftBox.getChildren().add(exitBtn);
        midBox.getChildren().add(cardCountLabel);
        rightBox.getChildren().add(logoutBtn);
        top.setLeft(leftBox);
        top.setCenter(midBox);
        top.setRight(rightBox);
        return top;
    }

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

    private StackPane getGenericRightBar(String btnText, EventHandler<MouseEvent> e) {
        StackPane rightBar = new StackPane();
        rightBar.setPadding(new Insets(0, 40, 0, 0));
        Button rightBtn = getButton(btnText);
        rightBtn.setOnMouseClicked(e);
        rightBar.getChildren().add(rightBtn);
        return rightBar;
    }

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

    private Button getButton(String btnText) {
        Button btn = new Button(btnText);
        btn.setMinSize(50, 50);
        return btn;
    }

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

    // TODO: DO NOT RUN! JavaFX requires use of modules, so for now, run main from Main.java until properly implemented
    public static void main(String[] args) {
        launch(args);
    }
}

