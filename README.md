# Flashcard Program

Our project is a program designed to allow users to create, study, and organize flashcards for whatever tasks they need. Users log into accounts using their username and password, and their accounts hold all of the user’s flashcards, decks, and other pieces of data permanently. Once logged in, users may access and edit their “decks” of flashcards, identified by name. Users can also run different types of study sessions with these decks based on their requirements, namely the Learning, Practice, amd Test sessions.

Practice session generates infinitely generates cards.
Learning session also infinitely generates cards based on the user’s feedback and proficiency with the cards and orders them from worst proficiency to best proficiency.
Test session generates a finite number of random cards and tracks the percentage of cards that the user gets correct.

## Installation instructions

When opening the project in an IDE, make sure to mark the following: `src/main/java` folder as sources root, `src/test` as Test sources root and `src/main/resources` as Resources root.

Before being able to run the program, you must first install certain dependecies.

JDBC is a Java API used to connect and interact with databases. To install it follow the instructions below:
1. At the top of IntelliJ go to File -> Project Structure -> Modules -> Dependencies
2. Click the + button, click on "JARs or directories" , then find "mysql-connector-java" under the "lib" folder in the project folder
3. Apply changes

JavaFX is a library that provides the tools required to make simple GUIs. To install it follow the instructions below:
1. At the top of IntelliJ go to File -> Project Structure -> Global Libraries
2. Click the + button, click on Java, open the "lib" folder in the project folder, open the JavaFX sdk folder, then select the "lib" folder to add from there
3. Apply changes

JUnit is used to unit test our program. To import it:
1. Navigate to `src\test\` and open any class there. 
2. Look at the top of the class for any import that resembles import "`org.junit.jupiter.api`"
3. Hover over the "`junit`" part and let IntelliJ help you import the required module.
4. Hover over the "`jupiter`" part and let IntelliJ help you import the required module.

## Running the program

To start the program, navigate to `src\main\java\Main\Main.java` and run the main method.

The Login window then opens up and prompts the user to enter login details. At this point, you can create an account if you don't have one yet and then log in, or log in with existing user credentials.

Note that creating a new account is successful if no popup appears after clicking the button.

This brings us to the main menu where we are presented with four options: creating a new deck, studying an existing deck, editing an existing deck, or logging out.

### Creating a new deck

You can do so by clicking on the "Create New Deck" button and entering a name for the new deck.

### Adding Flashcards

You can do so by clicking on the "Edit Existing Deck" button. On the right hand side, select the deck to which you want to add a flashcard to and enter the front text and back text you want the card to display. You can also add an image to the front of the card by uploading a file if you wish to. Click on the "Add Card" button to add this card to your selected deck.

### Editing and deleting Flashcards

You can edit a flashcard by clicking on the "Edit Existing Deck" button. Select the required deck and navigate to the flashcard you want to make edits on. Edit the front and back on the left hand side of the screen and save the edits. 

Similarly, you can delete a card by navigating to the required flashcard and clicking the "Delete Card" button on the left hand side of the screen.

### Renaming and deleting the deck

You can rename a deck by clicking on the "Edit Existing Deck" button. Navigate to the required deck and enter the new name on the left hand side to rename it. 

You can delete a deck by navigating to the required deck and clicking the "Delete Deck" button on the right hand side.

### Running Study Sessions

You can start a study session by clicking on the "Study Existing Deck" button on the main menu. This prompts the user to select a deck from the prior decks created by the user and a type of study session (either Practice, Learning, or Test).
