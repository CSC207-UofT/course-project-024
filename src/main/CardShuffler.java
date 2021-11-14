import java.util.Map;

public abstract class CardShuffler {

    protected Map<Flashcard, FlashcardData> flashcardToData;

    abstract Flashcard returnChosenFlashcard();

    abstract void updateDeckContext();

    protected Map<Flashcard, FlashcardData> getFlashcardToData() {
        return this.flashcardToData;
    }



}
