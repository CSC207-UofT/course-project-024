//import Accounts.AccountDTO;
//import Decks.DeckDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//import Decks.Deck;
//import Decks.DeckController;
//import Accounts.Account;
//import Accounts.AccountInteractor;
//
//public class DeckControllerTest {
//    DeckController deckController;
//    DeckDTO deck;
//    AccountDTO account;
//
//    @BeforeEach
//    void setUp() {
//        deckController = new DeckController();
//        account = AccountInteractor.createAccount("user1", "pass1");
//        deckController.createDeck("Deck Name");
//        deck = deckController.getCurrentDeck();
//    }
//
//    @Test
//    void addCard() {
//        deckController.addCard( "front", null, "back");
//        // TODO: check if card exists in account's Deck-StudySession proficiency map
//        assertEquals(1, deck.getFlashcards().size());
//    }
//
//    @Test
//    void deleteCard() {
//        deckController.addCard("front",null, "back");
//        deckController.deleteCard(0);
//        assertEquals(0, deck.getFlashcards().size());
//    }
//
//    @Test
//    void renameCurrentDeck() {
//        deckController.renameCurrentDeck("New name");
//        assertEquals("New name", deck.getName());
//    }
//
//    @Test
//    void createDeck() {
//        assertTrue(account.getDecks().contains(deck));
//    }
//
//    @Test
//    void deleteDeck() {
//        deckController.deleteDeck(deck);
//        assertFalse(account.getDecks().contains(deck));
//    }
//}
