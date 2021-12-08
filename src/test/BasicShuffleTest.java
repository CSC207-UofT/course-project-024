import Accounts.AccountDTO;
import Accounts.AccountInteractor;
import Decks.Deck;
import Flashcards.Flashcard;
import Flashcards.FlashcardData;
import Sessions.BasicShuffle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BasicShuffleTest {
    BasicShuffle basicShuffler;
    Deck deck;

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
    }

    /**
     * Test if after shuffle, the deck is the same size
     */
    @Test
    void size() {
        basicShuffler = new BasicShuffle(deck);
        basicShuffler.shuffleCards();
        assertEquals(5, basicShuffler.getDeckCopy().size());
    }

    /**
     * Test returnchosenflashcard actually goes through the entire deck before reshuffling
     */
    @Test
    void returnflashcard() {
        basicShuffler = new BasicShuffle(deck);
        Flashcard card1 = basicShuffler.returnChosenFlashcard();
        Flashcard card2 = basicShuffler.returnChosenFlashcard();
        Flashcard card3 = basicShuffler.returnChosenFlashcard();
        Flashcard card4 = basicShuffler.returnChosenFlashcard();
        Flashcard card5 = basicShuffler.returnChosenFlashcard();
        assertFalse(card1.equals(card2) || card1.equals(card3) || card1.equals(card4) || card1.equals(card5)
                || card2.equals(card3) || card2.equals(card4) || card2.equals(card5) || card3.equals(card4) || card4.equals(card5));
    }

    /**
     * Test update deckcontext when flash card is added
     */
    @Test
    void deckcontextadd() {

        AccountDTO accountDTO = AccountInteractor.createAccount("test", "password");

        AccountInteractor.login(accountDTO, "password");

        basicShuffler = new BasicShuffle(deck);


        Flashcard.Front front = new Flashcard.Front("<3", null);
        Flashcard card = new Flashcard(front, ":)");
        basicShuffler.getFlashcardToData().put(card, new FlashcardData(0));
        basicShuffler.update();

        List<String> fronts = new ArrayList<>();

        for (Flashcard flashcard : basicShuffler.getDeckCopy()) {
            fronts.add(flashcard.getFront().getText());
        }

        assertTrue(fronts.contains("<3"));

//        assertTrue(basicShuffler.getDeckCopy().contains(card));
    }

    /**
     * Test update deckcontext when flash card is deleted
     */
    @Test
    void deckcontextdelete() {
        AccountDTO accountDTO = AccountInteractor.createAccount("test", "password");

        AccountInteractor.login(accountDTO, "password");

        basicShuffler = new BasicShuffle(deck);

        Flashcard.Front front = new Flashcard.Front("<3", null);
        Flashcard card = new Flashcard(front, ":)");
        basicShuffler.getFlashcardToData().put(card, new FlashcardData(0));
        basicShuffler.update();
        basicShuffler.getFlashcardToData().remove(card);
        basicShuffler.update();

        List<String> fronts = new ArrayList<>();

        for (Flashcard flashcard : basicShuffler.getDeckCopy()) {
            fronts.add(flashcard.getFront().getText());
        }

        assertFalse(fronts.contains("<3"));
    }
}
