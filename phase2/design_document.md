# Phase 2 Design Document, Specification, and Accessibility Report



## Specification

* Our project is a program designed to allow users to create, study, and organize flashcards for whatever tasks they need. 
* Users log into accounts using their username and password, and their accounts hold all of the user’s flashcards, decks, and other pieces of data permanently. 
* Once logged in, users may access their “decks” of flashcards, identified by name. Actions performed on decks include: creating a new deck, renaming a deck, deleting a deck, or starting what is deemed a “study session” on the deck. 
* A study session is an algorithm which displays cards one-at-a-time to the user, to which the user will try to guess the text on the back. 
* The study session’s card shuffler dictates what cards are displayed and in which order they are displayed - examples of card shuffles include generating a random card or periodically displaying unseen cards between previously-seen cards. The card shuffler may use information gathered during each study session to make its decision. 
* Examples of study sessions include a practice session, which infinitely queues cards based on how well the user is doing, learning session, which infinitely generates cards based on the user’s proficiency with the cards, and test session, which generates a finite number of random cards and tracks the percentage of cards that the user gets correct. 
  * Learning session orders cards from worst proficiency to best proficiency whereas with the practice session, it queues cards to be seen, and the more a user gets a card consecutively correct, the further down the queue it goes.
* Within a deck, users are able to create a new flashcard (whose front face consists of a text, image, or both, and whose back face consists of a text) and adding it to the deck, editing a flashcard’s faces, and deleting a card. 
* The program uses a MySQL database as a persistence layer. All accounts, decks, and cards are stored in this database so that the user's data remains consistent over multiple sessions of running the program. This also means that the same user data can be accessed across multiple machines, allowing for a more dynamic studying experience.



## Major Design Decisions

Most major design decisions were made back in Phase 0 and Phase 1. As a recap:

### Flashcard Front Faces

* We chose to allow our flashcards’ front faces to be composed of an image, or text, or both, while restricting our back faces to be text-only. We made this decision to allow more flexibility for users to be creative with the flashcard’s front, while still allowing us to implement ideas such as automatically testing our user on the cards by making them type out the back of the card.

### Sessions

* We shifted away from keeping card choosing algorithms in the StudySession’s getNextCard() method, to creating a CardShuffler abstract class whose subclasses will be in charge of returning Flashcards to be shown. This opens up the possibility of having StudySessions be able to choose CardShuffle algorithms, and abstracts away the method of returning a Flashcard to StudySessions.
* We chose to make a CardData class that would contain all raw values for a certain Flashcard. We did this because we were unhappy with the abstract nature of “proficiency”, which could be interpreted differently by different StudySessions. Then, we chose to move away from a Flashcard to proficiency mapping toward a Flashcard to CardData mapping, and moved this mapping from StudySession to CardShuffler. This way, StudySessions are no longer in charge of keeping CardData objects and updating them, which is now delegated to CardShufflers, and now CardShufflers can decide how to use CardData values to return the next flashcard to show.

### Persistence

* We chose to use a database as our persistence layer to allow for more dynamic studying, and less cognitive load on the user. For persistence we had the option of using either a database or serialization, but we chose to use a database because it removes the cognitive load of keeping and maintaining a .ser file from the user. Additionally, using serialization would confine a user to a single machine if they wanted to maintain all of their information across sessions. A database allows the user to access the same information across multiple machines so the user gets a better experience.



However, we did make one major design decision throughout Phase 2: 

### DTOs

* A major change in our program came with the introduction of Data Transfer Objects to our program. These are immutable objects which encapsulate simple data from the Entities and are passed between the Interactors and Controllers, as well as to the database. The DTOs allowed for the UI to receive information from the inner layers without exposing the underlying Entities to the outer layers.



## Clean Architecture

We have tried our best to adhere to clean architecture where possible:

* We have clearly separated the project into the 4 layers described by Clean Architecture
* All entities, such as Flashcard or Deck, are basically simple data objects with some base functionality
* All use cases are labeled as “Interactors”, and interact with their respective entities
* All controllers are responsible for performing more complex actions involving the use cases, with more concrete functionality related to the end-user
* All drivers deal with the user interface and use the controllers to manage communications with the inner layers
* We deliberately made layers communicate from outside to inside, and most of the time, layers communicate exactly only with their own layer or the layer directly beneath them
* New to Phase 2, the MySQL database gateway is now separate from our database gateway interface. This adheres better to clean architecture, since controllers can now rely on an abstraction to communicate with the database instead of communicating with the concrete implementation itself



## SOLID Principles

