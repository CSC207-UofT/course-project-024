public class SessionInteractor {

    private SessionInteractor() {}

    public static StudySession createPracticeSession(Deck deck) {
        return new PracticeSession(deck);
    }

    public static Flashcard getNextCard(StudySession session) {
        return session.getNextCard();
    }

    public static StudySession createLearningSession(Deck deck, ShuffleType type) {
        return new LearningSession(deck, type);
    }

    public static void postAnswerUpdate(StudySession session, boolean wasCorrect) {
        if (session instanceof UpdatingSession) {
            ((UpdatingSession) session).postAnswerUpdate(session, wasCorrect);
        }
    }

}
