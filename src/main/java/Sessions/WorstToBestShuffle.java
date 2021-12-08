package Sessions;

import Decks.Deck;
import Flashcards.FlashcardData;
import Flashcards.Flashcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A strategy on how to shuffle and select cards. WorsttoBestShuffle's strategy is to order deck by ascending proficiency of the user
 */
public class WorstToBestShuffle extends CardShuffler implements UpdatingShuffler {

    List<Flashcard> deckCopy;
    List<Flashcard> originalDeck;
    private Flashcard lastFlashcardShown;
    private final Map<Flashcard, FlashcardData> flashcardToData;

    /**
     * Construct a new WorstToBestShuffle shuffler.
     * @param deck The Deck that the flashcardToData mapping will be constructed from, with default values.
     */
    public WorstToBestShuffle(Deck deck) {
        this.flashcardToData = new HashMap<>();
        for (Flashcard card : deck.getFlashcards()) {
            this.flashcardToData.put(card, new FlashcardData(0));
        }
        this.originalDeck = new ArrayList<>(this.flashcardToData.keySet().stream().toList());
        this.deckCopy = new ArrayList<>(originalDeck);
    }


    /**
     * Constructs a new WorstToBestShuffle shuffler using a preexisting map.
     *
     * @param flashcardToData    A mapping from Flashcard to FlashcardData taken from this shuffler's StudySession.
     * @param deckCopy           A list of flashcards which were copied from the original deck
     * @param lastFlashcardShown The last flashcard shown to the user
     */
    public WorstToBestShuffle(Map<Flashcard, FlashcardData> flashcardToData, List<Flashcard> deckCopy,
                        Flashcard lastFlashcardShown) {
        this.flashcardToData = flashcardToData;
        this.deckCopy = new ArrayList<>(deckCopy);
        this.lastFlashcardShown = lastFlashcardShown;
        this.deckCopy = new ArrayList<>(originalDeck);
    }

    /**
     * Shuffle cards from worst to best proficiency.
     */
    public void shuffleCards(){
        for (int i = 0; i < deckCopy.size(); i++){
            Flashcard cardOne = deckCopy.get(i);
            int j = i;
            while (j > 0 && flashcardToData.get(deckCopy.get(j - 1)).getProficiency()
                    > flashcardToData.get(cardOne).getProficiency()){
                deckCopy.remove(j);
                deckCopy.add(j, deckCopy.get(j - 1));
                j = j - 1;
            }
            deckCopy.remove(j);
            deckCopy.add(j, cardOne);
        }
    }


    /**
     * Get this shuffler's deckCopy
     * @return deck
     */
    public List<Flashcard> getDeckCopy() {
        return deckCopy;
    }

    /**
     * Get this shuffler's flashcardToData
     * @return
     */
    public Map<Flashcard, FlashcardData> getFlashcardToData() {
        return flashcardToData;
    }

    /**
     * Return the chosen flashcard of this card shuffle algorithm.
     * @return A Flashcard (the chosen one)
     */
    @Override
    public Flashcard returnChosenFlashcard() {
        this.shuffleCards();
        this.lastFlashcardShown = this.deckCopy.get(0);
        return deckCopy.get(0);
    }

    /**
     * Update this card shuffler's deckCopy after its original deck has changed.
     */
    @Override
    public void update() {
        this.updateFlashcardToData(originalDeck);
        for (Flashcard flashcard : this.flashcardToData.keySet()) {
            if (!this.deckCopy.contains(flashcard)) {
                this.deckCopy.add(flashcard);
            }
        }

        this.deckCopy.removeIf(card -> !this.flashcardToData.containsKey(card));

    }

    /**
     * get Last flashcardshown
     * @return Flashcard
     */
    public Flashcard getLastFlashcardShown() {
        return lastFlashcardShown;
    }

    /**
     * set Last flashcardshown
     */
    public void setLastFlashcardShown(Flashcard flashcard) {
        this.lastFlashcardShown = flashcard;
    }

    /**
     * Performs any post-answer updates that are needed for studySession.
     * @param wasCorrect Whether the user had correctly answered the current Flashcard
     */
    @Override
    public void postAnswerFlashcardDataUpdate(boolean wasCorrect) {
        this.updateFlashcardData(wasCorrect);
    }

    /**
     * Updates this shuffler's FlashcardData after an answer is graded.
     *
     * @param wasCorrect Whether the user was correct or not when reviewing this.lastFlashcardShown
     */
    @Override
    public void updateFlashcardData(boolean wasCorrect) {
        FlashcardData currFlashcardData = this.flashcardToData.get(this.lastFlashcardShown);
        if (wasCorrect) {
            currFlashcardData.addProficiency();
        }
        else {
            currFlashcardData.removeProficiency();
        }
    }

}
