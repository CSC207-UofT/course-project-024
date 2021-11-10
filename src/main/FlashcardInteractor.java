import java.awt.*;

public class FlashcardInteractor {
    /**
     * Construct and return a new Flashcard.
     * @param front The front of the new Flashcard to be returned
     * @param back The back of the new Flashcard to be returned
     * @return The Flashcard with front and back specified in the args
     */
    public static Flashcard createFlashcard(Flashcard.Front front, String back) {
        return new Flashcard(front, back);
    }

    public static Flashcard.Front createFront(String text, Image image) {
        return new Flashcard.Front(text, image);
    }

    /**
     * Edit the front of a Flashcard.
     * @param flashcard The Flashcard to be edited
     * @param newFront The new front that will replace the current one
     */
    public static void editFront(Flashcard flashcard, Flashcard.Front newFront) {
        flashcard.setFront(newFront);
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
