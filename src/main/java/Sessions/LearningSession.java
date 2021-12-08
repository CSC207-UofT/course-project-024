package Sessions;

import Decks.Deck;

/**
 * A type of study session for Learning. This session focuses on content the user is worst at.
 */
public class LearningSession extends StudySession {

    /**
     * Construct a new LearningSession
     * @param deck A deck that will be bound to this LearningSession.
     */
    public LearningSession(Deck deck) {
        super(deck, new WorstToBestShuffle(deck));
    }

    /**
     * Construct a new LearningSession
     * @param deck A deck that will be bound to this LearningSession.
     * @param cardShuffler The card shuffler algorithm used by this session.
     */
    public LearningSession(Deck deck, CardShuffler cardShuffler) {
        super(deck, cardShuffler);
    }
}
