import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.control.*; // Buttons, etc
import javafx.scene.layout.*; // Panes, etc.
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;

public class UI extends Application {

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

    private void setNewCardScene(Stage window) {
        Parent layout = getLayoutSelfGradeSession(window);
        Scene newCardScene = new Scene(layout, WINDOW_LENGTH, WINDOW_HEIGHT);
        window.setScene(newCardScene);
    }

    private void setFirstFlipScene(Stage window) {
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

    private void setFlippedBackScene(Stage window) {
        BorderPane layout = new BorderPane();

        BorderPane top = getTopBar();
        StackPane center = getFlippableFlashcardBack(e -> setFlippedFrontScene(window));
        // TODO: implement
        StackPane right = getRightBar(e -> System.out.println("Getting next card"));
        Region left = new Region();
        left.prefWidthProperty().bind(right.widthProperty());
        HBox bottom = getBottomBarBackDisabled(window);


        layout.setTop(top);
        layout.setCenter(center);
        layout.setRight(right);
        layout.setLeft(left);
        layout.setBottom(bottom);
        Scene flippedBackScene = new Scene(layout, WINDOW_LENGTH, WINDOW_HEIGHT);
        window.setScene(flippedBackScene);
    }

    private void setFlippedFrontScene(Stage window) {
        BorderPane layout = new BorderPane();

        BorderPane top = getTopBar();
        StackPane center = getFlippableFlashcardFront(e -> setFlippedBackScene(window));
        // TODO: implement
        StackPane right = getRightBar(e -> System.out.println("Getting next card"));
        Region left = new Region();
        left.prefWidthProperty().bind(right.widthProperty());
        HBox bottom = getBottomBarBackDisabled(window);

        layout.setTop(top);
        layout.setCenter(center);
        layout.setRight(right);
        layout.setLeft(left);
        layout.setBottom(bottom);
        Scene flippedFrontScene = new Scene(layout, WINDOW_LENGTH, WINDOW_HEIGHT);
        window.setScene(flippedFrontScene);
    }

    private Parent getLayoutSelfGradeSession(Stage window) {
        BorderPane layout = new BorderPane();

        // top layout
        BorderPane top = getTopBar();

        // center layout
        StackPane center = getFlippableFlashcardFront(e -> setFirstFlipScene(window));

        // bottom layout
        HBox bottom = getBottomBarFront();

        layout.setTop(top);
        layout.setCenter(center);
        layout.setBottom(bottom);

        return layout;
    }

    private BorderPane getTopBar() {
        BorderPane top = new BorderPane();
        HBox leftBox = new HBox();
        HBox midBox = new HBox();
        HBox rightBox = new HBox();
        leftBox.setSpacing(10);
        midBox.setSpacing(10);
        rightBox.setSpacing(10);
        leftBox.setPadding(new Insets(10, 10, 20, 10));
        midBox.setPadding(new Insets(10, 10, 20, 10));
        rightBox.setPadding(new Insets(10, 10, 20, 10));
        Button topBtn1 = new Button("Exit Session");
        // Button topBtn2 = new Button("No");
        Button topBtn3 = new Button("Logout");
        leftBox.getChildren().add(topBtn1);
        // midBox.getChildren().add(topBtn2);
        rightBox.getChildren().add(topBtn3);
        midBox.setAlignment(Pos.CENTER);
        top.setLeft(leftBox);
        top.setCenter(midBox);
        top.setRight(rightBox);
        return top;
    }

    private StackPane getFlashcardFront() {
        StackPane center = new StackPane();
        Rectangle flashcardBorder = getFlashcardBorder();
        ImageView frontImage = new ImageView(
                new Image("file:img/Flag_of_Canada.svg.png", 200, 200, true, true)
        );
        Label flashcardContent = new Label(
                "Which country's flag is this? RANDOM STUFF TO DEMONSTRATE TEXT WRAPPING AHHHHHHHHHHHHHHHHHHHHH",
                frontImage
        );
        // Make text appear ABOVE the image
        flashcardContent.setContentDisplay(ContentDisplay.BOTTOM);
        // Wrap the text so it doesn't get out of the flashcard
        flashcardContent.setWrapText(true);
        flashcardContent.setTextAlignment(TextAlignment.CENTER);
        flashcardContent.setGraphicTextGap(20);
        flashcardContent.setFont(Font.font(16));
        StackPane flashcardInterior = new StackPane();
        flashcardInterior.setAlignment(Pos.CENTER);
        flashcardInterior.setMaxWidth(300);
        flashcardInterior.getChildren().add(flashcardContent);

        center.getChildren().addAll(flashcardBorder, flashcardInterior);
        return center;
    }

    private StackPane getFlashcardBack() {
        StackPane center = new StackPane();
        Rectangle flashcardBorder = getFlashcardBorder();
        Label flashcardContent = new Label("It's Canada. Yay!");
        // Wrap the text so it doesn't get out of the flashcard
        flashcardContent.setWrapText(true);
        flashcardContent.setTextAlignment(TextAlignment.CENTER);
        flashcardContent.setFont(Font.font(16));
        StackPane flashcardInterior = new StackPane();
        flashcardInterior.setAlignment(Pos.CENTER);
        flashcardInterior.setMaxWidth(300);
        flashcardInterior.getChildren().add(flashcardContent);

        center.getChildren().addAll(flashcardBorder, flashcardInterior);
        return center;
    }

    private StackPane getFlippableFlashcardFront(EventHandler<MouseEvent> e) {
        StackPane center = getFlashcardFront();
        // Detect mouse clicks on the flashcard
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

    private HBox getBottomBarBackDisabled(Stage window) {
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
        Button nextBtn = new Button("Get another card");
        nextBtn.setOnMouseClicked(e);
        rightBar.getChildren().add(nextBtn);
        return rightBar;
    }

    private Button getButton(String btnText) {
        Button btn = new Button(btnText);
        btn.setMinSize(50, 50);
        return btn;
    }

    private Rectangle getFlashcardBorder() {
        Rectangle flashcardBorder = new Rectangle(FLASHCARD_LENGTH, FLASHCARD_HEIGHT);
        flashcardBorder.setFill(Color.TRANSPARENT);
        flashcardBorder.setStroke(Color.BLACK);
        return flashcardBorder;
    }


    // TODO: DO NOT RUN! JavaFX requires use of modules, so for now, run main from Main.java until properly implemented
    public static void main(String[] args) {
        launch(args);
    }
}
