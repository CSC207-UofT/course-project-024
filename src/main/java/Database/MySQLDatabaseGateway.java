package Database;

import Accounts.AccountDTO;
import Decks.DeckDTO;
import Flashcards.FlashcardDTO;
import Sessions.StudySessionDTO;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.List;

/**
 * This class implements DatabaseGateway with a MySQL concreteness, giving methods to communicate with a
 * MySQl database server.
 */
public class MySQLDatabaseGateway implements DatabaseGateway {

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
     * @return Whether this username-password pair exists in the database
     */
    public Boolean authenticateAccount(String username, String password){
        try {
            ResultSet account = createStatement().executeQuery("Select * FROM accounts WHERE username = '" + username +"'");
            while(account.next()) {
                String correctPassword = account.getString("password");
                if (correctPassword.equals(password)){
                    return true;
                }
            } return false;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Returns the account with the given username and password
     * @param username The username of the account
     * @param password The password of the account
     * @return The AccountDTO that has the same username and password
     */
    public AccountDTO getAccountFromDB(String username, String password){
        try{
            List<DeckDTO> deckDTOS = getDecksFromDB(username);
            Map<DeckDTO, List<StudySessionDTO>> deckDTOListMap = new HashMap<>();
            for (DeckDTO deckDTO: deckDTOS) {
                deckDTOListMap.put(deckDTO, new ArrayList<>());
            }
            return new AccountDTO(username, password, deckDTOS, deckDTOListMap);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Finds if an account with username already exists
     * @param username The username that will be checked for duplicates
     * @return Whether an account with the same username already exists.
     */
    public Boolean duplicateAccount(String username){
        try {
            ResultSet account = createStatement().executeQuery("Select * FROM accounts WHERE username = '" + username + "'");
            while (account.next()){
                String foundUsername = account.getString("username");
                if (Objects.equals(foundUsername, "null") || foundUsername.length() > 0) {
                    return true;
                }
            } return false;
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Add an account with the given username and password to the database.
     * @param username The username of the account
     * @param password The password of the account
     */
    public void addAccountToDB(String username, String password){
        try {
            PreparedStatement pstmt = connection().prepareStatement("INSERT INTO accounts VALUES (?, ?)");
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Convert an image to an InputStream object.
     * This will be used to store images from flashcards into the database as BLOBs
     * @param image Image object to be converted and stored into database
     * @return InputStream object that was built using the information from the image
     */
    public InputStream imageToInputStream(BufferedImage image){
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
     * This method is mainly used to initialize an instance of  program
     * @param accountUsername The username of the account that the returned decks will be assigned to
     * @return ArrayList of DeckDTOs that will then be added to the current accounts deck list
     */
    public List<DeckDTO> getDecksFromDB(String accountUsername){
        try {
            List<DeckDTO> deckList = new ArrayList<>();
            ResultSet decks = createStatement().executeQuery("Select * FROM decks WHERE account_id = '" + accountUsername +"'");
            while (decks.next()){
                ResultSet correspondingCards = createStatement().executeQuery("SELECT * FROM cards WHERE deck_id = '" + decks.getString("deck_id") + "'");
                List<FlashcardDTO> flashcardList = new ArrayList<>();
                while (correspondingCards.next()){
                    Blob imageBlob = correspondingCards.getBlob("Image");
                    if (imageBlob == null) {
                        FlashcardDTO newFlashcardDTO =  new FlashcardDTO(correspondingCards.getString("front"), null, correspondingCards.getString("back"));
                        flashcardList.add(newFlashcardDTO);
                    } else {
                        InputStream in = imageBlob.getBinaryStream();
                        BufferedImage currentCardImage = ImageIO.read(in);
                        FlashcardDTO newFlashcardDTO =  new FlashcardDTO(correspondingCards.getString("front"), currentCardImage, correspondingCards.getString("back"));
                        flashcardList.add(newFlashcardDTO);
                    }
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
     * @param accountUsername Username of the account that this deck will belong to
     * @param deck_name Name of the deck that will be added
     */
    public void addDeckToDB(String accountUsername, String deck_name){
        try {
            PreparedStatement pstmt = connection().prepareStatement("INSERT INTO decks (account_id, deck_name) VALUES (?, ?)");
            pstmt.setString(1, accountUsername);
            pstmt.setString(2, deck_name);
            pstmt.execute();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Update a single field of some row in the database
     * @param oldValue Previous value of the field we want to update
     * @param newValue New value of the field we want to update
     */
    public void updateCardFrontTextInDB(String oldValue, String newValue){
        try{
            PreparedStatement pstmt = connection().prepareStatement("UPDATE cards SET front = (?) WHERE front = (?)");
            pstmt.setString(1, newValue);
            pstmt.setString(2, oldValue);

            // System.out.println("UPDATE '" + table + "' SET '" + column + "' = '" + newValue + "' WHERE '" + column + "' = '" + oldValue +"'")

            pstmt.execute();
            pstmt.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Updates a flashcard with a given back with its new value.
     * @param oldValue The old back of the flashcard
     * @param newValue The new back of the flashcard
     */
    public void updateCardBackInDB(String oldValue, String newValue){
        try{
            PreparedStatement pstmt = connection().prepareStatement("UPDATE cards SET back = (?) WHERE back = (?)");
            pstmt.setString(1, newValue);
            pstmt.setString(2, oldValue);

            // System.out.println("UPDATE '" + table + "' SET '" + column + "' = '" + newValue + "' WHERE '" + column + "' = '" + oldValue +"'")

            pstmt.execute();
            pstmt.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Updates the deck name in the database after its name has been locally changed.
     * @param oldValue The old name of the deck
     * @param newValue The new name of the deck
     */
    public void updateDeckNameInDB(String oldValue, String newValue){
        try{
            PreparedStatement pstmt = connection().prepareStatement("UPDATE decks SET deck_name = (?) WHERE deck_name = (?)");
            pstmt.setString(1, newValue);
            pstmt.setString(2, oldValue);

            // System.out.println("UPDATE '" + table + "' SET '" + column + "' = '" + newValue + "' WHERE '" + column + "' = '" + oldValue +"'");

            pstmt.execute();
            pstmt.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Changes a flashcard's image in the database after it was changed locally.
     * @param oldText The flashcard's front text
     * @param newImage The image that will replace the old image
     */
    public void editFlashcardImage(String oldText, BufferedImage newImage){
        try{
            PreparedStatement pstmt = connection().prepareStatement("UPDATE cards SET image = (?) WHERE front = (?)");

            if (newImage != null) {
                InputStream in = imageToInputStream(newImage);
                pstmt.setBlob(1, in);
                pstmt.setString(2, oldText);
                pstmt.execute();
                pstmt.close();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Delete a card in the database
     * @param accountUsername Username of the account that the card we want to delete belongs to
     * @param deck_name Name of the deck that the card we want to delete belongs to
     * @param front Front text of the card we want to delete
     */
    public void deleteCardInDB(String accountUsername, String deck_name, String front){
        try{
            ResultSet corresponding_deck_id = connection().createStatement().executeQuery("SELECT deck_id FROM decks WHERE deck_name ='" + deck_name + "'");
            while (corresponding_deck_id.next()) {
                String deck_id = corresponding_deck_id.getString("deck_id");
                PreparedStatement pstmt = connection().prepareStatement("DELETE FROM cards WHERE deck_id=(?) AND front=(?) AND account_id=(?)");
                pstmt.setString(1, deck_id);
                pstmt.setString(2, front);
                pstmt.setString(3, accountUsername);
                pstmt.execute();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Delete a whole deck from the database.
     * When we delete a deck, we must also delete all its cards
     * @param accountUsername Username of the account that the deck we want to delete belongs to
     * @param deck_name Name of the deck we want to delete
     */
    public void deleteDeckInDB(String accountUsername, String deck_name){
        try{
            ResultSet corresponding_deck_id = connection().createStatement().executeQuery("SELECT deck_id FROM decks WHERE deck_name ='" + deck_name + "' AND account_id ='" + accountUsername + "'");
            while (corresponding_deck_id.next()) {
                String deck_id = corresponding_deck_id.getString("deck_id");
                PreparedStatement pstmt = connection().prepareStatement("DELETE FROM deck WHERE deck_id = (?) AND account_id = (?)");
                pstmt.setString(1, deck_id);
                pstmt.setString(2, accountUsername);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Add a card to a deck of the users choice to the database
     * @param accountUsername User name of the account that the added card will belong to
     * @param deck_name Name of the deck we want to add the card to
     * @param front Front text of the card we want to add
     * @param back Back text of the card we want to add
     * @param image Optional image that the card holds on the back
     */
    public void addCardToDeckInDB(String accountUsername, String deck_name, String front, String back, BufferedImage image){
        try {
            ResultSet deck_id = createStatement().executeQuery("SELECT * FROM decks WHERE deck_name = '" + deck_name + "' AND account_id = '" + accountUsername+"'");
            while (deck_id.next()){
                String id = deck_id.getString("deck_id");
                PreparedStatement pstmt = connection().prepareStatement("INSERT INTO cards (account_id, deck_id, front, back, image) VALUES (?, ?, ?, ?, ?)");
                pstmt.setString(1, accountUsername);
                pstmt.setString(2, id);
                pstmt.setString(3, front);
                pstmt.setString(4, back);
                if (image == null){
                    pstmt.setString(5, null);
                }else {
                    InputStream in = imageToInputStream(image);
                    pstmt.setBlob(5, in);
                }
                pstmt.execute();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}

