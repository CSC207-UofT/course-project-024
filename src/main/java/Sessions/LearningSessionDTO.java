package Sessions;

import Decks.DeckDTO;

public class LearningSessionDTO extends StudySessionDTO {

    public LearningSessionDTO(DeckDTO deckDTO, CardShufflerDTO worstToBestShuffleDTO) {
        super(deckDTO, worstToBestShuffleDTO);
    }
}
