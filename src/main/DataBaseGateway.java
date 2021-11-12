package main;

import java.sql.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;


public interface DataBaseGateway {
    static void main(String[] args) {
        try {
            String url = "mysql://b7da4dd8912b8e:3620922e@us-cdbr-east-04.cleardb.com/heroku_ee9e4fde75342a4?reconnect=true";
            Connection connection = DriverManager.getConnection("jdbc:" + url, "b7da4dd8912b8e", "3620922e");
            Statement statement = connection.createStatement();

            ResultSet results = statement.executeQuery("select * from main");
            while (results.next()) {
                System.out.println(results.getString("User"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    default ArrayList<Deck> getDecksFromDB(){
        try {
            ArrayList<Deck> deckList = new ArrayList<Deck>();
            ResultSet decks = createStatement().executeQuery("Select * from decks");
            while (decks.next()){
                Deck newDeck = DeckInteractor.createDeck(decks.getString("deck_name"));
                deckList.add(DeckInteractor.createDeck(decks.getString("deck_name")));
                ResultSet correspondingCards = createStatement().executeQuery("Select * from cards where deck_id = '" + decks.getString("deck_id") + "'");
                while (correspondingCards.next()){
                    DeckController.addCard(newDeck, correspondingCards.getString("front"), correspondingCards.getString("back"));
                }
                deckList.add(newDeck);
            }
            return deckList;
        } catch (Exception e){
            e.printStackTrace();
            return null;
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

    default void insertDeckIntoDB(String table, String column, String Value){
        Statement statement = createStatement();
        try {
            statement.execute ("Insert INTO " + table + "(" + column + ") VALUES ('" + Value + "')");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    default void updateDeckInDB(String table, String column, String oldValue, String newValue){
        Statement statement = createStatement();
        try{
            statement.executeUpdate("Update "+ table + " SET " + column +" ='"+ newValue + "' Where " + column +" = '" + oldValue + "'");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    default void deleteDBRow(String table, String column, String valueToDelete){
        Statement statement = createStatement();
        try{
            statement.execute("Delete FROM " + table + " WHERE " + column + " ='" + valueToDelete +"'");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    default void addCardToDeckInDB (String deck_name, String front, String back){
        try {
            ResultSet deck_id = createStatement().executeQuery("SELECT * FROM decks Where deck_name = '" + deck_name + "'");
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
