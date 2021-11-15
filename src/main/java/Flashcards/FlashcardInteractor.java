package Flashcards;

import java.awt.*;

public class FlashcardInteractor {
    /**
     * Construct and return a new Flashcard.
     * @param frontText The text of the front of the new Flashcard to be returned (possibly null)
     * @param frontImage The image of the front of the new Flashcard to be returned (possibly null)
     * @param back The back of the new Flashcard to be returned
     * @return The Flashcard with front and back specified in the args
     */
    public static Flashcard createFlashcard(String frontText, Image frontImage, String back) {
        Flashcard.Front front = new Flashcard.Front(frontText, frontImage);
        return new Flashcard(front, back);
    }

    /**
     * Edit the front of a Flashcard.
     * @param flashcard The Flashcard to be edited
     * @param frontText The new text of the front of the Flashcard (possibly null)
     * @param frontImage The new image of the front of the Flashcard (possibly null)
     */
    public static void editFront(Flashcard flashcard, String frontText, Image frontImage) {
        Flashcard.Front front = new Flashcard.Front(frontText, frontImage);
        flashcard.setFront(front);
    }

    /**
     * Edit the back of a Flashcard.
     * @param flashcard The Flashcard to be edited
     * @param newBack The new back that will replace the current one
     */
    public static void editBack(Flashcard flashcard, String newBack) {
        flashcard.setBack(newBack);
    }

}
