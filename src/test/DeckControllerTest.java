import Accounts.AccountDTO;
import Database.DatabaseGateway;
import Decks.DeckDTO;
import Decks.DeckInteractor;
import Flashcards.FlashcardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import Decks.DeckController;
import Accounts.AccountInteractor;

import java.awt.image.BufferedImage;
import java.util.List;

public class DeckControllerTest {
    DeckController deckController;
    DeckDTO deck;
    AccountDTO account;

    @BeforeEach
    void setUp() {
        deckController = new DeckController(new DummyDatabase());
        account = AccountInteractor.createAccount("user", "pass");
        AccountInteractor.login(account, "pass");
        deck = DeckInteractor.createDeck("New deck");
        AccountInteractor.addDeckToCurrentAccount(deck);
        AccountInteractor.selectDeck(deck);
    }

    @Test
    void addCard() {
        deckController.addCard( "front", null, "back");
        assertEquals(1, DeckInteractor.getCurrentDeck().getFlashcards().size());
    }

    @Test
    void deleteCard() {
        deckController.addCard("front",null, "back");
        deckController.deleteCard(new FlashcardDTO("front", null, "back"));
        assertEquals(0, DeckInteractor.getCurrentDeck().getFlashcards().size());
    }

    @Test
    void renameCurrentDeck() {
        deckController.renameCurrentDeck("New name");
        assertEquals("New name", DeckInteractor.getCurrentDeck().getName());
    }

    @Test
    void createDeck() {
        assertTrue(AccountInteractor.getCurrentAccount().getDecks().stream().anyMatch(d -> d.getName().equals("New deck")));
    }

    @Test
    void deleteDeck() {
        deckController.deleteDeck(deck);
        assertFalse(AccountInteractor.getCurrentAccount().getDecks().contains(deck));
    }

    @Test
    void editCurrentFlashcardFront() {
        deckController.addCard("front",null, "back");
        deckController.selectFlashcard(new FlashcardDTO("front", null, "back"));
        deckController.editCurrentFlashcardFront("New front", null);
        assertEquals("New front", deckController.getCurrentFlashcard().getFrontText());
    }

    @Test
    void editCurrentFlashcardBack() {
        deckController.addCard("front",null, "back");
        deckController.selectFlashcard(new FlashcardDTO("front", null, "back"));
        deckController.editCurrentFlashcardBack("New back");
        assertEquals("New back", deckController.getCurrentFlashcard().getBack());
    }

    private static class DummyDatabase implements DatabaseGateway {

        @Override
        public Boolean authenticateAccount(String username, String password) {
            return null;
        }

        @Override
        public List<DeckDTO> getDecksFromDB(String accountUsername) {
            return null;
        }

        @Override
        public void addDeckToDB(String accountUsername, String deck_name) {
        }

        @Override
        public void updateCardFrontTextInDB(String oldValue, String newValue) {
        }

        @Override
        public void deleteCardInDB(String accountUsername, String deck_name, String front) {
        }

        @Override
        public void deleteDeckInDB(String accountUsername, String deck_name) {
        }

        @Override
        public void addCardToDeckInDB(String accountUsername, String deck_name, String front, String back, BufferedImage image) {
        }

        @Override
        public void editFlashcardImage(String frontText, BufferedImage frontImage) {
        }

        @Override
        public void updateCardBackInDB(String back, String newBack) {
        }

        @Override
        public void updateDeckNameInDB(String name, String newName) {
        }
    }
}
