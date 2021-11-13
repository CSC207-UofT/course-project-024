import java.util.LinkedList;
import java.util.Map;

public class SmartShuffle implements CardShuffler {

    private final LinkedList<Flashcard> deckCopy;
    private final Map<Flashcard, FlashcardData> flashcardToData;

    /**
     * Constructs a new SmartShuffle shuffler.
     * @param flashcardToData A mapping from Flashcard to FlashcardData taken from this shuffler's StudySession.
     */
    public SmartShuffle(Map<Flashcard, FlashcardData> flashcardToData) {
        this.flashcardToData = flashcardToData;
        // This turns an ImmutableList, which ToList returns, to a LinkedList.
        this.deckCopy = new LinkedList<>(this.flashcardToData.keySet().stream().toList());
    }

    /**
     * Returns this shuffler's chosen flashcard based on this shuffler's choosing algorithm.
     * @return The flashcard that this shuffler chooses based on an algorithm.
     */
    @Override
    public Flashcard returnChosenFlashcard() {
        return this.deckCopy.getFirst();
    }

    /**
     * Updates this card shuffler to make it up-to-date with any changes to its flashcardToData mapping.
     */
    @Override
    public void updateCardShuffler() {
        for (Flashcard flashcard : this.flashcardToData.keySet()) {
            if (!this.deckCopy.contains(flashcard)) {
                this.deckCopy.addLast(flashcard);
            }
        }

        for (Flashcard card : this.deckCopy) {
            if (!this.flashcardToData.containsKey(card)) {
                this.deckCopy.remove(card);
            }
        }
    }


    public LinkedList<Flashcard> getDeckCopy() {
        return this.deckCopy;
    }

    /**
     * Updates this shuffler's deckCopy to order it after the FlashcardData objects are updated in this.flashcardToData.
     * After this method is called, this shuffler's returnChosenFlashcard method is ready to be called again.
     * @param currentFlashcard The Flashcard that was previously returned by this.returnChosenFlashcard.
     */
    public void updateDeckCopyOrder(Flashcard currentFlashcard) {
        int stepsToSendBackward = this.flashcardToData.get(currentFlashcard).getCardsUntilDue();
        // System.out.println(stepsToSendBackward);
        int currFlashcardIndex = -1;
        for (int i = 0; i < this.deckCopy.size(); i++) {
            if (this.deckCopy.get(i) == currentFlashcard) {
                currFlashcardIndex = i;
                break;
            }
        }

        if (currFlashcardIndex + stepsToSendBackward >= this.deckCopy.size()) {
            // send currentFlashcard to the back
            this.deckCopy.addLast(currentFlashcard);
            this.deckCopy.remove(currFlashcardIndex);
            // System.out.println("Sent to the back");
        } else {
            // send currentFlashcard stepsToSendBackward number of indices
            this.deckCopy.remove(currFlashcardIndex);
            this.deckCopy.add(currFlashcardIndex + stepsToSendBackward, currentFlashcard);
            // System.out.println("Sent " + stepsToSendBackward + " steps backward.");
        }
    }
}
