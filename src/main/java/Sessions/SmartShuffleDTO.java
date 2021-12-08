package Sessions;

import Flashcards.FlashcardDTO;
import Flashcards.FlashcardDataDTO;

import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object for SmartShuffle
 */
public class SmartShuffleDTO extends CardShufflerDTO {

    private final List<FlashcardDTO> deckCopy;
    private final FlashcardDTO lastFlashcardShown;

    public SmartShuffleDTO(Map<FlashcardDTO, FlashcardDataDTO> flashcardToData,
                           List<FlashcardDTO> deckCopy, FlashcardDTO lastFlashcardShown) {
        super(flashcardToData);
        this.deckCopy = deckCopy;
        this.lastFlashcardShown = lastFlashcardShown;
    }

    /**
     * getter method for deckCopy
     * @return List<FlashcardDTO>
     */
    public List<FlashcardDTO> getDeckCopy() {
        return deckCopy;
    }

    /**
     * getter method for last flashcard shown
     * @return FlashcardDTO
     */
    public FlashcardDTO getLastFlashcardShown() {
        return lastFlashcardShown;
    }
}
