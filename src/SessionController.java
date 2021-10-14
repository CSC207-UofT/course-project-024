import java.util.ArrayList;

public class SessionController {
    ArrayList<StudySession> sessions = new ArrayList<>();
    StudySession recent;

    public void sessionController() {
        this.sessions = new ArrayList<>();
    }

    public void createSession(Deck deck, String name) {
        StudySession session = new StudySession(deck, name);
        sessions.add(session);
        recent = session;
    }

    public StudySession resumeSession(String name) {
        for (StudySession studySession : this.sessions) {
            if (studySession.name.equals(name)) {
                return studySession;
            }
        }
        return resumeSession();
    }

    public StudySession resumeSession() {
        if (recent != null){
            return recent;
        }
        else if (this.sessions.size() != 0){
           StudySession session = this.sessions.get(this.sessions.size() - 1);
           recent = session;
           return session;
        }
        else {
            // TODO throw an error
        }
    }
}
