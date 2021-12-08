import Flashcards.FlashcardData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlashcardDataTest {

    /**
     * Test addproficiency
     */
    @Test
    void addProficiency() {
        FlashcardData data = new FlashcardData(0);
        data.addProficiency();
        data.addProficiency();
        assertEquals(data.getProficiency(), 2);
    }

    /**
     * Test removeProficiency
     */
    @Test
    void removeProficiency() {
        FlashcardData data = new FlashcardData(0);
        data.addProficiency();
        data.addProficiency();
        assertEquals(data.getProficiency(), 2);
    }
}
