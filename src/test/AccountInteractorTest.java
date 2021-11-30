import Flashcards.Flashcard;
import Sessions.SessionController;
import Sessions.StudySession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import Decks.Deck;
import Decks.DeckController;
import Accounts.Account;
import Accounts.AccountInteractor;


public class AccountInteractorTest {

    Account account;
    Deck deck;
    DeckController deckController;
    StudySession session;
    SessionController sessionController;

    @BeforeEach
    void setUp() {
        account = AccountInteractor.createAccount("username", "password");
        deckController = new DeckController();
        deck = deckController.createDeck(account, "deck name");
        sessionController = new SessionController();
        session = sessionController.getPracticeSession(deck, account);
    }

    @Test
    void createAccount() {
        assertEquals("username", account.getUsername());
    }

    @Test
    void changeUsername() {
        AccountInteractor.changeUsername(account, "newUser");
        assertEquals("newUser", account.getUsername());
    }

    @Test
    void isCorrectPassword() {
        assertTrue(AccountInteractor.isCorrectPassword(account,"password"));
    }

    @Test
    void addDeckToAccount() {
        AccountInteractor.addDeckToAccount(account, deck);
        assertTrue(account.getDecks().contains(deck));
    }

    @Test
    void deleteDeckFromAccount() {
        AccountInteractor.deleteDeckFromAccount(account, deck);
        assertFalse(account.getDecks().contains(deck));
    }

    @Test
    void addSessionToAccount() {
        AccountInteractor.addSessionToAccount(account, deck, session);
        assertTrue(account.getDecksToSessions().get(deck).contains(session));
    }

    @Test
    void deleteSessionFromAccount() {
        AccountInteractor.deleteSessionFromAccount(account, deck, session);
        assertFalse(account.getDecksToSessions().get(deck).contains(session));
    }

    @Test
    void updateSessionsOfDeck() {
        deckController.addCard(account, deck, "front", null, "back");
        AccountInteractor.updateSessionsOfDeck(account, deck);
        for (Flashcard flashcard : deck.getFlashcards()) {
            assertTrue(session.getFlashcardToData().containsKey(flashcard));
        }
        for (Flashcard flashcard : session.getFlashcardToData().keySet()) {
            assertTrue(deck.getFlashcards().contains(flashcard));
        }
    }
}
