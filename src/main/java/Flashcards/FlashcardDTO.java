package Flashcards;

import java.awt.*;

public class FlashcardDTO {

    private final String frontText;
    private final Image frontImage;
    private final String back;

    public FlashcardDTO(String frontText, Image frontImage, String back) {
        this.frontText = frontText;
        this.frontImage = frontImage;
        this.back = back;
    }

    /**
     * @return the String on the front of this Flashcard
     */
    public String getFrontText() {
        return frontText;
    }

    /**
     * @return the Image on the front of this Flashcard
     */
    public Image getFrontImage() {
        return frontImage;
    }

    /**
     * @return the String on the back of this Flashcard
     */
    public String getBack() {
        return back;
    }
}
