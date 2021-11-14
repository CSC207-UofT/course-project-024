package FlashcardProgram;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String username;
    private final String password;
    private final List<Deck> decks;

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
        return password.equals(attemptedPassword);
    }

    public List<Deck> getDecks() {
        return decks;
    }
}
