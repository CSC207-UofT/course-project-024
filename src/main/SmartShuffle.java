import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SmartShuffle implements CardShuffler {

    private LinkedList<Flashcard> linkedListSeen;
    private LinkedList<Flashcard> linkedListUnseen;
    private LinkedList<Flashcard> linkedListDeck;
    private Map<Flashcard, FlashcardData> flashcardData;

    public SmartShuffle(Map<Flashcard, FlashcardData> flashcardData) {
        this.flashcardData = flashcardData;
        // This turns an ImmutableList, which ToList returns, to a LinkedList.
        this.linkedListDeck = new LinkedList<Flashcard>(this.flashcardData.keySet().stream().toList());
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

    @Override
    public Flashcard returnChosenFlashcard() {
        // We need to choose between a review card or a new card
        shuffleCards();

        // Step 1: Choose between review card or new card
        // Give a review card if the cumulative cardsUntilSeen is too small

        int cardsUntilSeenSum = 0;

        // Only check the first 20 cards, to see if there are enough low-CUD cards up next
        for (int i = 0; i < Math.min(this.linkedListSeen.size(), 20); i++) {
            Flashcard flashcard = this.linkedListSeen.get(i);
            cardsUntilSeenSum += this.flashcardData.get(flashcard).getCardsUntilDue();
        }

        if (cardsUntilSeenSum > this.linkedListSeen.size() / 2 || this.linkedListSeen.isEmpty()) {
            return getNewCard();
        } else {
            return getReviewCard();
        }
    }

    private Flashcard getReviewCard() {
        return this.linkedListSeen.getFirst();
    }

    private Flashcard getNewCard() {
        return this.linkedListUnseen.getFirst();
    }

    public void updateReviewCardList(Flashcard currentFlashcard) {
        insertWithOrder();
    }


    public LinkedList<Flashcard> getLinkedListDeck() {
        return this.linkedListDeck;
    }

    public void insertWithOrder(LinkedList<Flashcard> lst, Flashcard flashcard) {
        int cardsUntilDue = this.flashcardData.get(flashcard).getCardsUntilDue();
        for (int j = 0; j < lst.size(); j++) {
            int sortedFlashcardCardsUntilDue = this.flashcardData.get(flashcard).getCardsUntilDue();
            if (cardsUntilDue <= sortedFlashcardCardsUntilDue) {
                lst.add(j, flashcard);
                break;
            } else if (j == lst.size() - 1) {
                lst.addLast(flashcard);
                break;
            }
        }
    }

    public void changeNewCardToSeenCard(Flashcard newFlashcard) {
        insertWithOrder(this.linkedListSeen, newFlashcard);
    }

}
