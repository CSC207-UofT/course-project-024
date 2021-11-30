# Group 024 Design Document



## Updated Specification

* Our project is a program designed to allow users to create, study, and organize flashcards for whatever tasks they need. 
  * Users log into accounts using their username and password, and their accounts hold all of the user’s flashcards, decks, and other pieces of data permanently. 
  * Once logged in, users may access their “decks” of flashcards, identified by name. Actions performed on decks include: creating a new deck, renaming a deck, deleting a deck, or starting what is deemed a “study session” on the deck. 
  * A study session is an algorithm which displays cards one-at-a-time to the user, to which the user will try to guess the text on the back. 
    * The study session’s card shuffler dictates what cards are displayed and in which order they are displayed - examples of card shuffles include generating a random card or periodically displaying unseen cards between previously-seen cards. 
    * The card shuffler may use information gathered during each study session to make its decision. 
    * Examples of study sessions include a practice session, which generates random cards infinitely, learning session, which infinitely generates cards based on the user’s proficiency with the cards, and test session, which generates a finite number of random cards and tracks the percentage of cards that the user gets correct. 
  * Within a deck, users are able to create a new flashcard (whose front face consists of a text, image, or both, and whose back face consists of a text) and adding it to the deck, editing a flashcard’s faces, and deleting a card. 
  * The program uses a MySQL database as a persistence layer. All decks and cards are stored in this database so that the user's data remains consistent over multiple sessions of running the program. This also means that the same user data can be accessed across multiple machines, allowing for a more dynamic studying experience.

## Major Design Decisions

### Flashcard Design

* We chose to allow our flashcards’ front faces to be composed of an image, or text, or both, while restricting our back faces to be text-only. We made this decision to allow more flexibility for users to be creative with the flashcard’s front, while still allowing us to implement ideas such as automatically testing our user on the cards by making them type out the back of the card.

### StudySession Design

* We shifted away from keeping card choosing algorithms in the StudySession’s getNextCard() method, to creating a CardShuffler abstract class whose subclasses will be in charge of returning Flashcards to be shown. This opens up the possibility of having StudySessions be able to choose CardShuffle algorithms, and abstracts away the method of returning a Flashcard to StudySessions.
* We chose to make a CardData class that would contain all raw values for a certain Flashcard. We did this because we were unhappy with the abstract nature of “proficiency”, which could be interpreted differently by different StudySessions. Then, we chose to move away from a Flashcard to proficiency mapping toward a Flashcard to CardData mapping, and moved this mapping from StudySession to CardShuffler. This way, StudySessions are no longer in charge of keeping CardData objects and updating them, which is now delegated to CardShufflers, and now CardShufflers can decide how to use CardData values to return the next flashcard to show.

### Data Persistence

* We chose to use a database as our persistence layer to allow for more dynamic studying, and less cognitive load on the user. For persistence we had the option of using either a database or serialization, but we chose to use a database because it removes the cognitive load of keeping and maintaining a .ser file from the user. Additionally, using serialization would confine a user to a single machine if they wanted to maintain all of their infiltration across sessions. A database allows the user to access the same information across multiple machines so the user gets a better experience.



## Clean Architecture

### Our Program's Adherence to Clean Architecture

* We have clearly separated the project into the 4 layers described by Clean Architecture
* All entities, such as Flashcard or Deck, are basically simple data objects with some base functionality 
* All use cases are labeled as “Interactors”, and interact with their respective entities
* All controllers are responsible for performing more complex actions involving the use cases, with more concrete functionality related to the end-user
* All drivers deal with the user interface and use the controllers to manage communications with the inner layers
* We deliberately made layers communicate from outside to inside, and most of the time, layers communicate exactly only with their own layer or the layer directly beneath them

### Clean Architecture Violations

* We are unsure if the database gateway interface is designed correctly to avoid stepping into the outermost layer
* We designed our UI to be in the outermost layer, but they are named controllers, which implies that they are in the controller layer



## SOLID Design Principles

### Single Responsibility

Due to the clearly-separated naming of each class, each class is responsible for only one piece of functionality, be it the DeckController’s responsibility over changes to Decks, or CardShuffler’s responsibility to return cards from its “shuffled” deck.

### Open/Closed

We use this principle in StudySessions to ensure that we can add new types of StudySessions for the user to use without modifying the base StudySession.

### Liskov Substitution

We emphasized using abstract classes like CardShuffler and StudySession throughout the program to make sure that any specific CardShuffler and StudySession subclasses can be used by the program to return a flashcard regardless of how such a flashcard is chosen, so that one can substitute any subclass and the program will still work as intended.

### Interface Segregation

