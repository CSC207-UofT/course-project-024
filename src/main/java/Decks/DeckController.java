package Decks;

import Accounts.AccountDTO;
import Accounts.AccountInteractor;
//import Database.DataBaseGateway;
import Database.DatabaseGateway;
import Flashcards.FlashcardDTO;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DeckController { //implements DataBaseGateway {

    public DeckController() {}

    /**
     * Get a DTO representation of the selected deck.
     * @return a DeckDTO
     */
    public DeckDTO getCurrentDeck() {
        return DeckInteractor.getCurrentDeck();
    }

    /**
     * Provide relevant interactors with the ability to use the flashcard at this index.
     * @param index The index of the selected flashcard which will be worked on in the current deck.
     */
    public void selectFlashcard(int index) {
        DeckInteractor.selectFlashcard(index);
    }

    /**
     * Create a new empty deck, which is bound to the current account and stored in a database. Return whether the
     * deck was successfully created.
     * @param name Name of the new deck
     * @return whether the deck was successfully created
     */
    public boolean createDeck(String name){
        DeckDTO deckDTO = DeckInteractor.createDeck(name);
        AccountDTO accountDTO = AccountInteractor.getCurrentAccount();
        if (!hasUniqueName(deckDTO, accountDTO)) {
            return false;
        }
        AccountInteractor.addDeckToCurrentAccount(deckDTO);
        DatabaseGateway.addDeckToDB(name);
        return true;
    }

    /**
     * Delete an existing deck from the current account. Also deletes the deck from the database.
     * @param deckDTO The deck to be deleted
     */
    public void deleteDeck(DeckDTO deckDTO) {
        AccountInteractor.deleteDeckFromCurrentAccount(deckDTO);
        //deleteDeckInDB(account, deck.getName());
    }

    /**
     * Rename the current deck. Also renames the deck in the database.
     * @param newName The new name of the deck
     */
    public void renameCurrentDeck(String newName) {
        DeckInteractor.renameCurrentDeck(newName);
        //updateRowInDB("decks", "deck_name", deck.getName(), newName);
    }

    /**
     * Delete a flashcard from the current deck. Also deletes the card from the database.
     * @param index The index of the flashcard to be deleted in the current deck
     */
    public void deleteCard(int index) {
        DeckInteractor.deleteFlashcardFromCurrentDeck(index);
        AccountInteractor.updateSessionsOfDeckInCurrentAccount(getCurrentDeck());

        //deleteCardInDB(account, deck.getName(), flashcard.getFront().getText(), flashcard.getBack());
    }

    /**
     * Add a new flashcard to the specified deck. Also adds the card to the database.
     * @param frontText The text on the front of the new flashcard (possibly null)
     * @param frontImage The image on the front of the new flashcard (possibly null)
     * @param back The text on the back of the new flashcard
     */
    public void addCard(String frontText, Image frontImage, String back) {
        DeckInteractor.addFlashcardToCurrentDeck(frontText, frontImage, back);
        AccountInteractor.updateSessionsOfDeckInCurrentAccount(getCurrentDeck());
        DatabaseGateway.addCardToDeckInDB(DeckInteractor.getCurrentDeck().getName(), frontText, back, frontImage);
    }

    private boolean hasUniqueName(DeckDTO deckDTO, AccountDTO accountDTO) {
        List<String> existingDeckNames = new ArrayList<>();
        for (DeckDTO d : accountDTO.getDecks()) {
            existingDeckNames.add(d.getName());
        }
        return !existingDeckNames.contains(deckDTO.getName());
    }

}
