public class SessionInteractor {

    private SessionInteractor() {}

    public static StudySession createPracticeSession(Deck deck) {
        return new PracticeSession(deck);
    }

    public static Flashcard getNextCard(StudySession session) {
        return session.getNextCard();
    }

    public static StudySession createLearningSession(Deck deck) {
        return new LearningSession(deck);
    }

    public static void postAnswerUpdate(StudySession session, boolean wasCorrect) {
        if (session.cardShuffler instanceof UpdatingShuffler) {
            ((UpdatingShuffler) session.cardShuffler).postAnswerFlashcardDataUpdate(wasCorrect);
        }
    }

}
