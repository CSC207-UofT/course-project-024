import java.util.ArrayList;

public class DeckInteractor {
    public FlashcardInteractor flashcardInteractor;

    public Deck createDeck(String name, ArrayList<Flashcard> flashcard){
        return (new Deck(name, flashcard));
    }

    public void deleteFlashcard(Deck deck, Flashcard flashcard){
        deck.flashcards.remove(flashcard);
    }

//    public void addFlashcard(Deck deck, Flashcard flashcard){
//        deck.flashcards.add(flashcard);
//    }

}
