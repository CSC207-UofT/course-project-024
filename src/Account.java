import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Account {
    public String username;
    public String password;
    public List<Deck> decks;

    public Account(String username, String password, List<Deck> decks){
        this.username = username;
        this.password = password;
        this.decks = decks;
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.decks = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String newUsername){
        username = newUsername;
    }

    public boolean isCorrectPassword(String attemptedPassword){
        return Objects.equals(password, attemptedPassword);
    }
}
