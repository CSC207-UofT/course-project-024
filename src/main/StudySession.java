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
        this.cardshuffler = cardShuffler;
    }

    public StudySession(Deck deck, CardShuffler cardShuffler) {
        this.deck = deck;
        this.name = "Untitled";
    }

    // TODO: REMOVE, FOR TESTING
    public StudySession(Deck deck) {
        this.deck = deck;
        this.name = "Untitled";
    }

    public abstract Flashcard getNextCard();
//        currentCard = currentCard + 1;
//        return deck.getFlashcards().get(currentCard);
//    };

//    public void shuffleCards() {
//        cardshuffler.shuffleCardsAndReturnFirst(this.deck, this.flashcardData);
//        currentCard = 0;
//    }

    public Map<Flashcard, FlashcardData> getFlashcardToData(){
        return this.flashcardToData;
    }

    public Flashcard getCurrentCard(){
        return this.currentCard;
    }

}
