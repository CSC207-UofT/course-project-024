import java.util.Collections;
import java.util.Map;

public class BasicShuffle implements GetNextCard {
    @Override
    public Flashcard getNextCard(Deck deck, Map<Flashcard, Integer> proficiencies) {
        Collections.shuffle(deck.getFlashcards());
        Flashcard card = deck.getFlashcards().get(0);
        return card;
    }
}
