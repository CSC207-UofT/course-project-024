import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckControllerTest {
    DeckController deckController;
    AccountInteractor accountInteractor;

    @BeforeEach
    void setUp() {
        deckController = new DeckController();
        accountInteractor = new AccountInteractor();
        Account account = accountInteractor.createAccount("user1", "pass1");
    }

    @Test
    void createDeck() {
        Deck deck = deckController.createDeck(account);
        assertTrue(accountInteractor.getDecks(account).contains(deck));
    }
}
