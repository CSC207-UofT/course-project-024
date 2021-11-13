import java.util.HashMap;
import java.util.Map;

// TODO documentation
public abstract class StudySession {
    protected Deck deck;
    protected Map<Flashcard, Integer> proficiencies = new HashMap<>();
    protected String name;
    protected int currentCard;
    protected CardShuffler cardShuffler;

    public StudySession(Deck deck, String name, CardShuffler cardShuffler) {
        this.deck = deck;
        this.name = name;
        setCardShuffler(cardShuffler);
        setProficiencies();
    }

    public StudySession(Deck deck, CardShuffler cardShuffler) {
        this.deck = deck;
        this.name = "Untitled";
        this.cardShuffler = cardShuffler;
    }

    public Flashcard returnChosenFlashcard(){
        return cardShuffler.returnChosenFlashcard();
    };

    public void setProficiencies(){
        for (Flashcard flashcard : deck.getFlashcards()){
            proficiencies.put(flashcard, 0);
        }
    }


    public Map<Flashcard, Integer> getProficiencies(){
        return proficiencies;
    }

    public void setCardShuffler(CardShuffler cardShuffler){
        this.cardShuffler = cardShuffler;
    }

}
