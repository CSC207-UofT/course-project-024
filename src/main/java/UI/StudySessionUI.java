package UI;

import Flashcards.FlashcardDTO;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * User interface for study sessions
 */
public abstract class StudySessionUI extends Application {

    public static final String APPLICATION_TITLE = "Flashcard Program";
    public static final int WINDOW_LENGTH = 1000;
    public static final int WINDOW_HEIGHT = 600;
    public static final int FLASHCARD_LENGTH = 325;
    public static final int FLASHCARD_HEIGHT = 450;

    FlashcardDTO flashcard;

    /**
     * Get a StackPane filled with elements representing the front of the flashcard.
     * @return a StackPane
     */
    protected StackPane getFlashcardFront() {
        StackPane center = new StackPane();
        Rectangle flashcardBorder = getFlashcardBorder();
        Label flashcardContent;
        if (flashcard.getFrontImage() != null) {
            ImageView frontImage = new ImageView(SwingFXUtils.toFXImage(flashcard.getFrontImage(), null));
            resizeImageViewToFit(frontImage);
            flashcardContent = new Label(flashcard.getFrontText(), frontImage);
        } else {
            flashcardContent = new Label(flashcard.getFrontText());
        }
        StackPane flashcardInterior = getFlashcardInterior(flashcardContent);

        center.getChildren().addAll(flashcardBorder, flashcardInterior);
        return center;
    }

    /**
     * Get a StackPane filled with elements representing the back of the flashcard.
     * @return a StackPane
     */
    protected StackPane getFlashcardBack() {
        StackPane center = new StackPane();
        Rectangle flashcardBorder = getFlashcardBorder();
        Label flashcardContent = new Label(flashcard.getBack());
        StackPane flashcardInterior = getFlashcardInterior(flashcardContent);

        center.getChildren().addAll(flashcardBorder, flashcardInterior);
        return center;
    }

    /**
     * Get a StackPane filled with the elements of the front of a flashcard. Also flippable.
     * @param e what to do when the flashcard is clicked by a mouse
     * @return a StackPane
     */
    protected StackPane getFlippableFlashcardFront(EventHandler<MouseEvent> e) {
        StackPane center = getFlashcardFront();
        // Make invisible region to detect mouse clicks on the flashcard
        Rectangle flashcardClickArea = new Rectangle(FLASHCARD_LENGTH, FLASHCARD_HEIGHT);
        flashcardClickArea.setFill(Color.TRANSPARENT);
        flashcardClickArea.setOnMouseClicked(e);
        center.getChildren().add(flashcardClickArea);
        return center;
    }

    /**
     * Get a StackPane filled with the elements of the back of a flashcard. Also flippable.
     * @param e what to do when the flashcard is clicked by a mouse
     * @return a StackPane
     */
    protected StackPane getFlippableFlashcardBack(EventHandler<MouseEvent> e) {
        StackPane center = getFlashcardBack();
        // Detect mouse clicks on the flashcard
        Rectangle flashcardClickArea = new Rectangle(FLASHCARD_LENGTH, FLASHCARD_HEIGHT);
        flashcardClickArea.setFill(Color.TRANSPARENT);
        flashcardClickArea.setOnMouseClicked(e);
        center.getChildren().add(flashcardClickArea);
        return center;
    }

    /**
     * Get a drawable rectangle representing a flashcard's border
     * @return a Rectangle representing a flashcard's border
     */
    protected Rectangle getFlashcardBorder() {
        Rectangle flashcardBorder = new Rectangle(FLASHCARD_LENGTH, FLASHCARD_HEIGHT);
        flashcardBorder.setFill(Color.TRANSPARENT);
        flashcardBorder.setStroke(Color.BLACK);
        return flashcardBorder;
    }

    /**
     * TODO
     * @param image
     */
    protected void resizeImageViewToFit(ImageView image) {
        image.setFitWidth(FLASHCARD_LENGTH * 0.9);
        image.setPreserveRatio(true);
    }

    /**
     * Create and return a properly-formatted StackPane holding the content of the flashcard.
     * @param flashcardContent a label holding the text and image in the flashcard.
     */
    protected StackPane getFlashcardInterior(Label flashcardContent) {
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

}