package Sessions;

import Decks.Deck;
import Flashcards.FlashcardDTO;
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
     * getter method for the deckCopy
     * @return List<Flashcard>
     */
    public abstract List<Flashcard> getDeckCopy();

    /**
     * getter method for flashcardToData
     * @return Map<Flashcard, FlashcardData>
     */
    protected Map<Flashcard, FlashcardData> getFlashcardToData() {
        return this.flashcardToData;
    }

    public void updateFlashcardToData(List<Flashcard> flashcardList){

        // First, check for updates needed from adding a card:
        // Loop through flashcardList. If it is in flashcardData, move on.
        // If it is not in flashcardData, add it to flashcardData.
        // Second, check for updates needed from deleting a card:
        // Loop through flashcardData. If it is in flashcardList, move on.
        // If it is not in flashcardList, then delete it from flashcardData

        for (Flashcard flashcard : flashcardList) {
            if (!flashcardToData.containsKey(flashcard)) {
                flashcardToData.put(flashcard, new FlashcardData(0));
            }
        }

        for (Flashcard flashcard : flashcardToData.keySet().stream().toList()) {
            if (!flashcardList.contains(flashcard)) {
                flashcardList.remove(flashcard);
            }
        }
    }



}
