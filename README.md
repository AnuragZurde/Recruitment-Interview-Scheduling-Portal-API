# Recruitment & Interview Scheduling API

An enterprise-grade, role-based Applicant Tracking System (ATS) and Interview Scheduling REST API built with Spring Boot. This platform handles the complete recruitment lifecycle—from HR posting jobs and candidates applying, to scheduling interviews and gathering feedback.

# Tech Stack
* Language: Java 26
* Framework: Spring Boot 4.1.0
* Database: MySQL

# Dependencies
* *Spring Web MVCspring-boot-starter-webmvc):* For building the core RESTful APIs.
  
* *Spring Data JPA(spring-boot-starter-data-jpa):* For database operations, entities, and ORM using Hibernate.
  
* *Spring Security (spring-boot-starter-security):* For role-based access control and protecting API endpoints.
  
* Spring Validation (spring-boot-starter-validation):* For strict DTO input validation (@NotBlank, @Valid, etc.).
  
* MySQL Connector (mysql-connector-j):* JDBC driver for MySQL database connectivity.
  
* JJWT API, Impl, & Jackson:* For generating, parsing, and validating stateless JSON Web Tokens.
  
* MapStruct:* For clean, type-safe mapping between JPA Entities and DTOs.
  
* Lombok:* To reduce boilerplate code (Getters, Setters, Builders).
  
* Lombok MapStruct Binding:* Bridges the gap between Lombok's Builder pattern and MapStruct.
  
* *Springdoc OpenAPI UI:* For automated, interactive Swagger UI API documentation.

# Key Features
* *Role-Based Access Control:* secured endpoints using @PreAuthorize based on three distinct roles (HR_ADMIN, CANDIDATE, INTERVIEWER).
* *Global Exception Handling:* Standardized JSON error responses via @RestControllerAdvice (No messy stack traces leaked to the client).
* *Data Transfer Objects (DTOs):* Strict separation of internal entities and exposed API data using MapStruct.

#  User Roles & Capabilities
1. *HR_ADMIN*
   * *Job Management:* Create, fully update, partially patch, and softly delete job postings.
   * *Applicant Tracking:* View all applications for a specific job posting.
   * *Interview Scheduling:* Assign a specific candidate to an interviewer, auto-updating their status to INTERVIEWING.

2. *CANDIDATE*
   * *Apply:* Submit an application (resume link) to active job postings.
   * *Track:* View a personalized dashboard of all submitted applications and their current statuses (APPLIED,
     SCREENING, INTERVIEWING, etc.).
   * *Duplicate Prevention:* Cannot apply for the exact same job twice.

3. *INTERVIEWER*
   * *My Schedule:* View a tailored list of assigned upcoming interviews (complete with meeting links and times).
   * *Feedback:* Submit a final review/feedback string and a definitive passed (True/False) evaluation.

# API Endpoints

## Authentication

| HTTP Method | Endpoint | Role Required |
| ----------- | -------- | ------------- |
| `POST` | `/auth/signup` | *None* |
| `POST` | `/auth/login` | *None* |

### HR Endpoints

| HTTP Method | Endpoint                                                                      | Role Required |
| ----------- | ----------------------------------------------------------------------------- | ------------- |
| `POST`      | `/api/hr/job`                                                                 | `HR_ADMIN`    |
| `GET`       | `/api/hr/job/all`                                                             | Authenticated |
| `DELETE`    | `/api/hr/job/{jobId}`                                                         | `HR_ADMIN`    |
| `PUT`       | `/api/hr/job/{jobId}`                                                         | `HR_ADMIN`    |
| `PATCH`     | `/api/hr/job/{jobId}`                                                         | `HR_ADMIN`    |
| `GET`       | `/api/hr/job/{jobId}/applications`                                            | `HR_ADMIN`    |
| `PATCH`     | `/api/hr/job/applications/{applicationId}/status?status={APPLICATION_STATUS}` | `HR_ADMIN`    |
| `POST`      | `/api/hr/job/application/{applicationId}`                                     | `HR_ADMIN`    |


## Candidate Endpoints

### User Profile

| HTTP Method | Endpoint | Role Required |
| ----------- | -------- | ------------- |
| `GET` | `/api/user/profile` | Authenticated |
| `PUT` | `/api/user/profile` | Authenticated |
| `PATCH` | `/api/user/profile` | Authenticated |
| `DELETE` | `/api/user/profile` | Authenticated |

### Job Applications

| HTTP Method | Endpoint | Role Required |
| ----------- | -------- | ------------- |
| `POST` | `/api/applications/job/{jobId}` | `CANDIDATE` |
| `GET` | `/api/applications/my-application` | `CANDIDATE` |

## Interviewer Endpoints

| HTTP Method | Endpoint | Role Required |
| ----------- | -------- | ------------- |
| `GET` | `/api/interviewers/my-interviews` | `INTERVIEWER` |
| `PATCH` | `/api/interviewers/{interviewId}/feedback` | `INTERVIEWER` |


# Getting Started
1. Prerequisites
   * Java 26
   * MySQL Server
   * Maven
2. Configuration
Open src/main/resources/application.properties and update your database credentials and JWT Secret:
*     spring.datasource.username=your_mysql_username
*     spring.datasource.password=your_mysql_password
*     jwt.secretKey=your_super_secret_256_bit_jwt_key_here
7. Run the Application

# API Documentation (Swagger UI)
  This project includes automated OpenAPI documentation. Once the application is running, you can interact with the API directly from your browser.
  1.  Navigate to: http://localhost:8080/swagger-ui/index.html
  2.  Use the /auth/login endpoint via Swagger to generate a JWT.
  3.  Click the green "Authorize" button at the top of the page, paste your JWT, and click Save.
  4.  You can now securely test all protected endpoints.

# Future Enhancements
* *Pagination:* Implement Spring Data Pageable on the /admin/job/all endpoint for massive scale.
* *Email Notifications:* Integrate spring-boot-starter-mail to notify candidates when their application status changes or an interview is scheduled.
