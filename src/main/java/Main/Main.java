package Main;

import FlashcardProgram.FlashcardSystem;
import UI.LearningSessionUI;
import UI.PracticeSessionUI;
import UI.TestSessionUI;

public class Main {
    public static void main(String[] args) {
//        FlashcardSystem fs = new FlashcardSystem();
//        fs.displayMainMenu();
        // TODO: properly use modules instead of this hack for JavaFX
        // TestSessionUI.main(args);
        // PracticeSessionUI.main(args);
        LearningSessionUI.main(args);
        // UI.MainUI.main(args);
    }
}
