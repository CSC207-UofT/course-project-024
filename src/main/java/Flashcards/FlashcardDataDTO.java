package Flashcards;

/**
 * Data Transfer Object for FlashcardData
 */
public record FlashcardDataDTO(int proficiency, int cardsUntilDue) {

    /**
     * getter method for proficiency
     *
     * @return proficiency
     */
    public int getProficiency() {
        return proficiency;
    }

    /**
     * getter method for cards until due
     *
     * @return proficiency
     */
    public int getCardsUntilDue() {
        return cardsUntilDue;
    }
}
