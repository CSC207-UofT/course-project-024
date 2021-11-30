package Flashcards;

import java.awt.*;

public class FlashcardDTO {

    private final Flashcard.Front front;
    private final String back;

    public FlashcardDTO(Flashcard.Front front, String back) {
        this.front = front;
        this.back = back;
    }

    /**
     * @return the String on the front of this Flashcard
     */
    public String getFrontText() {
        return front.getText();
    }

    /**
     * @return the Image on the front of this Flashcard
     */
    public Image getFrontImage() {
        return front.getImage();
    }

    /**
     * @return the String on the back of this Flashcard
     */
    public String getBack() {
        return this.back;
    }
}
