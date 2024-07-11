# Project 2 Tasks

## Feature 2: Common Library (40)

_Implement a common library accessible to all users for contributing and exploring books. This feature allows users to add books in a common library and rate the books._

- [ ] Figure out which exact classes are critical for this
- [ ] See how to add a page to the frontend

### Random thoughts

- Factory pattern for different types of book additions (self shelf or lubr)

### Files that are important to this tasl

- `books-core/src/main/java/com/sismics/books/core/model/jpa/Book.java`: class Book: It represents books in the system, storing attributes such as title, subtitle, author, description, ISBN, page count, language, and publication date
    - will probably extend this for a library book
- `books-core/src/main/java/com/sismics/books/core/dao/jpa/UserBookDao.java`: class UserBookDao: It provides methods for managing user books in the database. It includes functionalities for creating, deleting, and searching user books based on various criteria.
    - note how it has functionality for creating, deleting, searching
    - and to some extent, `books-core/src/main/java/com/sismics/books/core/model/jpa/UserBook.java`: class UserBook: This class facilitates the representation and manipulation of user-book associations
- `books-core/src/main/java/com/sismics/books/core/dao/jpa/dto/UserBookDto.java`: class UserBookDto:  It contains attributes such as the user book ID, title, subtitle, author, language, publication date, creation date, and read date. These attributes facilitate transferring user book data between different layers of the application.
- `books-core/src/main/java/com/sismics/books/core/util/jpa/PaginatedList.java`: class PaginatedList: It manages paginated data with details such as page size, offset, total result count, and the list of records for the current page
    - `books-core/src/main/java/com/sismics/books/core/util/jpa/PaginatedLists.java`: class PaginatedLists :It provides utilities for managing paginated lists, including methods for creating paginated lists with customizable parameters, executing count queries to determine the total number of results, and executing paginated queries to retrieve data for the current page.
    - Actually everything in `books-core/src/main/java/com/sismics/books/core/util/jpa`

---

These are the old ones

## Task 1 - 30 - Mitansh (40%) + Vaishnavi (60%) + swayam (vibes)

1. Identify relevant classes
2. Document functionality and behaviour
3. Create UML diagrams
4. Observations and comments
5. Assumptions

## Task 2 - 30 - Vineeth (50%) + Ishwar (50%)

1. (2a) Detect 5-7 design smells using the tools mentioned above, especially Sonarqube. (Make sure that you list
design smells, not code smells)
2. (2b) Code Metrics Analysis: Employ code analysis tools (e.g., CodeMR,
Checkstyle, PMD) to extract up to 6 relevant code metrics for the project.
3. Tools Used: Clearly state the tools used for the code metrics analysis and
ensure that the selected tools provide a reliable and accurate representation
of the project's codebase
4. Implications Discussion: For each identified metric, discuss its implications
in the context of software quality, maintainability, and potential performance
issues. Provide insights into how each metric reflects on the project's current
state.

## Task 3 - 40 - Swayam (50%) + Mitansh (50%) + Ishwar (Vibes)

1. (3a) You previously identified 5-7 design smells. Now, the goal is to rectify these issues
through code refactoring without fundamentally altering the existing codebase
2. (3b) Following the refactoring process, it's time to reassess the metrics measured earlier.
Have they seen improvements or deterioration? I
3. (3c) Leveraging Large Language Models for Refactoring

## Side task - Checking everything after refactoring (for checking whether it works) - Vaishnavi (100%)

## Bonus (2% of course) - Vineeth (35%) + Ishwar (35%) + S&M (30%)

Note: Bonus can be submitted till the hard deadline without any penalty. Late
days are not applicable for bonus components

## Timeline

1. Task1 - Feb 2nd
2. task2 - Feb 5th
