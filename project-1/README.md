## SE Project-1 Report

Please refer to the report's PDF version [here](https://github.com/FlightVin/few-software-engineering-projects/blob/main/project-1/SE%20Project-1%20Report.pdf) for the complete details. Attached is a shorter LLM-generated summary.

### Task 1: Mining the Repository

#### Introduction
The project involves a deep exploration of a complex codebase, focusing on the functionality of a web application developed in Java and JavaScript. The methodology includes executing code and testing components to understand class interactions and dependencies.

#### Methodology
The approach began with executing the code to identify classes and their relationships. PlantUML was utilized to generate UML diagrams, which were refined through manual inspection to accurately represent class interactions.

#### Assumptions
- All user-defined classes are included in the UML diagrams.
- Only classes that directly affect subsystem functionality are represented to maintain clarity.

### Book Addition & Display Subsystem

![UML Diagram from Projet 1](../images/project-1.png)

#### UML Diagrams
- **UML V1.1**: All relations, a superset of classes.
- **UML V1.2**: Major classes only.
- **UML V1.3**: Major classes without dependencies.

#### Classes Used
1. **BookResource**: Manages book operations.
2. **Book**: Represents book attributes.
3. **User**: Contains user-related information.
4. **UserBook**: Manages user-book associations.
5. **BookDao**: Handles database operations for books.

#### Functionality and Behaviour
The subsystem enables users to manage book collections, including adding, updating, and retrieving book information. It also features a tagging system for better organization.

#### OOP Concepts
- **Abstraction**: BaseResource class provides common functionalities.
- **Inheritance**: TagResource inherits from BaseResource.
- **Encapsulation**: Classes encapsulate data and methods.
- **Polymorphism**: Methods can behave differently based on the implementing class.

#### Strengths
- Robust user and book management functionalities.
- Efficient data access and validation mechanisms.

#### Weaknesses
- Lack of comprehensive data validation may affect integrity.
- Limited user customization options.

### Task 2: Analysis Methodology

#### Task 2A: Design Smells
Identified design issues include cyclic dependencies, broken modularization, and primitive obsession.

#### Task 2B: Code Metrics
Utilized tools like CodeMR and CheckStyle to analyze code quality and maintainability.

### Task 3: Refactoring Methodology

#### Task 3A: Design Smells
Issues identified in the previous analysis were addressed through refactoring.

#### Task 3B: Code Metrics
Post-refactoring metrics showed improvements in code quality.

#### Task 3C: Leveraging Large Language Models for Refactoring
Explored the use of AI models to assist in code refactoring processes.

This report encapsulates the findings and methodologies employed throughout the SE Project-1, highlighting the intricacies of the codebase and the strategies for effective management and improvement.
