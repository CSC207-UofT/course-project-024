package Sessions;

import Decks.Deck;
import Decks.DeckDTO;
import Decks.DeckInteractor;
import Flashcards.*;

import java.util.*;

public class SessionInteractor {

    private static StudySession currentSession;

    private SessionInteractor() {}

    /**
     * Set the current StudySession for this interactor to work on and mutate.
     * @param session the session that will be worked on by this interactor.
     */
    public static void setCurrentSession(StudySession session) {
        currentSession = session;
    }

    /**
     * Return a new PracticeSession based on the specified deck.
     * @param deckDTO The deck which this session will be based on
     * @return a new PracticeSession.
     */
    public static StudySessionDTO createPracticeSession(DeckDTO deckDTO) {
        return convertSessionToDTO(new PracticeSession(DeckInteractor.convertDTOToDeck(deckDTO)));
    }

    /**
     * Return a new LearningSession based on the specified deck.
     * @param deckDTO The deck which this session will be based on
     * @return a new LearningSession.
     */
    public static StudySessionDTO createLearningSession(DeckDTO deckDTO) {
        return convertSessionToDTO(new LearningSession(DeckInteractor.convertDTOToDeck(deckDTO)));
    }

    /**
     * @return the current session
     */
    public static StudySessionDTO getCurrentSession() {
        return convertSessionToDTO(currentSession);
    }

    /**
     * Return the next card that should be shown to the user in the current StudySession.
     * @return a FlashcardDTO
     */
    public static FlashcardDTO getNextCard() {
        return FlashcardInteractor.convertFlashcardToDTO(currentSession.getNextCard());
    }

    /**
     * Update the current StudySession's algorithmic metrics on the user's performance on the previous card guess.
     * This will influence what cards the StudySession will give to the user in the future if the StudySession uses
     * a complex card-shuffling algorithm.
     * @param wasCorrect Whether the user guessed the back of the flashcard correctly
     */
    public static void postAnswerUpdate(boolean wasCorrect) {
        if (currentSession.getCardShuffler() instanceof UpdatingShuffler) {
            ((UpdatingShuffler) currentSession.getCardShuffler()).postAnswerFlashcardDataUpdate(wasCorrect);
        }
    }

    /**
     * Get a StudySession entity from its DTO representation.
     * @param sessionDTO The target StudySession
     * @return a StudySession
     */
    public static StudySession convertDTOToSession(StudySessionDTO sessionDTO) {
        if (sessionDTO instanceof PracticeSessionDTO practiceSessionDTO) {
            Deck deck = DeckInteractor.convertDTOToDeck(practiceSessionDTO.getDeckDTO());
            CardShuffler cardShuffler = convertDTOToShuffler(practiceSessionDTO.getCardShufflerDTO());
            return new PracticeSession(deck, cardShuffler);
        } else if (sessionDTO instanceof LearningSessionDTO learningSessionDTO) {
            Deck deck = DeckInteractor.convertDTOToDeck(learningSessionDTO.getDeckDTO());
            CardShuffler cardShuffler = convertDTOToShuffler(learningSessionDTO.getCardShufflerDTO());
            return new LearningSession(deck, cardShuffler);
        } else if (sessionDTO instanceof TestSessionDTO testSessionDTO){
            Deck deck = DeckInteractor.convertDTOToDeck(testSessionDTO.getDeckDTO());
            CardShuffler cardShuffler = convertDTOToShuffler(testSessionDTO.getCardShufflerDTO());
            int length = testSessionDTO.getLength();
            int cardsSeen = testSessionDTO.getCardsSeen();
            int numCorrect = testSessionDTO.getNumCorrect();
            return new TestSession(deck, cardShuffler, length, cardsSeen, numCorrect);
        } else {
            return null;
        }
    }

    /**
     * Get a DTO representation of the specified StudySession.
     * @param session The target StudySession
     * @return a StudySessionDTO
     */
    public static StudySessionDTO convertSessionToDTO(StudySession session) {
        if (session instanceof PracticeSession practiceSession) {
            DeckDTO deckDTO = DeckInteractor.convertDeckToDTO(practiceSession.getDeck());
            CardShufflerDTO shufflerDTO = convertShufflerToDTO(practiceSession.getCardShuffler());
            return new PracticeSessionDTO(deckDTO, shufflerDTO);
        } else if (session instanceof LearningSession learningSession) {
            DeckDTO deckDTO = DeckInteractor.convertDeckToDTO(learningSession.getDeck());
            CardShufflerDTO shufflerDTO = convertShufflerToDTO(learningSession.getCardShuffler());
            return new LearningSessionDTO(deckDTO, shufflerDTO);
        } else if (session instanceof TestSession testSession) {
            DeckDTO deckDTO = DeckInteractor.convertDeckToDTO(testSession.getDeck());
            CardShufflerDTO shufflerDTO = convertShufflerToDTO(testSession.getCardShuffler());
            int length = testSession.getLength();
            int cardsSeen = testSession.getCardsSeen();
            int numCorrect = testSession.getNumCorrect();
            return new TestSessionDTO(deckDTO, shufflerDTO, length, cardsSeen, numCorrect);
        } else {
            return null;
        }
    }

