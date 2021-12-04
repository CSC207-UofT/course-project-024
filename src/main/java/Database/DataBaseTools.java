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

    // Method to return a statement object that can be used to execute queries and updates to the databse.
    public Statement createStatement();

    // Method to return a connection object that will mainly be used to create PreparedStatements
    public Connection connection();
    
    // Method that reconstructs all the decks in the same state as they were during the most recent instantiation of the program
    public ArrayList<DeckDTO> getDecksFromDB();

    // Method to add a new deck into the database
    public void addDeckToDB(String deck_name);

    // Method to update a single column of a single row in any table
    public void updateRowInDB(String table, String column, String oldValue, String newValue);

    // Method to delete a specific card that belongs to a specific deck, belonging to a specific account
    public void deleteCardInDB(String deck_name, String front, String back);

    // Method to delete a whole deck and its corresponding cards
    public void deleteDeckInDB(String deck_name);

    // Add a new card to a deck in the database
    public void addCardToDeckInDB (String deck_name, String front, String back, Image image);

}
