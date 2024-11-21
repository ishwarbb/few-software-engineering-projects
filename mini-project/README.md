## SE Project-3: Architectural Patterns for Event Management System

Please refer to the report's PDF for all the details. Attached is an LLM generated summary.

### Project Overview
This project focuses on developing a scalable seat booking and event management system for the Univaq Street Science event, part of the European Researchers Night in L'Aquila, Italy. The system addresses key challenges in managing a large-scale event with approximately 35,000 visitors.

### Architectural Patterns Implemented

#### 1. Microservices Architecture
- **Characteristics**: 
  - Breaks down the application into small, independent services
  - Each service responsible for specific functionalities
  - Enables better scalability and maintainability
- **Key Components**:
  - Booking Request Handler
  - Booking Processor
  - Booking Confirmation Service
  - Utilities for database management

#### 2. Publisher-Subscriber (Pub-Sub) Pattern
- **Characteristics**:
  - Decoupled communication between components
  - Asynchronous message delivery
  - Flexible and scalable messaging
- **Key Components**:
  - Publishers: 
    - Booking Request Publisher
    - Booking Response Publisher
    - Message Publisher
  - Subscribers:
    - Booking Request Subscriber
    - Booking Response Subscriber
    - Message Subscriber

### System Requirements
- Support up to 1000 requests/second
- Venue availability checking
- Parking lot availability management
- Booking/ticketing service
- Accessibility services
- Weather forecast integration
- Event recommendations
- Visualization and analytics

### Technology Stack
- Python
- Flask (for RESTful APIs)
- RabbitMQ (message broker)
- SQLite (database)

### Performance Comparison

#### Average Response Time
- Microservices: 
  - Starts at 20 ms
  - Increases to 619.89 ms at 500 users
  - Reaches 2014.42 ms at 1000 users
- Pub-Sub:
  - Starts at 0.3 ms
  - Remains low at 0.53 ms with 500 users
  - Slightly increases to 0.62 ms at 1000 users

#### Requests per Second
- Microservices:
  - 89 RPS at 500 users
  - 101 RPS at 1000 users
- Pub-Sub:
  - 334 RPS at 500 users
  - 367 RPS at 1000 users

### Key Advantages of Pub-Sub Architecture
1. Decoupled communication
2. Faster message queues compared to HTTP requests
3. Better scalability and elasticity
4. Parallel processing capabilities
5. Improved fault tolerance
6. Optimized resource utilization

### Constraints
- Battery-powered sensors with power-saving mechanisms
- One-day event in city center
- Limited external power sources

### Conclusion
The Pub-Sub architecture demonstrated superior performance compared to the Microservices approach, offering lower latency, higher requests per second, and more efficient resource utilization.

---