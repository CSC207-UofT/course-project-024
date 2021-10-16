import java.util.*;

public class FlashcardSystem {
    public DeckController deckController = new DeckController();
    public SessionController sessionController = new SessionController();
    public Scanner scanner = new Scanner(System.in);

    public void displayMainMenu() {
        System.out.println("(0) Logout (1) Create a Deck (2) Select deck to study (3) Edit deck");
        String select = scanner.nextLine();
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

    }

    public Deck displayDecks() {
        //TODO: change format to (0) Back (1) Deck 1 (2) Deck 2 ....
        List<Deck> decks = deckController.decks;
        for (int i=0; i<decks.size(); i++) {
            System.out.println("("+i+") Deck "+ decks.get(i).getName());
        }
        String x = scanner.nextLine();
        //TODO: handle NumberFormatException
        return decks.get(Integer.parseInt(x));
    }

    public Flashcard displayCards(Deck deck) {
        //TODO: change format to (0) Back (1) Card 1 (2) Card 2 ....
        List<Flashcard> flashcards = deck.getFlashcards();
        for (int i=0; i<flashcards.size(); i++) {
            System.out.println("("+i+") Card "+i);
        }
        String x = scanner.nextLine();
        //TODO: handle NumberFormatException
        return flashcards.get(Integer.parseInt(x));
    }

    public void displaySelectDeckToStudyMenu() {
        System.out.println("Which deck would you like to study?");
        Deck deck = displayDecks();
        //TODO: now start a study session for the selected Deck deck
    }

    public void displayCreateDeckMenu() {
        System.out.println("Name of New Deck:");
        String name = scanner.nextLine();
        deckController.createDeck(name);
    }

    public void displayEditDeckMenu() {
        System.out.println("Which deck would you like to edit?");
        Deck deck = displayDecks();
        System.out.println("(0) Back (1) Add a Card (2) Select Card for Edit (3) Rename Deck (4) Delete Deck");
        String select = scanner.nextLine();
        //TODO: check for invalid input
        switch(select) {
            case "0":
                displayMainMenu();
                break;
            case "1":
                displayAddCardMenu(deck);
                break;
            case "2":
                displayEditCardMenu(deck);
                break;
            case "3":
                displayRenameDeckMenu(deck);
                break;
            case "4":
                //TODO: delete deck
                break;
        }
    }

    public void displayRenameDeckMenu(Deck deck) {
        System.out.println("New Name for Deck:");
        String name = scanner.nextLine();
        deckController.renameDeck(deck, name);
    }

    public void displayAddCardMenu(Deck deck) {
        System.out.println("Front of card:");
        String front = scanner.nextLine();
        System.out.println("Back of card:");
        String back = scanner.nextLine();
        deckController.addCard(deck, front, back);
    }

    public void displayEditCardMenu(Deck deck) {
        System.out.println("Which card would you like to edit?");
        Flashcard card = displayCards(deck);
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
                deckController.deleteCard(deck, card);
                break;
        }
    }
}
