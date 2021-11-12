
import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;


public interface DataBaseGateway {
    static void main(String[] args) {
        try {
            String url = "mysql://b7da4dd8912b8e:3620922e@us-cdbr-east-04.cleardb.com/heroku_ee9e4fde75342a4?reconnect=true";
            Connection connection = DriverManager.getConnection("jdbc:" + url, "b7da4dd8912b8e", "3620922e");
            Statement statement = connection.createStatement();

            ResultSet results = statement.executeQuery("SELECT * FROM main");
            while (results.next()) {
                System.out.println(results.getString("User"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    default Statement createStatement() {
        try {
            String url = "mysql://b7da4dd8912b8e:3620922e@us-cdbr-east-04.cleardb.com/heroku_ee9e4fde75342a4?reconnect=true";
            Connection connection = DriverManager.getConnection("jdbc:" + url, "b7da4dd8912b8e", "3620922e");
            return connection.createStatement();
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
            createStatement().execute ("INSERT INTO " + table + "(" + column + ") VALUES ('" + Value + "')");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    default void updateRowInDB(String table, String column, String oldValue, String newValue){
        try{
            createStatement().executeUpdate("UPDATE "+ table + " SET " + column +" ='"+ newValue + "' WHERE " + column +" = '" + oldValue + "'");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    default void deleteCardInDB(String deck_name, String front, String back){
        try{
            ResultSet corresponding_deck_id = createStatement().executeQuery("SELECT deck_id FROM decks WHERE deck_name ='" + deck_name + "'");
            while (corresponding_deck_id.next()) {
                String deck_id = corresponding_deck_id.getString("deck_id");
                createStatement().execute("DELETE FROM cards WHERE deck_id ='" + deck_id + "' AND front ='" + front + "' AND back ='" + back + "'");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    default void addCardToDeckInDB (String deck_name, String front, String back){
        try {
            ResultSet deck_id = createStatement().executeQuery("SELECT * FROM decks WHERE deck_name = '" + deck_name + "'");
            if (deck_id.next()){
                String id = deck_id.getString("deck_id");
                createStatement().execute("INSERT INTO cards (deck_id, front, back) VALUES ('" + id + "', '" + front + "', '" + back +"')");
            } else{
                throw new NoSuchElementException();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }



}
