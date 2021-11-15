package Sessions;

import Decks.Deck;
import Flashcards.Flashcard;
import Sessions.LearningSession;
import Sessions.PracticeSession;
import Sessions.StudySession;
import Sessions.UpdatingShuffler;

public class SessionInteractor {

    private SessionInteractor() {}

    /**
     * Return a new PracticeSession based on the specified deck.
     * @param deck The deck which this session will be based on
     * @return a new PracticeSession.
     */
    public static StudySession createPracticeSession(Deck deck) {
        return new PracticeSession(deck);
    }

    /**
     * Return a new LearningSession based on the specified deck.
     * @param deck The deck which this session will be based on
     * @return a new LearningSession.
     */
    public static StudySession createLearningSession(Deck deck) {
        return new LearningSession(deck);
    }

    /**
     * Return the next card that should be shown to the user in the specified StudySession.
     * @param session The StudySession which will provide cards to the user
     * @return a Flashcard
     */
    public static Flashcard getNextCard(StudySession session) {
        return session.getNextCard();
    }

    /**
     * Update the StudySession's algorithmic metrics on the user's performance on the previous card guess.
     * This will influence what cards the StudySession will give to the user in the future if the StudySession uses
     * a complex card-shuffling algorithm.
     * @param session The StudySession which is being run
     * @param wasCorrect Whether the user guessed the back of the flashcard correctly
     */
    public static void postAnswerUpdate(StudySession session, boolean wasCorrect) {
        if (session.cardShuffler instanceof UpdatingShuffler) {
            ((UpdatingShuffler) session.cardShuffler).postAnswerFlashcardDataUpdate(wasCorrect);
        }
    }

}
