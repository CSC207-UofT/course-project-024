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
        }
        else {
            StudySession session = this.sessions.get(this.sessions.size() - 1);
            recent = session;
            return session;
        }
    }
}
