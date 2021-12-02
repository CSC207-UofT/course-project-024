package Accounts;

import Decks.DeckDTO;
import Sessions.StudySessionDTO;

import java.util.List;
import java.util.Map;

public class AccountDTO {

    private final String username;
    private final String password;
    private final List<DeckDTO> decks;
    private final Map<DeckDTO, List<StudySessionDTO>> decksToSessions;

    public AccountDTO(String username, String password, List<DeckDTO> decks, Map<DeckDTO,
            List<StudySessionDTO>> decksToSessions) {
        this.username = username;
        this.password = password;
        this.decks = decks;
        this.decksToSessions = decksToSessions;
    }

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
