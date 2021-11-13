public interface UpdatingSession {
    /**
     * Performs any post-answer updates that are needed for studySession.
     * @param studySession The StudySession that needs to be updated
     * @param wasCorrect Whether the user had correctly answered the current Flashcard
     */
    void postAnswerUpdate(StudySession studySession, boolean wasCorrect);
}
