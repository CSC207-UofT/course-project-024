package Flashcards;

/**
 * This class represents user data on a certain flashcard
 */
public class FlashcardData {
    private int proficiency;
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

    /**
     * get the flashcardData's proficiency
     * @return int
     */
    public int getProficiency() {
        return proficiency;
    }

    /**
     * add to the flashcardData's proficiency
     */
    public void addProficiency() {
        this.proficiency = proficiency + 1;
    }

    /**
     * remove from the flashcardData's proficiency
     */
    public void removeProficiency() {
        this.proficiency = proficiency - 1;
    }

    /**
     * get the flashcardData's cards until due
     * @return int
     */
    public int getCardsUntilDue() {
        return cardsUntilDue;
    }

    /**
     * set the flashcardData's cards until due
     */
    public void setCardsUntilDue(int newCardsUntilDue) {
        this.cardsUntilDue = newCardsUntilDue;
    }


}
