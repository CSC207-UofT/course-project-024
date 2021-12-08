import Decks.Deck;
import Flashcards.Flashcard;
import Sessions.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    Deck deck;
    List<CardShuffler> observers;

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
        this.deck = deck;

        CardShuffler observerOne = new WorstToBestShuffle(deck);
        CardShuffler observerTwo = new BasicShuffle(deck);
        CardShuffler observerThree = new SmartShuffle(deck);
        observers.add(observerOne);
        observers.add(observerTwo);
        observers.add(observerThree);
    }

    /**
     * Test add observer
     */
    @Test
    public void addObserver() {
        CardShuffler observerFour = new BasicShuffle(deck);
        deck.addObserver(observerFour);
        assertEquals(deck.getObservers().size(), 4);
        assertTrue(deck.getObservers().contains(observerFour));
    }

    /**
     * Test remove observer
     */
    @Test
    public void deleteObserver() {
        CardShuffler observerFour = new BasicShuffle(deck);
        CardShuffler observerFive = new BasicShuffle(deck);
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
        assertNotEquals(deck.getFlashcardsLastState(), deck.getFlashcards());
        assertTrue(deck.hasChanged());
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

        for (CardShuffler observer: observers){
            assertNotEquals(observer.getDeckCopy(), deck.getFlashcards());
        }

        deck.notifyObservers();

        for (CardShuffler observer: observers){
            assertEquals(observer.getDeckCopy(), deck.getFlashcards());
        }
    }
}
