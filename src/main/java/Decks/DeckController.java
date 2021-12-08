package Decks;

import Accounts.AccountDTO;
import Accounts.AccountInteractor;
import Database.DatabaseGateway;
import Flashcards.FlashcardDTO;
import Flashcards.FlashcardInteractor;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the logic that UI elements need from Decks.
 */
public class DeckController {
    // DatabaseGateway DBgateway = new DatabaseGateway();
    DatabaseGateway DBgateway;

    public DeckController(DatabaseGateway DBgateway) {
        this.DBgateway = DBgateway;
    }

    /**
     * Get a DTO representation of the selected deck.
     * @return a DeckDTO
     */
    public DeckDTO getCurrentDeck() {
        return DeckInteractor.getCurrentDeck();
    }

    /**
     * Get a DTO representation of the selected flashcard.
     * @return a FlashcardDTO
     */
    public FlashcardDTO getCurrentFlashcard() {
        return FlashcardInteractor.getCurrentFlashcard();
    }

    /**
     * Provide relevant interactors with the ability to use the flashcard at this index.
     * @param flashcardDTO The selected flashcard which will be worked on in the current deck.
     */
    public void selectFlashcard(FlashcardDTO flashcardDTO) {
        DeckInteractor.selectFlashcard(flashcardDTO);
    }

    /**
     * Create a new empty deck, which is bound to the current account and stored in a database. Return whether the
     * deck was successfully created.
     * @param name Name of the new deck
     */
    public void createDeck(String name){
        DeckDTO deckDTO = DeckInteractor.createDeck(name);
        AccountDTO accountDTO = AccountInteractor.getCurrentAccount();
        if (!hasUniqueName(deckDTO, accountDTO)) {
            return;
        }
        AccountInteractor.addDeckToCurrentAccount(deckDTO);
        DBgateway.addDeckToDB(accountDTO.getUsername(), name);
    }

    /**
     * Delete an existing deck from the current account. Also deletes the deck from the database.
     * @param deckDTO The deck to be deleted
     */
    public void deleteDeck(DeckDTO deckDTO) {
        AccountInteractor.deleteDeckFromCurrentAccount(deckDTO);
        DBgateway.deleteDeckInDB(AccountInteractor.getCurrentAccount().getUsername(), deckDTO.getName());
    }

    /**
     * Rename the current deck. Also renames the deck in the database.
     * @param newName The new name of the deck
     */
    public void renameCurrentDeck(String newName) {
        DBgateway.updateDeckNameInDB(DeckInteractor.getCurrentDeck().getName(), newName);
        DeckInteractor.renameCurrentDeck(newName);
    }

    /**
     * Delete a flashcard from the current deck. Also deletes the card from the database.
     * @param flashcardDTO The flashcard to be deleted in the current deck
     */
    public void deleteCard(FlashcardDTO flashcardDTO) {
        DeckInteractor.deleteFlashcardFromCurrentDeck(flashcardDTO);
        AccountInteractor.updateSessionsOfDeckInCurrentAccount(getCurrentDeck());

        DBgateway.deleteCardInDB(AccountInteractor.getCurrentAccount().getUsername(), DeckInteractor.getCurrentDeck().getName(), flashcardDTO.getFrontText());
    }

    /**
     * Add a new flashcard to the specified deck. Also adds the card to the database. Return whether the card
     * was successfully created.
     * @param frontText The text on the front of the new flashcard (possibly null)
     * @param frontImage The image on the front of the new flashcard (possibly null)
     * @param back The text on the back of the new flashcard
     */
    public void addCard(String frontText, BufferedImage frontImage, String back) {
        if (!hasUniqueName(frontText, getCurrentDeck())) {
            return;
        }
        DeckInteractor.addFlashcardToCurrentDeck(frontText, frontImage, back);
        AccountInteractor.updateSessionsOfDeckInCurrentAccount(getCurrentDeck());
        DBgateway.addCardToDeckInDB(AccountInteractor.getCurrentAccount().getUsername(), DeckInteractor.getCurrentDeck().getName(), frontText, back, frontImage);

    }

    /**
     * Edit the front of the current flashcard.
     * @param frontText The new text of the front of the Flashcard (possibly null)
     * @param frontImage The new image of the front of the Flashcard (possibly null)
     */
    public void editCurrentFlashcardFront(String frontText, BufferedImage frontImage) {
        FlashcardDTO oldFlashcard = FlashcardInteractor.getCurrentFlashcard();
        FlashcardInteractor.editCurrentFlashcardFront(frontText, frontImage);
        DBgateway.updateCardFrontTextInDB(oldFlashcard.getFrontText(), frontText);
        DBgateway.editFlashcardImage(oldFlashcard.getFrontText(), frontImage);

    }

    /**
     * Edit the back of the current flashcard.
     * @param newBack The new back that will replace the current one
     */
    public void editCurrentFlashcardBack(String newBack) {
        FlashcardDTO oldFlashcard = FlashcardInteractor.getCurrentFlashcard();
        FlashcardInteractor.editCurrentFlashcardBack(newBack);
        DBgateway.updateCardBackInDB(oldFlashcard.getBack(), newBack);
    }

    /**
     * Check and return whether the deckDTO has a unique name or not.
     * @param deckDTO The deckDTO to be checked
     * @param accountDTO The accountDTO that the deckDTO belongs to
     * @return Whether the deckDTO's name is unique
     */
    private boolean hasUniqueName(DeckDTO deckDTO, AccountDTO accountDTO) {
        List<String> existingDeckNames = new ArrayList<>();
        for (DeckDTO d : accountDTO.getDecks()) {
            existingDeckNames.add(d.getName());
        }
        return !existingDeckNames.contains(deckDTO.getName());
    }

    /**
     * Check and return whether a flashcard front has a unique front text or not.
     * @param frontText The text to be compared
     * @param deckDTO The deck that the flashcard belongs to
     * @return Whether the front text is unique within that deck or not
     */
    private boolean hasUniqueName(String frontText, DeckDTO deckDTO) {
        List<String> existingFlashcardNames = new ArrayList<>();
        for (FlashcardDTO f : deckDTO.getFlashcards()) {
            existingFlashcardNames.add(f.getFrontText());
        }
        return !existingFlashcardNames.contains(frontText);
    }

}
