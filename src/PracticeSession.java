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
    public Flashcard getNextCard() {
        Random random = new Random();
        ArrayList<Flashcard> flashcard_lst = this.deck.flashcards;
        int rand_index = random.nextInt();
        return flashcard_lst.get(rand_index);
    }

}
