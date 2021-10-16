import java.util.Map;

public class SessionInteractor {
    // TODO: remove this attribute to be handled elsewhere
    private static boolean currentCardStatus = true;

    private SessionInteractor() {
    }

    public static StudySession createPracticeSession(Deck deck) {
        return new PracticeSession(deck);
    }

    // if they get the card right, add one to proficiency
    // TODO: Move this method into StudySession implementers to be customized by calling session.adjustProficiency()
    public static void adjustProficiency(StudySession session, Flashcard flashcard){
        // Flashcard card = session.deck.getFlashcards()[session.getCurrentCard()];
        Flashcard card = session.deck.getFlashcards().get(session.getCurrentCard());
        if (currentCardStatus){
            session.getProficiencies().put(card, session.getProficiencies().get(card) + 1);
        }
    }
}
