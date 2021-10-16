import java.util.ArrayList;
import java.util.List;

public class SessionController {
    List<StudySession> sessions = new ArrayList<StudySession>();
    StudySession recent;

    public SessionController() {
        this.sessions = new ArrayList<>();
    }

    public StudySession createPracticeSession(Deck deck, String name) {
        // TODO createsession for every type
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
}
