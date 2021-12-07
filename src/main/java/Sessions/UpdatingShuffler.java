package Sessions;

public interface UpdatingShuffler {
    /**
     * Performs any post-answer updates that are needed for studySession.
     * @param wasCorrect Whether the user had correctly answered the current Flashcard
     */
    void postAnswerFlashcardDataUpdate(boolean wasCorrect);

    /**
     * Updates this shuffler's FlashcardData after an answer is graded.
     *
     * @param wasCorrect Whether the user was correct or not when reviewing this.lastFlashcardShown
     */
    void updateFlashcardData(boolean wasCorrect);
}
