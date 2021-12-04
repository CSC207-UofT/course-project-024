package Accounts;

import Decks.Deck;
import Decks.DeckDTO;
import Decks.DeckInteractor;
import Sessions.SessionInteractor;
import Sessions.StudySession;
import Sessions.StudySessionDTO;

import java.util.ArrayList;
import java.util.List;

public class AccountController {

    public AccountController(){}

    /**
     * Attempt to log in to the given account. Return whether the login was successful.
     * @param accountDTO The target account
     * @param attemptedPassword The password used to attempt a login
     * @return whether login was successful
     */
    public static boolean login(AccountDTO accountDTO, String attemptedPassword) {
        AccountInteractor.login(accountDTO, attemptedPassword);
    }

    /**
     * Provide relevant interactors with the ability to use the given deck.
     */
    public static void selectDeck(DeckDTO selectedDeckDTO) {
        AccountInteractor.selectDeck(selectedDeckDTO);
    }

    /**
     * Provide relevant interactors with the ability to use the given StudySession.
     */
    public static void selectSession(DeckDTO deckDTO, StudySessionDTO sessionDTO) {
        AccountInteractor.selectSession(deckDTO, sessionDTO);
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
     * Change the current account's username to the given username.
     * @param newUsername The new username of the account
     */
    public static void changeCurrentAccountUsername(String newUsername) {
        AccountInteractor.changeCurrentAccountUsername(newUsername);
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


    // TODO: Determine whether to keep or remove this function.
    // The UI probably won't ever use this, so if everyone's okay with DeckController calling
    // AccountInteractor instead of AccountController, then we can safely remove this method
    /**
     * Adds or deletes flashcards in the flashcard-to-data mapping of each StudySession to match the current state of
     * the specified deck. Should be called when a card is added or deleted from a deck.
     * @param deckDTO The deck which has had cards added or deleted
     */
    public static void updateSessionsOfDeckInCurrentAccount(DeckDTO deckDTO) {
        AccountInteractor.updateSessionsOfDeckInCurrentAccount(deckDTO);
    }
}
