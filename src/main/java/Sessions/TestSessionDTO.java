package Sessions;

import Decks.DeckDTO;

/**
 * Data Transfer Object for TestSession
 */
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

    /**
     * getter method for length.
     * @return int
     */
    public int getLength() {
        return length;
    }

    /**
     * getter method for cardsSeen
     * @return int
     */
    public int getCardsSeen() {
        return cardsSeen;
    }

    /**
     * getter method for numCorrect
     * @return int
     */
    public int getNumCorrect() {
        return numCorrect;
    }
}
