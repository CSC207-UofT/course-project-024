public class FlashcardData {
    private int proficiency;
    private int cardsUntilDue;
    private int consecutiveCorrects;
    private int consecutiveIncorrects;
    private boolean isSeen;

    /**
     * Construct a new FlashcardData instance, which contains raw statistics for a Flashcard in a StudySession.
     * @param defaultCardsUntilDue Default cardsUntilDue, customizable for the needs of a StudySession.
     */
    public FlashcardData(int defaultCardsUntilDue) {
        this.isSeen = false;
        this.proficiency = 0;
        this.cardsUntilDue = defaultCardsUntilDue;
        this.consecutiveCorrects = 0;
        this.consecutiveIncorrects = 0;
    }

    public boolean getIsSeen() {
        return this.isSeen;
    }

    public int getProficiency() {
        return proficiency;
    }

    public int getCardsUntilDue() {
        return cardsUntilDue;
    }

    public int getConsecutiveCorrects() {
        return consecutiveCorrects;
    }

    public int getConsecutiveIncorrects() {
        return consecutiveIncorrects;
    }

    public void setCardsUntilDue(int newCardsUntilDue) {
        this.cardsUntilDue = newCardsUntilDue;
    }

    public void resetConsecutiveCorrects() {
        this.consecutiveCorrects = 0;
    }

    public void resetConsecutiveIncorrects() {
        this.consecutiveIncorrects = 0;
    }

    public void incrementConsecutiveCorrects() {
        this.consecutiveCorrects += 1;
    }

    public void incrementConsecutiveIncorrects() {
        this.consecutiveIncorrects += 1;
    }

    public void incrementProficiency(int delta) {
        this.proficiency += delta;
    }

}
