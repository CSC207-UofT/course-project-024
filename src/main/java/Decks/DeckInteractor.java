package Decks;

import Flashcards.Flashcard;
import Flashcards.FlashcardInteractor;

import java.awt.*;

public class DeckInteractor {

    /**
     * Create and return a new deck.
     * @param deckName The name of the new deck
     * @return the created deck.
     */
    public static Deck createDeck(String deckName) {
        return new Deck(deckName);
    }

    /**
     * Rename the specified deck.
     * @param deck The deck to be renamed
     * @param newName The new name of the deck
     */
    public static void renameDeck(Deck deck, String newName) {
        deck.setName(newName);
    }

    /**
     * Delete a flashcard from the specified deck.
     * @param deck The deck which owns the flashcard to be deleted
     * @param flashcard The flashcard to be deleted
     */
    public static void deleteFlashcard(Deck deck, Flashcard flashcard) {
        deck.removeFlashcard(flashcard);
    }

    /**
     * Add a new flashcard to the specified deck.
     * @param deck The deck which will own the new flashcard
     * @param frontText The text on the front of the new flashcard (possibly null)
     * @param frontImage The image on the front of the new flashcard (possibly null)
     * @param back The text on the back of the new flashcard
     */
    public static void addFlashcard(Deck deck, String frontText, Image frontImage, String back) {
        Flashcard newFlashcard = FlashcardInteractor.createFlashcard(frontText, frontImage, back);
        deck.addFlashcard(newFlashcard);
    }

}
