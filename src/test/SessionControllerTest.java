import Accounts.Account;
import Decks.Deck;
import Decks.DeckController;
import Flashcards.Flashcard;
import Sessions.LearningSession;
import Sessions.PracticeSession;
import Sessions.SessionController;
import Sessions.StudySession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SessionControllerTest {

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
    }

    @Test
    void getPracticeSession() {
        this.session = this.sessionController.getPracticeSession(this.deck, this.account);
        assertTrue(this.session instanceof PracticeSession);
        StudySession otherSession = this.sessionController.getPracticeSession(this.deck, this.account);
        assertEquals(otherSession, this.session);

        assertTrue(this.account.getDecksToSessions().get(this.deck).contains(this.session));

    }

    @Test
    void getLearningSession() {
        this.session = this.sessionController.getLearningSession(this.deck, this.account);
        assertTrue(this.session instanceof LearningSession);
        StudySession otherSession = this.sessionController.getLearningSession(this.deck, this.account);
        assertEquals(otherSession, this.session);

        assertTrue(this.account.getDecksToSessions().get(this.deck).contains(this.session));
    }

    @Test
    void getNextCard() {
        this.session = this.sessionController.getPracticeSession(this.deck, this.account);
        this.deckController.addCard(this.account, this.deck, "1", null, "1");
        assertNotNull(this.sessionController.getNextCard(this.session));
    }

    @Test
    void postAnswerUpdate() {
        this.session = this.sessionController.getLearningSession(this.deck, this.account);
        this.deckController.addCard(account, this.deck, "1", null, "1");
        this.deckController.addCard(account, this.deck, "2", null, "2");
        Flashcard currFlashcard = this.session.getNextCard();
        this.sessionController.postAnswerUpdate(this.session, true);
        assertTrue(this.session.getFlashcardToData().get(currFlashcard).getCardsUntilDue() > 0);
        currFlashcard = this.session.getNextCard();
        this.sessionController.postAnswerUpdate(this.session, false);
        assertTrue(this.session.getFlashcardToData().get(currFlashcard).getCardsUntilDue() > 0);
    }
}