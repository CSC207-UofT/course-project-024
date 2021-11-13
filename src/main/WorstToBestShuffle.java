import java.util.ArrayList;
import java.util.Map;

public class WorstToBestShuffle implements CardShuffler {

    int index = 0;
    Deck deckCopy;
    Map<Flashcard, Integer> proficiencies;

    public void setDeck(Deck deck){
        this.deck = deck.copyDeck();
    }
    @Override
    public void shuffleCards(){
        for (int i = 0; i < deckCopy.getFlashcards().size(); i++){
            Flashcard cardOne = deckCopy.getFlashcards().get(i);
            int j = i;
            while (j > 0 && proficiencies.get(deckCopy.getFlashcards().get(j - 1)) > proficiencies.get(cardOne)){
                deckCopy.getFlashcards().remove(j);
                deckCopy.getFlashcards().add(j, deckCopy.getFlashcards().get(j - 1));
                j = j - 1;
            }
            deckCopy.getFlashcards().remove(j);
            deckCopy.getFlashcards().add(j, cardOne);
        }

    }
    @Override
    public Flashcard returnChosenFlashcard() {
        Flashcard card = deckCopy.getFlashcards().get(0);
        return card;
    }
}
