package Decks;

import Accounts.Account;
import Accounts.AccountInteractor;
import Database.DataBaseGateway;
import Flashcards.Flashcard;

import java.awt.*;

public class DeckController implements DataBaseGateway {

    public DeckController() {}

    /**
     * Create and return a new empty deck, which is bound to the specified account and stored in a database.
     * @param account The account to add the new deck to
     * @param name Name of the new deck
     * @return the new deck.
     */
    public Deck createDeck(Account account, String name){
        Deck deck = DeckInteractor.createDeck(name);
        addDeckToDB(account, name);
        AccountInteractor.addDeckToAccount(account, deck);
        return deck;
    }

    /**
     * Delete an existing deck from the specified account. Also deletes the deck from the database.
     * @param account The account to delete the deck from
     * @param deck The deck to be deleted
     */
    public void deleteDeck(Account account, Deck deck) {
        AccountInteractor.deleteDeckFromAccount(account, deck);
        deleteDeckInDB(account, deck.getName());
    }

    /**
     * Rename an existing deck. Also renames the deck in the database.
     * @param deck The deck to be renamed
     * @param newName The new name of the deck
     */
    public void renameDeck(Deck deck, String newName){
        //updateRowInDB("decks", "deck_name", deck.getName(), newName);
        DeckInteractor.renameDeck(deck, newName);
    }

    /**
     * Adds or deletes flashcards in the flashcard-to-data mapping of each StudySession to match the current state of
     * the specified deck. Should be called when a card is added or deleted from a deck.
     * @param account The target account
     * @param deck The deck which has had cards added or deleted
     */
    public void updateSessionsOfDeck(Account account, Deck deck) {
        AccountInteractor.updateSessionsOfDeck(account, deck);
    }

    /**
     * Delete a flashcard from the specified deck. Also deletes the card from the database.
     * @param account The target account
     * @param deck The deck which owns the flashcard
     * @param flashcard The flashcard to be deleted
     */
    public void deleteCard(Account account, Deck deck, Flashcard flashcard){
        DeckInteractor.deleteFlashcard(deck, flashcard);
        deleteCardInDB(account, deck.getName(), flashcard.getFront().getText(), flashcard.getBack());
        updateSessionsOfDeck(account, deck);
    }

    /**
     * Add a new flashcard to the specified deck. Also adds the card to the database.
     * @param account The target account
     * @param deck The deck which will own the flashcard
     * @param frontText The text on the front of the new flashcard (possibly null)
     * @param frontImage The image on the front of the new flashcard (possibly null)
     * @param back The text on the back of the new flashcard
     */
    public void addCard(Account account, Deck deck, String frontText, Image frontImage, String back) {
        DeckInteractor.addFlashcard(deck, frontText, frontImage, back);
//        addCardToDeckInDB(account, deck.getName(), frontText, back, );
        updateSessionsOfDeck(account, deck);
    }



}
