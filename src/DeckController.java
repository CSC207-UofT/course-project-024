import java.util.ArrayList;
import java.util.List;

public class DeckController {
//    public AccountInteractor accountInteractor;
//    public DeckInteractor deckInteractor;
    public List<Deck> decks;

    public DeckController(List<Deck> decks){
        this.decks = decks;
    }

    public DeckController(){
        this.decks = new ArrayList<Deck>();
    }

    public void createDeck(String name){
        decks.add(DeckInteractor.createDeck(name));
    }

    public void renameDeck(Deck deck, String newName){
        DeckInteractor.renameDeck(deck, newName);
    }

    public void addCard(Deck deck, String front, String back){
        DeckInteractor.addFlashcard(deck, front, back);
    }

    public void deleteCard(Deck deck, Flashcard flashcard){
        DeckInteractor.deleteFlashcard(deck, flashcard);
    }

}
