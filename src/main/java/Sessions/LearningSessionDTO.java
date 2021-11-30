package Sessions;

import Decks.DeckDTO;

public class LearningSessionDTO extends StudySessionDTO {

    public LearningSessionDTO(DeckDTO deckDTO, CardShufflerDTO smartShuffleDTO) {
        super(deckDTO, smartShuffleDTO);
    }
}
