import Decks.Deck;
import Flashcards.Flashcard;
import Sessions.SmartShuffle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartShuffleTest {
    SmartShuffle cardShuffler;
    Deck deck;

    @BeforeEach
    void setUp() {
        this.deck = new Deck("deck1");
//
        Flashcard flashcard = new Flashcard(new Flashcard.Front("1", null), "1");
        Flashcard flashcard2 = new Flashcard(new Flashcard.Front("2", null), "2");
        Flashcard flashcard3 = new Flashcard(new Flashcard.Front("3", null), "3");
        Flashcard flashcard4 = new Flashcard(new Flashcard.Front("4", null), "4");
        Flashcard flashcard5 = new Flashcard(new Flashcard.Front("5", null), "5");


        this.deck.getFlashcards().add(flashcard);
        this.deck.getFlashcards().add(flashcard2);
        this.deck.getFlashcards().add(flashcard3);
        this.deck.getFlashcards().add(flashcard4);
        this.deck.getFlashcards().add(flashcard5);
//
        this.cardShuffler = new SmartShuffle(this.deck);
    }

    @Test
    void returnChosenFlashcard() {

        SmartShuffle smartShuffle = new SmartShuffle(this.deck);

        Flashcard actual = smartShuffle.returnChosenFlashcard();

        Flashcard expected = smartShuffle.getDeckCopy().get(0);

        assertEquals(expected, actual);

    }

    @Test
    void update() {
        Flashcard flashcardAdded = this.cardShuffler.addToFlashcardData();

        this.cardShuffler.update();

        assertTrue(this.cardShuffler.getFlashcardToData().containsKey(flashcardAdded));

        Flashcard flashcardRemoved = this.cardShuffler.removeFromFlashcardData();

        assertFalse(this.cardShuffler.getFlashcardToData().containsKey(flashcardRemoved));

    }

    @Test
    void postAnswerFlashcardDataUpdate() {
        Flashcard testFlashcard = this.cardShuffler.getDeckCopy().get(0);

        this.cardShuffler.setLastFlashcardShown(testFlashcard);

        this.cardShuffler.postAnswerFlashcardDataUpdate(true);

        int previousValue = this.cardShuffler.getFlashcardToData().get(testFlashcard).getCardsUntilDue();

        assertTrue(this.cardShuffler.getFlashcardToData().get(testFlashcard).getCardsUntilDue() > 0);

        this.cardShuffler.postAnswerFlashcardDataUpdate(false);

        assertTrue(this.cardShuffler.getFlashcardToData().get(testFlashcard).getCardsUntilDue() > 0
                && this.cardShuffler.getFlashcardToData().get(testFlashcard).getCardsUntilDue()
                != previousValue);
    }

    @Test
    void updateFlashcardData() {

        Flashcard testFlashcard = this.cardShuffler.getDeckCopy().get(0);

        this.cardShuffler.setLastFlashcardShown(testFlashcard);

        this.cardShuffler.updateFlashcardData(true);

        int previousValue = this.cardShuffler.getFlashcardToData().get(testFlashcard).getCardsUntilDue();

        assertEquals(1, previousValue);

        assertEquals(1, this.cardShuffler.getFlashcardToData().get(testFlashcard).getCardsUntilDue());

        int previousValue2 = this.cardShuffler.getFlashcardToData().get(testFlashcard).getCardsUntilDue();

        this.cardShuffler.updateFlashcardData(false);



        assertEquals(previousValue2 + 2, this.cardShuffler.getFlashcardToData().get(testFlashcard).getCardsUntilDue());
    }
}