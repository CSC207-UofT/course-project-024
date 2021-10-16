public class DeckController {
    public AccountInteractor accountInteractor;
    public DeckInteractor deckInteractor;
    public Deck[] decks;

    public void renameDeck(Deck deck, String name){
        deck.setName(name);
    }
}
