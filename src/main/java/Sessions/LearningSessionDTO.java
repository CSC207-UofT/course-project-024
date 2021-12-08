package Sessions;

import Decks.DeckDTO;

/**
 * Data Transfer Object for LearningSession
 */
public class LearningSessionDTO extends StudySessionDTO {

    public LearningSessionDTO(DeckDTO deckDTO, CardShufflerDTO worstToBestShuffleDTO) {
        super(deckDTO, worstToBestShuffleDTO);
    }
}
