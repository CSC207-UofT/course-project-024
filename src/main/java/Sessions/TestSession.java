package Sessions;

import Decks.Deck;

public class TestSession extends StudySession {

    private final int length;
    private int numCorrect = 0; // the number of times the user guessed the back of the card correctly

    /**
     * Construct a new TestSession
     * @param deck A deck that will be bound to this TestSession.
     */
    public TestSession(Deck deck, int length) {
        super(deck, new BasicShuffle(deck));
        this.length = length;
    }

    /**
     * @return the number of times the user guessed the back of the card correctly.
     */
    public int getNumCorrect() {
        return this.numCorrect;
    }

    /**
     * Increment the counter for how many times the user guessed the back of the card correctly.
     */
    public void incrementNumCorrect() {
        this.numCorrect++;
    }

    /**
     * @return the number of cards that will be shown to the user in this session.
     */
    public int getLength() {
        return this.length;
    }

    /**
     * @return a double representing the percentage of cards that the users guessed correctly.
     */
    public double getPercentageCorrect() {
        return ((double)(this.numCorrect) / this.length * 100) ;
    }

    /**
     * Updates this session's CardShuffler's shuffled deck with the updated version in the Account.
     */
    public void updateFlashcardData() {
        this.cardShuffler.updateDeckContext();
    }
}