    /**
     * Get a DTO representation of the specified CardShuffler.
     * @param shuffler The target StudySession
     * @return a CardShufflerDTO
     */
    public static CardShufflerDTO convertShufflerToDTO(CardShuffler shuffler) {
        if (shuffler instanceof BasicShuffle basicShuffle) {
            Map<FlashcardDTO, FlashcardDataDTO> flashcardToDataDTO = new HashMap<>();
            List<FlashcardDTO> deckCopy = new ArrayList<>();
            for (Flashcard flashcard : basicShuffle.getDeckCopy()) {
                FlashcardDTO flashcardDTO = FlashcardInteractor.convertFlashcardToDTO(flashcard);
                FlashcardDataDTO dataDTO = FlashcardInteractor.convertFlashcardDataToDTO(
                        basicShuffle.flashcardToData.get(flashcard));
                deckCopy.add(flashcardDTO);
                flashcardToDataDTO.put(flashcardDTO, dataDTO);
            }
            int index = basicShuffle.getIndex();
            return new BasicShuffleDTO(flashcardToDataDTO, deckCopy, index);
        } else if (shuffler instanceof SmartShuffle smartShuffle) {
            Map<FlashcardDTO, FlashcardDataDTO> flashcardToDataDTO = new HashMap<>();
            LinkedList<FlashcardDTO> deckCopy = new LinkedList<>();
            for (Flashcard flashcard : smartShuffle.getDeckCopy()) {
                FlashcardDTO flashcardDTO = FlashcardInteractor.convertFlashcardToDTO(flashcard);
                FlashcardDataDTO dataDTO = FlashcardInteractor.convertFlashcardDataToDTO(
                        smartShuffle.flashcardToData.get(flashcard));
                deckCopy.add(flashcardDTO);
                flashcardToDataDTO.put(flashcardDTO, dataDTO);
            }
            FlashcardDTO lastFlashcardShownDTO = FlashcardInteractor.convertFlashcardToDTO(
                    smartShuffle.getLastFlashcardShown());
            return new SmartShuffleDTO(flashcardToDataDTO, deckCopy, lastFlashcardShownDTO);
        } else {
            return null;
        }
    }

    /**
     * Get a CardShuffler entity from its DTO representation.
     * @param shufflerDTO The target CardShuffler
     * @return a CardShuffler
     */
    public static CardShuffler convertDTOToShuffler(CardShufflerDTO shufflerDTO) {
        if (shufflerDTO instanceof BasicShuffleDTO basicShuffleDTO) {
            Map<Flashcard, FlashcardData> flashcardToData = new HashMap<>();
            List<Flashcard> deckCopy = new ArrayList<>();
            for (FlashcardDTO flashcardDTO : basicShuffleDTO.getDeckCopy()) {
                Flashcard flashcard = FlashcardInteractor.convertDTOToFlashcard(flashcardDTO);
                FlashcardData data = FlashcardInteractor.convertDTOToFlashcardData(
                        basicShuffleDTO.flashcardToData.get(flashcardDTO));
                deckCopy.add(flashcard);
                flashcardToData.put(flashcard, data);
            }
            int index = basicShuffleDTO.getIndex();
            return new BasicShuffle(flashcardToData, deckCopy, index);
        } else if (shufflerDTO instanceof SmartShuffleDTO smartShuffleDTO) {
            Map<Flashcard, FlashcardData> flashcardToData = new HashMap<>();
            LinkedList<Flashcard> deckCopy = new LinkedList<>();
            for (FlashcardDTO flashcardDTO : smartShuffleDTO.getDeckCopy()) {
                Flashcard flashcard = FlashcardInteractor.convertDTOToFlashcard(flashcardDTO);
                FlashcardData data = FlashcardInteractor.convertDTOToFlashcardData(
                        smartShuffleDTO.flashcardToData.get(flashcardDTO));
                deckCopy.add(flashcard);
                flashcardToData.put(flashcard, data);
            }
            Flashcard lastFlashcardShown = FlashcardInteractor.convertDTOToFlashcard(
                    smartShuffleDTO.getLastFlashcardShown());
            return new SmartShuffle(flashcardToData, deckCopy, lastFlashcardShown);
        } else {
            return null;
        }
    }

}
