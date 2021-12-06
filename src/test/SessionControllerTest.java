import Accounts.Account;
import Decks.Deck;
import Decks.DeckController;
import Decks.DeckDTO;
import Decks.DeckInteractor;
import Flashcards.Flashcard;
import Sessions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionControllerTest {

    DeckController deckController;
    Account account;
    SessionController sessionController;
    StudySessionDTO session;
    DeckDTO deck;

    @BeforeEach
    void setUp() {
        this.deckController = new DeckController();
        this.account = new Account("hi", "bye");
        this.sessionController = new SessionController();
        this.deckController.createDeck("default");
    }

    @Test
    void startPracticeSession() {
        this.deck = DeckInteractor.getCurrentDeck();
        this.sessionController.startPracticeSession(this.deck);
        StudySession nonDTOsession = SessionInteractor.convertDTOToSession(this.session);
        Deck nonDTOdeck = DeckInteractor.convertDTOToDeck(this.deck);
        assertTrue(this.account.getDecksToSessions().get(nonDTOdeck).contains(nonDTOsession));

    }

    @Test
    void startLearningSession() {
        this.deck = DeckInteractor.getCurrentDeck();
        this.sessionController.startLearningSession(this.deck);
        StudySession nonDTOsession = SessionInteractor.convertDTOToSession(this.session);
        Deck nonDTOdeck = DeckInteractor.convertDTOToDeck(this.deck);

        assertTrue(this.account.getDecksToSessions().get(nonDTOdeck).contains(nonDTOsession));
    }

    @Test
    void getNextCard() {
        this.deck = DeckInteractor.getCurrentDeck();
        this.sessionController.startPracticeSession(this.deck);
        this.deckController.addCard("1", null, "1");
        assertNotNull(this.sessionController.getNextCard());
    }

    @Test
    void postAnswerUpdate() {
        this.deck = DeckInteractor.getCurrentDeck();
        this.sessionController.startLearningSession(this.deck);
        this.session = SessionInteractor.getCurrentSession();
        StudySession nonDTOsession = SessionInteractor.convertDTOToSession(this.session);
        this.deckController.addCard("1", null, "1");
        this.deckController.addCard("2", null, "2");
        Flashcard currFlashcard = nonDTOsession.getNextCard();
        this.sessionController.postAnswerUpdate(true);
        assertTrue(nonDTOsession.getFlashcardToData().get(currFlashcard).getCardsUntilDue() > 0);
        currFlashcard = nonDTOsession.getNextCard();
        this.sessionController.postAnswerUpdate(false);
        assertTrue(nonDTOsession.getFlashcardToData().get(currFlashcard).getCardsUntilDue() > 0);
    }
}