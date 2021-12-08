package Sessions;

import Flashcards.FlashcardDTO;
import Flashcards.FlashcardDataDTO;

import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object for WorstToBestShuffle
 */
public class WorstToBestShuffleDTO extends CardShufflerDTO{

    private final List<FlashcardDTO> deckCopy;
    private final FlashcardDTO lastFlashcardShown;

    public WorstToBestShuffleDTO(Map<FlashcardDTO, FlashcardDataDTO> flashcardToData, List<FlashcardDTO> deckCopy,
                                 FlashcardDTO lastFlashcardShown) {
        super(flashcardToData);
        this.deckCopy = deckCopy;
        this.lastFlashcardShown = lastFlashcardShown;
    }

    /**
     * getter method for deckCopy
     * @return FlashcardDTO
     */
    public List<FlashcardDTO> getDeckCopy() {
        return deckCopy;
    }

    /**
     * getter method for lastFlashcardShown
     * @return FlashcardDTO
     */
    public FlashcardDTO getLastFlashcardShown() {
        return lastFlashcardShown;
    }
}
