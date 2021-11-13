import java.util.HashMap;

public class LearningSession extends StudySession implements UpdatingSession {

    /**
     * Construct a new LearningSession
     * @param deck A deck that will be bound to this LearningSession.
     * @param shuffler A CardShuffler that this LearningSession will use
     */
    public LearningSession(Deck deck, ShuffleType shuffler) {
        super(deck);
        this.flashcardToData = new HashMap<>();
        this.deck = deck;
        for (Flashcard card : this.deck.getFlashcards()) {
            this.flashcardToData.put(card, new FlashcardData(0));
        }

        if (shuffler == ShuffleType.SMART) {
            this.cardshuffler = new SmartShuffle(this.flashcardToData);
        }

    }

    /**
     * Returns the next Flashcard to be shown using this LearningSession's CardShuffler.
     * @return A Flashcard chosen by this LearningSession's CardShuffler.
     */
    @Override
    public Flashcard getNextCard() {
        this.currentCard = this.cardshuffler.returnChosenFlashcard();
        return this.currentCard;
    }

    /**
     * Performs any post-answer updates that are needed for studySession.
     * @param studySession The StudySession that needs to be updated
     * @param wasCorrect Whether the user had correctly answered the current Flashcard
     */
    public void postAnswerUpdate(StudySession studySession, boolean wasCorrect) {
        updateFlashcardData(wasCorrect);
    }

    /**
     * Updates this LearningSession's
     * @param wasCorrect
     */
    public void updateFlashcardData(boolean wasCorrect) {
        FlashcardData currFlashcardData = this.flashcardToData.get(this.currentCard);
        if (wasCorrect) {
            currFlashcardData.setCardsUntilDue(currFlashcardData.getCardsUntilDue() * 2 + 1);
        } else { // user got the back wrong
            currFlashcardData.setCardsUntilDue(currFlashcardData.getCardsUntilDue() + 3);
        }

        ((SmartShuffle) this.cardshuffler).updateDeckCopyOrder(this.currentCard);
    }

}
