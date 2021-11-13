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

    public void updateSessionsOfDeck(Account account, Deck deck) {
        AccountInteractor.updateSessionsOfDeck(account, deck);
    }
  
    public void deleteCard(Account account, Deck deck, Flashcard flashcard){
        DeckInteractor.deleteFlashcard(deck, flashcard);
        updateSessionsOfDeck(account, deck);
    }

    public void addCard(Account account, Deck deck, String frontText, Image frontImage, String back) {
        DeckInteractor.addFlashcard(deck, frontText, frontImage, back);
        updateSessionsOfDeck(account, deck);
    }



}
