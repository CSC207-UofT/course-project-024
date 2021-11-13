public class AccountInteractor {

    public static Account createAccount(String username, String password){
        return new Account(username, password);
    }

    public static boolean isCorrectPassword(Account account, String attemptedPassword) {
        return account.isCorrectPassword(attemptedPassword);
    }

    public static void addDeckToAccount(Account account, Deck deck) {
        account.addDeck(deck);
    }

    public static void deleteDeckFromAccount(Account account, Deck deck) {
        account.deleteDeck(deck);
    }

    public static void addSessionToAccount(Account account, Deck deck, StudySession session) {
        account.addSession(deck, session);
    }

    public static void deleteSessionFromAccount(Account account, Deck deck, StudySession session) {
        account.deleteSession(deck, session);
    }

    public static void updateSessionsOfDeck(Account account, Deck deck) {
        account.updateSessionsOfDeck(deck);
    }

}
