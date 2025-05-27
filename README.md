Price API - Spring Boot Hexagonal Architecture
Description

This project is a Java 17 API developed with Spring Boot that retrieves the applicable price of a product for a given date and brand. It follows a clean and modular Hexagonal Architecture (Ports & Adapters) to separate domain logic from infrastructure and delivery layers.

Technologies Used

- Java 17

- Spring Boot

- Spring Web

- Spring Data JPA

- H2 (in-memory database)

- JUnit 5

- MockMvc

- Maven

How to run:
```
./mvnw spring-boot:run
```
The application will be available at: http://localhost:8085

How to Run Tests

```
./mvnw test
```

Available Endpoint

- GET /prices

Query Parameters:
```
brandId (Long)
```

```
productId (Long)
```
```
applicationDate (ISO format: yyyy-MM-dd'T'HH:mm:ss)
```

Example:
```
GET /prices?brandId=1&productId=35455&applicationDate=2020-06-14T10:00:00
```

Successful Response:
```
json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "currency": "EUR"
}
```
Error Handling
If no applicable price is found, a 404 Not Found response will be returned with an error message.
```
{
  "timestamp": "2025-05-26T00:00:00",
  "error": "No applicable price found for brandId=999 and productId=999 at 2025-05-26T00:00:00"
}
```
