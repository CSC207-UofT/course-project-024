import java.util.*;

/**
 * A strategy on how to shuffle and select cards. BasicShuffle's strategy is randomization
 */
public class BasicShuffle extends CardShuffler {

    int index = 0;
    ArrayList<Flashcard> deckCopy;

    /**
     * Construct a new BasicShuffle card shuffler.
     * @param flashcardToData A mapping from Flashcard to FlashcardData taken from this shuffler's StudySession.
     */
    public BasicShuffle(Map<Flashcard, FlashcardData> flashcardToData) {
        // This turns an ImmutableList, which ToList returns, to an ArrayList. Don't ask me why.
        this.deckCopy = new ArrayList<>(flashcardToData.keySet().stream().toList());
        this.flashcardToData = flashcardToData;
    }

    /**
     * Construct a new BasicShuffle shuffler.
     * @param deck The Deck that the flashcardToData mapping will be constructed from, with default values.
     */
    public BasicShuffle(Deck deck) {
        this.flashcardToData = new HashMap<>();
        for (Flashcard card : deck.getFlashcards()) {
            this.flashcardToData.put(card, new FlashcardData(0));
        }
        this.deckCopy = new ArrayList<>(this.flashcardToData.keySet().stream().toList());
    }

    /**
     * Randomly shuffles this shuffler's deckCopy to a random order.
     */
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
            shuffleCards();
            chosenFlashcard = this.deckCopy.get(index);
            index += 1;
        } else if (index < this.deckCopy.size() - 1) {
            chosenFlashcard = this.deckCopy.get(index);
            index += 1;
        } else { // index == flashcardData.size() - 1
            chosenFlashcard = this.deckCopy.get(index);
            index = 0;

        }
        return chosenFlashcard;

    }

    /**
     * Updates this card shuffler to make it up-to-date with any changes to its flashcardToData mapping.
     */
    @Override
    public void updateDeckContext() {
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
