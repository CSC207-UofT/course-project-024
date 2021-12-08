package Flashcards;

import java.awt.image.BufferedImage;

/**
 * This class handles use cases for Flashcards.
 */
public class FlashcardInteractor {

    private static Flashcard currentFlashcard;

    // public static DatabaseGateway DBgateway = new DatabaseGateway();

    private FlashcardInteractor() {}

    /**
     * Set the current flashcard for this interactor to work on and mutate.
     * @param flashcard the flashcard that will be worked on by this interactor.
     */
    public static void setCurrentFlashcard(Flashcard flashcard) {
        currentFlashcard = flashcard;
    }

    /**
     * @return the current flashcard
     */
    public static FlashcardDTO getCurrentFlashcard() {
        return convertFlashcardToDTO(currentFlashcard);
    }

    /**
     * Construct and return a new FlashcardDTO.
     * @param frontText The text of the front of the new Flashcard to be returned (possibly null)
     * @param frontImage The image of the front of the new Flashcard to be returned (possibly null)
     * @param back The back of the new Flashcard to be returned
     * @return The Flashcard with front and back specified in the args
     */
    public static FlashcardDTO createFlashcard(String frontText, BufferedImage frontImage, String back) {
        return new FlashcardDTO(frontText, frontImage, back);
    }

    /**
     * Edit the front of the current flashcard.
     * @param frontText The new text of the front of the Flashcard (possibly null)
     * @param frontImage The new image of the front of the Flashcard (possibly null)
     */
    public static void editCurrentFlashcardFront(String frontText, BufferedImage frontImage) {

        Flashcard.Front front = new Flashcard.Front(frontText, frontImage);
        currentFlashcard.setFront(front);
    }

    /**
     * Edit the back of the current flashcard.
     * @param newBack The new back that will replace the current one
     */
    public static void editCurrentFlashcardBack(String newBack) {

        currentFlashcard.setBack(newBack);
    }

    /**
     * Get a DTO representation of the specified flashcard.
     * @param flashcard The target flashcard
     * @return a FlashcardDTO
     */
    public static FlashcardDTO convertFlashcardToDTO(Flashcard flashcard) {
        return new FlashcardDTO(flashcard.getFront().getText(), flashcard.getFront().getImage(), flashcard.getBack());
    }

    /**
     * Get a Flashcard entity from its DTO representation.
     * @param flashcardDTO The target flashcard
     * @return a Flashcard
     */
    public static Flashcard convertDTOToFlashcard(FlashcardDTO flashcardDTO) {
        Flashcard.Front front = new Flashcard.Front(flashcardDTO.getFrontText(), flashcardDTO.getFrontImage());
        return new Flashcard(front, flashcardDTO.getBack());
    }

    /**
     * Get a DTO representation of the specified FlashcardData.
     * @param data The target FlashcardData
     * @return a FlashcardDataDTO
     */
    public static FlashcardDataDTO convertFlashcardDataToDTO(FlashcardData data) {
        return new FlashcardDataDTO(data.getProficiency(), data.getCardsUntilDue());
    }

    /**
     * Get a FlashcardData entity from its DTO representation.
     * @param dataDTO The target FlashcardData
     * @return a FlashcardData
     */
    public static FlashcardData convertDTOToFlashcardData(FlashcardDataDTO dataDTO) {
        return new FlashcardData(dataDTO.getProficiency(), dataDTO.getCardsUntilDue());
    }

}
