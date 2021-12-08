import Accounts.AccountController;
import Accounts.AccountDTO;
import Accounts.AccountInteractor;
import Decks.Deck;
import Decks.DeckDTO;
import Decks.DeckInteractor;
import Flashcards.Flashcard;
import Sessions.PracticeSessionDTO;
import Sessions.SessionInteractor;
import Sessions.StudySessionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountControllerTest {

    AccountDTO accountDTO;
    DeckDTO deckDTO;
    Deck deck;

    @BeforeEach
    void setUp() {
        this.accountDTO = AccountInteractor.createAccount("username", "password");

        AccountInteractor.login(accountDTO, "password");

        this.deck = new Deck("deck1");

        Flashcard flashcard = new Flashcard(new Flashcard.Front("1", null), "1");
        Flashcard flashcard2 = new Flashcard(new Flashcard.Front("2", null), "2");
        Flashcard flashcard3 = new Flashcard(new Flashcard.Front("3", null), "3");
        Flashcard flashcard4 = new Flashcard(new Flashcard.Front("4", null), "4");
        Flashcard flashcard5 = new Flashcard(new Flashcard.Front("5", null), "5");


        this.deck.getFlashcards().add(flashcard);
        this.deck.getFlashcards().add(flashcard2);
        this.deck.getFlashcards().add(flashcard3);
        this.deck.getFlashcards().add(flashcard4);
        this.deck.getFlashcards().add(flashcard5);

        this.deckDTO = DeckInteractor.convertDeckToDTO(this.deck);
    }

    @Test
    void login() {
        AccountController.login(accountDTO, "password");
        assertEquals(AccountInteractor.getCurrentAccount().getUsername(), accountDTO.getUsername());
    }

    @Test
    void isCorrectPassword() {
        AccountDTO accountDTO = AccountController.createAccount("username", "password");

        AccountController.login(accountDTO, "password");

        assertTrue(AccountController.isCorrectPassword(accountDTO,"password"));
    }

    @Test
    void createAccount() {
        AccountDTO actual = AccountController.createAccount("username", "password");
        assertTrue(actual.getUsername().equals("username") && actual.getPassword().equals("password"));
    }

    @Test
    void changeCurrentAccountUsername() {
    }

    @Test
    void addDeckToCurrentAccount() {
        Deck deck = new Deck("Test");

        deck.addFlashcard(new Flashcard(new Flashcard.Front("1", null), "1"));

        DeckDTO deckDTO = DeckInteractor.convertDeckToDTO(deck);

        AccountController.addDeckToCurrentAccount(deckDTO);

        List<String> deckNames = new ArrayList<>();

        for (int i = 0; i < AccountController.getCurrentAccount().getDecks().size(); i++) {
            deckNames.add(AccountController.getCurrentAccount().getDecks().get(i).getName());
        }

        assertTrue(deckNames.contains(deckDTO.getName()));
    }

    @Test
    void deleteDeckFromCurrentAccount() {
        AccountController.addDeckToCurrentAccount(this.deckDTO);
        AccountController.deleteDeckFromCurrentAccount(this.deckDTO);
        assertFalse(AccountController.getCurrentAccount().getDecks().contains(this.deckDTO));
    }

    @Test
    void addSessionToCurrentAccount() {
        AccountController.addDeckToCurrentAccount(this.deckDTO);

        StudySessionDTO sessionDTO = SessionInteractor.createSession(this.deckDTO, PracticeSessionDTO.class);

        AccountController.addSessionToCurrentAccount(this.deckDTO, sessionDTO);

        assertSame(AccountController.getSessionsOfDeck(this.deckDTO).get(0).getClass(), PracticeSessionDTO.class);
    }

    @Test
    void deleteSessionFromCurrentAccount() {
        AccountController.addDeckToCurrentAccount(this.deckDTO);

        StudySessionDTO sessionDTO = SessionInteractor.createSession(this.deckDTO, PracticeSessionDTO.class);

        AccountController.addSessionToCurrentAccount(this.deckDTO, sessionDTO);

        AccountController.deleteSessionFromCurrentAccount(this.deckDTO, sessionDTO);

        assertTrue(AccountController.getSessionsOfDeck(this.deckDTO).isEmpty());
    }
}