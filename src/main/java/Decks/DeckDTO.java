package Decks;

import Flashcards.Flashcard;
import Flashcards.FlashcardDTO;
import Flashcards.FlashcardInteractor;

import java.util.ArrayList;
import java.util.List;

public class DeckDTO {
    private final String name;
    private final List<FlashcardDTO> flashcards;

    public DeckDTO(String name, List<FlashcardDTO> flashcards) {
        this.name = name;
        this.flashcards = flashcards;
    }

    public String getName() {
        return name;
    }

    public List<FlashcardDTO> getFlashcards() {
        return flashcards;
    }
}
