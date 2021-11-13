import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeckControllerTest {
    DeckController deckController;
    Account account;

    @BeforeEach
    void setUp() {
        deckController = new DeckController();
        account = AccountInteractor.createAccount("user1", "pass1");
    }

    @Test
    void addCard() {
        Deck deck = deckController.createDeck("Deck Name");
        deckController.addCard(deck, "front", null, "back");
        // TODO: check if card exists in account's Deck-StudySession proficiency map
        assertEquals(1, deck.getFlashcards().size());
    }

    @Test
    void deleteCard() {
        Deck deck = deckController.createDeck("Deck Name");
        deckController.addCard(deck, "front",null, "back");
        deckController.deleteCard(deck, deck.getFlashcards().get(0));
        assertEquals(0, deck.getFlashcards().size());
    }

    @Test
    void renameDeck() {
        Deck deck = deckController.createDeck("Deck Name");
        deckController.renameDeck(deck, "New name");
        assertEquals("New name", deck.getName());
    }

    @Test
    void createDeck() {
        Deck deck = deckController.createDeck("Deck Name");
        // TODO: check if account has deck
        assertTrue(deckController.decks.contains(deck));
    }
}
