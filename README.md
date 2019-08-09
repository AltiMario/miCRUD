# Mini report CRUD

A mini Clojure RESTful API microservice to manage simple reports, with CRUD operations based on DynamoDB, a basic web interface and Swagger contracts

## Usage

To start the service:
```
lein ring server
```
or
```
java -jar target/report.jar
```


#### RESTful service

http://localhost:3000/v1/report - This will get all reports from database and display it as JSON.

#### Webapp service

http://localhost:3000

#### Swagger service

http://localhost:3000/swagger


## Configuration

Modify the resources/config.edn file for the AWS credentials and server port
