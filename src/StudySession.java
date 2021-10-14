import java.util.Collections;
import java.util.Map;

// TODO documentation
public class StudySession {
    public Deck deck;
    private Map<Flashcard, Integer> proficiencies = new Map<Flashcard, Integer>();
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

    public Flashcard getNextCard(){
        this.currentCard = currentCard + 1;
        return deck.getFlashcards()[this.currentCard - 1];
    }

    public void shuffleCards(){
        Collections.shuffle(this.deck);
        currentCard = 0;
    }

}
