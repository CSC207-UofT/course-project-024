import java.util.Map;

public interface GetNextCard {
    /**
     * Shuffles cards in a deck and gets the next card.
     */
    Flashcard getNextCard(Deck deck, Map<Flashcard, Integer> proficiencies);
}
