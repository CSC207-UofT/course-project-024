import java.util.Map;

public class SessionInteractor {
    StudySession session;
    boolean currentCardStatus = true;

    public SessionInteractor(StudySession session){
        this.session = session;
    }

    // if they get the card right, add one to proficiency
    public void adjustProficiency(){
        Flashcard card = this.session.deck.getFlashcards()[this.session.currentCard];
        if (currentCardStatus){
            this.session.proficiencies.put(card, this.session.proficiencies.get(card) + 1);
        }
    }
}
