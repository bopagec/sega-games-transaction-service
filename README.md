SEGA - Transaction Service (Simple API)
=======================================

### Task (as given)
The task is to design and build a simple REST API.
There is a requirement for an API that must be able to store and retrieve game transactions. 
A game transaction is a single piece of data that consists of a user id, a transaction id, a product and a monetary amount. 
The API must have at least two endpoints – one to store a single transaction and the second to retrieve all transactions.

## Solution
sega-games-transactions-service API has been implemented without security in mind for the simplicity.
The API should be able to transact a new transaction and to view all transactions.
API requires communicate to other micro services such as ProductService, UserService etc in real world applications scenarios.
for simplicity, I implemented this API with 3 domain objects (Transaction, Product and User)
Transaction domain object is having ManyToOne relationship between other tables.
However, In real world application each micro service should have their own database to manage data.


at the start-up of the application CommandLineRunner will populate the following test data.
* 10 products
* 5 users
* 10 transactions

## API End points
TransactionController has 1 POST and 4 GET end points 

* /transact - POST - to perform a transaction with body 
  ie: {
    	"userId": 11,
    	"productId": 10
       }

* /transactions/all - GET - to view all transactions
* /transactions/{transactionId} - GET - to view transaction by transaction id
* /transactions/user/{userId} - GET - to view transactions by user id
* /transactions/product/{productId} - GET to view transactions by product id

## Technologies involved

* Java 11
* Spring Boot 2.4.5
* Spring Data & JPA
* H2 in-memory Database
* JUnit 5 (Jupiter) with MockMvc
* Lombok

## Improvements to consider
due to the time constrain, spring security has not been introduced.

* Spring Pagination
* Validation & Exception handling
* Spring Security
* JWT Token Authentication
* RESTAssured Integration Test
* RestTemplate API Client
