
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;


public interface DataBaseGateway {
    static void main(String[] args) {
        try {
            String url = "jdbc:mysql://b7da4dd8912b8e:3620922e@us-cdbr-east-04.cleardb.com/heroku_ee9e4fde75342a4?reconnect=true";
            Connection connection = DriverManager.getConnection(url, "b7da4dd8912b8e", "3620922e");
            Statement statement = connection.createStatement();

//            PreparedStatement pstmt = connection.prepareStatement("INSERT INTO images VALUES(?, ?)");
//            pstmt.setString(1, "sample image");
//            InputStream in = new FileInputStream("/Users/abdus/Desktop/google.jpeg");
//            pstmt.setBlob(2, in);
//            pstmt.execute();

            ResultSet results =  statement.executeQuery("SELECT * from images");
            while (results.next()) {
                Blob blob = results.getBlob("image");
                InputStream in = blob.getBinaryStream();
                OutputStream out = new FileOutputStream("someFile.jpeg");
                byte[] buff = new byte[4096];  // how much of the blob to read/write at a time
                int len = 0;

                while ((len = in.read(buff)) != -1) {
                    out.write(buff, 0, len);
                }
                System.out.println(blob);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    default Statement createStatement() {
        try {
            String url = "jdbc:mysql://b7da4dd8912b8e:3620922e@us-cdbr-east-04.cleardb.com/heroku_ee9e4fde75342a4?reconnect=true";
            Connection connection = DriverManager.getConnection(url, "b7da4dd8912b8e", "3620922e");
            return connection.createStatement();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    default Connection connection(){
        try{
            String url = "jdbc:mysql://b7da4dd8912b8e:3620922e@us-cdbr-east-04.cleardb.com/heroku_ee9e4fde75342a4?reconnect=true";
            return DriverManager.getConnection(url, "b7da4dd8912b8e", "3620922e");
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    default ArrayList<Deck> getDecksFromDB(){
        try {
            ArrayList<Deck> deckList = new ArrayList<>();
            ResultSet decks = createStatement().executeQuery("Select * FROM decks");
            while (decks.next()){
                Deck newDeck = DeckInteractor.createDeck(decks.getString("deck_name"));
                ResultSet correspondingCards = createStatement().executeQuery("SELECT * FROM cards WHERE deck_id = '" + decks.getString("deck_id") + "'");
                while (correspondingCards.next()){

                    DeckInteractor.addFlashcard(newDeck, correspondingCards.getString("front"), correspondingCards.getString("back"));
                }
                deckList.add(newDeck);
            }
            return deckList;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    default void insertDeckIntoDB(String table, String column, String Value){
        try {
            PreparedStatement pstmt = connection().prepareStatement("INSERT INTO " + table + "(" + column + ") VALUES ('" + Value + "')");
            pstmt.execute();
//            createStatement().execute ("INSERT INTO " + table + "(" + column + ") VALUES ('" + Value + "')");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    default void updateRowInDB(String table, String column, String oldValue, String newValue){
        try{
            PreparedStatement pstmt = connection().prepareStatement("UPDATE (?) SET (?) = (?) WHERE (?) = (?)");
            pstmt.setString(1, table);
            pstmt.setString(2, column);
            pstmt.setString(3, newValue);
            pstmt.setString(4, column);
            pstmt.setString(5, oldValue);
//            createStatement().executeUpdate("UPDATE "+ table + " SET " + column +" ='"+ newValue + "' WHERE " + column +" = '" + oldValue + "'");
            pstmt.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    default void deleteCardInDB(String deck_name, String front, String back){
        try{
            ResultSet corresponding_deck_id = connection().createStatement().executeQuery("SELECT deck_id FROM decks WHERE deck_name ='" + deck_name + "'");
            while (corresponding_deck_id.next()) {
                String deck_id = corresponding_deck_id.getString("deck_id");
//                createStatement().execute("DELETE FROM cards WHERE deck_id ='" + deck_id + "' AND front ='" + front + "' AND back ='" + back + "'");
                PreparedStatement pstmt =  connection().prepareStatement("DELETE FROM cards WHERE deck_id ='" + deck_id + "' AND front ='" + front + "' AND back ='" + back + "'");
                pstmt.execute();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    default void addCardToDeckInDB (String deck_name, String front, String back, String file_path){
        try {
            ResultSet deck_id = createStatement().executeQuery("SELECT * FROM decks WHERE deck_name = '" + deck_name + "'");
            while (deck_id.next()){
                String id = deck_id.getString("deck_id");
                PreparedStatement pstmt = connection().prepareStatement("INSERT INTO cards (deck_id, front, back, image) VALUES (?, ?, ?, ?)");
                pstmt.setString(1, id);
                pstmt.setString(2, front);
                pstmt.setString(3, back);
                InputStream in = new FileInputStream(file_path);
                pstmt.setBlob(4, in);
                pstmt.execute();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
