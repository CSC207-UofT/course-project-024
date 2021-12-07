package Decks;

import Flashcards.Flashcard;
import Flashcards.FlashcardDTO;
import Flashcards.FlashcardInteractor;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DeckInteractor {

    private static Deck currentDeck;

    private DeckInteractor() {}

    /**
     * Set the current deck for this interactor to work on and mutate.
     * @param deck the deck that will be worked on by this interactor.
     */
    public static void setCurrentDeck(Deck deck) {
        currentDeck = deck;
    }

    /**
     * Provide relevant interactors with the ability to use the flashcard at this index.
     * @param flashcardDTO The flashcard to be selected
     */
    public static void selectFlashcard(FlashcardDTO flashcardDTO) {
        Flashcard flashcard = findFlashcardInCurrentDeckFromDTO(flashcardDTO);
        FlashcardInteractor.setCurrentFlashcard(flashcard);
    }

    /**
     * @return the current deck
     */
    public static DeckDTO getCurrentDeck() {
        return convertDeckToDTO(currentDeck);
    }

    /**
     * Create a new deck. Return the DTO representation of the created deck.
     * @param deckName The name of the new deck
     * @return the created deck.
     */
    public static DeckDTO createDeck(String deckName) {
        Deck deck = new Deck(deckName);
        return convertDeckToDTO(deck);
    }

    /**
     * Rename the current deck.
     * @param newName The new name of the deck
     */
    public static void renameCurrentDeck(String newName) {
        currentDeck.setName(newName);
    }

    /**
     * Delete a flashcard from the current deck.
     * @param flashcardDTO The flashcard to be deleted
     */
    public static void deleteFlashcardFromCurrentDeck(FlashcardDTO flashcardDTO) {
        Flashcard flashcard = findFlashcardInCurrentDeckFromDTO(flashcardDTO);
        currentDeck.getFlashcards().remove(flashcard);
        currentDeck.notifyObservers();
    }

    /**
     * Add a new flashcard to the current deck.
     * @param frontText The text on the front of the new flashcard (possibly null)
     * @param frontImage The image on the front of the new flashcard (possibly null)
     * @param back The text on the back of the new flashcard
     */
    public static void addFlashcardToCurrentDeck(String frontText, BufferedImage frontImage, String back) {
        FlashcardDTO newFlashcard = FlashcardInteractor.createFlashcard(frontText, frontImage, back);
        currentDeck.addFlashcard(FlashcardInteractor.convertDTOToFlashcard(newFlashcard));
        currentDeck.notifyObservers();
    }

    /**
     * Get a Deck entity from its DTO representation.
     * @param deckDTO The target deck
     * @return a Deck
     */
    public static Deck convertDTOToDeck(DeckDTO deckDTO) {
        List<Flashcard> flashcards = new ArrayList<>();
        for (FlashcardDTO flashcardDTO : deckDTO.getFlashcards()) {
            Flashcard flashcard = FlashcardInteractor.convertDTOToFlashcard(flashcardDTO);
            flashcards.add(flashcard);
        }
        return new Deck(deckDTO.getName(), flashcards);
    }

    /**
     * Get a DTO representation of the specified deck.
     * @param deck The target deck
     * @return a DeckDTO
     */
    public static DeckDTO convertDeckToDTO(Deck deck) {
        String name = deck.getName();
        List<FlashcardDTO> flashcardsDTO = new ArrayList<>();
        for (Flashcard flashcard : deck.getFlashcards()) {
            FlashcardDTO flashcardDTO = FlashcardInteractor.convertFlashcardToDTO(flashcard);
            flashcardsDTO.add(flashcardDTO);
        }
        return new DeckDTO(name, flashcardsDTO);
    }

    private static Flashcard findFlashcardInCurrentDeckFromDTO(FlashcardDTO flashcardDTO) {
        return currentDeck.getFlashcards().stream()
                .filter(flashcard -> flashcard.getFront().getText().equals(flashcardDTO.getFrontText()))
                .findAny()
                .orElse(null);
    }

}
