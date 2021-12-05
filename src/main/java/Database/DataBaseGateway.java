package Database;

import Accounts.Account;
import Decks.DeckDTO;
import Decks.DeckInteractor;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

// A collection of methods that interact with the database on behalf of the program
public interface DataBaseGateway {
    // This main method is used for testing the database, it does not have an actual purpose in the overall program so it should generally be ignored
    static void main(String[] args) {
        try {
            String url = "jdbc:mysql://b7da4dd8912b8e:3620922e@us-cdbr-east-04.cleardb.com/heroku_ee9e4fde75342a4?reconnect=true";
            Connection connection = DriverManager.getConnection(url, "b7da4dd8912b8e", "3620922e");
            Statement statement = connection.createStatement();

            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO cards VALUES(?, ?, ?, ?)");
            pstmt.setString(1, "sample image");
            pstmt.setString(2, "");
            pstmt.setString(3, "");
            InputStream in = new FileInputStream("/Users/abdus/Desktop/google.jpeg");
            pstmt.setBlob(4, in);
            pstmt.execute();

//            ResultSet results =  statement.executeQuery("SELECT * from images");
//            while (results.next()) {
//                Blob blob = results.getBlob("image");
//                InputStream in = blob.getBinaryStream();
//                OutputStream out = new FileOutputStream("someFile.png");
//                byte[] buff = new byte[4096];  // how much of the blob to read/write at a time
//                int len = 0;
//
//                while ((len = in.read(buff)) != -1) {
//                    out.write(buff, 0, len);
//                }
//                System.out.println(blob);
//            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    // Method to return a statement object that can be used to execute queries and updates to the databse.
    default Statement createStatement() {
        try {
            // Credentials to connect to the databse
            String url = "jdbc:mysql://b7da4dd8912b8e:3620922e@us-cdbr-east-04.cleardb.com/heroku_ee9e4fde75342a4?reconnect=true";
            // Establish a connection with the databse
            Connection connection = DriverManager.getConnection(url, "b7da4dd8912b8e", "3620922e");
            // Return a statement object that can be used to query and update the database
            return connection.createStatement();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // Method to return a connection object that will mainly be used to create PreparedStatements
    default Connection connection(){
        try{
            String url = "jdbc:mysql://b7da4dd8912b8e:3620922e@us-cdbr-east-04.cleardb.com/heroku_ee9e4fde75342a4?reconnect=true";
            // Establish and return a connection to the database
            return DriverManager.getConnection(url, "b7da4dd8912b8e", "3620922e");
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    // Method that reconstructs all the decks in the same state as they were during the most recent instantiation of the program
    default ArrayList<DeckDTO> getDecksFromDB(){
        try {
            // Create an ArrayList of decks that will be built up throughout the rest of the function
            ArrayList<DeckDTO> deckList = new ArrayList<>();
            // Obtain a table of all of the decks in the database
            ResultSet decks = createStatement().executeQuery("Select * FROM decks");
            // Add flashcards to the decks that have the corresponding deck_id
            while (decks.next()){
                DeckDTO newDeck = DeckInteractor.createDeck(decks.getString("deck_name"));
                ResultSet correspondingCards = createStatement().executeQuery("SELECT * FROM cards WHERE deck_id = '" + decks.getString("deck_id") + "'");
                while (correspondingCards.next()){

//                    DeckInteractor.addFlashcard(newDeck, correspondingCards.getString("front"), correspondingCards.getString("back"));
                }
                deckList.add(newDeck);
            }
            return deckList;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // Method to add a new deck into the database
    default void addDeckToDB(Account account, String deck_name){
        try {
            // Construct a MySQL insert statement that will populate a row of the deck table with the name of the deck, and the account it belongs to
            PreparedStatement pstmt = connection().prepareStatement("INSERT INTO decks (account_id, deck_name) VALUES (?, ?)");
            pstmt.setString(1, account.getUsername());
            pstmt.setString(2, deck_name);
            // Execute the insert statement
            pstmt.execute();

//            createStatement().execute ("INSERT INTO " + table + "(" + column + ") VALUES ('" + Value + "')");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Method to update a single column of a single row in any table
    default void updateRowInDB(String table, String column, String oldValue, String newValue){
        try{
            // create and populate a MySQL update statement to fit the function parameters
            PreparedStatement pstmt = connection().prepareStatement("UPDATE (?) SET (?) = (?) WHERE (?) = (?)");
            pstmt.setString(1, table);
            pstmt.setString(2, column);
            pstmt.setString(3, newValue);
            pstmt.setString(4, column);
            pstmt.setString(5, oldValue);
//            createStatement().executeUpdate("UPDATE "+ table + " SET " + column +" ='"+ newValue + "' WHERE " + column +" = '" + oldValue + "'");
            // Execute the prepared statement
            pstmt.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Method to delete a specific card that belongs to a specific deck, belonging to a specific account
    default void deleteCardInDB(Account account, String deck_name, String front, String back){
        try{
            // Obtain a table of the deck id of the deck that holds the card we want to delete
            ResultSet corresponding_deck_id = connection().createStatement().executeQuery("SELECT deck_id FROM decks WHERE deck_name ='" + deck_name + "'");
            while (corresponding_deck_id.next()) {
                // Obtain the deck id of the deck containing the card we want to delete
                String deck_id = corresponding_deck_id.getString("deck_id");
                // Prepare a MySQL statement to delete the desired card
                PreparedStatement pstmt = connection().prepareStatement("DELETE FROM cards WHERE deck_id=(?) AND front=(?) AND back=(?) and account_id=(?)");
                pstmt.setString(1, deck_id);
                pstmt.setString(2, front);
                pstmt.setString(3, back);
                pstmt.setString(4, account.getUsername());
                // Execute the prepared statement
                pstmt.execute();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Method to delete a whole deck and its corresponding cards
    default void deleteDeckInDB(Account account, String deck_name){
        try{
            // Obtain a table containing the deck we want to delete
            ResultSet corresponding_deck_id = connection().createStatement().executeQuery("SELECT deck_id FROM decks WHERE deck_name ='" + deck_name + "' AND account_id ='" + account.getUsername() + "'");
            while (corresponding_deck_id.next()) {
                // Obtain the id of the deck we want to delete
                String deck_id = corresponding_deck_id.getString("deck_id");
                // Delete the deck that has the deck id that was just retrieved
                PreparedStatement pstmt = connection().prepareStatement("DELETE FROM deck WHERE deck_id = (?) AND account_id = (?)");
                pstmt.setString(1, deck_id);
                pstmt.setString(2, account.getUsername());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    // Add a new card to a deck in the database
    default void addCardToDeckInDB (Account account, String deck_name, String front, String back, String file_path){
        try {
            // Obtain a table of the deck that will hold the card we want to add
            ResultSet deck_id = createStatement().executeQuery("SELECT * FROM decks WHERE deck_name = '" + deck_name + "'");
            while (deck_id.next()){
                // Obtain the id of the deck we will add the card to
                String id = deck_id.getString("deck_id");
                // Insert the card into the correct deck in the database with the given information
                PreparedStatement pstmt = connection().prepareStatement("INSERT INTO cards (account_id, deck_id, front, back, image) VALUES (?, ?, ?, ?, ?)");
                pstmt.setString(1, account.getUsername());
                pstmt.setString(2, id);
                pstmt.setString(3, front);
                pstmt.setString(4, back);
                InputStream in = new FileInputStream(file_path);
                pstmt.setBlob(5, in);
                pstmt.execute();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}