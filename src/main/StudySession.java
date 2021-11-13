import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// TODO documentation
public abstract class StudySession {
    // TODO consider changing some of these private
    protected Deck deck;
    protected Map<Flashcard, FlashcardData> flashcardData = new HashMap<>();
    protected String name;
    protected int currentCard;
    protected CardShuffler cardshuffler;

    public StudySession(Deck deck, String name) {
        this.deck = deck;
        this.name = name;
    }

    public StudySession(Deck deck) {
        this.deck = deck;
        this.name = "Untitled";
    }

    public abstract Flashcard getNextCard();

    public void shuffleCards() {
        Collections.shuffle(this.deck.getFlashcards());
        currentCard = 0;
    }

    public int getCurrentCard(){
        return currentCard;
    }

    public Map<Flashcard, FlashcardData> getFlashcardData() {
        return this.flashcardData;
    }

}
