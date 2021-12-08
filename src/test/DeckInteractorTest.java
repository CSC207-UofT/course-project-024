import Accounts.AccountDTO;
import Accounts.AccountInteractor;
import Decks.DeckDTO;
import Decks.DeckInteractor;
import Flashcards.FlashcardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeckInteractorTest {
    DeckDTO deck;
    AccountDTO account;

    @BeforeEach
    void setUp() {
        account = AccountInteractor.createAccount("user", "pass");
        AccountInteractor.login(account, "pass");
        deck = DeckInteractor.createDeck("New deck");
        AccountInteractor.addDeckToCurrentAccount(deck);
        AccountInteractor.selectDeck(deck);
    }

    @Test
    void addCardToCurrentDeck() {
        DeckInteractor.addFlashcardToCurrentDeck( "front", null, "back");
        assertEquals(1, DeckInteractor.getCurrentDeck().getFlashcards().size());
    }

    @Test
    void deleteCardFromCurrentDeck() {
        DeckInteractor.addFlashcardToCurrentDeck("front",null, "back");
        DeckInteractor.deleteFlashcardFromCurrentDeck(new FlashcardDTO("front", null, "back"));
        assertEquals(0, DeckInteractor.getCurrentDeck().getFlashcards().size());
    }

    @Test
    void renameCurrentDeck() {
        DeckInteractor.renameCurrentDeck("New name");
        assertEquals("New name", DeckInteractor.getCurrentDeck().getName());
    }

    @Test
    void createDeck() {
        assertTrue(AccountInteractor.getCurrentAccount().getDecks().stream().anyMatch(d -> d.getName().equals("New deck")));
    }
}
