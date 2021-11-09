import java.util.Map;

public interface CardShuffler {
    /**
     * Shuffles cards in a deck.
     */
    void shuffleCards(Deck deck, Map<Flashcard, Integer> proficiencies);
}
