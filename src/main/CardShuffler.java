import java.util.Map;

public interface CardShuffler {
    /**
     * sets the deck to be shuffled
     * @param deck
     */
    void setDeck(Deck deck);

    /**
     * Shuffles cards in a deck.
     */
    void shuffleCards();

    /**
     * Returns a flashcard to present to a user
     * @return
     */
    Flashcard returnChosenFlashcard();
}
