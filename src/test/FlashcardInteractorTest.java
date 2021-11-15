import Flashcards.Flashcard;
import Flashcards.FlashcardInteractor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlashcardInteractorTest {

    Flashcard flashcard;

    @Test
    void createFlashcard() {
        this.flashcard = FlashcardInteractor.createFlashcard("Front 1", null, "Back 1");
        assertEquals("Front 1", this.flashcard.getFront().getText());
        assertEquals("Back 1", this.flashcard.getBack());
    }

    @Test
    void editFront() {
        this.flashcard = FlashcardInteractor.createFlashcard("Front 1", null, "Back 1");
        FlashcardInteractor.editFront(this.flashcard, "Front 2", null);
        assertEquals("Front 2", this.flashcard.getFront().getText());
    }

    @Test
    void editBack() {
        this.flashcard = FlashcardInteractor.createFlashcard("Front 1", null, "Back 1");
        FlashcardInteractor.editBack(this.flashcard, "Back 2");
        assertEquals("Back 2", this.flashcard.getBack());
    }
}