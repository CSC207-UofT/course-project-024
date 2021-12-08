//import Decks.Deck;
//import Accounts.Account;
//import Decks.DeckController;
//import Decks.DeckDTO;
//import Decks.DeckInteractor;
//import Flashcards.Flashcard;
//import Flashcards.FlashcardData;
//import Sessions.*;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import java.util.LinkedList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class SmartShuffleTest {
//    DeckController deckController;
//    Account account;
//    SessionController sessionController;
//    StudySessionDTO session;
//    DeckDTO deck;
//
//    @BeforeEach
//    void setUp() {
//        this.deckController = new DeckController();
//        this.account = new Account("hi", "bye");
//        this.sessionController = new SessionController();
//
//        this.deckController.createDeck("default");
//        this.deck = DeckInteractor.getCurrentDeck();
//        this.sessionController.startLearningSession(this.deck);
//        this.session = sessionController.getCurrentSession();
//    }
//
//    @Test
//    void returnChosenFlashcard() {
//
//        deckController.addCard("1", null, "1");
//        deckController.addCard("2", null, "2");
//        deckController.addCard("3", null, "3");
//        deckController.addCard("4", null, "4");
//        deckController.addCard("5", null, "5");
//
//        StudySession nonDTOsession = SessionInteractor.convertDTOToSession(this.session);
//        Flashcard actual = nonDTOsession.getCardShuffler().returnChosenFlashcard();
//        List<Flashcard> deckCopy = ((SmartShuffle) nonDTOsession.getCardShuffler()).getDeckCopy();
//
//        Flashcard expected = deckCopy.get(0);
//
//        assertEquals(expected, actual);
//
//    }
//
//    @Test
//    void updateDeckContext() {
//        deckController.addCard("new", null, "newBack");
//        StudySession nonDTOsession = SessionInteractor.convertDTOToSession(this.session);
//        nonDTOsession.updateDeckContext();
//        // TODO: need to fix the assertEqual statements
//        assertEquals(this.deck.getFlashcards(), ((SmartShuffle) nonDTOsession.getCardShuffler()).getDeckCopy());
//
//        deckController.deleteCard(0);
//
//        assertEquals(this.deck.getFlashcards(), ((SmartShuffle) nonDTOsession.getCardShuffler()).getDeckCopy());
//    }
//
//    @Test
//    void getDeckCopy() {
//        this.deckController.addCard("1", null, "1");
//        this.deckController.addCard("2", null, "2");
//        this.deckController.addCard("3", null, "3");
//        // TODO: need to fix the assertEqual statements
//        StudySession nonDTOsession = SessionInteractor.convertDTOToSession(this.session);
//        LinkedList<Flashcard> actual = ((SmartShuffle) nonDTOsession.getCardShuffler()).getDeckCopy();
//        assertEquals(actual, this.deck.getFlashcards());
//    }
//
//    @Test
//    void postAnswerFlashcardDataUpdate() {
//        this.deckController.addCard("1", null, "1");
//
//        this.deckController.addCard("2", null, "2");
//
//        StudySession nonDTOsession = SessionInteractor.convertDTOToSession(this.session);
//        Flashcard currFlashcard = nonDTOsession.getNextCard();
//
//        ((SmartShuffle) nonDTOsession.getCardShuffler()).postAnswerFlashcardDataUpdate(true);
//
//        assertTrue(nonDTOsession.getFlashcardToData().get(currFlashcard).getCardsUntilDue() > 0);
//
//        currFlashcard = nonDTOsession.getNextCard();
//
//        ((SmartShuffle) nonDTOsession.getCardShuffler()).postAnswerFlashcardDataUpdate(false);
//
//        assertTrue(nonDTOsession.getFlashcardToData().get(currFlashcard).getCardsUntilDue() > 0);
//    }
//
//    @Test
//    void updateFlashcardData() {
//
//        this.deckController.addCard("1", null, "1");
//        this.deckController.addCard("2", null, "2");
//        StudySession nonDTOsession = SessionInteractor.convertDTOToSession(this.session);
//        nonDTOsession.getNextCard();
//
//        ((SmartShuffle) nonDTOsession.getCardShuffler()).updateFlashcardData(true);
//
//        for (FlashcardData data : nonDTOsession.getFlashcardToData().values()) {
//            assertTrue(data.getCardsUntilDue() >= 0);
//        }
//    }
//}