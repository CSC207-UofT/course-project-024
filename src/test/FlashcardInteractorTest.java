import Flashcards.Flashcard;
import Flashcards.FlashcardDTO;
import Flashcards.FlashcardInteractor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlashcardInteractorTest {

    FlashcardDTO flashcard;

    @Test
    void createFlashcard() {
        this.flashcard = FlashcardInteractor.createFlashcard("Front 1", null, "Back 1");
        Flashcard nonDTOflashcard = FlashcardInteractor.convertDTOToFlashcard(this.flashcard);
        assertEquals("Front 1", nonDTOflashcard.getFront().getText());
        assertEquals("Back 1", nonDTOflashcard.getBack());
    }

    @Test
    void editCurrentFlashcardFront() {
        this.flashcard = FlashcardInteractor.createFlashcard("Front 1", null, "Back 1");
        Flashcard nonDTOflashcard = FlashcardInteractor.convertDTOToFlashcard(this.flashcard);
        FlashcardInteractor.setCurrentFlashcard(nonDTOflashcard);
        FlashcardInteractor.editCurrentFlashcardFront("Front 2", null);
        assertEquals("Front 2", nonDTOflashcard.getFront().getText());
    }

    @Test
    void editCurrentFlashcardBack() {
        this.flashcard = FlashcardInteractor.createFlashcard("Front 1", null, "Back 1");
        Flashcard nonDTOflashcard = FlashcardInteractor.convertDTOToFlashcard(this.flashcard);
        FlashcardInteractor.setCurrentFlashcard(nonDTOflashcard);
        FlashcardInteractor.editCurrentFlashcardBack("Back 2");
        assertEquals("Back 2", nonDTOflashcard.getBack());
    }
}