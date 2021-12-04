package Sessions;

import Decks.DeckDTO;

public class TestSessionDTO extends StudySessionDTO {

    private final int length;
    private final int cardsSeen;
    private final int numCorrect;

    public TestSessionDTO(DeckDTO deckDTO, CardShufflerDTO cardShufflerDTO, int length, int cardsSeen, int numCorrect) {
        super(deckDTO, cardShufflerDTO);
        this.length = length;
        this.cardsSeen = cardsSeen;
        this.numCorrect = numCorrect;
    }

    public int getLength() {
        return length;
    }

    public int getCardsSeen() {
        return cardsSeen;
    }

    public int getNumCorrect() {
        return numCorrect;
    }
}
