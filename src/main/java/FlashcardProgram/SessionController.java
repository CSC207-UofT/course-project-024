package FlashcardProgram;
import java.util.List;

public class SessionController {

    public SessionController() {}

    // TODO: createSession for every type
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

    private StudySession getExistingSameSession(List<StudySession> sessions, Class<? extends StudySession> sessionClass) {
        for (StudySession session : sessions) {
            if (sessionClass.isInstance(session)) {
                return session;
            }
        }
        return null;
    }

    public Flashcard getNextCard(StudySession session) {
        // TODO: throw an exception if the deck is empty
        return SessionInteractor.getNextCard(session);
    }
}
