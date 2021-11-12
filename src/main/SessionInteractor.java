public class SessionInteractor {

    private SessionInteractor() {}

    public static StudySession createPracticeSession(Deck deck) {
        return new PracticeSession(deck);
    }

    // if they get the card right, add one to proficiency
    // TODO: Move this method into StudySession implementers to be customized by calling session.adjustProficiency()
    public static void adjustProficiency(StudySession session, Flashcard flashcard) {
        int currProficiency = session.getProficiencies().get(flashcard);
        session.getProficiencies().put(flashcard, currProficiency + 1);
    }

    public static Flashcard getNextCard(StudySession session) {
        return session.getNextCard();
    }
}
