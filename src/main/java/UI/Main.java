package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main stage for the GUI
 */
public class Main extends Application {

    /**
     * Starts the stage
     * @param stage automatic new stage
     * @throws IOException if main-view.fxml is not found
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Flashcard Program");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Starts GUI
     * @param args command line argument
     */
    public static void main(String[] args) {
        launch(args);
    }
}
