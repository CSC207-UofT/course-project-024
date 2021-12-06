package Accounts;

import Decks.Deck;
import Decks.DeckDTO;
import Decks.DeckInteractor;
import Flashcards.Flashcard;
import Flashcards.FlashcardData;
import Sessions.SessionInteractor;
import Sessions.StudySession;
import Sessions.StudySessionDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountInteractor {

    private static Account currentAccount;

    private AccountInteractor() {}

    /**
     * Attempt to log in to the given account. Return whether the login was successful.
     * @param accountDTO The target account
     * @param attemptedPassword The password used to attempt a login
     * @return whether login was successful
     */
    public static boolean login(AccountDTO accountDTO, String attemptedPassword) {
        Account account = convertDTOToAccount(accountDTO);
        if (account.isCorrectPassword(attemptedPassword)) {
            currentAccount = account;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Provide relevant interactors with the ability to use the given deck.
     */
    public static void selectDeck(DeckDTO selectedDeckDTO) {
        Deck currentDeck = findDeckInCurrentAccountFromDTO(selectedDeckDTO);
        DeckInteractor.setCurrentDeck(currentDeck);
    }

    /**
     * Provide relevant interactors with the ability to use the given StudySession.
     */
    public static void selectSession(DeckDTO deckDTO, StudySessionDTO sessionDTO) {
        Deck currentDeck = findDeckInCurrentAccountFromDTO(deckDTO);
        StudySession currentSession = findSessionInCurrentAccountFromDTO(currentDeck, sessionDTO);
        SessionInteractor.setCurrentSession(currentSession);
    }

    /**
     * @return the current account
     */
    public static AccountDTO getCurrentAccount() {
        return convertAccountToDTO(currentAccount);
    }

    /**
     * Return whether the attempted password matches the account's true password.
     * @param accountDTO The targeted account for login
     * @param attemptedPassword The attempted password
     * @return whether the attempted password matches the account's true password.
     */
    public static boolean isCorrectPassword(AccountDTO accountDTO, String attemptedPassword) {
        Account account = convertDTOToAccount(accountDTO);
        return account.isCorrectPassword(attemptedPassword);
    }

    /**
     * Create and return an AccountDTO.
     * @param username This account's identifying username.
     * @param password This account's password.
     * @return a new AccountDTO.
     */
    public static AccountDTO createAccount(String username, String password) {
        Account account = new Account(username, password);
        return convertAccountToDTO(account);
    }

    /**
     * Change the current account's username to the given username.
     * @param newUsername The new username of the account
     */
    public static void changeCurrentAccountUsername(String newUsername) {
        currentAccount.setUsername(newUsername);
    }

    /**
     * Add a new deck to the account.
     * @param deckDTO The deck to be added
     */
    public static void addDeckToCurrentAccount(DeckDTO deckDTO) {
        Deck deck = DeckInteractor.convertDTOToDeck(deckDTO);
        currentAccount.addDeck(deck);
    }

    /**
     * Delete an existing deck from this account.
     * @param deckDTO The deck to be deleted
     */
    public static void deleteDeckFromCurrentAccount(DeckDTO deckDTO) {
        Deck deck = findDeckInCurrentAccountFromDTO(deckDTO);
        currentAccount.getDecks().remove(deck);
    }

    /**
     * Add a new StudySession on a deck to this account.
     * @param deckDTO The deck to which the StudySession is based on
     * @param sessionDTO The added StudySession
     */
    public static void addSessionToCurrentAccount(DeckDTO deckDTO, StudySessionDTO sessionDTO) {
        Deck deck = findDeckInCurrentAccountFromDTO(deckDTO);
        currentAccount.addSession(deck, SessionInteractor.convertDTOToSession(sessionDTO));
    }

    /**
     * Delete an existing StudySession on a deck from the current account.
     * @param deckDTO The deck to which the StudySession is based on
     * @param sessionDTO The StudySession to be deleted
     */
    public static void deleteSessionFromCurrentAccount(DeckDTO deckDTO, StudySessionDTO sessionDTO) {
        Deck deck = findDeckInCurrentAccountFromDTO(deckDTO);
        StudySession session = findSessionInCurrentAccountFromDTO(deck, sessionDTO);
        currentAccount.deleteSession(deck, session);
    }

    /**
     * Get the StudySessions linked to the given deck.
     * @param deckDTO the deck whose sessions will be fetched
     * @return a List of StudySessionDTOs
     */
    public static List<StudySessionDTO> getSessionsOfDeck(DeckDTO deckDTO) {
        Deck deck = findDeckInCurrentAccountFromDTO(deckDTO);
        List<StudySession> sessions = currentAccount.getDecksToSessions().get(deck);
        List<StudySessionDTO> sessionDTOs = new ArrayList<>();
        for (StudySession s : sessions) {
            sessionDTOs.add(SessionInteractor.convertSessionToDTO(s));
        }
        return sessionDTOs;
    }

    /**
     * Adds or deletes flashcards in the flashcard-to-data mapping of each StudySession to match the current state of
     * the specified deck. Should be called when a card is added or deleted from a deck.
     * @param deckDTO The deck which has had cards added or deleted
     */
    public static void updateSessionsOfDeckInCurrentAccount(DeckDTO deckDTO) {
        Deck deck = findDeckInCurrentAccountFromDTO(deckDTO);

        List<Flashcard> flashcardList = deck.getFlashcards();

        List<StudySession> listOfSessions = currentAccount.getDecksToSessions().get(deck);

        for (StudySession session : listOfSessions) {
            // First, check for updates needed from adding a card:
            // Loop through flashcardList. If it is in flashcardData, move on.
            // If it is not in flashcardData, add it to flashcardData.
            // Second, check for updates needed from deleting a card:
            // Loop through flashcardData. If it is in flashcardList, move on.
            // If it is not in flashcardList, then delete it from flashcardData

            Map<Flashcard, FlashcardData> flashcardToFlashcardData = session.getFlashcardToData();

            for (Flashcard flashcard : flashcardList) {
                if (!session.getFlashcardToData().containsKey(flashcard)) {
                    session.getFlashcardToData().put(flashcard, new FlashcardData(0));
                }
            }

            for (Flashcard flashcard : flashcardToFlashcardData.keySet().stream().toList()) {
                if (!flashcardList.contains(flashcard)) {
                    flashcardToFlashcardData.remove(flashcard);
                }
            }

            session.update();
        }
    }

    public static Account convertDTOToAccount(AccountDTO accountDTO) {
        String username = accountDTO.getUsername();
        String password = accountDTO.getPassword();
        List<Deck> decks = new ArrayList<>();
        Map<Deck, List<StudySession>> decksToSessions = new HashMap<>();
        for (DeckDTO deckDTO : accountDTO.getDecks()) {
            Deck deck = DeckInteractor.convertDTOToDeck(deckDTO);
            decks.add(deck);
            List<StudySession> sessions = new ArrayList<>();
            for (StudySessionDTO sessionDTO : accountDTO.getDecksToSessions().get(deckDTO)) {
                StudySession session = SessionInteractor.convertDTOToSession(sessionDTO);
                sessions.add(session);
            }
            decksToSessions.put(deck, sessions);
        }
        return new Account(username, password, decks, decksToSessions);
    }

    public static AccountDTO convertAccountToDTO(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();
        List<DeckDTO> decksDTO = new ArrayList<>();
        Map<DeckDTO, List<StudySessionDTO>> decksToSessionsDTO = new HashMap<>();
        for (Deck deck : account.getDecks()) {
            DeckDTO deckDTO = DeckInteractor.convertDeckToDTO(deck);
            decksDTO.add(deckDTO);
            List<StudySessionDTO> sessionsDTO = new ArrayList<>();
            for (StudySession session : account.getDecksToSessions().get(deck)) {
                StudySessionDTO sessionDTO = SessionInteractor.convertSessionToDTO(session);
                sessionsDTO.add(sessionDTO);
            }
            decksToSessionsDTO.put(deckDTO, sessionsDTO);
        }
        return new AccountDTO(username, password, decksDTO, decksToSessionsDTO);
    }

    private static Deck findDeckInCurrentAccountFromDTO(DeckDTO deckDTO) {
        return currentAccount.getDecks().stream()
                .filter(d -> d.getName().equals(deckDTO.getName()))
                .findAny()
                .orElse(null);
    }

    private static StudySession findSessionInCurrentAccountFromDTO(Deck deck, StudySessionDTO sessionDTO) {
        StudySession selectedSession = SessionInteractor.convertDTOToSession(sessionDTO);
        return currentAccount.getDecksToSessions().get(deck).stream()
                .filter(session -> session.getClass().equals(selectedSession.getClass()))
                .findAny()
                .orElse(null);
    }

}
