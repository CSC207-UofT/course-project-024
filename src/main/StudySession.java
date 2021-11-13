import java.util.HashMap;
import java.util.Map;

// TODO documentation
public abstract class StudySession {
    protected Deck deck;
    protected Map<Flashcard, FlashcardData> flashcardToData = new HashMap<>();
    protected String name;
    // protected int currentCard;
    protected Flashcard currentCard;
    protected CardShuffler cardshuffler;

    public StudySession(Deck deck, String name, CardShuffler cardShuffler) {
        this.deck = deck;
        this.name = name;
        setCardshuffler(cardShuffler);
    }

    public StudySession(Deck deck, CardShuffler cardShuffler) {
        this.deck = deck;
        this.name = "Untitled";
        this.cardshuffler = cardShuffler;
    }

    // TODO: REMOVE, FOR TESTING
    public StudySession(Deck deck) {
        this.deck = deck;
        this.name = "Untitled";
    }

    public abstract Flashcard getNextCard();

    public Map<Flashcard, FlashcardData> getFlashcardToData(){
        return this.flashcardToData;
    }

    public Flashcard getCurrentCard() {
        return this.currentCard;
    }

    public Flashcard returnChosenFlashcard() {
        return cardshuffler.returnChosenFlashcard();
    }

    public void setCardshuffler(CardShuffler cardshuffler){
        this.cardshuffler = cardshuffler;
    }

}
