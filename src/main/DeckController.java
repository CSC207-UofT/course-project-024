import java.awt.*;

public class DeckController {

    public DeckController() {}

    public Deck createDeck(String name, Account account){
        Deck deck = DeckInteractor.createDeck(name);
        AccountInteractor.addDeckToAccount(account, deck);
        return deck;
    }

    public void deleteDeck(Deck deck, Account account) {
        AccountInteractor.deleteDeckFromAccount(account, deck);
    }

    public void renameDeck(Deck deck, String newName){
        DeckInteractor.renameDeck(deck, newName);
    }

    public void addCard(Deck deck, Flashcard.Front front, String back){
        DeckInteractor.addFlashcard(deck, front, back);
    }

    public void deleteCard(Deck deck, Flashcard flashcard){
        DeckInteractor.deleteFlashcard(deck, flashcard);
    }

    public Flashcard.Front createFront(String text, Image image) {
        return DeckInteractor.createFront(text, image);
    }

}
