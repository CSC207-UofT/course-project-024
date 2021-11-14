public class TestSession extends StudySession {

    private int length;
    private int numCorrect = 0;

    /**
     * Construct a new TestSession
     * @param deck A deck that will be bound to this TestSession.
     */
    public TestSession(Deck deck, int length) {
        super(deck, new BasicShuffle(deck));
        this.deck = deck;
        this.length = length;
    }

    public int getNumCorrect() {
        return this.numCorrect;
    }

    public void incrementNumCorrect() {
        this.numCorrect++;
    }

    public int getLength() {
        return this.length;
    }

    public double getPercentageCorrect() {
        return ((double)(this.numCorrect) / this.length * 100) ;
    }

    /**
     * Updates this LearningSession's CardShuffler's FlashcardData.
     * @param wasCorrect Whether the user got the flashcard correct.
     */
    public void updateFlashcardData(boolean wasCorrect) {
        this.cardShuffler.updateDeckContext();
    }
}
