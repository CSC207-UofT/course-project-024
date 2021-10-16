import java.util.ArrayList;

public class DeckInteractor {
//    public FlashcardInteractor flashcardInteractor;

    public static Deck createDeck(String deckName){return (new Deck(deckName));}

    public static void renameDeck(Deck deck, String newName){
        deck.setName(newName);
    }

    public static void deleteFlashcard(Deck deck, Flashcard flashcard){
        deck.removeFlashcard(flashcard);
    }

    public static void addFlashcard(Deck deck, String front, String back){
        Flashcard newFlashcard = FlashcardInteractor.createFlashcard(front, back);
        deck.addFlashcard(newFlashcard);
    }



}
