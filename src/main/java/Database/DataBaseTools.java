package Database;

import Accounts.Account;
import Decks.Deck;
import Decks.DeckDTO;
import Decks.DeckInteractor;

import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

// A collection of methods that interact with the database on behalf of the program
interface DatabaseTools {

    /**
     * Create a connection with the database.
     * Return a statement using the connection that can be executed to modify the database
     * @return Statement object to be used to modify the database
     */
    public Statement createStatement();

    /**
     * Create a connection with the database.
     * @return connection object that is connected to the database
     */
    public Connection connection();

    /**
     * Attempts to match am accounts username to their password. Basic form of account authentication
     * @param username Username given as input by the user
     * @param password Password given as input by the user
     * @return Whether or not this username-password pair exists in the database
     */
    public Boolean authenticateAccount(String username, String password);

    /**
     * Generate a list of all the decks belonging to the current account.
     * This method is mainly used to initialize an instance of the program
     * @param accountUsername The username of the account that the returned decks will be assigned to
     * @return ArrayList of DeckDTOs that will then be added to the current accounts deck list
     */
    public ArrayList<DeckDTO> getDecksFromDB(String accountUsername);

    /**
     * Add a deck to the database.
     * This method is called when a new deck is created and it needs to be stored for persistence
     * @param deck_name Name of the deck that will be added
     */
    public void addDeckToDB(String deck_name);

    /**
     * Update a single field of some row in the database
     * @param table Name of the table where the row belongs
     * @param column Name of the field we want to update
     * @param oldValue Previous value of the field we want to update
     * @param newValue New value of the field we want to update
     */
    public void updateRowInDB(String table, String column, String oldValue, String newValue);

    /**
     * Delete a card in the database
     * @param deck_name Name of the deck that the card we want to delete belongs to
     * @param front Front text of the card we want to delete
     * @param back Back text of the card we want to delete
     */
    public void deleteCardInDB(String deck_name, String front, String back);

    /**
     * Delete a whole deck from the databse.
     * When we delete a deck, we must also delete all its cards
     * @param deck_name Name of the deck we want to delete
     */
    public void deleteDeckInDB(String deck_name);

    /**
     * Add a card to a deck of the users choice to the databse
     * @param deck_name Name of the deck we want to add the card to
     * @param front Front text of the card we want to add
     * @param back Back text of the card we want to add
     * @param image Optional image that the card holds on the back
     */
    public void addCardToDeckInDB (String deck_name, String front, String back, Image image);

}
