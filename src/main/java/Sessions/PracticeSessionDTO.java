package Sessions;

import Decks.DeckDTO;

public class PracticeSessionDTO extends StudySessionDTO {

    public PracticeSessionDTO(DeckDTO deckDTO, CardShufflerDTO smartShuffleDTO) {
        super(deckDTO, smartShuffleDTO);
    }
}
