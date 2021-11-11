import java.util.HashMap;

public class LearningSession extends StudySession implements UpdatingSession {

    public LearningSession(Deck deck) {
        super(deck);
        this.flashcardData = new HashMap<>();
        this.deck = deck;
        for (Flashcard card : this.deck.getFlashcards()) {
            this.flashcardData.put(card, new FlashcardData(0));
        }
        this.cardshuffler = new SmartShuffle(this.flashcardData);

    }

    @Override
    public Flashcard getNextCard() {
        this.currentCard = this.cardshuffler.returnChosenFlashcard();
        return this.currentCard;
    }

    public void postAnswerUpdate(StudySession studySession, boolean wasCorrect) {
        updateFlashcardData(wasCorrect);
    }

    public void updateFlashcardData(boolean wasCorrect) {
        FlashcardData currFlashcardData = this.flashcardData.get(this.currentCard);
        if (wasCorrect) {
            currFlashcardData.setCardsUntilDue(currFlashcardData.getCardsUntilDue() * 2 + 1);
        } else { // user got the back wrong
            currFlashcardData.setCardsUntilDue(currFlashcardData.getCardsUntilDue() + 5);
        }

        ((SmartShuffle) this.cardshuffler).updateLinkedListDeck(this.currentCard);
    }

}
