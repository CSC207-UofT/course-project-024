import java.util.Collections;
import java.util.Map;

// TODO documentation
public class StudySession {
    public Deck deck;
    int currentCard;

    public void createSession(Deck deck) {
        this.deck = deck;
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
