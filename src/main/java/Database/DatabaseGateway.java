package Database;

import Accounts.Account;
import Accounts.AccountInteractor;
import Decks.DeckDTO;
import Decks.DeckInteractor;
import Flashcards.FlashcardDTO;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseGateway implements DatabaseTools {
    // This main method is used for testing the database, it does not have an actual purpose in the overall program so it should generally be ignored
//    static void main(String[] args) {
//        try {
//            String url = "jdbc:mysql://b7da4dd8912b8e:3620922e@us-cdbr-east-04.cleardb.com/heroku_ee9e4fde75342a4?reconnect=true";
//            Connection connection = DriverManager.getConnection(url, "b7da4dd8912b8e", "3620922e");
//            Statement statement = connection.createStatement();
//
//            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO cards VALUES(?, ?, ?, ?)");
//            pstmt.setString(1, "sample image");
//            pstmt.setString(2, "");
//            pstmt.setString(3, "");
//            InputStream in = new FileInputStream("/Users/abdus/Desktop/google.jpeg");
//            pstmt.setBlob(4, in);
//            pstmt.execute();
//
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
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }


    /**
     * Create a connection with the database.
     * Return a statement using the connection that can be executed to modify the database
     * @return Statement object to be used to modify the database
     */
    public Statement createStatement() {
        try {
            String url = "jdbc:mysql://b7da4dd8912b8e:3620922e@us-cdbr-east-04.cleardb.com/heroku_ee9e4fde75342a4?reconnect=true";
            Connection connection = DriverManager.getConnection(url, "b7da4dd8912b8e", "3620922e");
            return connection.createStatement();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Create a connection with the database.
     * @return connection object that is connected to the database
     */
    public  Connection connection(){
        try{
            String url = "jdbc:mysql://b7da4dd8912b8e:3620922e@us-cdbr-east-04.cleardb.com/heroku_ee9e4fde75342a4?reconnect=true";
            return DriverManager.getConnection(url, "b7da4dd8912b8e", "3620922e");
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Attempts to match am accounts username to their password. Basic form of account authentication
     * @param username Username given as input by the user
     * @param password Password given as input by the user
     * @return Whether or not this username-password pair exists in the database
     */
    public Boolean authenticateAccount(String username, String password){
        try {
            ResultSet account = createStatement().executeQuery("Select * FROM accounts WHERE username = '" + username +"'");
            while(account.next()) {
                String correctPassword = account.getString("password");
                return (correctPassword.equals(password));
            } return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }



    /**
     * Convert an image to an InputStream object.
     * This will be used to store images from flashcards into the database as BLOBs
     * @param image Image object to be converted and stored into database
     * @return InputStream object that was built using the information from the image
     */
    public InputStream imageToInputStream(Image image){
        try {
            BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = bufferedImage.createGraphics();
            g2.drawImage(image, null, null);
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outStream);
            return new ByteArrayInputStream(outStream.toByteArray());
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * Generate a list of all the decks belonging to the current account.
     * This method is mainly used to initialize an instance of the program
     * @param accountUsername The username of the account that the returned decks will be assigned to
     * @return ArrayList of DeckDTOs that will then be added to the current accounts deck list
     */
    public ArrayList<DeckDTO> getDecksFromDB(String accountUsername){
        try {
            ArrayList<DeckDTO> deckList = new ArrayList<>();
            ResultSet decks = createStatement().executeQuery("Select * FROM decks WHERE account_id = '" + accountUsername +"'");
            while (decks.next()){
                ResultSet correspondingCards = createStatement().executeQuery("SELECT * FROM cards WHERE deck_id = '" + decks.getString("deck_id") + "'");
                ArrayList<FlashcardDTO> flashcardList = new ArrayList<>();
                while (correspondingCards.next()){
                    Blob imageBlob = correspondingCards.getBlob("Image");
                    InputStream in = imageBlob.getBinaryStream();
                    Image currentCardImage = ImageIO.read(in);
                    FlashcardDTO newFlashcardDTO =  new FlashcardDTO(correspondingCards.getString("front"), currentCardImage, correspondingCards.getString("back"));
                    flashcardList.add(newFlashcardDTO);
                }
                String newDeckName = decks.getString("deck_name");
                DeckDTO newDeck = new DeckDTO(newDeckName, flashcardList);
                deckList.add(newDeck);
            }
            return deckList;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Add a deck to the database.
     * This method is called when a new deck is created and it needs to be stored for persistence
     * @param deck_name Name of the deck that will be added
     */
    public void addDeckToDB(String deck_name){
        try {
            PreparedStatement pstmt = connection().prepareStatement("INSERT INTO decks (account_id, deck_name) VALUES (?, ?)");
            pstmt.setString(1, AccountInteractor.getCurrentAccount().getUsername());
            pstmt.setString(2, deck_name);
            pstmt.execute();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Update a single field of some row in the database
     * @param table Name of the table where the row belongs
     * @param column Name of the field we want to update
     * @param oldValue Previous value of the field we want to update
     * @param newValue New value of the field we want to update
     */
    public void updateRowInDB(String table, String column, String oldValue, String newValue){
        try{
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
    /**
     * Delete a card in the database
     * @param deck_name Name of the deck that the card we want to delete belongs to
     * @param front Front text of the card we want to delete
     * @param back Back text of the card we want to delete
     */
    public void deleteCardInDB(String deck_name, String front, String back){
        try{
            ResultSet corresponding_deck_id = connection().createStatement().executeQuery("SELECT deck_id FROM decks WHERE deck_name ='" + deck_name + "'");
            while (corresponding_deck_id.next()) {
                String deck_id = corresponding_deck_id.getString("deck_id");
                PreparedStatement pstmt = connection().prepareStatement("DELETE FROM cards WHERE deck_id=(?) AND front=(?) AND back=(?) and account_id=(?)");
                pstmt.setString(1, deck_id);
                pstmt.setString(2, front);
                pstmt.setString(3, back);
                pstmt.setString(4, AccountInteractor.getCurrentAccount().getUsername());
                pstmt.execute();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Delete a whole deck from the databse.
     * When we delete a deck, we must also delete all its cards
     * @param deck_name Name of the deck we want to delete
     */
    public void deleteDeckInDB(String deck_name){
        try{
            ResultSet corresponding_deck_id = connection().createStatement().executeQuery("SELECT deck_id FROM decks WHERE deck_name ='" + deck_name + "' AND account_id ='" + account.getUsername() + "'");
            while (corresponding_deck_id.next()) {
                String deck_id = corresponding_deck_id.getString("deck_id");
                PreparedStatement pstmt = connection().prepareStatement("DELETE FROM deck WHERE deck_id = (?) AND account_id = (?)");
                pstmt.setString(1, deck_id);
                pstmt.setString(2, AccountInteractor.getCurrentAccount().getUsername());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Add a card to a deck of the users choice to the databse
     * @param deck_name Name of the deck we want to add the card to
     * @param front Front text of the card we want to add
     * @param back Back text of the card we want to add
     * @param image Optional image that the card holds on the back
     */
    public void addCardToDeckInDB(String deck_name, String front, String back, Image image){
        try {
            String currentUsername = AccountInteractor.getCurrentAccount().getUsername();
            ResultSet deck_id = createStatement().executeQuery("SELECT * FROM decks WHERE deck_name = '" + deck_name + "' AND account_id = '" + currentUsername+"'");
            while (deck_id.next()){
                String id = deck_id.getString("deck_id");
                PreparedStatement pstmt = connection().prepareStatement("INSERT INTO cards (account_id, deck_id, front, back, image) VALUES (?, ?, ?, ?, ?)");
                pstmt.setString(1, currentUsername);
                pstmt.setString(2, id);
                pstmt.setString(3, front);
                pstmt.setString(4, back);
                InputStream in = imageToInputStream(image);
                pstmt.setBlob(5, in);
                pstmt.execute();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}

