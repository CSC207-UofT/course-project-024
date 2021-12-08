import Sessions.CardShuffler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import Flashcards.Flashcard;
import Decks.Deck;
import Flashcards.FlashcardData;
import Sessions.WorstToBestShuffle;

public class WorstToBestTest {
    WorstToBestShuffle shuffler;
    private Deck deck;

    @BeforeEach
    void setUp() {
        Flashcard.Front front1 = new Flashcard.Front("1", null);
        Flashcard flashcard1 = new Flashcard(front1, "1");
        Flashcard.Front front2 = new Flashcard.Front("2", null);
        Flashcard flashcard2 = new Flashcard(front2, "2");
        Flashcard.Front front3 = new Flashcard.Front("3", null);
        Flashcard flashcard3 = new Flashcard(front3, "3");
        Flashcard.Front front4 = new Flashcard.Front("4", null);
        Flashcard flashcard4 = new Flashcard(front4, "4");
        Flashcard.Front front5 = new Flashcard.Front("5", null);
        Flashcard flashcard5 = new Flashcard(front5, "5");
        Deck deck = new Deck("deck");
        deck.addFlashcard(flashcard1);
        deck.addFlashcard(flashcard2);
        deck.addFlashcard(flashcard3);
        deck.addFlashcard(flashcard4);
        deck.addFlashcard(flashcard5);

        shuffler = new WorstToBestShuffle(deck);

        FlashcardData data1 = new FlashcardData(4,3);
        shuffler.getFlashcardToData().put(flashcard1, data1);

        FlashcardData data2 = new FlashcardData(1,3);
        shuffler.getFlashcardToData().put(flashcard2, data2);

        FlashcardData data3 = new FlashcardData(5,3);
        shuffler.getFlashcardToData().put(flashcard3, data3);

        FlashcardData data4 = new FlashcardData(3,3);
        shuffler.getFlashcardToData().put(flashcard4, data4);

        FlashcardData data5 = new FlashcardData(2,3);
        shuffler.getFlashcardToData().put(flashcard5, data5);

        shuffler = new WorstToBestShuffle(deck);
        this.deck = deck;
    }

    /**
     * Test if after shuffle, the deck is the same size
     */
    @Test
    void size() {
        shuffler.shuffleCards();
        assertEquals(5, shuffler.getDeckCopy().size());
    }

    /**
     * Test that the cards are in proper order after shuffling
     */
    @Test
    void shuffle() {
        shuffler.shuffleCards();
        assertTrue(shuffler.getDeckCopy().get(0).equals(deck.getFlashcards().get(1)) ||
                shuffler.getDeckCopy().get(1).equals(deck.getFlashcards().get(4)) ||
                shuffler.getDeckCopy().get(2).equals(deck.getFlashcards().get(3)) ||
                shuffler.getDeckCopy().get(3).equals(deck.getFlashcards().get(0)) ||
                shuffler.getDeckCopy().get(4).equals(deck.getFlashcards().get(2)));
    }

    /**
     * Test returnchosenflashcard actually goes through the entire deck before reshuffling
     */
    @Test
    void returnFlashcard() {
        Flashcard.Front front1 = new Flashcard.Front("!!!", null);
        Flashcard flashcard1 = new Flashcard(front1, "???");
        shuffler.update();
        shuffler.shuffleCards();
        Flashcard card1 = shuffler.returnChosenFlashcard();
        assertEquals(card1, flashcard1);
    }

    /**
     * Test update when flashcard is added
     */
    @Test
    void updateAdd() {
        Flashcard.Front front = new Flashcard.Front("<3", null);
        Flashcard card = new Flashcard(front, ":)");
        deck.addFlashcard(card);
        shuffler.update();
        assertTrue(shuffler.getDeckCopy().contains(card));
    }

    /**
     * Test update when flashcard is deleted
     */
    @Test
    void updateDelete() {
        Flashcard.Front front = new Flashcard.Front("<3", null);
        Flashcard card = new Flashcard(front, ":)");
        deck.addFlashcard(card);
        deck.removeFlashcard(card);
        shuffler.update();
        assertFalse(shuffler.getDeckCopy().contains(card));
    }

    /**
     * Test update flashcard data when the card is correct
     */
    @Test
    void updateCardCorrect() {
        Flashcard.Front front = new Flashcard.Front("<3", null);
        Flashcard card = new Flashcard(front, ":)");
        deck.addFlashcard(card);
        shuffler.update();
        shuffler.setLastFlashcardShown(card);
        shuffler.postAnswerFlashcardDataUpdate(true);
        assertEquals(shuffler.getFlashcardToData().get(card).getProficiency(), 1);
    }

    /**
     * Test update flashcard data when the card is incorrect
     */
    @Test
    void updateCardIncorrect() {
        Flashcard.Front front = new Flashcard.Front("<3", null);
        Flashcard card = new Flashcard(front, ":)");
        deck.addFlashcard(card);
        shuffler.update();
        shuffler.setLastFlashcardShown(card);
        shuffler.postAnswerFlashcardDataUpdate(true);
        assertEquals(shuffler.getFlashcardToData().get(card).getProficiency(), 0);
    }
}
