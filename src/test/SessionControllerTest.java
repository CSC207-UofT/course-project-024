import Accounts.AccountDTO;
import Accounts.AccountInteractor;
import Decks.DeckDTO;
import Decks.DeckInteractor;
import Flashcards.FlashcardDTO;
import Flashcards.FlashcardDataDTO;
import Sessions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SessionControllerTest {

    SessionController sessionController;

    @BeforeEach
    void setUp() {
        AccountDTO accountDTO = AccountInteractor.createAccount("user", "pass");
        AccountInteractor.login(accountDTO, "pass");
        this.sessionController = new SessionController();
        DeckDTO deck = DeckInteractor.createDeck("default");
        AccountInteractor.addDeckToCurrentAccount(deck);
        AccountInteractor.selectDeck(deck);
        DeckInteractor.addFlashcardToCurrentDeck("front1", null, "back1");
        DeckInteractor.addFlashcardToCurrentDeck("front2", null, "back2");
    }

    @Test
    void startPracticeSession() {
        sessionController.startSession(DeckInteractor.getCurrentDeck(), PracticeSessionDTO.class);
        DeckDTO deck = null;
        Map<DeckDTO, List<StudySessionDTO>> decksToSessions = AccountInteractor.getCurrentAccount().getDecksToSessions();
        Set<DeckDTO> decks = decksToSessions.keySet();

        for (DeckDTO d : decks) {
            if (d.getName().equals("default")) {
                deck = d;
            }
        }
        assertTrue(decksToSessions.get(deck).stream().anyMatch(
                s -> s.getClass().equals(PracticeSessionDTO.class)));
    }

    @Test
    void startLearningSession() {
        sessionController.startSession(DeckInteractor.getCurrentDeck(), LearningSessionDTO.class);
        DeckDTO deck = null;
        Map<DeckDTO, List<StudySessionDTO>> decksToSessions = AccountInteractor.getCurrentAccount().getDecksToSessions();
        Set<DeckDTO> decks = decksToSessions.keySet();

        for (DeckDTO d : decks) {
            if (d.getName().equals("default")) {
                deck = d;
            }
        }
        assertTrue(decksToSessions.get(deck).stream().anyMatch(
                s -> s.getClass().equals(LearningSessionDTO.class)));
    }

    @Test
    void startTestSession() {
        sessionController.startSession(DeckInteractor.getCurrentDeck(), TestSessionDTO.class);
        DeckDTO deck = null;
        Map<DeckDTO, List<StudySessionDTO>> decksToSessions = AccountInteractor.getCurrentAccount().getDecksToSessions();
        Set<DeckDTO> decks = decksToSessions.keySet();

        for (DeckDTO d : decks) {
            if (d.getName().equals("default")) {
                deck = d;
            }
        }
        assertTrue(decksToSessions.get(deck).stream().anyMatch(
                s -> s.getClass().equals(TestSessionDTO.class)));
    }

    @Test
    void getNextCard() {
        this.sessionController.startSession(DeckInteractor.getCurrentDeck(), PracticeSessionDTO.class);
        assertNotNull(this.sessionController.getNextCard());
    }

    @Test
    void postAnswerUpdate() {
        sessionController.startSession(DeckInteractor.getCurrentDeck(), PracticeSessionDTO.class);
        FlashcardDTO flashcard = this.sessionController.getNextCard();
        this.sessionController.postAnswerUpdate(true);
        Map<FlashcardDTO, FlashcardDataDTO> flashcardToData = SessionInteractor.getCurrentSession().getCardShufflerDTO().getFlashcardToData();

        Set<FlashcardDTO> flashcardDTOs = flashcardToData.keySet();
        for (FlashcardDTO f : flashcardDTOs) {
            if (f.getFrontText().equals(flashcard.getFrontText())) {
                flashcard = f;
            }
        }
        assertTrue(flashcardToData.get(flashcard).getCardsUntilDue() > 0);
    }
}