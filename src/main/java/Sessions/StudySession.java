package Sessions;

import Decks.Deck;
import Flashcards.FlashcardData;
import Flashcards.Flashcard;

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
     * Constructs a new StudySession with the given deck, name, and cardShuffler
     * @param deck The deck that this StudySession will be based upon
     * @param name The string name of this StudySession
     * @param cardShuffler The CardShuffler that this StudySession will use to get Flashcards
     */
    public StudySession(Deck deck, String name, CardShuffler cardShuffler) {
        this.deck = deck;
        this.name = name;
        this.cardShuffler = cardShuffler;
    }

    /**
     * Constructs a StudySession with given deck and cardShuffler
     * @param deck The deck that this StudySession will be based upon
     * @param cardShuffler The CardShuffler that this StudySession will use to get Flashcards
     */
    public StudySession(Deck deck, CardShuffler cardShuffler) {
        this.deck = deck;
        this.name = "Untitled";
        this.cardShuffler = cardShuffler;
    }

    /**
     * Returns this StudySession's CardShuffler.
     * @return This StudySession's CardShuffler
     */
    public CardShuffler getCardShuffler() {
        return this.cardShuffler;
    }

     /**
     * Returns this StudySession's CardShuffler's flashcardToData mapping.
     * @return This StudySession's CardShuffler's flashcardToData mapping.
     */
    public Map<Flashcard, FlashcardData> getFlashcardToData(){
        return this.cardShuffler.getFlashcardToData();
    }
    
    /**
     * Returns the next card given by this StudySession's CardShuffler.
     * @return A Flashcard chosen by this StudySession's CardShuffler
     */
    public Flashcard getNextCard() {
        return cardShuffler.returnChosenFlashcard();
    }

    /**
     * Updates this StudySession's CardShuffler to make it up-to-date with any changes to its FlashcardToData mapping.
     */
    public void updateDeckContext() {
        this.cardShuffler.updateDeckContext();
    }
}