import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class WorstToBestTest {
    WorstToBestShuffle shuffler;
    Deck deck;
    Map<Flashcard, FlashcardData> map;

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

        FlashcardData data1 = new FlashcardData(3);
        data1.incrementProficiency(4);
        map.put(flashcard1, data1);

        FlashcardData data2 = new FlashcardData(3);
        data1.incrementProficiency(1);
        map.put(flashcard2, data2);

        FlashcardData data3 = new FlashcardData(3);
        data1.incrementProficiency(5);
        map.put(flashcard3, data3);

        FlashcardData data4 = new FlashcardData(3);
        data1.incrementProficiency(3);
        map.put(flashcard4, data4);

        FlashcardData data5 = new FlashcardData(3);
        data1.incrementProficiency(2);
        map.put(flashcard5, data5);

        shuffler.setDeck(deck);
        this.deck = deck;
    }

    /**
     * Test if after shuffle, the deck is the same size
     */
    @Test
    void size() {
        shuffler.shuffleCards();
        assertEquals(5, shuffler.deckCopy.getFlashcards().size());
    }

    /**
     * Test that the cards are in proper order after shuffling
     */
    @Test
    void order() {
        shuffler.shuffleCards();
        assertTrue(shuffler.deckCopy.getFlashcards().get(0).equals(deck.getFlashcards().get(1)) ||
                shuffler.deckCopy.getFlashcards().get(1).equals(deck.getFlashcards().get(4)) ||
                shuffler.deckCopy.getFlashcards().get(2).equals(deck.getFlashcards().get(3)) ||
                shuffler.deckCopy.getFlashcards().get(3).equals(deck.getFlashcards().get(0)) ||
                shuffler.deckCopy.getFlashcards().get(4).equals(deck.getFlashcards().get(2)));
    }

    /**
     * Test returnchosenflashcard actually goes through the entire deck before reshuffling
     */
    @Test
    void returnflashcard() {
        Flashcard.Front front1 = new Flashcard.Front("!!!", null);
        Flashcard flashcard1 = new Flashcard(front1, "???");
        shuffler.updateDeckContext();
        shuffler.shuffleCards();
        Flashcard card1 = shuffler.returnChosenFlashcard();
        assertTrue(card1.equals(flashcard1));
    }

    /**
     * Test update deckcontext when flash card is added
     */
    @Test
    void deckcontextadd() {
        Flashcard.Front front = new Flashcard.Front("<3", null);
        Flashcard card = new Flashcard(front, ":)");
        deck.addFlashcard(card);
        shuffler.updateDeckContext();
        assertTrue(shuffler.deckCopy.getFlashcards().contains(card));
    }

    /**
     * Test update deckcontext when flash card is deleted
     */
    @Test
    void deckcontextdelete() {
        Flashcard.Front front = new Flashcard.Front("<3", null);
        Flashcard card = new Flashcard(front, ":)");
        deck.addFlashcard(card);
        deck.removeFlashcard(card);
        shuffler.updateDeckContext();
        assertFalse(shuffler.deckCopy.getFlashcards().contains(card));
    }
}
