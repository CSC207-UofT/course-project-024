package Sessions;

import Accounts.AccountInteractor;
import Decks.DeckDTO;
import Flashcards.FlashcardDTO;

import java.util.List;

/**
 * TODO, describe class
 */
public class SessionController {

    public SessionController() {}

    /**
     * Get a DTO representation of the selected StudySession.
     * @return a DeckDTO
     */
    public StudySessionDTO getCurrentSession() {
        return SessionInteractor.getCurrentSession();
    }

    /**
     * Either start an existing StudySession or create a new one if the type of StudySession does not already exist in
     * the specified account. This methods sets the "current session" to the selected session.
     * @param deckDTO The deck which this PracticeSession is based on
     * @param sessionClass The type of session to be started
     */
    public void startSession(DeckDTO deckDTO, Class<? extends StudySessionDTO> sessionClass) {
        List<StudySessionDTO> sessions = AccountInteractor.getSessionsOfDeck(deckDTO);
        StudySessionDTO existingSession = getExistingSameSession(sessions, sessionClass);
        // if session already exists, resume it, else, create a new session
        if (existingSession == null) {
            StudySessionDTO newSession = SessionInteractor.createSession(deckDTO, sessionClass);
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
