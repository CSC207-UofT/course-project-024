import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SessionControllerTest {
    SessionController sessionController;

    @BeforeEach
    void setUp() {
        sessionController = new SessionController();
    }

    @Test
    void createPracticeSession() {
        DeckController deckController = new DeckController();
        Deck deck = deckController.createDeck(account);
        StudySession session = sessionController.createPracticeSession(deck, "name");
        assertTrue(AccountInteractor.getSessions(account).contains(session));
    }
}
