import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SmartShuffle implements CardShuffler {

    private LinkedList<Flashcard> linkedListDeck;
    private Map<Flashcard, FlashcardData> flashcardData;

    public SmartShuffle(Map<Flashcard, FlashcardData> flashcardData) {
        this.flashcardData = flashcardData;
        // This turns an ImmutableList, which ToList returns, to a LinkedList.
        this.linkedListDeck = new LinkedList<Flashcard>(this.flashcardData.keySet().stream().toList());
    }

    /**
     *
     */
    public void shuffleCards() {
        returnChosenFlashcard();
    }

    @Override
    public Flashcard returnChosenFlashcard() {
        return this.linkedListDeck.getFirst();
    }

    @Override
    public void updateCardShuffler() {
        for (Flashcard flashcard : this.flashcardData.keySet()) {
            if (!this.linkedListDeck.contains(flashcard)) {
                this.linkedListDeck.addLast(flashcard);
            }
        }

        for (Flashcard card : this.linkedListDeck) {
            if (!this.flashcardData.containsKey(card)) {
                this.linkedListDeck.remove(card);
            }
        }
    }


    public LinkedList<Flashcard> getLinkedListDeck() {
        return this.linkedListDeck;
    }

    public void updateLinkedListDeck(Flashcard currentFlashcard) {
        int stepsToSendBackward = this.flashcardData.get(currentFlashcard).getCardsUntilDue();
        // System.out.println(stepsToSendBackward);
        int currIndex = -1;
        for (int i = 0; i < this.linkedListDeck.size(); i++) {
            if (this.linkedListDeck.get(i) == currentFlashcard) {
                currIndex = i;
                break;
            }
        }

        if (currIndex + stepsToSendBackward >= this.linkedListDeck.size()) {
            // send currentFlashcard to the back
            this.linkedListDeck.addLast(currentFlashcard);
            this.linkedListDeck.remove(currIndex);
            // System.out.println("Sent to the back");
        } else {
            // send currentFlashcard stepsToSendBackward number of indices
            this.linkedListDeck.remove(currIndex);
            this.linkedListDeck.add(currIndex + stepsToSendBackward, currentFlashcard);
            // System.out.println("Sent " + stepsToSendBackward + " steps backward.");
        }

    }
}
