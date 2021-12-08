import Accounts.AccountDTO;
import Accounts.AccountInteractor;
import Decks.DeckDTO;
import Decks.DeckInteractor;
import Flashcards.FlashcardDTO;
import Flashcards.FlashcardDataDTO;
import Sessions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SessionInteractorTest {

    StudySession session;

    @BeforeEach
    void setUp() {
        AccountDTO accountDTO = AccountInteractor.createAccount("user", "pass");
        AccountInteractor.login(accountDTO, "pass");
        DeckDTO deck = DeckInteractor.createDeck("default");
        AccountInteractor.addDeckToCurrentAccount(deck);
        AccountInteractor.selectDeck(deck);
        DeckInteractor.addFlashcardToCurrentDeck("front1", null, "back1");
        DeckInteractor.addFlashcardToCurrentDeck("front2", null, "back2");
        session = new PracticeSession(DeckInteractor.convertDTOToDeck(DeckInteractor.getCurrentDeck()));
    }

    @Test
    void createPracticeSession() {
        StudySessionDTO practice = SessionInteractor.createSession(DeckInteractor.getCurrentDeck(), PracticeSessionDTO.class);
        assertNotNull(practice);
    }

    @Test
    void createLearningSession() {
        StudySessionDTO practice = SessionInteractor.createSession(DeckInteractor.getCurrentDeck(), LearningSessionDTO.class);
        assertNotNull(practice);
    }

    @Test
    void startTestSession() {
        StudySessionDTO practice = SessionInteractor.createSession(DeckInteractor.getCurrentDeck(), TestSessionDTO.class);
        assertNotNull(practice);
    }

    @Test
    void getNextCard() {
        SessionInteractor.setCurrentSession(session);
        assertNotNull(SessionInteractor.getNextCard());
    }

    @Test
    void postAnswerUpdate() {
        SessionInteractor.setCurrentSession(session);
        FlashcardDTO flashcard = SessionInteractor.getNextCard();
        SessionInteractor.postAnswerUpdate(true);
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
