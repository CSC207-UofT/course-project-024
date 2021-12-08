package Sessions;

import Flashcards.FlashcardData;
import Flashcards.Flashcard;

import java.util.List;
import java.util.Map;

/**
 * This class deals with how to shuffle decks
 */
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

    /**
     * getter method for flashcardToData
     * @return Map<Flashcard, FlashcardData>
     */
    public Map<Flashcard, FlashcardData> getFlashcardToData() {
        return this.flashcardToData;
    }

    /**
     * A method for testing that adds a throwaway flashcard to this shuffler's flashcard to flashcard data map.
     * @return The flashcard that was added
     */
    public Flashcard addToFlashcardData() {
        Flashcard newFlashcard = new Flashcard(new Flashcard.Front("Hi", null), "Hi");
        this.flashcardToData.put(newFlashcard, new FlashcardData(0));
        return newFlashcard;
    }

    /**
     * A method for testing that removes the first flashcard from this shuffler's flashcard to flashcard data map.
     * @return The Flashcard that was removed
     */
    public Flashcard removeFromFlashcardData() {
        Flashcard removedFlashcard = this.flashcardToData.keySet().stream().toList().get(0);
        this.flashcardToData.remove(removedFlashcard);
        return removedFlashcard;
    }

    /**
     * getter method for the deckCopy
     * @return List<Flashcard>
     */
    public abstract List<Flashcard> getDeckCopy();

}
