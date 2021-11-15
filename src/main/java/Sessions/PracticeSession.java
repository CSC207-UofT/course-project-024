package Sessions;

import Decks.Deck;
import Sessions.BasicShuffle;
import Sessions.StudySession;

public class PracticeSession extends StudySession {
    /**
     * Construct a PracticeSession with all FlashcardData initialized to default values.
     * @param deck The deck that this session will use to show cards to the user.
     */
    public PracticeSession(Deck deck) {
        super(deck, new BasicShuffle(deck));
        this.deck = deck;
        this.cardShuffler = new BasicShuffle(deck);
    }
}
