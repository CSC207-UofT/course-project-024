package UI;

import Database.MySQLDatabaseGateway;
import Accounts.AccountController;
import Decks.DeckController;
import Sessions.SessionController;
import Decks.DeckDTO;
import Flashcards.FlashcardDTO;
import Sessions.LearningSessionDTO;
import Sessions.PracticeSessionDTO;
import Sessions.TestSessionDTO;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class MainUI {
    //initialize create deck UI reference
    @FXML private TextField deckName;
    //initialize current account
    private final MySQLDatabaseGateway DBgateway = new MySQLDatabaseGateway();
    private final DeckController deckController = new DeckController(DBgateway);
    private final SessionController sessionController = new SessionController();
    //initialize decks in current account
    @FXML private ComboBox<String> deckSelect = new ComboBox<>();
    private final List<String> deckNames = new ArrayList<>();
    private final ObservableList<String> deckObservableList = FXCollections.observableArrayList();
    //initialize non-empty decks in current account
    private final List<String> studyDeckNames = new ArrayList<>();
    private final ObservableList<String> studyDeckObservableList = FXCollections.observableArrayList();
    //initialize session start UI references
    @FXML private ComboBox<String> sessionDeckSelect = new ComboBox<>();
    @FXML private ComboBox<String> sessionTypeSelect = new ComboBox<>();
    @FXML private Button startSessionButton;
    //initialize deck edit UI references
    private BufferedImage cardImage;
    @FXML private Label cardCount;
    @FXML private StackPane currentFrontImage;
    @FXML private TextField currentFrontText;
    @FXML private TextField currentBackText;
    @FXML private TextField newDeckName;
    @FXML private TextField newFrontText;
    @FXML private TextField newBackText;
    @FXML private Button renameDeckButton;
    @FXML private Button deleteDeckButton;
    @FXML private Button editCardButton;
    @FXML private Button deleteCardButton;
    @FXML private Button nextCardButton;
    @FXML private Button previousCardButton;
    @FXML private Button currentFrontImageUpload;
    @FXML private Button newFrontImageUpload;

    /**
     * Updates list of deck names on the current account
     */
    protected void setDecks() {
        List<DeckDTO> decks = AccountController.getCurrentAccount().getDecks();
        //add deck names to list
        for (DeckDTO d : decks) {
            deckNames.add(d.getName());
            if (d.getFlashcards().size() > 0) {
                studyDeckNames.add(d.getName());
            }
        }
        //update decks in current account
        deckObservableList.setAll(deckNames);
        deckSelect.setItems(deckObservableList);
        //update decks in current account that can be studied
        studyDeckObservableList.setAll(studyDeckNames);
        sessionDeckSelect.setItems(studyDeckObservableList);
    }

    /**
     * Sets session types to choose from to start a session
     */
    public void setSessionTypes() {
        sessionTypeSelect.setItems((FXCollections.observableArrayList("Practice", "Learning", "Test")));
    }

    /**
     * Initializes main menu with the current account and its decks
     */
    @FXML
    void initialize() {
        setDecks();
        setSessionTypes();
    }

    /**
     * Opens Deck creation menu
     * @param e action event on click
     * @throws IOException if create-deck-view.fxml is not found
     */
    @FXML
    protected void onCreateDeckButtonClick(ActionEvent e) throws IOException {
        Parent createDeckParent = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("/create-deck-view.fxml")));
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
        onBackButtonClick(e);
        setCurrentDeck(deckName.getText());
    }

    /**
     * Opens Deck study menu
     * @param e action event on click
     * @throws IOException if study-deck-view.fxml is not found
     */
    @FXML
    protected void onStudyDeckButtonClick(ActionEvent e) throws IOException {
        Parent studyDeckParent = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("/study-deck-view.fxml")));
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
     * Enables button for starting session if a deck and session type are selected
     */
    @FXML
    protected void onSessionOptionSelected() {
        if (sessionTypeSelect.getValue() != null && sessionDeckSelect.getValue() != null) {
            startSessionButton.setDisable(false);
        }
    }

    /**
     * Opens study session application
     */
    @FXML
    protected void onStartSessionSubmit() {
        //sets current deck
        setCurrentDeck(sessionDeckSelect.getValue());
        //gets type of session to start
        String sessionType = sessionTypeSelect.getValue();
        Platform.runLater(() -> {
            try {
                switch (sessionType) {
                    case "Practice" -> {
                        sessionController.startSession(deckController.getCurrentDeck(), PracticeSessionDTO.class);
                        new SelfGradeSessionUI().start(new Stage());
                    }
                    case "Learning" -> {
                        sessionController.startSession(deckController.getCurrentDeck(), LearningSessionDTO.class);
                        new SelfGradeSessionUI().start(new Stage());
                    }
                    case "Test" -> {
                        sessionController.startSession(deckController.getCurrentDeck(), TestSessionDTO.class);
                        new TestSessionUI().start(new Stage());
                    }
                }
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
        Parent editDeckParent = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("/edit-deck-view.fxml")));
        Scene editDeckScene = new Scene(editDeckParent);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(editDeckScene);
        stage.show();
    }

    /**
     * Update disable property of edit deck UI related to modifying cards
     * @param b new disable value
     */
    protected void setDisableCardUI(boolean b) {
        editCardButton.setDisable(b);
        deleteCardButton.setDisable(b);
        nextCardButton.setDisable(b);
        previousCardButton.setDisable(b);
        currentFrontImageUpload.setDisable(b);
    }

    /**
     * Update disable property of edit deck UI related to modifying decks
     */
    protected void setDisableDeckUI() {
        renameDeckButton.setDisable(false);
        deleteDeckButton.setDisable(false);
        newFrontImageUpload.setDisable(false);
    }

    /**
     * updates current deck
     * @param select name of deck
     */
    protected void setCurrentDeck(String select) {
        for (DeckDTO d : AccountController.getCurrentAccount().getDecks()) {
            if (select.equals(d.getName())) {
                AccountController.selectDeck(d);
            }
        }
    }

    /**
     * Gets current flashcard and updates the display to the current flashcard
     */
    protected void setCardView() {
        //get flashcard values
        FlashcardDTO card = deckController.getCurrentFlashcard();
        String frontText = card.getFrontText();
        String backText = card.getBack();
        currentFrontImage.getChildren().clear();
        //set flashcard image
        if (card.getFrontImage() != null) {
            Image frontImage = SwingFXUtils.toFXImage(card.getFrontImage(), null);
            ImageView frontImageView = new ImageView(frontImage);
            frontImageView.preserveRatioProperty();
            frontImageView.fitWidthProperty().bind(currentFrontImage.widthProperty());
            frontImageView.fitHeightProperty().bind(currentFrontImage.heightProperty());
            currentFrontImage.getChildren().add(frontImageView);
        }
        //set flashcard text
        currentFrontText.setText(frontText);
        currentBackText.setText(backText);
    }

    /**
     * Updates card count
     * @param index index of the current flashcard
     */
    protected void updateCardCount(int index) {
        List<FlashcardDTO> cards = deckController.getCurrentDeck().getFlashcards();
        cardCount.setText("Flashcard "+(index+1)+" of "+cards.size());
    }

    /**
     * Enables deck modification buttons and initializes cards of the current deck
     * once a deck is chosen
     */
    @FXML
    protected void onDeckSelect() {
        //enable deck modification buttons
        setDisableDeckUI();
        //set current deck and set current flashcard to the first flashcard if it exists
        setCurrentDeck(deckSelect.getValue());
        List<FlashcardDTO> cards = deckController.getCurrentDeck().getFlashcards();
        if (cards.size() > 0) {
            //update flashcard view to first flashcard in deck
            deckController.selectFlashcard(cards.get(0));
            updateCardCount(0);
            setCardView();
            setDisableCardUI(false);
        } else {
            //reset flashcard display
            updateCardCount(-1);
            currentFrontImage.getChildren().clear();
            currentFrontText.setText("");
            currentBackText.setText("");
            setDisableCardUI(true);
        }
    }

    /**
     * Returns the index of the current flashcard in the current deck
     * @return index of flashcard
     */
    protected int getCurrentCardIndex() {
        int index = 0;
        List<FlashcardDTO> cards = deckController.getCurrentDeck().getFlashcards();
        FlashcardDTO currentCard = deckController.getCurrentFlashcard();
        for (FlashcardDTO f : cards) {
            if (f.getFrontText().equals(currentCard.getFrontText())) {
                index = cards.indexOf(f);
            }
        }
        return index;
    }

    /**
     * Sets the current flashcard to the next flashcard in the deck and updates
     * the flashcard display. If the current flashcard is the last flashcard in
     * the deck, set the current flashcard to the first card of the deck instead.
     */
    @FXML
    protected void onNextCardButtonClick () {
        int index = getCurrentCardIndex();
        List<FlashcardDTO> cards = deckController.getCurrentDeck().getFlashcards();
        if (index == cards.size() - 1) {
            deckController.selectFlashcard(cards.get(0));
            updateCardCount(0);
        } else {
            deckController.selectFlashcard(cards.get(index + 1));
            updateCardCount(index+1);
        }
        setCardView();
    }

    /**
     * Sets the current flashcard to the previous flashcard in the deck and updates
     * the flashcard display. If the current flashcard is the first flashcard in
     * the deck, set the current flashcard to the last card of the deck instead.
     */
    @FXML
    protected void onPreviousCardButtonClick () {
        int index = getCurrentCardIndex();
        List<FlashcardDTO> cards = deckController.getCurrentDeck().getFlashcards();
        if (index == 0) {
            deckController.selectFlashcard(cards.get(cards.size() - 1));
            updateCardCount(cards.size() - 1);
        } else {
            deckController.selectFlashcard(cards.get(index - 1));
            updateCardCount(index - 1);
        }
        setCardView();
    }

    /**
     * Gets image file for flashcard from user and displays the uploaded image
     * @param e action event on click
     */
    @FXML
    protected void onUploadFileButtonClick (ActionEvent e) {
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        //limit file options to .jpg and .png
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image files (*.JPG, *.PNG)","*.JPG","*.PNG"));
        File file = fileChooser.showOpenDialog(stage);
        //store uploaded file as image object
        if (file != null) {
            try {
                this.cardImage = ImageIO.read(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Creates a new flashcard with the given parameters and adds them to the current deck
     */
    @FXML
    protected void onAddCardSubmit () {
        //add card to current deck
        deckController.addCard(newFrontText.getText(), cardImage, newBackText.getText());
        //reset values
        newFrontText.setText("");
        newBackText.setText("");
        cardImage = null;
        //update current flashcard view
        List<FlashcardDTO> cards = deckController.getCurrentDeck().getFlashcards();
        deckController.selectFlashcard(cards.get(cards.size()-1));
        updateCardCount(cards.size()-1);
        setCardView();
        setDisableCardUI(false);
    }

    /**
     * Updates current flashcard with new front and back text
     */
    @FXML
    protected void onEditCardButtonClick () {
        BufferedImage newCardImage;
        //update image if a file was uploaded
        if (cardImage != null) {
            newCardImage = cardImage;
            //reset stored image
            cardImage = null;
        } else {
            newCardImage = deckController.getCurrentFlashcard().getFrontImage();
        }
        //update text
        String newFrontText = currentFrontText.getText();
        String newBackText = currentBackText.getText();
        deckController.editCurrentFlashcardFront(newFrontText, newCardImage);
        deckController.editCurrentFlashcardBack(newBackText);
        //update flashcard display
        setCardView();
    }

    /**
     * Deletes the current flashcard from its deck
     */
    @FXML
    protected void onDeleteCardButtonClick () {
        //delete current flashcard
        deckController.deleteCard(deckController.getCurrentFlashcard());
        //update flashcard view
        List<FlashcardDTO> cards = deckController.getCurrentDeck().getFlashcards();
        if (cards.size() > 0) {
            //return to first flashcard in deck
            deckController.selectFlashcard(cards.get(0));
            updateCardCount(0);
            setCardView();
        } else {
            //reset flashcard display
            updateCardCount(-1);
            currentFrontImage.getChildren().clear();
            currentFrontText.setText("");
            currentBackText.setText("");
            setDisableCardUI(true);
        }
    }

    /**
     * Renames the current deck
     * @param e action event on click
     * @throws IOException if main menu is not found
     */
    @FXML
    protected void onRenameDeckButtonClick (ActionEvent e) throws IOException {
        deckController.renameCurrentDeck(newDeckName.getText());
        onMainMenuButtonClick(e);
    }

    /**
     * Deletes the current deck and returns to the main menu
     * @param e action event on click
     * @throws IOException if main-view.fxml is not found
     */
    @FXML
    protected void onDeleteDeckButtonClick (ActionEvent e) throws IOException {
        deckController.deleteDeck(deckController.getCurrentDeck());
        onMainMenuButtonClick(e);
    }

    /**
     * Returns to main stage
     * @param e action event on click
     * @throws IOException if main-view.fxml is not found
     */
    @FXML
    protected void onMainMenuButtonClick(ActionEvent e) throws IOException {
        Parent mainMenuParent = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("/main-view.fxml")));
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

    /**
     * Prints contents of current account to the console
     */
    @FXML
    protected void onDebugButtonClick() {
        System.out.println("UN: "+AccountController.getCurrentAccount().getUsername());
        System.out.println("DECKS: ");
        for (DeckDTO d : AccountController.getCurrentAccount().getDecks()) {
            System.out.println(d.getName() + ": " + d.getFlashcards().size());
            for (FlashcardDTO f : d.getFlashcards()) {
                System.out.println("> Card: "+f.getFrontText()+", "+f.getBack());
            }
        }
    }
}
