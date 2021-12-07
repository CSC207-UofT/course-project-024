import Accounts.AccountDTO;
import Decks.DeckDTO;
import Decks.DeckInteractor;
import Flashcards.Flashcard;
import Flashcards.FlashcardDTO;
import Sessions.SessionController;
import Sessions.SessionInteractor;
import Sessions.StudySession;
import Sessions.StudySessionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import Decks.Deck;
import Decks.DeckController;
import Accounts.Account;
import Accounts.AccountInteractor;


public class AccountInteractorTest {

    AccountDTO account;
    DeckDTO deck;
    DeckController deckController;
    StudySessionDTO session;
    SessionController sessionController;

    @BeforeEach
    void setUp() {
        account = AccountInteractor.createAccount("username", "password");
        deckController = new DeckController();
        deckController.createDeck("deck name");
        deck = deckController.getCurrentDeck();
        sessionController = new SessionController();
        sessionController.startPracticeSession(deck);
        session = sessionController.getCurrentSession();
    }

    @Test
    void createAccount() {
        assertEquals("username", account.getUsername());
    }

    @Test
    void getCurrentAccount() {
        assertEquals(account, AccountInteractor.getCurrentAccount());
    }

    @Test
    void isCorrectPassword() {
        assertTrue(AccountInteractor.isCorrectPassword(account,"password"));
    }

    @Test
    void addDeckToCurrentAccount() {
        AccountInteractor.addDeckToCurrentAccount(deck);
        assertTrue(account.getDecks().contains(deck));
    }

    @Test
    void deleteDeckFromCurrentAccount() {
        AccountInteractor.deleteDeckFromCurrentAccount(deck);
        assertFalse(account.getDecks().contains(deck));
    }

    @Test
    void addSessionToCurrentAccount() {
        AccountInteractor.addSessionToCurrentAccount(deck, session);
        assertTrue(account.getDecksToSessions().get(deck).contains(session));
    }

    @Test
    void deleteSessionFromCurrentAccount() {
        AccountInteractor.deleteSessionFromCurrentAccount(deck, session);
        assertFalse(account.getDecksToSessions().get(deck).contains(session));
    }

    @Test
    void updateSessionsOfDeckInCurrentAccount() {
        deckController.addCard("front", null, "back");
        AccountInteractor.updateSessionsOfDeckInCurrentAccount(deck);
        StudySession nonDTOsession = SessionInteractor.convertDTOToSession(session);
        Deck nonDTOdeck = DeckInteractor.convertDTOToDeck(deck);
        for (Flashcard flashcard : nonDTOdeck.getFlashcards()) {
            assertTrue(nonDTOsession.getFlashcardToData().containsKey(flashcard));
        }
        for (Flashcard flashcard : nonDTOsession.getFlashcardToData().keySet()) {
            assertTrue(nonDTOdeck.getFlashcards().contains(flashcard));
        }
    }
}
