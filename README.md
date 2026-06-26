# Recruitment & Interview Scheduling API

An enterprise-grade, role-based Applicant Tracking System (ATS) and Interview Scheduling REST API built with Spring Boot 3. This platform handles the complete recruitment lifecycle—from HR posting jobs and candidates applying, to scheduling interviews and gathering feedback.

# Tech Stack
* Language: Java 26
* Framework: Spring Boot 4.1.0
* Database: MySQL

# Dependencies
* Spring Web MVCspring-boot-starter-webmvc): For building the core RESTful APIs.
  
* Spring Data JPA(spring-boot-starter-data-jpa): For database operations, entities, and ORM using Hibernate.
  
* Spring Security (spring-boot-starter-security): For role-based access control and protecting API endpoints.
  
* Spring Validation (spring-boot-starter-validation): For strict DTO input validation (@NotBlank, @Valid, etc.).
  
* MySQL Connector (mysql-connector-j): JDBC driver for MySQL database connectivity.
  
* JJWT API, Impl, & Jackson: For generating, parsing, and validating stateless JSON Web Tokens.
  
* MapStruct: For clean, type-safe mapping between JPA Entities and DTOs.
  
* Lombok: To reduce boilerplate code (Getters, Setters, Builders).
  
* Lombok MapStruct Binding: Bridges the gap between Lombok's Builder pattern and MapStruct.
  
* Springdoc OpenAPI UI: For automated, interactive Swagger UI API documentation.
