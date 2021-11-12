import java.util.Map;

public class WorstToBestShuffle implements CardShuffler{
    @Override
    public void shuffleCards(Deck deck, Map<Flashcard, Integer> proficiencies) {
        for (int i = 0; i < deck.getFlashcards().size(); i++){
            Flashcard cardOne = deck.getFlashcards().get(i);
            while (i > 0 && proficiencies.get(deck.getFlashcards().get(i - 1)) > proficiencies.get(cardOne)){
                deck.getFlashcards().remove(i);
                deck.getFlashcards().add(i, deck.getFlashcards().get(i-1));
                i = i - 1;
            }
            deck.getFlashcards().add(i, cardOne);

        }
    }
}
