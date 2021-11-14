import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * A strategy on how to shuffle and select cards. BasicShuffle's strategy is to order deck by ascending proficiency of the user
 */
public class WorstToBestShuffle extends CardShuffler {

    int index = 0;
    Deck deckCopy;
    Map<Flashcard, FlashcardData> proficiencies;

    /**
     * Construct a new WorstToBestShuffle card shuffler.
     */
    public WorstToBestShuffle() {
    }

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
     * @param deck
     */
    public void setDeck(Deck deck){
        this.deckCopy = deck.copyDeck();
    }

    /**
     * Return the chosen flashcard of this card shuffle algorithm.
     * @return A Flashcard
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

        for (Flashcard card : this.deckCopy.getFlashcards()) {
            if (!this.flashcardToData.containsKey(card)) {
                this.deckCopy.getFlashcards().remove(card);
            }
        }

    }
}
