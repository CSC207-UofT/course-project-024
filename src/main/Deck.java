import java.util.ArrayList;
import java.util.List;

public class Deck {
    private String name;
    private final List<Flashcard> flashcards;

    public Deck(String name, List<Flashcard> flashcards){
        this.name = name;
        this.flashcards = flashcards;
    }

    public Deck(String name){
        this.name = name;
        this.flashcards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public List<Flashcard> getFlashcards() {
        return flashcards;
    }

    public void addFlashcard(Flashcard newFlashcard){
        flashcards.add(newFlashcard);
    }

    public void removeFlashcard(Flashcard flashcard) {
        flashcards.remove(flashcard);
    }

    public Deck copyDeck(){
        List<Flashcard> copiedFlashcards = new ArrayList<>();
        for (Flashcard card: getFlashcards()){
            copiedFlashcards.add(card);
        }
        Deck deck = new Deck(this.name, copiedFlashcards);
        return deck;
    }

}
