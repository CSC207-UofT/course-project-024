import java.util.ArrayList;

public class Deck {
    public String name;
    public ArrayList<Flashcard> flashcards;

    public Deck(String name, ArrayList<Flashcard> flashcards){
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

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Flashcard> getFlashcards() {
        return flashcards;
    }

    public static void main(String[] args) {
        Deck a = new Deck("hi");
        System.out.println(a.getName());
    }

}
