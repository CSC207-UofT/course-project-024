package UI;

import Accounts.AccountInteractor;
import Flashcards.FlashcardDTO;
import Decks.DeckController;
import Decks.DeckDTO;
import Accounts.AccountDTO;

import Flashcards.FlashcardInteractor;
import Sessions.LearningSessionDTO;
import Sessions.PracticeSessionDTO;
import Sessions.SessionController;
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
    private final DeckController deckController = new DeckController();
    private final SessionController sessionController = new SessionController();
//    private final AccountDTO account = AccountInteractor.createAccount("user","pwd");
    //initialize decks in current account
    @FXML private ComboBox<String> deckSelect = new ComboBox<>();
    private final List<String> deckNames = new ArrayList<>();
    private final ObservableList<String> deckObservableList = FXCollections.observableArrayList();
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
    @FXML private TextField cardFrontText;
    @FXML private TextField cardBackText;
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
        sessionTypeSelect.setItems((FXCollections.observableArrayList("Practice", "Learning", "Test")));
    }

    /**
     * initializes main menu with the current account and its decks
     */
    @FXML
    void initialize() {
        //TODO: convert this testing code to actual integration with accounts
//        try {
//            deckController.getCurrentDeck().getName();
//        }
//        catch(Exception e){
//            List<FlashcardDTO> cards = new ArrayList<>();
//            Image img = new Image("file:img/Flag_of_Canada.svg.png",500, 500, true, true);
//            cards.add(FlashcardInteractor.createFlashcard("front",SwingFXUtils.fromFXImage(img,null),"back"));
//            cards.add(FlashcardInteractor.createFlashcard("foo",SwingFXUtils.fromFXImage(img, null),"bar"));
//            DeckDTO deck1 = new DeckDTO("test", cards);
//            DeckDTO deck2 = new DeckDTO("boot", cards);
//            DeckDTO deck3 = new DeckDTO("room", cards);
//
//            AccountInteractor.login(account, account.getPassword());
//            AccountInteractor.addDeckToCurrentAccount(deck1);
//            AccountInteractor.addDeckToCurrentAccount(deck2);
//            AccountInteractor.addDeckToCurrentAccount(deck3);
//        }
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
        Platform.runLater(
                () -> {
                    try {
                        switch (sessionType) {
                            case "Practice" -> {
                                sessionController.startSession(deckController.getCurrentDeck(), PracticeSessionDTO.class);
                                new PracticeSessionUI().start(new Stage());
                            }
                            case "Learning" -> {
                                sessionController.startSession(deckController.getCurrentDeck(), LearningSessionDTO.class);
                                new LearningSessionUI().start(new Stage());
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
        //get flashcard values
        FlashcardDTO card = FlashcardInteractor.getCurrentFlashcard();
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
        renameDeckButton.setDisable(false);
        deleteDeckButton.setDisable(false);
        //set current deck and set current flashcard to the first flashcard if it exists
        setCurrentDeck(deckSelect.getValue());
        List<FlashcardDTO> cards = deckController.getCurrentDeck().getFlashcards();
        if (cards.size() > 0) {
            deckController.selectFlashcard(cards.get(0));
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
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG file (*.JPG)","*.JPG"),
                new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG"),
                new FileChooser.ExtensionFilter("png files (*.png)", "*.png")
        );
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
        deckController.addCard(cardFrontText.getText(), cardImage, cardBackText.getText());
        //reset values
        cardFrontText.setText("");
        cardBackText.setText("");
        cardImage = null;
        //update current flashcard view
        List<FlashcardDTO> cards = deckController.getCurrentDeck().getFlashcards();
        deckController.selectFlashcard(cards.get(cards.size()-1));
        updateCardCount(cards.size()-1);
        setCardView();
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
            newCardImage = FlashcardInteractor.getCurrentFlashcard().getFrontImage();
        }
        //update text
        String newFrontText = currentFrontText.getText();
        String newBackText = currentBackText.getText();
        FlashcardInteractor.editCurrentFlashcardFront(newFrontText, newCardImage);
        FlashcardInteractor.editCurrentFlashcardBack(newBackText);
        //update flashcard display
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
