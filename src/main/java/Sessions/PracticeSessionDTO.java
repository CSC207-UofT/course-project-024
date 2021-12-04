package Sessions;

import Decks.DeckDTO;

public class PracticeSessionDTO extends StudySessionDTO {

    public PracticeSessionDTO(DeckDTO deckDTO, CardShufflerDTO basicShuffleDTO) {
        super(deckDTO, basicShuffleDTO);
    }
}
