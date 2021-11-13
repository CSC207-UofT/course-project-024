import java.util.Map;

public class WorstToBestShuffle implements GetNextCard {
    @Override
    public Flashcard getNextCard(Deck deck, Map<Flashcard, Integer> proficiencies) {
        Deck newDeck = deck.copyDeck();
        for (int i = 0; i < newDeck.getFlashcards().size(); i++){
            Flashcard cardOne = newDeck.getFlashcards().get(i);
            int j = i;
            while (j > 0 && proficiencies.get(newDeck.getFlashcards().get(j - 1)) > proficiencies.get(cardOne)){
                newDeck.getFlashcards().remove(j);
                newDeck.getFlashcards().add(j, newDeck.getFlashcards().get(j - 1));
                j = j - 1;
            }
            newDeck.getFlashcards().remove(j);
            newDeck.getFlashcards().add(j, cardOne);

        }
        Flashcard card = newDeck.getFlashcards().get(0);
        return card;
    }
}
