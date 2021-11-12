import java.util.HashMap;
import java.util.Map;

// TODO documentation
public abstract class StudySession {
    protected Deck deck;
    protected Map<Flashcard, Integer> proficiencies = new HashMap<>();
    protected String name;
    protected int currentCard;
    protected CardShuffler cardshuffler;

    public StudySession(Deck deck, String name, CardShuffler cardShuffler) {
        this.deck = deck.copyDeck();
        this.name = name;
        setCardShuffler(cardShuffler);
        setProficiencies();
    }

    public StudySession(Deck deck, CardShuffler cardShuffler) {
        this.deck = deck.copyDeck();
        this.name = "Untitled";
        this.cardshuffler = cardShuffler;
    }

    public Flashcard getNextCard(){
        currentCard = currentCard + 1;
        return deck.getFlashcards().get(currentCard);
    };

    public void setProficiencies(){
        for (Flashcard flashcard : deck.getFlashcards()){
            proficiencies.put(flashcard, 0);
        }
    }

    public void shuffleCards() {
        cardshuffler.shuffleCards(this.deck, this.proficiencies);
        currentCard = 0;
    }

    public Map<Flashcard, Integer> getProficiencies(){
        return proficiencies;
    }

    public void setCardShuffler(CardShuffler cardShuffler){
        this.cardshuffler = cardShuffler;
    }

}
