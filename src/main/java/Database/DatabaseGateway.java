package Database;

import Accounts.Account;
import Accounts.AccountInteractor;
import Decks.DeckDTO;
import Decks.DeckInteractor;
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

    public Boolean authenticateAccount(){

    }

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

    public static Connection connection(){
        try{
            String url = "jdbc:mysql://b7da4dd8912b8e:3620922e@us-cdbr-east-04.cleardb.com/heroku_ee9e4fde75342a4?reconnect=true";
            return DriverManager.getConnection(url, "b7da4dd8912b8e", "3620922e");
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public InputStream imageToInputStream(Image image) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),    BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, null, null);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", outStream);
        return new ByteArrayInputStream(outStream.toByteArray());
    }



    public ArrayList<DeckDTO> getDecksFromDB(){
        try {
            ArrayList<DeckDTO> deckList = new ArrayList<>();
            String accountUsername = AccountInteractor.getCurrentAccount().getUsername();
            ResultSet decks = createStatement().executeQuery("Select * FROM decks WHERE account_id = '" + accountUsername +"'");
            while (decks.next()){
                DeckDTO newDeck = DeckInteractor.createDeck(decks.getString("deck_name"));
                ResultSet correspondingCards = createStatement().executeQuery("SELECT * FROM cards WHERE deck_id = '" + decks.getString("deck_id") + "'");
                while (correspondingCards.next()){
                    Blob imageBlob = correspondingCards.getBlob("Image");
                    InputStream in = imageBlob.getBinaryStream();
                    Image currentCardImage = ImageIO.read(in);
                    DeckInteractor.addFlashcardToCurrentDeck(correspondingCards.getString("front"), currentCardImage, correspondingCards.getString("back"));
                }
                deckList.add(newDeck);
            }
            return deckList;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void addDeckToDB(String deck_name){
        try {
            PreparedStatement pstmt = connection().prepareStatement("INSERT INTO decks (account_id, deck_name) VALUES (?, ?)");
            pstmt.setString(1, AccountInteractor.getCurrentAccount().getUsername());
            pstmt.setString(2, deck_name);
            pstmt.execute();

        } catch (Exception e){
            e.printStackTrace();
        }
    }

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

    public void deleteDeckInDB(Account account, String deck_name){
        try{
            ResultSet corresponding_deck_id = connection().createStatement().executeQuery("SELECT deck_id FROM decks WHERE deck_name ='" + deck_name + "' AND account_id ='" + account.getUsername() + "'");
            while (corresponding_deck_id.next()) {
                String deck_id = corresponding_deck_id.getString("deck_id");
                PreparedStatement pstmt = connection().prepareStatement("DELETE FROM deck WHERE deck_id = (?) AND account_id = (?)");
                pstmt.setString(1, deck_id);
                pstmt.setString(2, account.getUsername());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addCardToDeckInDB (String deck_name, String front, String back, Image image){
        try {
            ResultSet deck_id = createStatement().executeQuery("SELECT * FROM decks WHERE deck_name = '" + deck_name + "'");
            while (deck_id.next()){
                String id = deck_id.getString("deck_id");
                PreparedStatement pstmt = connection().prepareStatement("INSERT INTO cards (account_id, deck_id, front, back, image) VALUES (?, ?, ?, ?, ?)");
                pstmt.setString(1, AccountInteractor.getCurrentAccount().getUsername());
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

