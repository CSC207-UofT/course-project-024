import java.util.Collections;
import java.util.Map;

public class BasicShuffle implements CardShuffler{
    @Override
    public void shuffleCards(Deck deck, Map<Flashcard, Integer> proficiencies) {
        Collections.shuffle(deck.getFlashcards());
    }
}
