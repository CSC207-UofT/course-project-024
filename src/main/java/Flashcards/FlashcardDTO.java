package Flashcards;

import java.awt.image.BufferedImage;

/**
 * Data Transfer Object for Flashcard
 */
public record FlashcardDTO(String frontText, BufferedImage frontImage, String back) {

    /**
     * @return the String on the front of this Flashcard
     */
    public String getFrontText() {
        return frontText;
    }

    /**
     * @return the Image on the front of this Flashcard
     */
    public BufferedImage getFrontImage() {
        return frontImage;
    }

    /**
     * @return the String on the back of this Flashcard
     */
    public String getBack() {
        return back;
    }
}
