import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckControllerTest {
    DeckController deckController;

    @BeforeEach
    void setUp() {
        deckController = new DeckController();
        Account account = AccountInteractor.createAccount("user1", "pass1");
    }

    @Test
    void createDeck() {
        Deck deck = deckController.createDeck(account);
        assertTrue(AccountInteractor.getDecks(account).contains(deck));
    }
}
