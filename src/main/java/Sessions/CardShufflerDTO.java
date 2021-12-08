package Sessions;

import Flashcards.FlashcardDTO;
import Flashcards.FlashcardDataDTO;

import java.util.Map;

public abstract class CardShufflerDTO {

    Map<FlashcardDTO, FlashcardDataDTO> flashcardToData;

    public CardShufflerDTO(Map<FlashcardDTO, FlashcardDataDTO> flashcardToData) {
        this.flashcardToData = flashcardToData;
    }

    public Map<FlashcardDTO, FlashcardDataDTO> getFlashcardToData() {
        return this.flashcardToData;
    }
}
