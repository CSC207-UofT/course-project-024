import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DeckController {
    public List<Deck> decks;

    public DeckController(List<Deck> decks){
        this.decks = decks;
    }

    public DeckController(){
        this.decks = new ArrayList<>();
    }

    public Deck createDeck(String name){
        Deck deck = DeckInteractor.createDeck(name);
        decks.add(deck);
        return deck;
    }

    public void renameDeck(Deck deck, String newName){
        DeckInteractor.renameDeck(deck, newName);
    }

    public void addCard(Deck deck, Flashcard.Front front, String back){
        DeckInteractor.addFlashcard(deck, front, back);
    }

    public Flashcard.Front createFront(String text, Image image) {
        return DeckInteractor.createFront(text, image);
    }

    public void deleteCard(Deck deck, Flashcard flashcard){
        DeckInteractor.deleteFlashcard(deck, flashcard);
    }

}
