package Sessions;

import Flashcards.FlashcardData;
import Flashcards.Flashcard;

import java.util.Map;

public abstract class CardShuffler implements Observer{

    /**
     * A mapping from Flashcard to its corresponding FlashcardData.
     */
    protected Map<Flashcard, FlashcardData> flashcardToData;

    /**
     * Returns a Flashcard chosen by this CardShuffler's choosing algorithm.
     * @return A Flashcard chosen by this CardShuffler's choosing algorithm
     */
    public abstract Flashcard returnChosenFlashcard();

    /**
     * Updates this card shuffler to make it up-to-date with any changes to its flashcardToData mapping.
     */
    @Override
    public abstract void update();

    protected Map<Flashcard, FlashcardData> getFlashcardToData() {
        return this.flashcardToData;
    }



}
