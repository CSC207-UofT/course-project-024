package UI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*; // Buttons, etc
import javafx.scene.layout.*; // Panes, etc.
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

public class LearningSessionUI extends Application {

    public static final String APPLICATION_TITLE = "Flashcard Program";
    public static final int WINDOW_LENGTH = 1000;
    public static final int WINDOW_HEIGHT = 600;
    public static final int FLASHCARD_LENGTH = 325;
    public static final int FLASHCARD_HEIGHT = 450;

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
        StackPane right = getRightBar(e -> System.out.println("Getting next card..."));
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
        StackPane right = getRightBar(e -> System.out.println("Getting next card..."));
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

    private StackPane getFlashcardFront() {
        StackPane center = new StackPane();
        Rectangle flashcardBorder = getFlashcardBorder();
        ImageView frontImage = new ImageView(
                new Image("file:img/Flag_of_Canada.svg.png", 500, 500, true, true)
        );
        resizeImageViewToFit(frontImage);
        Label flashcardContent = new Label(
                "Which country's flag is this? RANDOM STUFF TO DEMONSTRATE TEXT WRAPPING AHHHHHHHHHHHHHHHHHHHHH",
                frontImage
        );
        StackPane flashcardInterior = getFlashcardInterior(flashcardContent);

        center.getChildren().addAll(flashcardBorder, flashcardInterior);
        return center;
    }

    private StackPane getFlashcardBack() {
        StackPane center = new StackPane();
        Rectangle flashcardBorder = getFlashcardBorder();
        Label flashcardContent = new Label("It's Canada. Yay!");
        StackPane flashcardInterior = getFlashcardInterior(flashcardContent);

        center.getChildren().addAll(flashcardBorder, flashcardInterior);
        return center;
    }

    private StackPane getFlippableFlashcardFront(EventHandler<MouseEvent> e) {
        StackPane center = getFlashcardFront();
        // Make invisible region to detect mouse clicks on the flashcard
        Rectangle flashcardClickArea = new Rectangle(FLASHCARD_LENGTH, FLASHCARD_HEIGHT);
        flashcardClickArea.setFill(Color.TRANSPARENT);
        flashcardClickArea.setOnMouseClicked(e);
        center.getChildren().add(flashcardClickArea);
        return center;
    }

    private StackPane getFlippableFlashcardBack(EventHandler<MouseEvent> e) {
        StackPane center = getFlashcardBack();
        // Detect mouse clicks on the flashcard
        Rectangle flashcardClickArea = new Rectangle(FLASHCARD_LENGTH, FLASHCARD_HEIGHT);
        flashcardClickArea.setFill(Color.TRANSPARENT);
        flashcardClickArea.setOnMouseClicked(e);
        center.getChildren().add(flashcardClickArea);
        return center;
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
            setFlippedBackScene(window);
        });
        Button noBtn = getButton("NO");
        noBtn.setOnMouseClicked(e -> {
            // TODO: handle NO
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

    /**
     * Get a drawable rectangle representing a flashcard's border
     * @return a Rectangle representing a flashcard's border
     */
    private Rectangle getFlashcardBorder() {
        Rectangle flashcardBorder = new Rectangle(FLASHCARD_LENGTH, FLASHCARD_HEIGHT);
        flashcardBorder.setFill(Color.TRANSPARENT);
        flashcardBorder.setStroke(Color.BLACK);
        return flashcardBorder;
    }

    private void resizeImageViewToFit(ImageView image) {
        image.setFitWidth(FLASHCARD_LENGTH * 0.9);
        image.setPreserveRatio(true);
    }

    /**
     * Create and return a properly-formatted StackPane holding the content of the flashcard.
     * @param flashcardContent a label holding the text and image in the flashcard.
     */
    private StackPane getFlashcardInterior(Label flashcardContent) {
        // Make text appear ABOVE the image
        flashcardContent.setContentDisplay(ContentDisplay.BOTTOM);
        // Wrap the text so it doesn't get out of the flashcard
        flashcardContent.setWrapText(true);
        flashcardContent.setTextAlignment(TextAlignment.CENTER);
        // Leave space between text and image if needed
        flashcardContent.setGraphicTextGap(20);
        flashcardContent.setFont(Font.font(16));

        StackPane flashcardInterior = new StackPane();
        flashcardInterior.setAlignment(Pos.CENTER);
        // ensure flashcard contents stay in the border
        flashcardInterior.setMaxWidth(FLASHCARD_LENGTH * 0.9);
        flashcardInterior.getChildren().add(flashcardContent);
        return flashcardInterior;
    }


    // TODO: DO NOT RUN! JavaFX requires use of modules, so for now, run main from Main.java until properly implemented
    public static void main(String[] args) {
        launch(args);
    }
}
