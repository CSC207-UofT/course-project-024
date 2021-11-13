import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {
    private String username;
    private final String password;
    private final List<Deck> decks;
    private final Map<Deck, List<StudySession>> decksToSessions;

    public Account(String username, String password, List<Deck> decks){
        this.username = username;
        this.password = password;
        this.decks = decks;
        this.decksToSessions = new HashMap<>();
        for (Deck deck : this.decks) {
            decksToSessions.put(deck, new ArrayList<>());
        }
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.decks = new ArrayList<>();
        this.decksToSessions = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public Map<Deck, List<StudySession>> getDecksToSessions() {
        return this.decksToSessions;
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

    public void addDeck(Deck deck) {
        this.decks.add(deck);
        this.decksToSessions.put(deck, new ArrayList<>());
    }

    public void deleteDeck(Deck deck) {
        this.decks.remove(deck);
        this.decksToSessions.remove(deck);
    }

    public void addSession(Deck deck, StudySession session) {
        this.decksToSessions.get(deck).add(session);
    }

    public void deleteSession(Deck deck, StudySession session) {
        this.decksToSessions.get(deck).remove(session);
    }

    public void updateSessionsOfDeck(Deck deck) {

        List<Flashcard> flashcardList = deck.getFlashcards();

        List<StudySession> listOfSessions = this.decksToSessions.get(deck);

        for (StudySession session : listOfSessions) {
            // First, check for updates needed from adding a card:
            // Loop through flashcardList. If it is in flashcardData, move on.
            // If it is not in flashcardData, add it to flashcardData.
            // Second, check for updates needed from deleting a card:
            // Loop through flashcardData. If it is in flashcardList, move on.
            // If it is not in flashcardList, then delete it from flashcardData

            Map<Flashcard, FlashcardData> flashcardToFlashcardData = session.getFlashcardData();

            for (Flashcard flashcard : flashcardList) {
                if (!session.getFlashcardData().containsKey(flashcard)) {
                    session.flashcardData.put(flashcard, new FlashcardData(0));
                }
            }

            for (Flashcard flashcard : flashcardToFlashcardData.keySet()) {
                if (!flashcardList.contains(flashcard)) {
                    flashcardToFlashcardData.remove(flashcard);
                }
            }

        }








    }
}
