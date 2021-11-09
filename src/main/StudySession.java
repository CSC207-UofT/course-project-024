import java.util.HashMap;
import java.util.Map;

// TODO documentation
public abstract class StudySession {
    // TODO consider changing some of these private
    protected Deck deck;
    protected Map<Flashcard, Integer> proficiencies = new HashMap<>();
    protected String name;
    protected int currentCard;
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

    public Flashcard getNextCard(){
        currentCard = currentCard + 1;
        return deck.getFlashcards().get(currentCard);
    };

    public void shuffleCards() {
        cardshuffler.shuffleCards(this.deck, this.proficiencies);
        currentCard = 0;
    }

    public Map<Flashcard, Integer> getProficiencies(){
        return proficiencies;
    }

    public int getCurrentCard(){
        return currentCard;
    }

}
