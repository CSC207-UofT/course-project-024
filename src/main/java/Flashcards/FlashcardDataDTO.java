package Flashcards;

/**
 * Data Transfer Object for FlashcardData
 */
public class FlashcardDataDTO {

    private final int proficiency;
    private final int cardsUntilDue;

    public FlashcardDataDTO(int proficiency, int cardsUntilDue) {
        this.proficiency = proficiency;
        this.cardsUntilDue = cardsUntilDue;
    }

    /**
     * getter method for proficiency
     * @return proficiency
     */
    public int getProficiency() {
        return proficiency;
    }

    /**
     * getter method for cards until due
     * @return proficiency
     */
    public int getCardsUntilDue() {
        return cardsUntilDue;
    }
}
