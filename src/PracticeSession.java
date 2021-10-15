import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PracticeSession implements StudySession {
    private Deck deck;
    private Map<Flashcard, Integer> proficiencies;

    /**
     * Construct a PracticeSession instance that initializes all proficiencies to 0.
     * @param deck The deck that this session will use to show cards to the user.
     */
    public PracticeSession(Deck deck) {
        this.proficiencies = new HashMap<Flashcard, Integer>();
        this.deck = deck;
        for (Flashcard card : this.deck.flashcards) {
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
        ArrayList<Flashcard> flashcard_lst = this.deck.flashcards;

        // nextInt takes an exclusive right bound, so rand_index takes from [0, flashcards.size() - 1]
        int rand_index = random.nextInt(this.deck.flashcards.size());
        return flashcard_lst.get(rand_index);
    }

}
