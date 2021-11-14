public interface UpdatingShuffler {
    /**
     * Performs any post-answer updates that are needed for studySession.
     * @param wasCorrect Whether the user had correctly answered the current Flashcard
     */
    void postAnswerFlashcardDataUpdate(boolean wasCorrect);
}
