import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckControllerTest {
    DeckController deckController;
    Deck deck;
    Account account;

    @BeforeEach
    void setUp() {
        deckController = new DeckController();
        deck = deckController.createDeck(account, "Deck Name");
        account = AccountInteractor.createAccount("user1", "pass1");
    }

    @Test
    void addCard() {
        Deck deck = deckController.createDeck(account, "Deck Name");
        deckController.addCard(account, deck, "front", null, "back");
        // TODO: check if card exists in account's Deck-StudySession proficiency map
        assertEquals(1, deck.getFlashcards().size());
    }

    @Test
    void deleteCard() {
        Deck deck = deckController.createDeck(account, "Deck Name");
        deckController.addCard(account, deck, "front",null, "back");
        deckController.deleteCard(account, deck, deck.getFlashcards().get(0));
        assertEquals(0, deck.getFlashcards().size());
    }

    @Test
    void renameDeck() {
        deckController.renameDeck(deck, "New name");
        assertEquals("New name", deck.getName());
    }

    @Test
    void createDeck() {
        assertTrue(account.getDecks().contains(deck));
    }

    @Test
    void deleteDeck() {
        assertFalse(account.getDecks().contains(deck));
    }
}
