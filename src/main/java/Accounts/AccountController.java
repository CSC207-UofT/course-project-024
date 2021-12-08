package Accounts;

import Decks.DeckDTO;
import Sessions.StudySessionDTO;

import java.util.List;

/**
 * This class handles logic that UI elements need from accounts.
 */

public class AccountController {

    public AccountController(){}

    /**
     * Attempt to log in to the given account. Return whether the login was successful.
     * @param accountDTO The target account
     * @param attemptedPassword The password used to attempt a login
     * @return whether login was successful
     */
    public static boolean login(AccountDTO accountDTO, String attemptedPassword) {
        return AccountInteractor.login(accountDTO, attemptedPassword);
    }

    /**
     * Provide relevant interactors with the ability to use the given deck.
     */
    public static void selectDeck(DeckDTO selectedDeckDTO) {
        AccountInteractor.selectDeck(selectedDeckDTO);
    }

    /**
     * @return the current account
     */
    public static AccountDTO getCurrentAccount() {
        return AccountInteractor.getCurrentAccount();
    }

    /**
     * Return whether the attempted password matches the account's true password.
     * @param accountDTO The targeted account for login
     * @param attemptedPassword The attempted password
     * @return whether the attempted password matches the account's true password.
     */
    public static boolean isCorrectPassword(AccountDTO accountDTO, String attemptedPassword) {
        return AccountInteractor.isCorrectPassword(accountDTO, attemptedPassword);
    }

    /**
     * Create and return an AccountDTO.
     * @param username This account's identifying username.
     * @param password This account's password.
     * @return a new AccountDTO.
     */
    public static AccountDTO createAccount(String username, String password) {
        return AccountInteractor.createAccount(username, password);
    }

    /**
     * Add a new deck to the account.
     * @param deckDTO The deck to be added
     */
    public static void addDeckToCurrentAccount(DeckDTO deckDTO) {
        AccountInteractor.addDeckToCurrentAccount(deckDTO);
    }

    /**
     * Delete an existing deck from this account.
     * @param deckDTO The deck to be deleted
     */
    public static void deleteDeckFromCurrentAccount(DeckDTO deckDTO) {
        AccountInteractor.deleteDeckFromCurrentAccount(deckDTO);
    }

    /**
     * Add a new StudySession on a deck to this account.
     * @param deckDTO The deck to which the StudySession is based on
     * @param sessionDTO The added StudySession
     */
    public static void addSessionToCurrentAccount(DeckDTO deckDTO, StudySessionDTO sessionDTO) {
        AccountInteractor.addSessionToCurrentAccount(deckDTO, sessionDTO);
    }

    /**
     * Delete an existing StudySession on a deck from the current account.
     * @param deckDTO The deck to which the StudySession is based on
     * @param sessionDTO The StudySession to be deleted
     */
    public static void deleteSessionFromCurrentAccount(DeckDTO deckDTO, StudySessionDTO sessionDTO) {
        AccountInteractor.deleteSessionFromCurrentAccount(deckDTO, sessionDTO);
    }

    /**
     * Get the StudySessions linked to the given deck.
     * @param deckDTO the deck whose sessions will be fetched
     * @return a List of StudySessionDTOs
     */
    public static List<StudySessionDTO> getSessionsOfDeck(DeckDTO deckDTO) {
        return AccountInteractor.getSessionsOfDeck(deckDTO);
    }
}
