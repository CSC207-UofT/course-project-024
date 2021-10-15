import java.util.ArrayList;
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
        this.deck = deck;
        for (Flashcard card : this.deck.flashcards) {
            this.proficiencies.put(card, 0);
        }
    }

    /**
     * Randomly pick a new card from this PracticeSession's deck to show to the user.
     * @return Return a randomly chosen Flashcard from this PracticeSession's deck.
     */
    public Flashcard getNextCard() throws Exception {
        Random random = new Random();
        ArrayList<Flashcard> flashcard_lst = this.deck.flashcards;
        if (flashcard_lst.size() == 0) {
            throw new Exception("Attempted to get next card from an empty deck");
        } else {
            int rand_index = random.nextInt(this.deck.flashcards.size());
            return flashcard_lst.get(rand_index);
        }
    }

}
