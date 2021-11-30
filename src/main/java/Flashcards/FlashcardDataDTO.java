package Flashcards;

public class FlashcardDataDTO {

    private final int proficiency;
    private final int cardsUntilDue;

    public FlashcardDataDTO(int proficiency, int cardsUntilDue) {
        this.proficiency = proficiency;
        this.cardsUntilDue = cardsUntilDue;
    }

    public int getProficiency() {
        return proficiency;
    }

    public int getCardsUntilDue() {
        return cardsUntilDue;
    }
}
