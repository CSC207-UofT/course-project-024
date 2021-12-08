package Accounts;

import Decks.Deck;
import Sessions.StudySession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the user's account
 */
public class Account {
    private String username;
    private final String password;
    private final List<Deck> decks;
    private final Map<Deck, List<StudySession>> decksToSessions; // maps decks to StudySessions that use them

    /**
     * Create a new Account with the given username, password, and list of decks.
     * @param username Username of the account
     * @param password Password of the account
     * @param decks Starting decks of the account
     */
    public Account(String username, String password, List<Deck> decks){
        this.username = username;
        this.password = password;
        this.decks = decks;
        this.decksToSessions = new HashMap<>();
        for (Deck deck : this.decks) {
            decksToSessions.put(deck, new ArrayList<>());
        }
    }

    /**
     * Create a new Account with the given username, password, and list of decks.
     * @param username Username of the account
     * @param password Password of the account
     * @param decks Starting decks of the account
     */
    public Account(String username, String password, List<Deck> decks, Map<Deck, List<StudySession>> decksToSessions) {
        this.username = username;
        this.password = password;
        this.decks = decks;
        this.decksToSessions = decksToSessions;
    }

    /**
     * Create a new Account with the given username and password, and an empty list of decks.
     * @param username Username of the account
     * @param password Password of the account
     */
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.decks = new ArrayList<>();
        this.decksToSessions = new HashMap<>();
    }

    /**
     * @return This account's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set this account's username to the given username.
     * @param username the new username of this account
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return This account's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return This account's deck-to-sessions mapping
     */
    public Map<Deck, List<StudySession>> getDecksToSessions() {
        return this.decksToSessions;
    }

    /**
     * Return whether the attempted password matches this account's password.
     * @param attemptedPassword The attempted password used to login
     * @return Whether the attempted password matches the account's password.
     */
    public boolean isCorrectPassword(String attemptedPassword){
        return password.equals(attemptedPassword);
    }

    /**
     * @return This account's list of decks
     */
    public List<Deck> getDecks() {
        return decks;
    }

    /**
     * Bind the given deck to this account.
     * @param deck The deck which will be added to this account
     */
    public void addDeck(Deck deck) {
        this.decks.add(deck);
        this.decksToSessions.put(deck, new ArrayList<>());
    }

    /**
     * Delete the given deck from this account.
     * @param deck The deck which will be deleted from this account
     */
    public void deleteDeck(Deck deck) {
        this.decks.remove(deck);
        this.decksToSessions.remove(deck);
    }

    /**
     * Add the given StudySession to this account.
     * @param deck The deck which the given session is based on
     * @param session The session which will be added to this account
     */
    public void addSession(Deck deck, StudySession session) {
        this.decksToSessions.get(deck).add(session);
    }

    /**
     * Delete the given StudySession from this account.
     * @param deck The deck which the given session is based on
     * @param session The session which will be deleted from this account
     */
    public void deleteSession(Deck deck, StudySession session) {
        this.decksToSessions.get(deck).remove(session);
    }
}
