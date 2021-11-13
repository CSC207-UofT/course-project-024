import java.util.HashMap;
import java.util.Map;

// TODO documentation
public abstract class StudySession {
    protected Deck deck;
    protected Map<Flashcard, FlashcardData> flashcardData = new HashMap<>();
    protected String name;
    protected int currentCard;
    protected CardShuffler cardshuffler;

    public StudySession(Deck deck, String name, CardShuffler cardShuffler) {
        this.deck = deck;
        this.name = name;
        setCardshuffler(cardShuffler);
        setProficiencies();
    }

    public StudySession(Deck deck, CardShuffler cardShuffler) {
        this.deck = deck;
        this.name = "Untitled";
        this.cardshuffler = cardShuffler;
    }

    public Flashcard returnChosenFlashcard(){
        return cardshuffler.returnChosenFlashcard();
    };


    public void setCardshuffler(CardShuffler cardshuffler){
        this.cardshuffler = cardshuffler;
    }

}
