package Flashcards;

public class FlashcardData {
    private final int proficiency;
    private int cardsUntilDue;

    /**
     * Construct a new FlashcardData instance, which contains raw statistics for a Flashcard in a StudySession.
     * @param defaultCardsUntilDue Default cardsUntilDue, customizable for the needs of a StudySession.
     */
    public FlashcardData(int defaultCardsUntilDue) {
        this.proficiency = 0;
        this.cardsUntilDue = defaultCardsUntilDue;
    }

    /**
     * Construct a new FlashcardData instance, which contains raw statistics for a Flashcard in a StudySession.
     * @param proficiency The proficiency of this flashcard
     * @param defaultCardsUntilDue Default cardsUntilDue, customizable for the needs of a StudySession.
     */
    public FlashcardData(int proficiency, int defaultCardsUntilDue) {
        this.proficiency = proficiency;
        this.cardsUntilDue = defaultCardsUntilDue;
    }

    public int getProficiency() {
        return proficiency;
    }

    public int getCardsUntilDue() {
        return cardsUntilDue;
    }

    public void setCardsUntilDue(int newCardsUntilDue) {
        this.cardsUntilDue = newCardsUntilDue;
    }


}
