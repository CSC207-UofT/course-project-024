import Decks.Deck;
import Flashcards.Flashcard;
import Sessions.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    Deck myDeck;

    @BeforeEach
    void setUp() {
        this.myDeck = new Deck("deck");
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
        myDeck.addFlashcard(flashcard1);
        myDeck.addFlashcard(flashcard2);
        myDeck.addFlashcard(flashcard3);
        myDeck.addFlashcard(flashcard4);
        myDeck.addFlashcard(flashcard5);

        CardShuffler observerOne = new WorstToBestShuffle(myDeck);
        CardShuffler observerTwo = new BasicShuffle(myDeck);
        CardShuffler observerThree = new SmartShuffle(myDeck);
        myDeck.addObserver(observerOne);
        myDeck.addObserver(observerTwo);
        myDeck.addObserver(observerThree);
    }

    /**
     * Test add observer
     */
    @Test
    public void addObserver() {
        CardShuffler observerFour = new BasicShuffle(myDeck);
        myDeck.addObserver(observerFour);
        assertEquals(myDeck.getObservers().size(), 4);
        assertTrue(myDeck.getObservers().contains(observerFour));
    }

    /**
     * Test remove observer
     */
    @Test
    public void deleteObserver() {
        CardShuffler observerFour = new BasicShuffle(myDeck);
        CardShuffler observerFive = new BasicShuffle(myDeck);
        myDeck.addObserver(observerFour);
        myDeck.addObserver(observerFive);
        myDeck.deleteObserver(observerFour);
        assertEquals(myDeck.getObservers().size(), 4);
        assertTrue(myDeck.getObservers().contains(observerFive));
        assertFalse(myDeck.getObservers().contains(observerFour));
    }

    /**
     * Test if the deck has changed
     */
    @Test
    public void hasChanged() {
        Flashcard.Front frontHey = new Flashcard.Front("Hey", null);
        Flashcard flashcardHey = new Flashcard(frontHey, "Hey");
        myDeck.addFlashcard(flashcardHey);
        assertNotEquals(myDeck.getFlashcardsLastState(), myDeck.getFlashcards());
        assertTrue(myDeck.hasChanged());
        assertEquals(myDeck.getFlashcardsLastState(), myDeck.getFlashcards());
    }

    /**
     * Test if the observers have been notified
     */
    @Test
    public void notifyObservers() {
        Flashcard.Front frontHey = new Flashcard.Front("Hey", null);
        Flashcard flashcardHey = new Flashcard(frontHey, "Hey");
        myDeck.addFlashcard(flashcardHey);

        for (Observer o: myDeck.getObservers()){
            if (o instanceof CardShuffler observer)
            assertNotEquals(observer.getDeckCopy(), myDeck.getFlashcards());
        }

        myDeck.notifyObservers();

        for (Observer o: myDeck.getObservers()){
            if (o instanceof CardShuffler observer)
            assertEquals(observer.getDeckCopy(), myDeck.getFlashcards());
        }
    }
}
