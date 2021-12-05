package FlashcardProgram;

import Accounts.AccountDTO;
import Accounts.AccountInteractor;
import Decks.DeckController;
import Decks.DeckDTO;
import Flashcards.FlashcardDTO;
import Sessions.LearningSessionDTO;
import Sessions.PracticeSessionDTO;
import Sessions.SessionController;
import Sessions.StudySessionDTO;

import java.util.*;

public class FlashcardSystem{
    private final DeckController deckController = new DeckController();
    private final SessionController sessionController = new SessionController();
    private final SessionPresenter sessionPresenter = new SessionPresenter();
    private final Scanner scanner = new Scanner(System.in);
    private final AccountDTO account = AccountInteractor.createAccount("User", "Pass");

    public FlashcardSystem() {
        AccountInteractor.login(account, "Pass");
    }

    public void displayMainMenu() {
        String select;
        do {
            System.out.println("(0) Logout (1) Create a Deck (2) Select deck to study (3) Edit deck");
            select = scanner.nextLine();
            //TODO: check for invalid input
            switch(select) {
                case "0":
                    break;
                case "1":
                    displayCreateDeckMenu();
                    break;
                case "2":
                    displaySelectDeckToStudyMenu();
                    break;
                case "3":
                    displayEditDeckMenu();
                    break;
            }
        } while(!select.equals("0"));

    }

    private DeckDTO displayDecks() {
        //TODO: change format to (0) Back (1) Deck 1 (2) Deck 2 ....
        List<DeckDTO> decks = AccountInteractor.getCurrentAccount().getDecks();
        for (int i=0; i<decks.size(); i++) {
            System.out.println("("+i+") Deck "+ decks.get(i).getName());
        }
        String x = scanner.nextLine();
        //TODO: handle NumberFormatException
        return decks.get(Integer.parseInt(x));
    }

    private int displayCards() {
        //TODO: change format to (0) Back (1) Card 1 (2) Card 2 ....
        List<FlashcardDTO> flashcards = deckController.getCurrentDeck().getFlashcards();
        for (int i=0; i<flashcards.size(); i++) {
            System.out.println("("+i+") Card "+ flashcards.get(i).getFrontText());
        }
        String x = scanner.nextLine();
        //TODO: handle NumberFormatException
        return Integer.parseInt(x);
    }

    private void displaySelectDeckToStudyMenu() {
        System.out.println("Which deck would you like to study?");
        DeckDTO deck = displayDecks();
        AccountInteractor.selectDeck(deck);
        StudySessionDTO session = displaySelectSessionMenu();
        AccountInteractor.selectSession(deckController.getCurrentDeck(), session);
        sessionPresenter.displaySession(session, "-1");
    }

    private StudySessionDTO displaySelectSessionMenu() {
        System.out.println("Which session would you like to study?");
        System.out.println("(0) Practice (1) Learning");
        String select = scanner.nextLine();
        //TODO: check for invalid input
        if (select.equals("0")) {
            sessionController.startSession(deckController.getCurrentDeck(), PracticeSessionDTO.class);
            return sessionController.getCurrentSession();
        } else if (select.equals("1")) {
            sessionController.startSession(deckController.getCurrentDeck(), LearningSessionDTO.class);
            return sessionController.getCurrentSession();
        }
        // TODO: remove once invalid input is properly handled
        return null;
    }

    private void displayCreateDeckMenu() {
        System.out.println("Name of New Deck:");
        String name = scanner.nextLine();
        deckController.createDeck(name);
    }

    private void displayEditDeckMenu() {
        System.out.println("Which deck would you like to edit?");
        DeckDTO deck = displayDecks();
        AccountInteractor.selectDeck(deck);
        System.out.println("(0) Back (1) Add a Card (2) Select Card for Edit (3) Rename Deck (4) Delete Deck");
        String select = scanner.nextLine();
        //TODO: check for invalid input
        switch(select) {
            case "0":
                displayMainMenu();
                break;
            case "1":
                displayAddCardMenu();
                break;
            case "2":
                displayEditCardMenu();
                break;
            case "3":
                displayRenameDeckMenu();
                break;
            case "4":
                //TODO: delete deck
                break;
        }
    }

    private void displayRenameDeckMenu() {
        System.out.println("New Name for Deck:");
        String name = scanner.nextLine();
        deckController.renameCurrentDeck(name);
    }

    private void displayAddCardMenu() {
        System.out.println("Front of card:");
        String frontInput = scanner.nextLine();
        System.out.println("Back of card:");
        String back = scanner.nextLine();
        deckController.addCard(frontInput, null, back);
    }

    private void displayEditCardMenu() {
        System.out.println("Which card would you like to edit?");
        int index = displayCards();
        deckController.selectFlashcard(index);
        System.out.println("(0) Back (1) Edit Front (2) Edit Back (3) Delete");
        String select = scanner.nextLine();
        //TODO: check for invalid input
        switch(select) {
            case "0":
                displayEditDeckMenu();
                break;
            case "1":
                //TODO: edit back
                break;
            case "2":
                //TODO: edit front
                break;
            case "3":
                deckController.deleteCard(index);
                break;
        }
    }
}
