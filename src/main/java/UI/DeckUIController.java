package UI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DeckUIController {
    @FXML private TextField deckName;
    @FXML private Button newDeckButton;

    @FXML private TextField cardFrontText;
    @FXML private TextField cardBackText;
    @FXML private Button newCardButton;

    /**
     * Generates pop-up to add a new Flashcard
     * @param e action event on click
     * @throws IOException if add-card-view.fxml is not found
     */
    @FXML
    protected void onAddCardButtonClick (ActionEvent e) throws IOException {
        Parent addCardParent = FXMLLoader.load(getClass().getResource("/add-card-view.fxml"));
        Scene addCardScene = new Scene(addCardParent);

        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Stage popUp = new Stage();
        popUp.setTitle("Add New Card");
        popUp.setScene((addCardScene));

        popUp.initModality(Modality.WINDOW_MODAL);
        popUp.initOwner(stage);
        popUp.setX(stage.getX()+20);
        popUp.setY(stage.getY()+20);

        popUp.show();
    }

    @FXML
    protected void onAddCardSubmit (ActionEvent e) {
        //TODO: add card with DeckController.addCard(<deck>, cardFrontText.getText(),
        // <image>, cardFrontText.getText())

        onBackButtonClick(e);
    }

    @FXML
    protected void onEditCardButtonClick (ActionEvent e) throws IOException {
        //TODO: edit card
        onMainMenuButtonClick(e);
    }

    @FXML
    protected void onRenameDeckButtonClick (ActionEvent e) throws IOException {
        //TODO: rename deck
        onMainMenuButtonClick(e);
    }
    @FXML
    protected void onDeleteDeckButtonClick (ActionEvent e) throws IOException {
        //TODO: delete deck
        onMainMenuButtonClick(e);
    }

    /**
     * Returns to previous stage
     * @param e action event on click
     */
    @FXML
    protected void onBackButtonClick(ActionEvent e) {
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.close();
    }

    /**
     * Returns to main stage
     * @param e action event on click
     * @throws IOException if main-view.fxml is not found
     */
    @FXML
    protected void onMainMenuButtonClick(ActionEvent e) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(getClass().getResource("/main-view.fxml"));
        Scene mainMenuScene = new Scene(mainMenuParent);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(mainMenuScene);
        stage.show();
    }
}
