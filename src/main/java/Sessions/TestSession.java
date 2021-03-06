package Sessions;

import Decks.Deck;
import Flashcards.Flashcard;

/**
 * A type of study session for testing. Users can be marked based off their answers.
 */
public class TestSession extends StudySession {

    private final int length; // How many cards will be shown by the session
    private int cardsSeen;
    private int numCorrect; // the number of times the user guessed the back of the card correctly

    /**
     * Construct a new TestSession
     * @param deck A deck that will be bound to this TestSession.
     * @param length How many cards in total will be shown by this session.
     */
    public TestSession(Deck deck, int length) {
        super(deck, new BasicShuffle(deck));
        this.length = length;
        this.cardsSeen = 0;
        this.numCorrect = 0;
    }

    /**
     * Construct a new TestSession
     * @param deck A deck that will be bound to this TestSession.
     */
    public TestSession(Deck deck, CardShuffler cardShuffler, int length, int cardsSeen, int numCorrect) {
        super(deck, cardShuffler);
        this.length = length;
        this.cardShuffler = cardShuffler;
        this.cardsSeen = cardsSeen;
        this.numCorrect = numCorrect;
    }

    /**
     * @return the number of times the user guessed the back of the card correctly.
     */
    public int getNumCorrect() {
        return this.numCorrect;
    }

    /**
     * Returns the next card given by this StudySession's CardShuffler.
     * @return A Flashcard chosen by this StudySession's CardShuffler
     */
    @Override
    public Flashcard getNextCard() {
        this.cardsSeen++;
        return cardShuffler.returnChosenFlashcard();
    }

    /**
     * Increment the counter for how many times the user guessed the back of the card correctly.
     */
    public void incrementNumCorrect() {
        this.numCorrect++;
    }

    /**
     * @return the number of flashcards seen by the user in total during this session.
     */
    public int getCardsSeen() {
        return this.cardsSeen;
    }

    /**
     * @return the number of cards that will be shown to the user in this session.
     */
    public int getLength() {
        return this.length;
    }
}
