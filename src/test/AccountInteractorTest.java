import Accounts.AccountDTO;
import Accounts.AccountInteractor;
import Decks.Deck;
import Decks.DeckDTO;
import Decks.DeckInteractor;
import Flashcards.Flashcard;
import Flashcards.FlashcardDTO;
import Sessions.PracticeSessionDTO;
import Sessions.SessionController;
import Sessions.SessionInteractor;
import Sessions.StudySessionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class AccountInteractorTest {

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
    void createAccount() {
        AccountDTO actual = AccountInteractor.createAccount("username", "password");
        assertTrue(actual.getUsername().equals("username") && actual.getPassword().equals("password"));
    }

    @Test
    void getCurrentAccount() {
        AccountDTO actual = AccountInteractor.getCurrentAccount();

        assertEquals(this.accountDTO.getUsername(), actual.getUsername());
    }

    @Test
    void isCorrectPassword() {
        AccountDTO accountDTO = AccountInteractor.createAccount("username", "password");

        AccountInteractor.login(accountDTO, "password");

        assertTrue(AccountInteractor.isCorrectPassword(accountDTO,"password"));
    }

    @Test
    void addDeckToCurrentAccount() {

        Deck deck = new Deck("Test");

        deck.addFlashcard(new Flashcard(new Flashcard.Front("1", null), "1"));

        DeckDTO deckDTO = DeckInteractor.convertDeckToDTO(deck);

        AccountInteractor.addDeckToCurrentAccount(deckDTO);

        List<String> deckNames = new ArrayList<>();

        for (int i = 0; i < AccountInteractor.getCurrentAccount().getDecks().size(); i++) {
            deckNames.add(AccountInteractor.getCurrentAccount().getDecks().get(i).getName());
        }

        assertTrue(deckNames.contains(deckDTO.getName()));

    }

    @Test
    void deleteDeckFromCurrentAccount() {
        AccountInteractor.addDeckToCurrentAccount(this.deckDTO);
        AccountInteractor.deleteDeckFromCurrentAccount(this.deckDTO);
        assertFalse(AccountInteractor.getCurrentAccount().getDecks().contains(this.deckDTO));
    }

    @Test
    void addSessionToCurrentAccount() {
        AccountInteractor.addDeckToCurrentAccount(this.deckDTO);

        StudySessionDTO sessionDTO = SessionInteractor.createSession(this.deckDTO, PracticeSessionDTO.class);

        AccountInteractor.addSessionToCurrentAccount(this.deckDTO, sessionDTO);

        assertSame(AccountInteractor.getSessionsOfDeck(this.deckDTO).get(0).getClass(), PracticeSessionDTO.class);

    }

    @Test
    void deleteSessionFromCurrentAccount() {

        AccountInteractor.addDeckToCurrentAccount(this.deckDTO);

        StudySessionDTO sessionDTO = SessionInteractor.createSession(this.deckDTO, PracticeSessionDTO.class);

        AccountInteractor.addSessionToCurrentAccount(this.deckDTO, sessionDTO);

        AccountInteractor.deleteSessionFromCurrentAccount(this.deckDTO, sessionDTO);

        assertTrue(AccountInteractor.getSessionsOfDeck(this.deckDTO).isEmpty());
    }

    @Test
    void updateSessionsOfDeckInCurrentAccount() {
        AccountInteractor.addDeckToCurrentAccount(this.deckDTO);

        SessionController sessionController = new SessionController();

        AccountInteractor.selectDeck(this.deckDTO);

        sessionController.startSession(DeckInteractor.getCurrentDeck(), PracticeSessionDTO.class);

        DeckInteractor.addFlashcardToCurrentDeck("front", null, "back");

        for (FlashcardDTO flashcard : DeckInteractor.getCurrentDeck().getFlashcards()) {
            System.out.println(flashcard.getFrontText());
        }

        AccountInteractor.updateSessionsOfDeckInCurrentAccount(DeckInteractor.getCurrentDeck());

        List<FlashcardDTO> flashcards = SessionInteractor.getCurrentSession().getCardShufflerDTO().getFlashcardToData().keySet().stream().toList();

        System.out.println(flashcards.size());

        List<String> sessionFronts = new ArrayList<>();

        for (FlashcardDTO flashcard : flashcards) {
            sessionFronts.add(flashcard.getFrontText());
            System.out.println(flashcard.getFrontText());
        }

        assertTrue(sessionFronts.contains("front"));

    }
}
