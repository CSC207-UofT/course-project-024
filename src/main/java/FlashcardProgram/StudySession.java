package FlashcardProgram;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a study session
 */
public abstract class StudySession {
    protected Deck deck;

    protected String name;
    // cardshuffler holds the strategy in how we choose the next card
    protected CardShuffler cardShuffler;

    /**
     * Constructs a study session with given deck, name and cardshuffler
     * @param deck
     * @param name
     * @param cardShuffler
     */
    public StudySession(Deck deck, String name, CardShuffler cardShuffler) {
        this.deck = deck;
        this.name = name;
        setCardShuffler(cardShuffler);
    }

    /**
     * Constructs a study session with given deck and cardshuffler
     * @param deck
     * @param cardShuffler
     */
    public StudySession(Deck deck, CardShuffler cardShuffler) {
        this.deck = deck;
        this.name = "Untitled";
        this.cardShuffler = cardShuffler;
    }

     /**
     * Returns this StudySession's CardShuffler's flashcardToData
     * @return Map<Flashcard, FlashcardData>
     */
    public Map<Flashcard, FlashcardData> getFlashcardToData(){
        return this.cardShuffler.getFlashcardToData();
    }
    
    /**
     * Returns the next card given by this StudySession's CardShuffler
     * @return Flashcard
     */
    public Flashcard getNextCard() {
        return cardShuffler.returnChosenFlashcard();
    }
    
    /**
     * Sets the CardShuffler for this StudySession
     * @param cardShuffler
     */
    public void setCardShuffler(CardShuffler cardShuffler){
        this.cardShuffler = cardShuffler;

    }
}