Most of SOLID principles are followed, however we do note some exceptions which we hope to explain:

### Single-responsibility

* Due to the clearly-separated naming of each class, each class is responsible for only one piece of functionality, be it the DeckController’s responsibility over changes to Decks, or CardShuffler’s responsibility to return cards from its “shuffled” deck

### Open/Closed

* We use this principle in StudySessions to ensure that we can add new types of StudySessions for the user to use without modifying the base StudySession. A similar concept goes for CardShufflers.

### Liskov Substitution

* We emphasized using abstract classes like CardShuffler and StudySession throughout the program to make sure that any specific CardShuffler and StudySession subclasses can be used by the program to return a flashcard regardless of how such a flashcard is chosen, so that one can substitute any subclass and the program will still work as intended. Some smaller uses of it were in our use of Lists and Maps in order to keep the storage implementations as general as possible. In some classes, such as TestSession, we were unable to satisfy Liskov substitution because it had specific logic that was special to it (for instance, it is the only StudySession that requires a concept of a user’s score since it should display the score at the end of the test) [see Open Questions].

### Interface Segregation

* Since Phase 1, we added an UpdatingShuffler interface which is an interface which requires the user’s performance to be fed to the card shuffler to enhance its algorithms. The DatabaseGateway interface is also an interface that is solely concerned with accessing the database.

### Dependency Inversion

* We did not strictly follow the dependency inversion rule, as some of our classes depend directly on concrete lower-level classes. Classes such as Deck do this deliberately, as from our specification, we do not anticipate that making an interface for Flashcards would improve any program functionality. 
* However, classes such as StudySession and CardShuffler do utilize dependency inversion, as they are abstract classes which higher-level classes will use in order to not directly depend on their concrete implementations, and exist in order to satisfy the open/closed principle and Liskov substitution principle because new types of their implementations arise and change quite often. 
* Furthermore, we used dependency inversion to expose an interface of our database to our Controllers, then used dependency injection to provide a concrete database gateway.



## Packaging Strategy

* We decided to package our classes by component to better emphasize our architecture and ideally group and encapsulate related classes together
* So, the module “Decks” would contain all deck related classes, such as the Deck entity, DeckController, DeckInteractor, etc., while the “UI” module houses the LoginUI, the MainUI, and StudySessionUI classes
* This allows users to see at a glance what the program is all about, and easily find the relevant files related to each part of the program



## Refactoring

* We refactored a number of pieces of the program since Phase 1. For instance, the Controllers and Drivers were refactored to use DTOs instead of raw Entities in order to satisfy the Dependency Rule
* We also unified the list implementations to remove the requirement for some lists to be specifically LinkedLists to better satisfy Liskov substitution
* The MySQL database gateway has also become an implementation of the DatabaseGateway interface, to better adhere to clean architecture by making sure that controllers interact with the interface to communicate with the database, and not with the concrete database itself



## Design Patterns

### Simple Factory

* We used the simple factory method design pattern to produce flashcards, as we wanted to hide the creation process for creating their special Front objects, and we also used it in StudySessions as a “get” method instead of a “create” method to not only obfuscate the creation process but also control how many StudySessions the user is allowed to create at a time.

### Strategy

* In our StudySessions, we used the Strategy design pattern to choose the way we shuffle cards and the algorithm for which we choose which card to present to the user. 
* In BasicShuffle the cards are randomized, which is a strategy best used for a testing study session. 
* In SmartShuffle, cards are placed in a queue to be seen. Once the user sees a flashcard, that flashcard is re-inserted into the queue to be seen again sooner if the user was incorrect, or later, if the user was correct. This way, flashcards that the user gets consistently correct are seen less than flashcards that get answered consistently incorrect. 

### Observer

* We did not want to use the original deck for our study sessions because we did not want to change the order or modify anything about the original deck. 
* Therefore, the solution was to use a copy of the deck. However, the copy of the deck must know when a flashcard is added or deleted to the original deck. 
* Essentially, the copy of the deck must observe any changes the original deck goes through so that it can be updated. Therefore, in our card shufflers, we used the observable design pattern to have the copies of the deck observe the original deck.



## Testing

* Overall, unit testing covers 44% of all lines. 
* This may seem like a low number, but in reality it is because the UI and DTO files takes up the vast majority of code lines, and are not unit tested because the UI only has untestable visual output and the DTOs only store data and have no behaviour to test. 
* Also, the majority of untested lines are simply getters and setters, which we decided were not worth testing. Most of the key behaviours of entities are tested, and most methods in the interactors and controllers are tested.
* To put it in perspective:
  * 80% of all classes have code coverage, with 100% of classes and 84% of lines in Accounts tested
  * 100% of classes and 74% of lines in Decks tested
  * 100% of classes and 86% of lines in Flashcards tested
  * 100% of classes and 76% of lines in Sessions tested