In this project’s current form, we do not make significant use of interfaces, except for the database gateway. However, we are considering separating some functionalities into interfaces (specifically, in the various types of StudySessions) to avoid needing to use methods it does not need.

### Dependency Inversion

We did not strictly follow the dependency inversion rule, as some of our classes depend directly on concrete lower-level classes. Classes such as Deck do this deliberately, as from our specification, we do not anticipate that making an interface for Flashcards would improve any program functionality. However, classes such as StudySession and CardShuffler do utilize dependency inversion, as they are abstract classes which higher-level classes will use in order to not directly depend on their concrete implementations, and exist in order to satisfy the open/closed principle and Liskov substitution principle because new types of their implementations arise and change quite often.



## Our Packaging Strategy

We decided to package our classes by component to better reflect or “scream” our architecture and ideally group and encapsulate related classes together. Due to time constraints, we did not have the chance to capitalize off of our packaging strategy, but for Phase 2, we would like to start shifting the scope of most of our classes to “protected” instead of “public” to simplify the interface and utilize the benefits of package-by-component.



## Design Patterns

### Simple Factory

We used the simple factory method design pattern to produce flashcards, as we wanted to hide the creation process for creating their special Front objects, and we also used it in a weird way in StudySessions as a “get” method instead of a “set” method to not only obfuscate the creation process but also control how many StudySessions the user is allowed to create at a time.

### Strategy

In our StudySessions, we used the Strategy design pattern to choose the way we shuffle cards and the algorithm for which we choose which card to present to the user. In BasicShuffle the cards are randomized which is a strategy best used for a testing study session. For WorstToBest shuffle, the cards are in order based on the user’s performance. Their worst cards will show up first and their best cards will show up last. This is best suited for a practicing session as they can practice their worst cards more. In SmartShuffle, cards are placed in a queue to be seen. Once the user is graded on a flashcard, that flashcard is re-inserted into the queue to be seen again sooner, if the user was incorrect, or later, if the user was correct. This way, flashcards that the user gets consistently correct are seen less than flashcards that get answered consistently incorrect. 



##  Open Questions

* The database is currently being hosted by a free service, this means that it has limited storage capacity. As a result, we must think of ways we can avoid filling the database past its limit, or how we can reduce the space taken up by the entities already present in there. Additionally, the database connection details are currently publicly visible in the GitHub repository which may pose security risks, so we must think about how to avoid or minimize this risk.
* In a few places in the program, data is copied and mutated, but also must respect and remain in continuity with the state of the data that it was copied from. While the Observer design pattern is perfect for this, we opted to avoid using it because it would establish a relationship between classes that we did not want, such as between Deck and StudySession, since in the event that a deck should be shared, its StudySessions should not, since they are unique to each account. Is this a good idea, or should we employ use of the Observer pattern anyway?

## What Has Worked Well So Far 

* Despite many moving parts in the progression of this project, since each layer interacted with exactly one layer below it, we could clearly find the interfaces we were provided to accomplish our tasks, allowing the team to work on individual parts with minimal guidance. In addition, it makes UI functionality much easier in the future once all the controllers are finalized, as UI designers will only need the controllers to do their work, providing a reduction in scope from the more complex logic of the program.

* Using the strategy design pattern has made it easier to switch and test different ways of shuffling the cards. Also it’ll be easy to add or remove a strategy with the way that we currently have it implemented. Implementing new StudySessions should be similarly simple.



## Summary of Contributions and Future Plans

### Abdus

* Set up, designed, and integrated database with the overall program
* Plans to implement user authentication to improve security

### Fatimah

* Worked on implementing strategy design pattern for study session. Worked on basic shuffle and worsttobest shuffle
* Plans to clean up study session and work on types of study sessions (maybe testing)

### Selena

* Worked on the main menu GUI for interactions with decks, flashcards, and starting study sessions
* Plans to continue working on the main menu GUI and integrate more functionality with the controllers

### Mahathi

* Currently working on a GUI for logging in
* Plans to continue working on the Log In GUI and integrate it with the accounts database for actual functionality.

### Jason

* Made an in-progress GUI for presenting StudySessions to the user
* Made TestSession
* Made Flashcard Front objects
* Plans to continue developing the StudySession GUI and linking the GUI to the session controller for real functionality

### Kevin

* Implemented SmartShuffle, overhauled the CardShuffle system, replaced proficiency with FlashcardData, and made some unit tests
* Plans to refactor the CardShuffle system to be less complicated and easier to read and track, and plans to improve the SmartShuffle algorithm, work on TestSession, and perhaps investigate the possibility of having other modes of study than just self-grading

