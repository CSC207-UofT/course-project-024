import java.util.Map;

public class WorstToBestShuffle implements GetNextCard {
    @Override
    public Flashcard getNextCard(Deck deck, Map<Flashcard, Integer> proficiencies) {
        for (int i = 0; i < deck.getFlashcards().size(); i++){
            Flashcard cardOne = deck.getFlashcards().get(i);
            int j = i;
            while (j > 0 && proficiencies.get(deck.getFlashcards().get(j - 1)) > proficiencies.get(cardOne)){
                deck.getFlashcards().remove(j);
                deck.getFlashcards().add(j, deck.getFlashcards().get(j - 1));
                j = j - 1;
            }
            deck.getFlashcards().remove(j);
            deck.getFlashcards().add(j, cardOne);

        }
        Flashcard card = deck.getFlashcards().get(0);
        return card;
    }
}
