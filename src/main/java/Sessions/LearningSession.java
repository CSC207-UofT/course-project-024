package Sessions;

import Decks.Deck;

public class LearningSession extends StudySession {

    /**
     * Construct a new LearningSession
     * @param deck A deck that will be bound to this LearningSession.
     */
    public LearningSession(Deck deck) {
        super(deck, new SmartShuffle(deck));
        this.deck = deck;
    }

    /**
     * Updates this LearningSession's CardShuffler's FlashcardData.
     * @param wasCorrect Whether the user got the flashcard correct.
     */
    public void updateFlashcardData(boolean wasCorrect) {
        this.cardShuffler.updateDeckContext();
    }

}
