# Music DB user guide

### How to start application
1. You need to have Java 8 and Maven installed on your system.
2. Make sure port 9082 is free since application is going to run default on port 9082.
3. run mvn clean install & java -jar target\ musicdb-0.0.1-SNAPSHOT.jar to start application from cmd

or we can start from idea/STS.

### documentation
Documentation is available in http://localhost:9082/swagger-ui.html

### choices and decisions you made
1. Used Spring boot 2.0.0.M6,I know its not latest one. but some of the frameworks doesn't support the latest springboot version. And I don't want spend time on investigating on it as I have only 3 hours for assignment.
2. Used Swagger for api documentation.
3. Used H2 as a embedded database. And did not write database create quarries as H2 will create in runtime based on entity classes
4. Did not create a different spring profile for testing. so using same db for junit.
5. Used specification-arg-resolver dependency for filtering in get call.
6. Used modelmapper for mapping from entity to dto and visa versa.
    

### parts you found easy and difficult
#### easy
1. because of specification-arg-resolver filtering was easy.
#### difficult
1. reactive implementation

### parts you skipped and the ones that you implemented extra

#### implemented
 Dockerize your application

#### parts skipped
 1. resource_url of album should be added to response dto. but to make development easier I added a variable to entity class as well. this needs to be corrected
 2. exposing metrics, We can do it by added prometheus & spring-metrics. But do not have enough time to do it. so skipped.
 3. Reactive implementation  
 
