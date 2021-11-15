import Decks.Deck;
import Accounts.Account;
import Decks.DeckController;
import Flashcards.Flashcard;
import Flashcards.FlashcardData;
import Sessions.SessionController;
import Sessions.SmartShuffle;
import Sessions.StudySession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SmartShuffleTest {
    DeckController deckController;
    Account account;
    SessionController sessionController;
    StudySession session;
    Deck deck;

    @BeforeEach
    void setUp() {
        this.deckController = new DeckController();
        this.account = new Account("hi", "bye");
        this.sessionController = new SessionController();

        this.deck = this.deckController.createDeck(account, "default");
        this.session = this.sessionController.getLearningSession(deck, account);
    }

    @Test
    void returnChosenFlashcard() {

        deckController.addCard(account, this.deck, "1", null, "1");
        deckController.addCard(account, this.deck, "2", null, "2");
        deckController.addCard(account, this.deck, "3", null, "3");
        deckController.addCard(account, this.deck, "4", null, "4");
        deckController.addCard(account, this.deck, "5", null, "5");

        Flashcard actual = this.session.getCardShuffler().returnChosenFlashcard();
        List<Flashcard> deckCopy = ((SmartShuffle) this.session.getCardShuffler()).getDeckCopy();

        Flashcard expected = deckCopy.get(0);

        assertEquals(expected, actual);

    }

    @Test
    void updateDeckContext() {
        deckController.addCard(account, this.deck, "new", null, "newBack");

        this.session.updateDeckContext();

        assertEquals(this.deck.getFlashcards(), ((SmartShuffle) this.session.getCardShuffler()).getDeckCopy());

        deckController.deleteCard(account, this.deck, this.deck.getFlashcards().get(0));

        assertEquals(this.deck.getFlashcards(), ((SmartShuffle) this.session.getCardShuffler()).getDeckCopy());
    }

    @Test
    void getDeckCopy() {
        this.deckController.addCard(account, this.deck, "1", null, "1");
        this.deckController.addCard(account, this.deck, "2", null, "2");
        this.deckController.addCard(account, this.deck, "3", null, "3");

        LinkedList<Flashcard> actual = ((SmartShuffle) this.session.getCardShuffler()).getDeckCopy();
        assertEquals(actual, this.deck.getFlashcards());
    }

    @Test
    void postAnswerFlashcardDataUpdate() {
        this.deckController.addCard(account, this.deck, "1", null, "1");

        this.deckController.addCard(account, this.deck, "2", null, "2");

        Flashcard currFlashcard = this.session.getNextCard();

        ((SmartShuffle) this.session.getCardShuffler()).postAnswerFlashcardDataUpdate(true);

        assertTrue(this.session.getFlashcardToData().get(currFlashcard).getCardsUntilDue() > 0);

        currFlashcard = this.session.getNextCard();

        ((SmartShuffle) this.session.getCardShuffler()).postAnswerFlashcardDataUpdate(false);

        assertTrue(this.session.getFlashcardToData().get(currFlashcard).getCardsUntilDue() > 0);
    }

    @Test
    void updateFlashcardData() {

        this.deckController.addCard(account, deck, "1", null, "1");
        this.deckController.addCard(account, deck, "2", null, "2");

        this.session.getNextCard();

        ((SmartShuffle) this.session.getCardShuffler()).updateFlashcardData(true);

        for (FlashcardData data : this.session.getFlashcardToData().values()) {
            assertTrue(data.getCardsUntilDue() >= 0);
        }
    }
}