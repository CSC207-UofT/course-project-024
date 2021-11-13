import java.util.LinkedList;
import java.util.Map;

public class WorstToBestShuffle implements CardShuffler {

    private LinkedList<Flashcard> linkedListDeck;
    private Map<Flashcard, FlashcardData> flashcardData;


    public WorstToBestShuffle(Map<Flashcard, FlashcardData> flashcardData) {
        // This turns an ImmutableList, which ToList returns, to a LinkedList.
        this.linkedListDeck = new LinkedList<Flashcard>(flashcardData.keySet().stream().toList());
    }

    /**
     * Sort the cards by cards until due in ascending order.
     */
    public void shuffleCards() {
        LinkedList<Flashcard> sortedLinkedList = new LinkedList<Flashcard>();
        for (Flashcard flashcard : this.linkedListDeck) {
            // Note: flashcard is the flashcard we want to insert sort
            int cardsUntilDue = this.flashcardData.get(flashcard).getCardsUntilDue();
            if (sortedLinkedList.isEmpty()) {
                sortedLinkedList.addFirst(flashcard);
            } else {
                for (int j = 0; j < sortedLinkedList.size(); j++) {
                    // sortedListFlashcard is the flashcard we are comparing flashcard to.
                    // Our goal is to insert flashcard right behind sortedListFlashcard if
                    // we find a sortedListFlashcard that is larger
                    Flashcard sortedListFlashcard = sortedLinkedList.get(j);
                    int sortedFlashcardCardsUntilDue = this.flashcardData.get(sortedListFlashcard).getCardsUntilDue();
                    if (cardsUntilDue <= sortedFlashcardCardsUntilDue) {
                        sortedLinkedList.add(j, flashcard);
                        break;
                    } else if (j == sortedLinkedList.size() - 1) {
                        sortedLinkedList.addLast(flashcard);
                        break;
                    }
                }
            }
        }
        this.linkedListDeck = sortedLinkedList;
    }

    // TODO: Implement
    @Override
    public Flashcard returnChosenFlashcard() {
        return null;
    }

    //TODO: Implement
    @Override
    public void updateCardShuffler() {

    }


}
