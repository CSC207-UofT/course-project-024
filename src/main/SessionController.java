import java.util.ArrayList;
import java.util.List;

public class SessionController {
    private List<StudySession> sessions;
    private StudySession recent;

    public SessionController() {
        this.sessions = new ArrayList<>();
    }

    public StudySession createPracticeSession(Deck deck) {
        // TODO: createSession for every type
        StudySession session = SessionInteractor.createPracticeSession(deck);
        sessions.add(session);
        recent = session;
        return recent;
    }

    // name of session if they want to resume a specific session
    public StudySession resumeSession(String name) {
        for (StudySession studySession : this.sessions) {
            if (studySession.name.equals(name)) {
                recent = studySession;
                return studySession;
            }
        }
        return resumeSession();
    }

    // resume recent session if they do not specify, if no recent choose last one created, if no options throw error
    public StudySession resumeSession() {
        if (recent != null){
            return recent;
        }
        else if (this.sessions.size() == 0){
           // TODO throw an error
            return null;
        }
        else {
            StudySession session = this.sessions.get(this.sessions.size() - 1);
            recent = session;
            return session;
        }
    }

    public Flashcard getNextCard(StudySession session) {
        // TODO: throw an exception if the deck is empty
        return SessionInteractor.getNextCard(session);
    }

    public StudySession createLearningSession(Deck deck) {
        StudySession session = SessionInteractor.createLearningSession(deck);
        sessions.add(session);
        recent = session;
        return recent;
    }

    public void postAnswerUpdate(StudySession session, boolean wasCorrect) {
        SessionInteractor.postAnswerUpdate(session, wasCorrect);
    }

}
