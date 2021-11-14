package FlashcardProgram;

import java.util.Scanner;

/**
 * A class which handles displaying StudySessions to the user.
 */
public class SessionPresenter {

    private final SessionController sessionController;
    private final Scanner input = new Scanner(System.in);

    public SessionPresenter() {
        sessionController = new SessionController();
    }

    /**
     * Displays and begins a StudySession for the user, which displays cards from the session's deck according to the
     * session's algorithms. Ends the session when "exitChar" is typed by the user.
     *
     * @param session The current StudySession for which we are displaying cards.
     * @param exitChar The String that the user types when they want to exit the StudySession.
     */
    public void displaySession(StudySession session, String exitChar) {
        Flashcard card;
        String userInput;
        System.out.println("Beginning session...");
        do {
            System.out.println("Retrieving new card...");
            card = sessionController.getNextCard(session);
            displayFlashcardFront(card);
            String answer = getUserInput("Please input anything to see the back.");
            displayFlashcardBack(card);
            if (card.getBack().equalsIgnoreCase(answer)) {
                sessionController.postAnswerUpdate(session, true);
                System.out.println("You got the answer right!");
            } else {
                sessionController.postAnswerUpdate(session, false);
                System.out.println("You got the answer wrong!");
            }
            userInput = getUserInput("Would you like to see another card? If not, please input \"" + exitChar + "\", " +
                    "and if so, input anything else.");
        } while (!userInput.equals(exitChar));
    }

    /**
     * Display the front of card.
     *
     * @param card The flashcard whose front is displayed.
     */
    private void displayFlashcardFront(Flashcard card) {
        System.out.println("Front: " + card.getFront());
    }

    /**
     * Display the back of card.
     *
     * @param card The flashcard whose back is displayed.
     */
    private void displayFlashcardBack(Flashcard card) {
        System.out.println("Back: " + card.getBack());
    }

    /**
     * Get the user's keyboard input.
     *
     * @return The user's keyboard input as a String.
     */
    private String getUserInput() {
        return input.next();
    }

    /**
     * Display message, then get the user's keyboard input.
     *
     * @param message   The String displayed before asking the user for input.
     * @return          The user's keyboard input as a String.
     */
    private String getUserInput(String message) {
        System.out.println(message);
        return input.next();
    }
}
