package Sessions;

import Decks.Deck;
import Flashcards.FlashcardData;
import Flashcards.Flashcard;

import java.util.Map;

/**
 * A strategy on how to shuffle and select cards. BasicShuffle's strategy is to order deck by ascending proficiency of the user
 */
public class WorstToBestShuffle extends CardShuffler {

    Deck deckCopy;
    Map<Flashcard, FlashcardData> proficiencies;

    /**
     * Shuffle cards from worst to best proficiency.
     */
    public void shuffleCards(){
        for (int i = 0; i < deckCopy.getFlashcards().size(); i++){
            Flashcard cardOne = deckCopy.getFlashcards().get(i);
            int j = i;
            while (j > 0 && proficiencies.get(deckCopy.getFlashcards().get(j - 1)).getProficiency()
                    > proficiencies.get(cardOne).getProficiency()){
                deckCopy.getFlashcards().remove(j);
                deckCopy.getFlashcards().add(j, deckCopy.getFlashcards().get(j - 1));
                j = j - 1;
            }
            deckCopy.getFlashcards().remove(j);
            deckCopy.getFlashcards().add(j, cardOne);
        }
    }

    /**
     * Set this shuffler's deckCopy to deck.
     * @param deck The deck that will be copied to this WorstToBestShuffler's copyDeck
     */
    public void setDeck(Deck deck){
        this.deckCopy = deck;
    }

    /**
     * Get this shuffler's deckCopy
     * @return deck
     */
    public Deck getDeckCopy() {
        return deckCopy;
    }

    /**
     * Return the chosen flashcard of this card shuffle algorithm.
     * @return A Flashcard (the chosen one)
     */
    @Override
    public Flashcard returnChosenFlashcard() {
        return deckCopy.getFlashcards().get(0);
    }

    /**
     * Update this card shuffler's deckCopy after its original deck has changed.
     */
    @Override
    public void updateDeckContext() {
        for (Flashcard flashcard : this.flashcardToData.keySet()) {
            if (!this.deckCopy.getFlashcards().contains(flashcard)) {
                this.deckCopy.getFlashcards().add(flashcard);
            }
        }

        this.deckCopy.getFlashcards().removeIf(card -> !this.flashcardToData.containsKey(card));

    }
}
