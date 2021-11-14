package FlashcardProgram;

import java.util.Map;

public interface CardShuffler {
    /**
     * Shuffles cards in a deck.
     */
    void shuffleCards();

    Flashcard returnChosenFlashcard();
}
