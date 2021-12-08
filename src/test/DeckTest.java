import Decks.Deck;
import Flashcards.Flashcard;
import Sessions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    Deck deck;

    @BeforeEach
    void setUp() {
        Flashcard flashcard1 = new Flashcard(new Flashcard.Front("1", null), "1");
        Flashcard flashcard2 = new Flashcard(new Flashcard.Front("2", null), "2");
        Flashcard flashcard3 = new Flashcard(new Flashcard.Front("3", null), "3");
        Flashcard flashcard4 = new Flashcard(new Flashcard.Front("4", null), "4");
        Flashcard flashcard5 = new Flashcard(new Flashcard.Front("5", null), "5");
        this.deck = new Deck("deck");
        deck.addFlashcard(flashcard1);
        deck.addFlashcard(flashcard2);
        deck.addFlashcard(flashcard3);
        deck.addFlashcard(flashcard4);
        deck.addFlashcard(flashcard5);

        Observer observerOne = new BasicShuffle(deck);
        Observer observerTwo = new SmartShuffle(deck);
        Observer observerThree = new WorstToBestShuffle(deck);
        deck.addObserver(observerOne);
        deck.addObserver(observerTwo);
        deck.addObserver(observerThree);
    }

    @Test
    void addFlashcard() {
        Flashcard flashcard = new Flashcard(new Flashcard.Front("front", null), "back");
        deck.addFlashcard(flashcard);
        assertTrue(deck.getFlashcards().contains(flashcard));
    }

    @Test
    void removeFlashcard() {
        Flashcard flashcard = new Flashcard(new Flashcard.Front("front", null), "back");
        deck.addFlashcard(flashcard);
        deck.removeFlashcard(flashcard);
        assertFalse(deck.getFlashcards().contains(flashcard));
    }

    /**
     * Test add observer
     */
    @Test
    public void addObserver() {
        Observer observerFour = new BasicShuffle(deck);
        deck.addObserver(observerFour);
        assertEquals(deck.getObservers().size(), 4);
        assertTrue(deck.getObservers().contains(observerFour));
    }

    /**
     * Test remove observer
     */
    @Test
    public void deleteObserver() {
        Observer observerFour = new BasicShuffle(deck);
        Observer observerFive = new SmartShuffle(deck);
        deck.addObserver(observerFour);
        deck.addObserver(observerFive);
        deck.deleteObserver(observerFour);
        assertEquals(deck.getObservers().size(), 4);
        assertTrue(deck.getObservers().contains(observerFive));
        assertFalse(deck.getObservers().contains(observerFour));
    }

    /**
     * Test if the deck has changed
     */
    @Test
    public void hasChanged() {
        Flashcard.Front frontHey = new Flashcard.Front("Hey", null);
        Flashcard flashcardHey = new Flashcard(frontHey, "Hey");
        deck.addFlashcard(flashcardHey);
        assertEquals(deck.getFlashcardsLastState(), deck.getFlashcards());
    }

    /**
     * Test if the observers have been notified
     */
    @Test
    public void notifyObservers() {
        Flashcard.Front frontHey = new Flashcard.Front("Hey", null);
        Flashcard flashcardHey = new Flashcard(frontHey, "Hey");
        deck.addFlashcard(flashcardHey);


        for (Observer observer: deck.getObservers()){
            if (observer instanceof CardShuffler shuffler) {
                assertFalse(deck.getFlashcards().stream().allMatch(d -> shuffler.getFlashcardToData().containsKey(d)));
            } else {
                assert false;
            }
        }
        deck.notifyObservers();

        for (Observer observer: deck.getObservers()){
            if (observer instanceof CardShuffler shuffler) {
                assertTrue(deck.getFlashcards().containsAll(shuffler.getFlashcardToData().keySet()));
            } else {
                assert false;
            }
        }
    }
}
