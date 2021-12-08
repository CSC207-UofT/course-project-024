package Sessions;

import Flashcards.FlashcardDTO;
import Flashcards.FlashcardDataDTO;

import java.util.Map;

/**
 * Data Transfer Object for CardShufflers
 */
public abstract class CardShufflerDTO {

    Map<FlashcardDTO, FlashcardDataDTO> flashcardToData;

    public CardShufflerDTO(Map<FlashcardDTO, FlashcardDataDTO> flashcardToData) {
        this.flashcardToData = flashcardToData;
    }

    /**
     * getter method for flashcardToData
     * @return Map<FlashcardDTO, FlashcardDataDTO>
     */
    public Map<FlashcardDTO, FlashcardDataDTO> getFlashcardToData() {
        return this.flashcardToData;
    }
}
