package FlashcardProgram;

public class AccountInteractor {

    public static Account createAccount(String username, String password){
        return (new Account(username, password));
    }

    public static boolean isCorrectPassword(Account account, String attemptedPassword) {
        return account.isCorrectPassword(attemptedPassword);
    }
}
