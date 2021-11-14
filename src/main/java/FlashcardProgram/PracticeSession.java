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
        this.flashcardData = new HashMap<>();
        this.deck = deck;
        for (Flashcard card : this.deck.getFlashcards()) {
            this.flashcardData.put(card, new FlashcardData(0));
        }
        this.cardshuffler = new BasicShuffle(this.flashcardData);
    }

    @Override
    public Flashcard getNextCard() {
        return this.cardshuffler.returnChosenFlashcard();
    }


}
