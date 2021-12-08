package Sessions;

import Decks.DeckDTO;

/**
 * Data Transfer Object for StudySession
 */
public abstract class StudySessionDTO {

    private final DeckDTO deckDTO;
    private final CardShufflerDTO cardShufflerDTO;

    public StudySessionDTO(DeckDTO deckDTO, CardShufflerDTO cardShufflerDTO) {
        this.deckDTO = deckDTO;
        this.cardShufflerDTO = cardShufflerDTO;
    }

    /**
     * getter method for deckDTO
     * @return DeckDTO
     */
    public DeckDTO getDeckDTO() {
        return deckDTO;
    }

    /**
     * getter method for CardShufflerDTO
     * @return CardShufflerDTO
     */
    public CardShufflerDTO getCardShufflerDTO() {
        return cardShufflerDTO;
    }
}
