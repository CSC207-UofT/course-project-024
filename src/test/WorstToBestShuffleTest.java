import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WorstToBestShuffleTest {

    DeckController deckController;
    Account account;


    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shuffleCards() {
        this.deckController = new DeckController();
        this.account = AccountInteractor.createAccount("Test", "123");
        Deck deck = this.deckController.createDeck("Test");
        for (int i = 0; i < 10; i++) {
            this.deckController.addCard(deck, Integer.toString(i), Integer.toString(i));
        }

        SessionController sessionController = new SessionController();

        StudySession session = sessionController.createLearningSession(deck, ShuffleType.SMART);

        Map<Flashcard, FlashcardData> flashcardData = session.flashcardData;

        Random random = new Random();
        for (Flashcard card : flashcardData.keySet()) {
            flashcardData.put(card, new FlashcardData(random.nextInt(10)));
        }

        session.cardshuffler.shuffleCards();
        LinkedList<Flashcard> expectedSortedLst = ((SmartShuffle) session.cardshuffler).getLinkedListDeck();

        List<Integer> sortedLstInt = new LinkedList<>();

        for (Flashcard flashcard : expectedSortedLst) {
            sortedLstInt.add(flashcardData.get(flashcard).getCardsUntilDue());
        }

        List<Integer> manuallySortedList = new LinkedList<>();

        for (FlashcardData data : flashcardData.values()) {
            manuallySortedList.add(data.getCardsUntilDue());
        }

        manuallySortedList = manuallySortedList.stream().sorted().toList();

        assertEquals(manuallySortedList, sortedLstInt);
    }

    @Test
    void returnChosenFlashcard() {
    }
}