import java.util.HashMap;
import java.util.Map;

// TODO documentation
public abstract class StudySession {
    protected Deck deck;
    protected String name;
    // protected int currentCard;
    protected CardShuffler cardShuffler;

    public StudySession(Deck deck, String name, CardShuffler cardShuffler) {
        this.deck = deck;
        this.name = name;
        setCardShuffler(cardShuffler);
    }

    public StudySession(Deck deck, CardShuffler cardShuffler) {
        this.deck = deck;
        this.name = "Untitled";
        this.cardShuffler = cardShuffler;
    }

    public Map<Flashcard, FlashcardData> getFlashcardToData(){
        return this.cardShuffler.getFlashcardToData();
    }

    public Flashcard getNextCard() {
        return cardShuffler.returnChosenFlashcard();
    }

    public void setCardShuffler(CardShuffler cardShuffler){
        this.cardShuffler = cardShuffler;
    }

}
