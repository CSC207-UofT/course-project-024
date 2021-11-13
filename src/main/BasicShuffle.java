import java.util.Collections;
import java.util.Map;

public class BasicShuffle implements GetNextCard {
    @Override
    public Flashcard getNextCard(Deck deck, Map<Flashcard, Integer> proficiencies) {
        Deck newDeck = deck.copyDeck();
        Collections.shuffle(newDeck.getFlashcards());
        Flashcard card = newDeck.getFlashcards().get(0);
        return card;
    }
}
