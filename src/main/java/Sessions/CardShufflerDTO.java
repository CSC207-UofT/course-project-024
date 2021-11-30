package Sessions;

import Flashcards.Flashcard;
import Flashcards.FlashcardDTO;
import Flashcards.FlashcardData;
import Flashcards.FlashcardDataDTO;

import java.util.Map;

public abstract class CardShufflerDTO {

    Map<FlashcardDTO, FlashcardDataDTO> flashcardToData;

    public CardShufflerDTO(Map<FlashcardDTO, FlashcardDataDTO> flashcardToData) {
        this.flashcardToData = flashcardToData;
    }

    Map<FlashcardDTO, FlashcardDataDTO> getFlashcardToData() {
        return this.flashcardToData;
    }
}
