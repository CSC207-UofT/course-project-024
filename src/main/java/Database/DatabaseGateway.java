package Database;


import Decks.DeckDTO;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/** Interface containing all the (unimplemented) basic tools required to interact with the database
 * in the context of this program
 */
public interface DatabaseGateway {

    /**
     * Attempts to match am accounts username to their password. Basic form of account authentication
     * @param username Username given as input by the user
     * @param password Password given as input by the user
     * @return Whether or not this username-password pair exists in the database
     */
    Boolean authenticateAccount(String username, String password);

    /**
     * Generate a list of all the decks belonging to the current account.
     * This method is mainly used to initialize an instance of the program
     * @param accountUsername The username of the account that the returned decks will be assigned to
     * @return ArrayList of DeckDTOs that will then be added to the current accounts deck list
     */
    List<DeckDTO> getDecksFromDB(String accountUsername);

    /**
     * Add a deck to the database.
     * This method is called when a new deck is created and it needs to be stored for persistence
     * @param accountUsername Username of the account that this deck will belong to
     * @param deck_name Name of the deck that will be added
     */
    void addDeckToDB(String accountUsername, String deck_name);

    /**
     * Update a single card in the database
     * @param oldValue Previous value of the field we want to update
     * @param newValue New value of the field we want to update
     */
    void updateCardFrontTextInDB(String oldValue, String newValue);

    /**
     * Delete a card in the database
     * @param accountUsername Username of the account that the card we want to delete belongs to
     * @param deck_name Name of the deck that the card we want to delete belongs to
     * @param front Front text of the card we want to delete
     */
    void deleteCardInDB(String accountUsername, String deck_name, String front);

    /**
     * Delete a whole deck from the database.
     * When we delete a deck, we must also delete all its cards
     * @param accountUsername Username of the account that the deck we want to delete belongs to
     * @param deck_name Name of the deck we want to delete
     */
    void deleteDeckInDB(String accountUsername, String deck_name);

    /**
     * Add a card to a deck of the users choice to the database
     * @param accountUsername User name of the account that the added card will belong to
     * @param deck_name Name of the deck we want to add the card to
     * @param front Front text of the card we want to add
     * @param back Back text of the card we want to add
     * @param image Optional image that the card holds on the back
     */
    void addCardToDeckInDB (String accountUsername, String deck_name, String front, String back, BufferedImage image);

    /**
     * Replaces a flashcard's image with frontImage.
     * @param frontText The front text of the flashcard which is to be edited
     * @param frontImage The replacement image
     */
    void editFlashcardImage(String frontText, BufferedImage frontImage);

    /**
     * Replaces a flashcard's back with back
     * @param back The back of the flashcard to be edited
     * @param newBack The back that will replace the old back
     */
    void updateCardBackInDB(String back, String newBack);

    /**
     * Renames the deck with the given name
     * @param name The name of the deck to be renamed
     * @param newName The new name to replace the old name
     */
    void updateDeckNameInDB(String name, String newName);
}
