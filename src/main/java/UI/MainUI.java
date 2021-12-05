package UI;

import Accounts.AccountInteractor;
import Flashcards.FlashcardDTO;
import Decks.DeckController;
import Decks.DeckDTO;
import Accounts.AccountDTO;

import Flashcards.FlashcardInteractor;
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
    @FXML private TextField deckName;

    private final DeckController deckController = new DeckController();
    private final AccountDTO account = AccountInteractor.createAccount("user","pwd");

    @FXML private ComboBox<String> deckSelect = new ComboBox<>();
    private final List<String> deckNames = new ArrayList<>();
    private final ObservableList<String> deckObservableList = FXCollections.observableArrayList();

    @FXML private ComboBox<String> sessionDeckSelect = new ComboBox<>();
    @FXML private ComboBox<String> sessionTypeSelect = new ComboBox<>();
    @FXML private Button startSessionButton;

    @FXML private Label cardCount;
    @FXML private TextField cardFrontText;
    @FXML private TextField cardBackText;
    private BufferedImage cardImage;
    @FXML private TextField newDeckName;

    @FXML private StackPane currentFrontImage;
    @FXML private TextField currentFrontText;
    @FXML private TextField currentBackText;

    @FXML private Button renameDeckButton;
    @FXML private Button deleteDeckButton;

    /**
     * updates list of deck names on the current account
     */
    protected void setDecks() {
        List<DeckDTO> decks = AccountInteractor.getCurrentAccount().getDecks();
        for (DeckDTO d : decks) {
            deckNames.add(d.getName());
        }
        deckObservableList.setAll(deckNames);
        deckSelect.setItems(deckObservableList);
        sessionDeckSelect.setItems(deckObservableList);
    }

    /**
     * Sets session types to choose from to start a session
     */
    public void setSessionTypes() {
        sessionTypeSelect.setItems((FXCollections.observableArrayList("Learning", "Practice", "Study", "Test")));
    }

    /**
     * updates current deck
     * @param select name of deck
     */
    protected void setCurrentDeck(String select) {
        for (DeckDTO d : AccountInteractor.getCurrentAccount().getDecks()) {
            if (select.equals(d.getName())) {
                AccountInteractor.selectDeck(d);
            }
        }
    }

    /**
     * Gets current flashcard and updates the display to the current flashcard
     */
    protected void setCardView() {
        ImageView frontImageView;
        FlashcardDTO card = FlashcardInteractor.getCurrentFlashcard();
        String frontText = card.getFrontText();
        String backText = card.getBack();
        currentFrontImage.getChildren().clear();
        if (card.getFrontImage() != null) {
            Image frontImage = SwingFXUtils.toFXImage((BufferedImage) card.getFrontImage(), null);
            frontImageView = new ImageView(frontImage);
            frontImageView.preserveRatioProperty();
            frontImageView.fitWidthProperty().bind(currentFrontImage.widthProperty());
            frontImageView.fitHeightProperty().bind(currentFrontImage.heightProperty());
            currentFrontImage.getChildren().add(frontImageView);
        }
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
        renameDeckButton.setDisable(false);
        deleteDeckButton.setDisable(false);
        setCurrentDeck(deckSelect.getValue());
        List<FlashcardDTO> cards = deckController.getCurrentDeck().getFlashcards();
        if (cards.size() > 0) {
            deckController.selectFlashcard(0);
            updateCardCount(0);
            setCardView();
        }
    }

    /**
     * Returns the index of the current flashcard in the current deck
     * @return index of flashcard
     */
    protected int getCurrentCardIndex() {
        int index = 0;
        List<FlashcardDTO> cards = deckController.getCurrentDeck().getFlashcards();
        FlashcardDTO currentCard = FlashcardInteractor.getCurrentFlashcard();
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
            deckController.selectFlashcard(0);
            updateCardCount(0);
        } else {
            deckController.selectFlashcard(index + 1);
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
            deckController.selectFlashcard(cards.size() - 1);
            updateCardCount(cards.size() - 1);
        } else {
            deckController.selectFlashcard(index - 1);
            updateCardCount(index - 1);
        }
        setCardView();
    }
    /**
     * initializes main menu with the current account and its decks
     */
    @FXML
    void initialize() {
        //TODO: convert this testing code to actual integration with accounts
        try {
            deckController.getCurrentDeck().getName();
        }
        catch(Exception e){
            List<FlashcardDTO> cards = new ArrayList<>();
            Image img = new Image("file:img/Flag_of_Canada.svg.png",500, 500, true, true);
            cards.add(FlashcardInteractor.createFlashcard("front",SwingFXUtils.fromFXImage(img,null),"back"));
            cards.add(FlashcardInteractor.createFlashcard("foo",SwingFXUtils.fromFXImage(img, null),"bar"));
            DeckDTO deck1 = new DeckDTO("test", cards);
            DeckDTO deck2 = new DeckDTO("boot", cards);
            DeckDTO deck3 = new DeckDTO("room", cards);

            AccountInteractor.login(account, account.getPassword());
            AccountInteractor.addDeckToCurrentAccount(deck1);
            AccountInteractor.addDeckToCurrentAccount(deck2);
            AccountInteractor.addDeckToCurrentAccount(deck3);
        }
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
        //gets current deck
        setCurrentDeck(sessionDeckSelect.getValue());
        //gets type of session to start
        String sessionType = sessionTypeSelect.getValue();
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
        Parent editDeckParent = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("/edit-deck-view.fxml")));
        Scene editDeckScene = new Scene(editDeckParent);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(editDeckScene);
        stage.show();
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
        deckController.addCard(cardFrontText.getText(), cardImage, cardBackText.getText());
        //reset values
        cardFrontText.setText("");
        cardBackText.setText("");
        cardImage = null;
        //update current flashcard view
        int newCardIndex = deckController.getCurrentDeck().getFlashcards().size()-1;
        deckController.selectFlashcard(newCardIndex);
        updateCardCount(newCardIndex);
        setCardView();
    }

    /**
     * Updates current flashcard with new front and back text
     */
    @FXML
    protected void onEditCardButtonClick () {
        BufferedImage newCardImage;
        if (cardImage != null) {
            newCardImage = cardImage;
            cardImage = null;
        } else {
            newCardImage = (BufferedImage) FlashcardInteractor.getCurrentFlashcard().getFrontImage();
        }
        String newFrontText = currentFrontText.getText();
        String newBackText = currentBackText.getText();
        FlashcardInteractor.editCurrentFlashcardFront(newFrontText, newCardImage);
        FlashcardInteractor.editCurrentFlashcardBack(newBackText);
        setCardView();
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
        System.out.println("UN: "+AccountInteractor.getCurrentAccount().getUsername());
        System.out.println("DECKS: ");
        for (DeckDTO d : AccountInteractor.getCurrentAccount().getDecks()) {
            System.out.println(d.getName() + ": " + d.getFlashcards().size());
            for (FlashcardDTO f : d.getFlashcards()) {
                System.out.println("> Card: "+f.getFrontText()+", "+f.getBack());
            }
        }
    }
}
