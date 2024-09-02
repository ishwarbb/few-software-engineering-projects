## SE Project-2

Please refer to the report's PDF [here](https://github.com/FlightVin/few-software-engineering-projects/blob/main/project-2/project2_14.pdf) for all the details. Attached is an LLM generated summary.

![Design Decision from Project 2](../images/project-2.png)

## SE Project-2 Summary

### Feature 1: Better User Management

**Introduction**  
The project aims to enhance user management by allowing self-registration directly from the login page, improving user experience.

**Plan**  
- Introduced a "Sign Up" tab in the navigation bar.
- Implemented a user-friendly registration interface requiring username, email, and password.
- Added validation checks for email uniqueness and password strength.

**Design Patterns**  
- **Chain of Responsibility**: Utilized for password validation through a series of handler classes, each checking specific password criteria.

**Implementation Details**  
- **Front End**: Added signup.html and Signup.js for user registration.
- **Back End**: Handled in UserHandler.java and ValidationUtil.java.

---

### Feature 2: Common Library

**Introduction**  
This feature allows users to contribute to and explore a common library, enhancing community engagement.

**Plan**  
- Designed schemas for books, ratings, and genres.
- Developed API calls for library operations and conducted testing using Postman.

**Design Patterns**  
- **Strategy Pattern**: Implemented for sorting and filtering library books based on various criteria.

**Implementation Details**  
- **Front End**: Created library.html for displaying books and options to add and rate them.
- **Back End**: Defined data models and DAO classes for library management.

---

### Feature 3: Online Integration

**Introduction**  
Integrates Spotify and iTunes for accessing audiobooks and podcasts, enhancing user experience.

**Objective**  
To provide users with the option to select their preferred service provider for audio content.

**Plan**  
- Conducted API testing to understand data structures from Spotify and iTunes.
- Implemented a mandatory user selection step before searches to minimize errors.

**Design Patterns**  
- **Factory Pattern**: Used for creating audio content based on the selected service provider.
- **Adapter Pattern**: Employed to standardize data formats across different service providers.

**Implementation Details**  
- **Front End**: Developed HTML and JS files for audio content display and user interaction.
- **Back End**: Managed audio content through dedicated resource and DAO classes.

---

### Conclusion
The project successfully enhances user management, establishes a common library, and integrates online audio services, employing various design patterns for maintainability and scalability.
