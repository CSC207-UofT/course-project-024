# Group 024 Progress Report

## Specification

Our project is a flashcard program which allows users to log into accounts, create, view, and edit decks of custom flashcards, and allow for integrated activities, deemed “study sessions”, on the flashcards, such as being able to learn new cards, practice cards, or both at once.

## CRC Model

Our CRC model is a collection of slides where each slide is a class or interface that will be created and used in our program. Each slide details the class’s name, attributes, methods, its place in the architecture, and any classes that it may collaborate with. Additionally, if the class is an interface we list all the classes that will implement it. In our CRC model are the core Entities: Flashcard, Deck, Account, and StudySession (with its concrete implementations); the Use Cases, labeled “Interactors” which work directly on the Entities; the Controllers: DeckController and SessionController, which perform high-level operations needed by the UI using the Interactors; and finally the Drivers: FlashcardSystem, SessionPresenter, and LoginSystem, which display UI elements for viewing flashcards, displaying study sessions, and logging in respectively. They receive input from the user, and delegate logic handling to the appropriate Controllers.



## Scenario Walkthrough

Our scenario walkthrough details the most common features that a user of this program will use: creating a deck, adding a flashcard to that deck, and then starting a study session with that deck. In a nutshell, our scenario starts at the Main class, which houses the main method. From there, it creates a FlashcardSystem and calls a displayMainMenu method. When the user indicates that they want to create a new deck, the FlashcardSystem calls on DeckController to create a deck and add it to the DeckController’s list of decks. Then, to add a flashcard, the FlashcardSystem takes in the deck, what the user wants for the front and back of the flashcard, and delegates it to DeckController to add such a flashcard to the deck. Finally, to study a deck, the FlashcardSystem asks the user for what type of study session they want. Say the user wants a practice session. Then, FlashcardSystem tells SessionPresenter to create and display a PracticeSession, and then SessionPresenter will be in charge of consulting the SessionController to get a flashcard to show to the user. The SessionPresenter can display the front of the card to the user, wait for the user to indicate that they’ve made a guess of the back, and then show the back of the flashcard, and then fetch a new flashcard and restart the whole process until the user quits the session.



## Skeleton Program

Our skeleton program is functional and covers most of the CRC model, except for the handling of accounts, some functionalities on editing flashcards, and more complex types of StudySessions. Our program is able to create, rename, and edit decks of flashcards as the user wishes. Edits that can be made to a deck include adding flashcards or deleting a card. It is also able to begin a “practice session” on any non-empty decks created by the user, where the program will randomly choose a card from the deck and display the front of the card to the user, and upon the user’s request, will display the back of the card. This session repeats with this process until the exit code is given. The program also has a rudimentary “logout” system to end the program at the main menu.



## Open Questions

We are unsure whether to keep our program as a native desktop app, or to create an android app of it as well. This is something we are still deciding on as we have limited experience with Android apps, so creating one for our program could be time-consuming. 

Another question we still have is how we will store user login information. As our program is off the web, we will need some external database to store user account information that can be accessed during any instance of the program. To solve this we will either need to set up an online database and connect with it everytime we run our program, or decide that such an endeavour is out of the scope of the program, and instead set up a small in-program database that serves as a proof-of-concept for what a large database would look like. More research is required.

We also are debating with the idea of how complex our program should be. Should we allow the user to create flashcards that have other types of data, such as images? Should we track more data about the user’s performance during study sessions so that the algorithm behind choosing the next card to the user can be customized and refined further? 

Lastly, there are numerous questions about design decisions we could make in regards to future development which are not in the specification. For instance, should we consider implementing features like merging decks, sharing decks with other accounts, or being able to start more than one of each type of StudySession?

## What Has Worked Well So Far With Our Design

Our design so far has made coding easier since it allowed us to clearly separate the responsibilities between the layers of the program and only be able to access a subset of the data. For instance, the Driver classes only need to use the Controller classes to achieve all of their functionalities, which helps remove the need to know all the use cases of the entities, of which many may not be needed. This makes coding simpler to think about as one layer needs only to concern themselves with the layer directly underneath it.

The separation of responsibilities in the design also allowed the team to individually code separate systems, and merge them all together in the end with little-to-no effort needed to get the final product to work.

## Summary of Work So Far and Future Plans

For Phase 0, Mahathi worked on implementing Account and AccountInteractor. Fatimah worked on implementing class StudySession, SessionInteractor, and SessionController. Fatimah has not put enough documentation in the java files so she will work on that. Selena worked on implementing the FlashcardSystem, including UI and user input. Jason implemented SessionPresenter and created unit tests. Kevin worked on implementing Flashcard, FlashcardInteractor, and PracticeSession. Abdus worked on creating Deck, DeckController, and DeckInteractor.

We’ve identified the next steps for our program, which include:

- Finishing up interactions with editing flashcards in a deck

- Unifying the flashcard system with the addition of accounts, so that decks belong to accounts

- Adding more StudySessions than just PracticeSession

- Finding a way to keep data after closing the program, like accounts, decks, and flashcard proficiencies

- Adding a graphical user interfaceAdding exceptions and handling exceptions for validation

- Consider extensions such as implementing a friend system, or making a graded testing system for the user’s proficiency with a deck of flashcards