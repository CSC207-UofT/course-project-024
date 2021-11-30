package Sessions;

import Decks.Deck;
import Decks.DeckDTO;

public abstract class StudySessionDTO {

    private final DeckDTO deckDTO;
    private final CardShufflerDTO cardShufflerDTO;

    public StudySessionDTO(DeckDTO deckDTO, CardShufflerDTO cardShufflerDTO) {
        this.deckDTO = deckDTO;
        this.cardShufflerDTO = cardShufflerDTO;
    }

    public DeckDTO getDeckDTO() {
        return deckDTO;
    }

    public CardShufflerDTO getCardShufflerDTO() {
        return cardShufflerDTO;
    }
}
