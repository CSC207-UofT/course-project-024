import java.util.Map;

public class SessionInteractor {
    boolean currentCardStatus = true;

    private SessionInteractor(StudySession session){
        this.session = session;
    }

    // if they get the card right, add one to proficiency
    public static void adjustProficiency(StudySession session, Flashcard flashcard){
        Flashcard card = session.deck.getFlashcards()[session.getCurrentCard()];
        if (currentCardStatus){
            session.getProficiencies().put(card, session.getProficiencies().get(card) + 1);
        }
    }
}
