package Sessions;

import Flashcards.FlashcardData;
import Flashcards.Flashcard;

import java.util.Map;

public abstract class CardShuffler {

    protected Map<Flashcard, FlashcardData> flashcardToData;

    public abstract Flashcard returnChosenFlashcard();

    public abstract void updateDeckContext();

    protected Map<Flashcard, FlashcardData> getFlashcardToData() {
        return this.flashcardToData;
    }



}
