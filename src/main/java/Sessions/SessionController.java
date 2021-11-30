package Sessions;

import Accounts.AccountInteractor;
import Decks.DeckDTO;
import Flashcards.FlashcardDTO;

import java.util.List;

public class SessionController {

    public SessionController() {}

    /**
     * Get a DTO representation of the selected StudySession.
     * @return a DeckDTO
     */
    public StudySessionDTO getCurrentSession() {
        return SessionInteractor.getCurrentSession();
    }

    // TODO: createSession for every type
    /**
     * Either start an existing PracticeSession or create a new one if a PracticeSession does not already exist in the
     * specified account.
     * @param deckDTO The deck which this PracticeSession is based on
     */
    public void startPracticeSession(DeckDTO deckDTO) {
        List<StudySessionDTO> sessions = AccountInteractor.getCurrentAccount().getDecksToSessions().get(deckDTO);
        StudySessionDTO existingSession = getExistingSameSession(sessions, PracticeSessionDTO.class);
        // if session already exists, resume it, else, create a new session
        if (existingSession == null) {
            StudySessionDTO newSession = SessionInteractor.createPracticeSession(deckDTO);
            AccountInteractor.addSessionToCurrentAccount(deckDTO, newSession);
            AccountInteractor.selectSession(deckDTO, newSession);
        } else {
            AccountInteractor.selectSession(deckDTO, existingSession);
        }
    }

    /**
     * Either start an existing LearningSession or create a new one if a LearningSession does not already exist in the
     * specified account.
     * @param deckDTO The deck which this LearningSession is based on
     */
    public void startLearningSession(DeckDTO deckDTO) {
        List<StudySessionDTO> sessions = AccountInteractor.getCurrentAccount().getDecksToSessions().get(deckDTO);
        StudySessionDTO existingSession = getExistingSameSession(sessions, LearningSessionDTO.class);
        // if session already exists, resume it, else, create a new session
        if (existingSession == null) {
            StudySessionDTO newSession = SessionInteractor.createLearningSession(deckDTO);
            AccountInteractor.addSessionToCurrentAccount(deckDTO, newSession);
            AccountInteractor.selectSession(deckDTO, newSession);
        } else {
            AccountInteractor.selectSession(deckDTO, existingSession);
        }
    }

    /**
     * Delete the specified StudySession from the specified account.
     * @param deckDTO The deck which the session is based on
     * @param sessionDTO The StudySession to be deleted
     */
    public void deleteSession(DeckDTO deckDTO, StudySessionDTO sessionDTO) {
        AccountInteractor.deleteSessionFromCurrentAccount(deckDTO, sessionDTO);
    }

    /**
     * Return the next card that should be shown to the user in the specified StudySession.
     * @return a FlashcardDTO
     */
    public FlashcardDTO getNextCard() {
        // TODO: throw an exception if the deck is empty
        return SessionInteractor.getNextCard();
    }

    /**
     * Update the StudySession's algorithmic metrics on the user's performance on the previous card guess.
     * This will influence what cards the StudySession will give to the user in the future.
     * @param wasCorrect Whether the user guessed the back of the flashcard correctly
     */
    public void postAnswerUpdate(boolean wasCorrect) {
        SessionInteractor.postAnswerUpdate(wasCorrect);
    }

    /**
     * Attempts to return an existing session of type sessionClass. If one exists, it will return it, else, it will
     * return null.
     * @param sessions A list of StudySessions which will be searched
     * @param sessionClass The desired type of StudySession
     * @return a StudySession of type sessionClass if it exists, otherwise, null
     */
    private StudySessionDTO getExistingSameSession(List<StudySessionDTO> sessions,
                                                   Class<? extends StudySessionDTO> sessionClass) {
        for (StudySessionDTO session : sessions) {
            if (sessionClass.isInstance(session)) {
                return session;
            }
        }
        return null;
    }

}
