package Sessions;

import Flashcards.FlashcardDTO;
import Flashcards.FlashcardDataDTO;

import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object for BasicShuffle
 */
public class BasicShuffleDTO extends CardShufflerDTO {

    private final int index;
    private final List<FlashcardDTO> deckCopy;

    public BasicShuffleDTO(Map<FlashcardDTO, FlashcardDataDTO> flashcardToData,
                           List<FlashcardDTO> deckCopy, int index) {
        super(flashcardToData);
        this.index = index;
        this.deckCopy = deckCopy;
    }

    public int getIndex() {
        return index;
    }

    public List<FlashcardDTO> getDeckCopy() {
        return deckCopy;
    }
}