## GitHub Features Used

* Over the course of Phase 2, and the entire project lifecycle, our group made frequent use of GitHub features. 
* In Phase 0, we emphasized branching and pull requests to work on code separately and merge respectively to maximize productivity, and we used GitHub’s code review feature to make suggestions.
* In Phase 1, we continued to use branching and pull requests, but chose to make code suggestions live within voice chat meetings, as we found that it was quicker and less confusing.
* In Phase 2, we still continued to branch and make pull requests, but we also made use of the issues board to set out a roadmap of what needed to be done, as well as to highlight bugs and missing features, and closed issues using pull request linking whenever an issue was fixed or addressed. 



## Open Questions and Wishful Next Steps

* The database is currently being hosted by a free service, this means that it has limited storage capacity. As a result, we must think of ways we can avoid filling the database past its limit, or how we can reduce the space taken up by the entities already present in there. Additionally, the database connection details are currently publicly visible in the github repository which may pose security risks, so we must think about how to avoid or minimize this risk.
* Currently, TestSessions have additional methods added to the base StudySession class, so UI work requires StudySessions to be cast to TestSessions before being able to access those special methods
  * However, this violates the Liskov Substitution principle, since we would no longer be working with the interface at all times. 
  * Alternatively, we could add its methods to the base StudySession class, but then it would violate the Open/Closed principle and Interface Segregation principle because we would require edits to be made to the StudySession class and have other StudySessions such as PracticeSession never use those additional methods
  * We decided to pick the option which violated the least principles, but we were wondering if there was a better option.
* Although the project is basically in its final stages, if we had more time to expand our scope, we would consider additional features such as loosening the structure of our flashcards (e.g. allow for fronts to be customized further), investigating social aspects of flashcards (such as being able to share decks with other users), and exploring what other types of study sessions would be useful for users to have.



## What Worked Well With the Design

* Despite many moving parts in the progression of this project, since each layer interacted with exactly one layer below it, we could clearly find the interfaces we were provided to accomplish our tasks, allowing the team to work on individual parts with minimal guidance. In addition, it makes UI functionality much easier, as UI designers will only need the controllers to do their work, providing a reduction in scope from the more complex logic of the program. Also, with the addition of Data Transfer Objects in phase 2, UI programmers now cannot directly know or influence the behaviour of entities, further solidifying the boundaries between higher level and lower level code and lowering the cognitive load on programmers seeing parts of the program that don’t concern them.

* Using the strategy design pattern has made it easier to switch and test different ways of shuffling the cards. Also it’ll be easy to add or remove a strategy with the way that we currently have it implemented. Implementing new StudySessions should be similarly simple.

* Using the observer pattern allows us to have copies of the deck observe an original deck. It particularly allows us that functionality without breaking any clean architecture rules or any SOLID principles. Having copies of the deck observe an original deck allows us to play around with the copy of the deck (particularly modifying its order) without affecting the original deck. 



## Summary of Group Contributions Since Phase 1

* Abdus
  * Separated interface and database gateway and modified database gateway to now work with DTOs
    * https://github.com/CSC207-UofT/course-project-024/pull/44 
      * This PR changed the database gateway to adhere to clean architecture and become compatible with the DTO objects that were created to satisfy the dependency rule
  * Implemented account authentication features through the database
    * https://github.com/CSC207-UofT/course-project-024/pull/38
      * This PR allows for logins to be possible
* Fatimah
  * Worked on basic shuffle and WorstToBest shuffle
    * https://github.com/CSC207-UofT/course-project-024/pull/53 
      * This PR implements a major shuffler used in LearningSession that fulfills the strategy design pattern
  * Worked on implementing observer pattern in study session
    * https://github.com/CSC207-UofT/course-project-024/pull/34 https://github.com/CSC207-UofT/course-project-024/pull/52 
      * This PR implemented the observer design pattern to update study sessions, thus allowing study sessions to have up-to-date versions of decks even after they are changed
  * Refactoring -> LinkedList to ArrayList
    * https://github.com/CSC207-UofT/course-project-024/pull/49 
      * This PR unifies all concrete List types to ArrayList for Liskov Substitution
* Selena
  * Implemented full functionality for main menu GUI
  * Primary main UI update
    *  https://github.com/CSC207-UofT/course-project-024/pull/42
      * This PR created the main menu, allowing a GUI to replace the old command line interface
