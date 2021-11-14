import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * A strategy on how to shuffle and select cards. SmartShuffle's strategy //TODO
 */
public class SmartShuffle extends CardShuffler implements UpdatingShuffler {

    private final LinkedList<Flashcard> deckCopy;
    private Flashcard lastFlashcardShown;

    /**
     * Constructs a new SmartShuffle shuffler using a preexisting map.
     * @param flashcardToData A mapping from Flashcard to FlashcardData taken from this shuffler's StudySession.
     */
    public SmartShuffle(Map<Flashcard, FlashcardData> flashcardToData) {
        this.flashcardToData = flashcardToData;
        // This turns an ImmutableList, which ToList returns, to a LinkedList.
        this.deckCopy = new LinkedList<>(this.flashcardToData.keySet().stream().toList());
    }

    /**
     * Constructs a new SmartShuffle shuffler, initializing an empty mapping using deck.
     * @param deck The deck that this shuffler will use to create a Flashcard to FlashcardData mapping.
     */
    public SmartShuffle(Deck deck) {
        this.flashcardToData = new HashMap<>();

        for (Flashcard card : deck.getFlashcards()) {
            this.flashcardToData.put(card, new FlashcardData(0));
        }

        this.deckCopy = new LinkedList<>(this.flashcardToData.keySet().stream().toList());
    }

    /**
     * Returns this shuffler's chosen flashcard based on this shuffler's choosing algorithm.
     * @return The flashcard that this shuffler chooses based on an algorithm.
     */
    @Override
    public Flashcard returnChosenFlashcard() {
        this.lastFlashcardShown = this.deckCopy.getFirst();
        return this.deckCopy.getFirst();
    }

    /**
     * Updates this card shuffler to make it up-to-date with any changes to its flashcardToData mapping.
     */
    @Override
    public void updateDeckContext() {
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
     * This method should be called after an answer is given by the user.
     * After this method is called, this shuffler's returnChosenFlashcard method is ready to be called again.
     * @param wasCorrect Whether the user was correct or not when reviewing this.lastFlashcardShown
     */
    @Override
    public void postAnswerFlashcardDataUpdate(boolean wasCorrect) {

        this.updateFlashcardData(wasCorrect);

        int stepsToSendBackward = this.flashcardToData.get(this.lastFlashcardShown).getCardsUntilDue();
        // System.out.println(stepsToSendBackward);
        int currFlashcardIndex = -1;
        for (int i = 0; i < this.deckCopy.size(); i++) {
            if (this.deckCopy.get(i) == this.lastFlashcardShown) {
                currFlashcardIndex = i;
                break;
            }
        }

        if (currFlashcardIndex + stepsToSendBackward >= this.deckCopy.size()) {
            // send currentFlashcard to the back
            this.deckCopy.addLast(this.lastFlashcardShown);
            this.deckCopy.remove(currFlashcardIndex);
            // System.out.println("Sent to the back");
        } else {
            // send currentFlashcard stepsToSendBackward number of indices
            this.deckCopy.remove(currFlashcardIndex);
            this.deckCopy.add(currFlashcardIndex + stepsToSendBackward, this.lastFlashcardShown);
            // System.out.println("Sent " + stepsToSendBackward + " steps backward.");
        }
    }


    /**
     * Updates this shuffler's FlashcardData after an answer is graded.
     * @param wasCorrect Whether the user was correct or not when reviewing this.lastFlashcardShown
     */
    public void updateFlashcardData(boolean wasCorrect) {
        for (FlashcardData data : this.flashcardToData.values()) {
            if (data.getCardsUntilDue() > 0) {
                data.setCardsUntilDue(data.getCardsUntilDue() - 1);
            }
        }

        FlashcardData currFlashcardData = this.flashcardToData.get(this.lastFlashcardShown);
        if (wasCorrect) {
            currFlashcardData.setCardsUntilDue(currFlashcardData.getCardsUntilDue() * 2 + 1);
        } else { // user got the back wrong
            currFlashcardData.setCardsUntilDue(currFlashcardData.getCardsUntilDue() + 3);
        }
    }




}
