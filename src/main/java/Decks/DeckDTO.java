package Decks;

import Flashcards.FlashcardDTO;

import java.util.List;

/**
 * Data Transfer Object for the deck
 */
public record DeckDTO(String name, List<FlashcardDTO> flashcards) {

    /**
     * getter method for the name
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * getter method for flashcards
     *
     * @return List of flashcard DTOs
     */
    public List<FlashcardDTO> getFlashcards() {
        return flashcards;
    }
}
