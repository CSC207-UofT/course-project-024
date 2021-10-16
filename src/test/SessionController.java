import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SessionController {
    SessionController sessionController;

    @BeforeEach
    void setUp() {
        sessionController = new SessionController();
        sessionController.sessionInteractor.createSession();
    }

    @Test
    void createSession() {
        Deck deck = deckController.createDeck(account);
        StudySession session = sessionController.createSession(deck);
        assertTrue(accountInteractor.getSessions(account).contains(session));
    }
}
