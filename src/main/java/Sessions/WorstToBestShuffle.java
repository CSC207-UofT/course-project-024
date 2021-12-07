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
    private Flashcard lastFlashcardShown;

    public WorstToBestShuffle(Map<Flashcard, FlashcardData> flashcardToData) {
        this.deckCopy = new ArrayList<>(flashcardToData.keySet().stream().toList());
        this.flashcardToData = flashcardToData;
    }

    /**
     * Construct a new WorstToBestShuffle shuffler.
     * @param deck The Deck that the flashcardToData mapping will be constructed from, with default values.
     */
    public WorstToBestShuffle(Deck deck) {
        this.flashcardToData = new HashMap<>();
        for (Flashcard card : deck.getFlashcards()) {
            this.flashcardToData.put(card, new FlashcardData(0));
        }
        this.deckCopy = new ArrayList<>(this.flashcardToData.keySet().stream().toList());
    }

    /**
     * Construct a new WorstToBestShuffle shuffler.
     * @param flashcardToData A mapping from Flashcard to FlashcardData taken from this shuffler's StudySession.
     * @param deckCopy A list of flashcards which were copied from the original deck
     */
    public WorstToBestShuffle(Map<Flashcard, FlashcardData> flashcardToData, List<Flashcard> deckCopy) {
        this.flashcardToData = flashcardToData;
        this.deckCopy = new ArrayList<>(deckCopy);
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
        this.deckCopy = deckCopy;
        this.lastFlashcardShown = lastFlashcardShown;
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
        for (Flashcard flashcard : this.flashcardToData.keySet()) {
            if (!this.deckCopy.contains(flashcard)) {
                this.deckCopy.add(flashcard);
            }
        }

        this.deckCopy.removeIf(card -> !this.flashcardToData.containsKey(card));

    }

    public Flashcard getLastFlashcardShown() {
        return lastFlashcardShown;
    }

    @Override
    public void postAnswerFlashcardDataUpdate(boolean wasCorrect) {
        this.updateFlashcardData(wasCorrect);
    }

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
