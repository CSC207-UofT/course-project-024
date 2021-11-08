package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseTest {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://34.124.113.162:3306/main", "root", "csc207-group-024");
            Statement statement = connection.createStatement();

            ResultSet results = statement.executeQuery("select * from test");
            while (results.next()) {
                System.out.println(results.getString("username"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
