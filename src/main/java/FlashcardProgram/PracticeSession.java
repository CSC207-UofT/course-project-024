package FlashcardProgram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PracticeSession extends StudySession {
    /**
     * Construct a PracticeSession instance that initializes all proficiencies to 0.
     * @param deck The deck that this session will use to show cards to the user.
     */
    public PracticeSession(Deck deck) {
        super(deck);
        this.proficiencies = new HashMap<>();
        this.deck = deck;
        for (Flashcard card : this.deck.getFlashcards()) {
            this.proficiencies.put(card, 0);
        }
    }

    /**
     * Randomly pick a new card from this PracticeSession's deck to show to the user.
     * Assume that this.deck.flashcards is non-empty. The empty case should be
     * handled by the SessionController before calling this method.
     * @return Return a randomly chosen Flashcard from this PracticeSession's deck.
     */
    @Override
    public Flashcard getNextCard() {
        Random random = new Random();
        // nextInt takes an exclusive right bound, so rand_index takes from [0, flashcards.size() - 1]
        int rand_index = random.nextInt(this.deck.getFlashcards().size());
        return this.deck.getFlashcards().get(rand_index);
    }

}
