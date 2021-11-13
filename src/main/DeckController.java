import java.util.ArrayList;
import java.util.List;

public class DeckController implements DataBaseGateway {
    public List<Deck> decks;

    public DeckController(List<Deck> decks){this.decks = decks;}

    public DeckController(){
        ArrayList<Deck> decksFromDB = getDecksFromDB();
        if (decksFromDB.size() > 0){
            this.decks = decksFromDB;
        }else{
            this.decks = new ArrayList<>();
        }
    }

    public Deck createDeck(String name){
        Deck deck = DeckInteractor.createDeck(name);
        decks.add(deck);
        insertDeckIntoDB("decks", "deck_name", deck.getName());
        return deck;
    }

    public void renameDeck(Deck deck, String newName){
        updateRowInDB("decks", "deck_name", deck.getName(), newName);
        DeckInteractor.renameDeck(deck, newName);
    }

    public void addCard(Deck deck, String front, String back){
        addCardToDeckInDB(deck.getName(),  front, back);
        DeckInteractor.addFlashcard(deck, front, back);
    }

    public void deleteCard(Deck deck, Flashcard flashcard){
        deleteCardInDB(deck.getName(), flashcard.getFront(), flashcard.getBack());
        DeckInteractor.deleteFlashcard(deck, flashcard);
    }

}
