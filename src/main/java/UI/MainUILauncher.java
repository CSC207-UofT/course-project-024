package UI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main stage for the GUI
 */
public class MainUILauncher extends Application {

    /**
     * Starts the stage
     * @param stage automatic new stage
     * @throws IOException if main-view.fxml is not found
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainUILauncher.class.getResource("/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Flashcards!");
        stage.setScene(scene);
        stage.show();
    }
}
