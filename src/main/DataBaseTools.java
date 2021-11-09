package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public interface DataBaseTools {
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

    default void insertIntoDatabase(String table, String column, String Value){
        Statement statement = createStatement();
        try {
            statement.execute ("Insert INTO " + table + "(" + column + ") VALUES ('" + Value + "')");
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}
