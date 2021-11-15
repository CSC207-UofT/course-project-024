package Sessions;
import Accounts.Account;
import Accounts.AccountInteractor;
import Decks.Deck;
import Flashcards.Flashcard;

import java.util.List;

public class SessionController {

    public SessionController() {}

    // TODO: createSession for every type
    /**
     * Either get an existing PracticeSession or create a new one if a PracticeSession does not already exist in the
     * specified account.
     * @param deck The deck which this PracticeSession is based on
     * @param account The target account
     * @return a PracticeSession
     */
    public StudySession getPracticeSession(Deck deck, Account account) {
        List<StudySession> sessions = account.getDecksToSessions().get(deck);
        StudySession existingSession = getExistingSameSession(sessions, PracticeSession.class);
        // if session already exists, resume it, else, create a new session
        if (existingSession == null) {
            StudySession newSession = SessionInteractor.createPracticeSession(deck);
            AccountInteractor.addSessionToAccount(account, deck, newSession);
            return newSession;
        } else {
            return existingSession;
        }
    }

    /**
     * Either get an existing LearningSession or create a new one if a LearningSession does not already exist in the
     * specified account.
     * @param deck The deck which this LearningSession is based on
     * @param account The target account
     * @return a LearningSession
     */
    public StudySession getLearningSession(Deck deck, Account account) {
        List<StudySession> sessions = account.getDecksToSessions().get(deck);
        StudySession existingSession = getExistingSameSession(sessions, LearningSession.class);
        // if session already exists, resume it, else, create a new session
        if (existingSession == null) {
            StudySession newSession = SessionInteractor.createLearningSession(deck);
            AccountInteractor.addSessionToAccount(account, deck, newSession);
            return newSession;
        } else {
            return existingSession;
        }
    }

    /**
     * Return the next card that should be shown to the user in the specified StudySession.
     * @param session The StudySession which will provide cards to the user
     * @return a Flashcard
     */
    public Flashcard getNextCard(StudySession session) {
        // TODO: throw an exception if the deck is empty
        return SessionInteractor.getNextCard(session);
    }

    /**
     * Update the StudySession's algorithmic metrics on the user's performance on the previous card guess.
     * This will influence what cards the StudySession will give to the user in the future.
     * @param session The StudySession which is being run
     * @param wasCorrect Whether the user guessed the back of the flashcard correctly
     */
    public void postAnswerUpdate(StudySession session, boolean wasCorrect) {
        SessionInteractor.postAnswerUpdate(session, wasCorrect);
    }

    /**
     * Attempts to return an existing session of type sessionClass. If one exists, it will return it, else, it will
     * return null.
     * @param sessions A list of StudySessions which will be searched
     * @param sessionClass The desired type of StudySession
     * @return a StudySession of type sessionClass if it exists, otherwise, null
     */
    private StudySession getExistingSameSession(List<StudySession> sessions, Class<? extends StudySession> sessionClass) {
        for (StudySession session : sessions) {
            if (sessionClass.isInstance(session)) {
                return session;
            }
        }
        return null;
    }

}
