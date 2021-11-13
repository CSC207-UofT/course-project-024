import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class BasicShuffle implements CardShuffler {

    int index = 0;
    ArrayList<Flashcard> deckCopy;
    private final Map<Flashcard, FlashcardData> flashcardToData;

    /**
     * Construct a new BasicShuffle card shuffler.
     * @param flashcardToData A mapping from Flashcard to FlashcardData taken from this shuffler's StudySession.
     */
    public BasicShuffle(Map<Flashcard, FlashcardData> flashcardToData) {
        // This turns an ImmutableList, which ToList returns, to an ArrayList. Don't ask me why.
        this.deckCopy = new ArrayList<>(flashcardToData.keySet().stream().toList());
        this.flashcardToData = flashcardToData;
    }

    public void shuffleCards() {
        Collections.shuffle(this.deckCopy);
    }

    /**
     * Returns this shuffler's chosen flashcard based on this shuffler's choosing algorithm.
     * @return The flashcard that this shuffler chooses based on an algorithm.
     */
    @Override
    public Flashcard returnChosenFlashcard() {
        Flashcard chosenFlashcard;
        if (index == 0) {
            // System.out.println("First run!");
            shuffleCards();
            chosenFlashcard = this.deckCopy.get(index);
            index += 1;
        } else if (index < this.deckCopy.size() - 1) {
            // System.out.println("Removing first and adding to last");
            chosenFlashcard = this.deckCopy.get(index);
            index += 1;
        } else { // index == flashcardData.size() - 1
            // System.out.println("Reached end, shuffling!");
            chosenFlashcard = this.deckCopy.get(index);
            index = 0;

        }
        return chosenFlashcard;

    }

    /**
     * Updates this card shuffler to make it up-to-date with any changes to its flashcardToData mapping.
     */
    @Override
    public void updateCardShuffler() {
        for (Flashcard flashcard : this.flashcardToData.keySet()) {
            if (!this.deckCopy.contains(flashcard)) {
                this.deckCopy.add(flashcard);
            }
        }

        for (Flashcard card : this.deckCopy) {
            if (!this.flashcardToData.containsKey(card)) {
                this.deckCopy.remove(card);
            }
        }
    }
}
