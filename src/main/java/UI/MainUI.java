package UI;

import Accounts.AccountInteractor;
import Flashcards.FlashcardDTO;
import Decks.DeckInteractor;
import Decks.DeckController;
import Decks.DeckDTO;
import Accounts.AccountDTO;

import Flashcards.FlashcardInteractor;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class MainUI {
    @FXML private TextField deckName;

    private final DeckController deckController = new DeckController();
    private final AccountDTO account = AccountInteractor.createAccount("user","pwd");

    @FXML private ComboBox<String> deckSelect = new ComboBox<>();
    private final List<String> deckNames = new ArrayList<>();
    private final ObservableList<String> deckObservableList = FXCollections.observableArrayList();

    @FXML private TextField cardFrontText;
    @FXML private TextField cardBackText;
    private Image cardImage;
    @FXML private TextField newDeckName;


    /**
     * updates decks names list of decks on the current account
     */
    @FXML
    protected void setDecks() {
        List<DeckDTO> decks = AccountInteractor.getCurrentAccount().getDecks();
        for (DeckDTO d : decks) {
            deckNames.add(d.getName());
        }
        deckObservableList.setAll(deckNames);
        deckSelect.setItems(deckObservableList);
    }

    protected void setCurrentDeck() {
        String select = deckSelect.getValue();
        for (DeckDTO d : AccountInteractor.getCurrentAccount().getDecks()) {
            if (select.equals(d.getName())) {
                DeckInteractor.setCurrentDeck(DeckInteractor.convertDTOToDeck(d));
            }
        }
    }

    /**
     * initializes main menu with the current account and its decks
     */
    @FXML
    void initialize() {
        //TODO: convert this testing code to actual integration with accounts
        try {
            System.out.println(DeckInteractor.getCurrentDeck().getName()+" deck has cards: "+
                    DeckInteractor.getCurrentDeck().getFlashcards());
            System.out.println("---------------");
        }
        catch(Exception e){
            List<FlashcardDTO> cards = new ArrayList<>();
            cards.add(FlashcardInteractor.createFlashcard("front",null,"back"));
            DeckDTO deck1 = new DeckDTO("test", cards);
            DeckDTO deck2 = new DeckDTO("boot", cards);
            DeckDTO deck3 = new DeckDTO("room", cards);

            AccountInteractor.login(account, account.getPassword());
            AccountInteractor.addDeckToCurrentAccount(deck1);
            AccountInteractor.addDeckToCurrentAccount(deck2);
            AccountInteractor.addDeckToCurrentAccount(deck3);
        }
        setDecks();
    }

    /**
     * Opens Deck creation menu
     * @param e action event on click
     * @throws IOException if create-deck-view.fxml is not found
     */
    @FXML
    protected void onCreateDeckButtonClick(ActionEvent e) throws IOException {
        Parent createDeckParent = FXMLLoader.load(getClass().getResource("/create-deck-view.fxml"));
        Scene createDeckScene = new Scene(createDeckParent);

        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Stage popUp = new Stage();
        popUp.setTitle("Create New Deck");
        popUp.setScene((createDeckScene));

        popUp.initModality(Modality.WINDOW_MODAL);
        popUp.initOwner(stage);
        popUp.setX(stage.getX()+20);
        popUp.setY(stage.getY()+20);

        popUp.show();
        setDecks();
    }

    /**
     * creates a new deck on the current account
     * @param e action event on click
     */
    @FXML
    protected void onCreateDeckSubmit(ActionEvent e) {
        deckController.createDeck(deckName.getText());
        System.out.println(deckName.getText()+" Deck created");
        onBackButtonClick(e);
    }

    /**
     * Opens Deck study menu
     * @param e action event on click
     * @throws IOException if study-deck-view.fxml is not found
     */
    @FXML
    protected void onStudyDeckButtonClick(ActionEvent e) throws IOException {
        Parent studyDeckParent = FXMLLoader.load(getClass().getResource("/study-deck-view.fxml"));
        Scene studyDeckScene = new Scene(studyDeckParent);

        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Stage popUp = new Stage();
        popUp.setTitle("Initialize New Session");
        popUp.setScene((studyDeckScene));

        popUp.initModality(Modality.WINDOW_MODAL);
        popUp.initOwner(stage);
        popUp.setX(stage.getX()+20);
        popUp.setY(stage.getY()+20);

        popUp.show();
    }

    /**
     * Opens study session application
     */
    @FXML
    protected void onStartSessionSubmit() {
        Platform.runLater(
                () -> {
                    try {
                        new LearningSessionUI().start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    /**
     * Opens Deck edit menu
     * @param e action event on click
     * @throws IOException if edit-deck-view.fxml is not found
     */
    @FXML
    protected void onEditDeckButtonClick(ActionEvent e) throws IOException {
        Parent editDeckParent = FXMLLoader.load(getClass().getResource("/edit-deck-view.fxml"));
        Scene editDeckScene = new Scene(editDeckParent);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(editDeckScene);
        stage.show();
    }

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

    /**
     * Gets image file for flashcard from user and displays the uploaded image
     * @param e action event on click
     */
    @FXML
    protected void onUploadFileButtonClick (ActionEvent e) {
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG file (*.JPG)","*.JPG"),
                new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG"),
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png")
        );
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                //TODO: show uploaded image within the application instead
                Desktop.getDesktop().open(file);
                this.cardImage = ImageIO.read(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Creates a new flashcard with the given parameters and adds them to the current deck
     * @param e action event on click
     */
    @FXML
    protected void onAddCardSubmit (ActionEvent e) {
        setCurrentDeck();
        deckController.addCard(cardFrontText.getText(), cardImage, cardBackText.getText());
        System.out.println("Cards in current deck: "+deckController.getCurrentDeck().getFlashcards());
        onBackButtonClick(e);
    }

    @FXML
    protected void onEditCardButtonClick (ActionEvent e) throws IOException {
        //TODO: edit card
        onMainMenuButtonClick(e);
    }

    /**
     * Renames the current deck
     * @param e action event on click
     * @throws IOException if main menu is not found
     */
    @FXML
    protected void onRenameDeckButtonClick (ActionEvent e) throws IOException {
        setCurrentDeck();
        System.out.println("Current deck is: "+deckController.getCurrentDeck().getName());
        deckController.renameCurrentDeck(newDeckName.getText());
        System.out.println("Deck name changed to: "+deckController.getCurrentDeck().getName());
        onMainMenuButtonClick(e);
        setDecks();
    }

    @FXML
    protected void onDeleteDeckButtonClick (ActionEvent e) throws IOException {
        deckController.deleteDeck(deckController.getCurrentDeck());
        System.out.println("Deck removed");
        onMainMenuButtonClick(e);
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
     * Closes the program
     */
    @FXML
    protected void onLogoutButtonClick() {
        Platform.exit();
    }
}
