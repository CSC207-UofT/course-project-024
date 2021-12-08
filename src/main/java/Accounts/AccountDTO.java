package Accounts;

import Decks.DeckDTO;
import Sessions.StudySessionDTO;

import java.util.List;
import java.util.Map;

/**
 * This class contains all data values that represent an Account, without any behaviour.
 */
public record AccountDTO(String username, String password, List<DeckDTO> decks,
                         Map<DeckDTO, List<StudySessionDTO>> decksToSessions) {

    /**
     * @return this account's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return this account's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return this account's decks
     */
    public List<DeckDTO> getDecks() {
        return decks;
    }

    /**
     * @return a mapping from a deck to its sessions owned by this account
     */
    public Map<DeckDTO, List<StudySessionDTO>> getDecksToSessions() {
        return decksToSessions;
    }
}
