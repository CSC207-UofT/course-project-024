public class AccountInteractor {

    public static Account createAccount(String username, String password){
        return (new Account(username, password));
    }
}
