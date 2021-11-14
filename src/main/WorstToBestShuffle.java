import java.util.ArrayList;
import java.util.Map;

/**
 * A class that represents a strategy on how to shuffle cards and retrieve the next card. WorstToBestShuffle orders cards by the user's proficiency (ascending).
 */
public class WorstToBestShuffle implements CardShuffler {

    Deck deckCopy;
    Map<Flashcard, FlashcardData> proficiencies;

    /**
     * sets the deck to be shuffled
     * @param deck
     */
    public void setDeck(Deck deck){
        this.deckCopy = deck.copyDeck();
    }

    /**
     * shuffles cards in the deck
     */
    @Override
    public void shuffleCards(){
        for (int i = 0; i < deckCopy.getFlashcards().size(); i++){
            Flashcard cardOne = deckCopy.getFlashcards().get(i);
            int j = i;
            while (j > 0 && proficiencies.get(deckCopy.getFlashcards().get(j - 1)).getProficiency() > proficiencies.get(cardOne).getProficiency()){
                deckCopy.getFlashcards().remove(j);
                deckCopy.getFlashcards().add(j, deckCopy.getFlashcards().get(j - 1));
                j = j - 1;
            }
            deckCopy.getFlashcards().remove(j);
            deckCopy.getFlashcards().add(j, cardOne);
        }

    }

    /**
     * returns a flashcard to present to the user based on a card shuffle strategy
     * @return Flashcard
     */
    @Override
    public Flashcard returnChosenFlashcard() {
        shuffleCards();
        return deckCopy.getFlashcards().get(0);
    }
}
