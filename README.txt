

Using the provided API to set up the company and assign them a bank using instructions

Tech: Java 8 using SpringBoot.

https://developer.askfractal.com/apps , endpoints were created to:
1. Get categorised transactions for a given category
2. Create a new category
3. Update a category for a given transaction

Postman endpoints
2) Add new category
POST: https://sandbox.askfractal.com/category

2) Get categorised transactions
GET: https://sandbox.askfractal.com/categories/transactions?companyId={{companyId}}&pg={{pg}}&from={{from}}&to={{to}}

3) Update categories of transactions
PUT: https://sandbox.askfractal.com/categories/transactions

The api are tested using RestClientTest & MockRestServiceServer along with with JUnit4 and AssertJ.
RestClientTest has annotations that helps simplify and speed up the testing of REST clients in Spring applications. It allows use of the Jackson’s ObjectMapper instance
to prepare the MockRestServiceServer’s mock answer value.

The build tool is maven.
1) mvn clean test to run test
2) mvn spring-boot:run start service
3) Invoke swagger http://localhost:8080/swagger-ui.html#

