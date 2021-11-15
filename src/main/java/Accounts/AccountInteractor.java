package Accounts;

import Decks.Deck;
import Flashcards.Flashcard;
import Flashcards.FlashcardData;
import Sessions.StudySession;

import java.util.List;
import java.util.Map;

public class AccountInteractor {

    private AccountInteractor() {}

    /**
     * Create and return a new user account.
     * @param username This account's identifying username.
     * @param password This account's password.
     * @return a new Account.
     */
    public static Account createAccount(String username, String password){
        return new Account(username, password);
    }

    /**
     * Change this account's username to the given username.
     * @param account The target account
     * @param newUsername The new username of the account
     */
    public static void changeUsername(Account account, String newUsername) {
        account.setUsername(newUsername);
    }

    /**
     * Return whether the attempted password matches the account's true password.
     * @param account The targeted account for login
     * @param attemptedPassword The attempted password
     * @return whether the attempted password matches the account's true password.
     */
    public static boolean isCorrectPassword(Account account, String attemptedPassword) {
        return account.isCorrectPassword(attemptedPassword);
    }

    /**
     * Add a new deck to this account.
     * @param account The target account
     * @param deck The deck to be added
     */
    public static void addDeckToAccount(Account account, Deck deck) {
        account.addDeck(deck);
    }

    /**
     * Delete an existing deck from this account.
     * @param account The target account
     * @param deck The deck to be deleted
     */
    public static void deleteDeckFromAccount(Account account, Deck deck) {
        account.deleteDeck(deck);
    }

    /**
     * Add a new StudySession on a deck to this account.
     * @param account The target account
     * @param deck The deck to which the StudySession is based on
     * @param session The added StudySession
     */
    public static void addSessionToAccount(Account account, Deck deck, StudySession session) {
        account.addSession(deck, session);
    }

    /**
     * Delete an existing StudySession on a deck from this account.
     * @param account The target account
     * @param deck The deck to which the StudySession is based on
     * @param session The StudySession to be deleted
     */
    public static void deleteSessionFromAccount(Account account, Deck deck, StudySession session) {
        account.deleteSession(deck, session);
    }

    /**
     * Adds or deletes flashcards in the flashcard-to-data mapping of each StudySession to match the current state of
     * the specified deck. Should be called when a card is added or deleted from a deck.
     * @param account The target account
     * @param deck The deck which has had cards added or deleted
     */
    public static void updateSessionsOfDeck(Account account, Deck deck) {
        List<Flashcard> flashcardList = deck.getFlashcards();

        List<StudySession> listOfSessions = account.getDecksToSessions().get(deck);

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

            for (Flashcard flashcard : flashcardToFlashcardData.keySet()) {
                if (!flashcardList.contains(flashcard)) {
                    flashcardToFlashcardData.remove(flashcard);
                }
            }
            session.updateDeckContext();
        }
    }

}
