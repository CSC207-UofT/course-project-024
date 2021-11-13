import java.util.HashMap;
import java.util.Map;

// TODO documentation
public abstract class StudySession {
    protected Deck deck;
    protected Map<Flashcard, Integer> proficiencies = new HashMap<>();
    protected String name;
    protected int currentCard;
    protected GetNextCard cardShuffler;

    public StudySession(Deck deck, String name, GetNextCard cardShuffler) {
        this.deck = deck.copyDeck();
        this.name = name;
        setCardShuffler(cardShuffler);
        setProficiencies();
    }

    public StudySession(Deck deck, GetNextCard cardShuffler) {
        this.deck = deck.copyDeck();
        this.name = "Untitled";
        this.cardShuffler = cardShuffler;
    }

    public Flashcard getNextCard(){
        return cardShuffler.getNextCard(this.deck, this.proficiencies);
    };

    public void setProficiencies(){
        for (Flashcard flashcard : deck.getFlashcards()){
            proficiencies.put(flashcard, 0);
        }
    }


    public Map<Flashcard, Integer> getProficiencies(){
        return proficiencies;
    }

    public void setCardShuffler(GetNextCard cardShuffler){
        this.cardShuffler = cardShuffler;
    }

}
