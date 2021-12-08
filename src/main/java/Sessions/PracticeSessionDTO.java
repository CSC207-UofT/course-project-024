package Sessions;

import Decks.DeckDTO;

/**
 * Data Transfer Object for PracticeSession
 */
public class PracticeSessionDTO extends StudySessionDTO {

    public PracticeSessionDTO(DeckDTO deckDTO, CardShufflerDTO smartShuffleDTO) {
        super(deckDTO, smartShuffleDTO);
    }
}
