import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a study session
 */
public abstract class StudySession {
    protected Deck deck;
    // flashcard data maps flashcard with statistics on the user's proficiency with said card
    protected Map<Flashcard, FlashcardData> flashcardData = new HashMap<>();
    protected String name;
    // cardshuffler holds the strategy in how we choose the next card
    protected CardShuffler cardshuffler;

    /**
     * Constructs a study session with given deck, name and cardshuffler
     * @param deck
     * @param name
     * @param cardShuffler
     */
    public StudySession(Deck deck, String name, CardShuffler cardShuffler) {
        this.deck = deck;
        this.name = name;
        setCardshuffler(cardShuffler);
    }

    /**
     * Constructs a study session with given deck and cardshuffler
     * @param deck
     * @param cardShuffler
     */
    public StudySession(Deck deck, CardShuffler cardShuffler) {
        this.deck = deck;
        this.name = "Untitled";
        this.cardshuffler = cardShuffler;
    }

    /**
     * Returns the next card
     * @return Flashcard
     */
    public Flashcard returnChosenFlashcard(){
        return cardshuffler.returnChosenFlashcard();
    }

    /**
     * Sets the cardshuffler for the study session
     * @param cardshuffler
     */
    public void setCardshuffler(CardShuffler cardshuffler){
        this.cardshuffler = cardshuffler;
    }

    /**
     * Returns flashcarddata
     * @return Map<Flashcard, FlashcardData>
     */
    public Map<Flashcard, FlashcardData> getFlashcardData() {
        return this.flashcardData;
    }

}
