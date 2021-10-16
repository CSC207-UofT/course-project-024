import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// TODO documentation
public class StudySession {
    // TODO consider changing some of these private
    public Deck deck;
    public Map<Flashcard, Integer> proficiencies = new HashMap<>();
    public String name;
    int currentCard;

    public StudySession(Deck deck, String name) {
        this.deck = deck;
        this.name = name;
    }

    public StudySession(Deck deck) {
        this.deck = deck;
        this.name = "Untitled";
    }

    public Flashcard getNextCard() {
        this.currentCard = currentCard + 1;
        return deck.getFlashcards()[this.currentCard];
    }

    public void shuffleCards() {
        Collections.shuffle(this.deck.getFlashcards());
        currentCard = 0;
    }

}
