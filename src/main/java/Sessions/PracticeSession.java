package Sessions;

import Decks.Deck;

/**
 * A type of study session for practicing.
 */
public class PracticeSession extends StudySession {

    /**
     * Construct a PracticeSession with all FlashcardData initialized to default values.
     * @param deck The deck that this session will use to show cards to the user.
     */
    public PracticeSession(Deck deck) {
        super(deck, new SmartShuffle(deck));
    }

    /**
     * Construct a PracticeSession with all FlashcardData initialized to default values.
     * @param deck The deck that this session will use to show cards to the user.
     * @param cardShuffler The card shuffler strategy used by this session.
     */
    public PracticeSession(Deck deck, CardShuffler cardShuffler) {
        super(deck, cardShuffler);
    }
}