package Sessions;

import Flashcards.FlashcardDTO;
import Flashcards.FlashcardDataDTO;

import java.util.LinkedList;
import java.util.Map;

public class SmartShuffleDTO extends CardShufflerDTO {

    private final LinkedList<FlashcardDTO> deckCopy;
    private final FlashcardDTO lastFlashcardShown;

    public SmartShuffleDTO(Map<FlashcardDTO, FlashcardDataDTO> flashcardToData,
                           LinkedList<FlashcardDTO> deckCopy, FlashcardDTO lastFlashcardShown) {
        super(flashcardToData);
        this.deckCopy = deckCopy;
        this.lastFlashcardShown = lastFlashcardShown;
    }

    public LinkedList<FlashcardDTO> getDeckCopy() {
        return deckCopy;
    }

    public FlashcardDTO getLastFlashcardShown() {
        return lastFlashcardShown;
    }
}