* Mahathi
  * Worked on writing and converting tests to work with DTOs
    * https://github.com/CSC207-UofT/course-project-024/pull/47
      * This PR converted tests to work again after they were broken from the introduction of DTOs
  * Worked on README.md and user instructions
    * https://github.com/CSC207-UofT/course-project-024/pull/56
      * This PR created installation instructions and general information that would be useful for new users
* Jason:
  * Finished GUI functionality for all three types of StudySessions
    * https://github.com/CSC207-UofT/course-project-024/pull/40 
      * This PR finished the GUI for users to be able to run study sessions on a GUI instead of a CLI
  * Refactored code to use DTOs in order to remove references to Entities from Controller or Driver layer
    * https://github.com/CSC207-UofT/course-project-024/pull/26 
      * This PR fixed the issue of the UI and Controllers accessing entities, violating the dependency rule
* Kevin
  * Implemented the login GUI and implemented AccountController
    * https://github.com/CSC207-UofT/course-project-024/pull/38
      * This PR gives a GUI for logging in instead of using the CLI
  * Refactored database gateway to adhere to clean architecture
    * https://github.com/CSC207-UofT/course-project-024/pull/51
      * This PR fixed outstanding issues with dependency inversion in the database gateway



# Accessibility Report



## Principles Of Universal Design

### Equitable use

So far the program uses typing to gain users input which means that users need functioning fingers to use it. This is not necessarily very equitable for those who are not physically able to use their fingers. In the future we could implement a feature where users can choose to type or speak whatever their input is.

### Flexibility in use

A feature in our program that is flexible in use is the variety of sessions we have. The feature provides choice in methods of use. In particular, users have the freedom to choose if they want their answers to be tracked and/or tested on their answers. They also get to choose through the sessions whether the next card is random or based on their progress.

### Simple and intuitive use

Our program was designed to be simple to navigate and each menu was organized to put relevant information in groups so as to not overwhelm the user. While some menus such as the Edit Deck menu are a bit complex, if we had more time, we could attempt to fix it by introducing submenus. Furthermore, descriptions for buttons do exactly as they say, and some less intuitive elements, such as the ability to flip flashcards in StudySessions by clicking on them, are described using a large text description underneath.

### Perceptible information

Our use of a GUI allows us to supply the user with both pictures and words as different modes of providing information. Essential information is also communicated to the user in a large font. We also have a simple black-text-on-light-background theme which aids in readability. 

### Tolerance for error

In our program, many invalid inputs are caught to avoid catastrophic or annoying errors. For instance, inputting a blank password will warn the user that blank fields are not accepted. Due to the design of our GUI however, buttons with different functionality are placed close to each other, which may lower the tolerance for error. Dividing elements more clearly using space would improve our tolerance for error.

### Low physical effort

Our program requires little motor effort other than clicking buttons using a mouse, and no actions require high physical effort. It is possible that actions may become repetitive, but since information is organized to be in different places on-screen, repetition of mouse-movements may also be minimized.

### Size and space for approach and use

As of right now, the flashcard is visually the biggest thing in our program as it is the most important thing the user needs to see. This provides a clear line of sight for important elements. However, some buttons are relatively small so maybe in the future we can have a setting that lets users adjust button size to their liking.



## Target Audience

We would primarily market our program towards students, or anyone trying to memorize some sort of bite-sized information, such as learning new vocabulary of a language, remembering the capitals of the world, or other related topics. This is because flashcards are used as a method to remember information in a more consistent method, leaving memorization to choice rather than choice. For students, they are generally at the greatest need to memorize things with aids like flashcards to succeed in school. Also, there is a grading feature they can use to practice for tests. This feature is especially helpful because ideally, students would like to study in a similar graded environment as a true real test. Thus, being tested and receiving feedback on their grade could be helpful for practicing for tests. Also, being able to receive certain cards based on progress will help solidify memory in the areas needed, which is what happens in a learning session.



## Demographics

People who aren’t in our target demographic (students) are less likely to use this program. This is because there are few conditions where people need to commit things to memory. This has to do with the fact that the internet is easily accessible and people can search up whatever they need whenever they need it. Also, most people commit important things to memory naturally so they won’t use the program. Not to mention, for the most part, people aren’t learning something new everyday (unless they're in a field like academia). We also anticipate that people who are visually-impaired would not find much use in our program since it is fully visual, and we have no systems in place to provide audio feedback. Its design as a desktop application may also be too inconvenient for people who are constantly away from home, since they may prefer mobile apps.