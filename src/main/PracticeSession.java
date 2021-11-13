import java.util.HashMap;

public class PracticeSession extends StudySession {
    /**
     * Construct a PracticeSession instance that initializes all proficiencies to 0.
     * @param deck The deck that this session will use to show cards to the user.
     */
    public PracticeSession(Deck deck) {
        super(deck);
        this.flashcardToData = new HashMap<>();
        this.deck = deck;
        for (Flashcard card : this.deck.getFlashcards()) {
            this.flashcardToData.put(card, new FlashcardData(0));
        }
        this.cardshuffler = new BasicShuffle(this.flashcardToData);
    }

    @Override
    public Flashcard getNextCard() {
        return this.cardshuffler.returnChosenFlashcard();
    }


}
