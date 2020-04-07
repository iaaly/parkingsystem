# Parking System API
Parking system management API that allows Cars of dynamic types to checkin and checkout with a simple billing system.

## API Documentation
https://documenter.getpostman.com/view/1241902/SzYeua9q

## Requirements
For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Gradle 4.6](https://gradle.org)

## Running the application locally
There are several ways to run the application on your local machine. One way is to execute the `main` method in the `com.iaaly.parkingsystem.ParkingSystemApplication` class from your IDE.

Alternatively you can use the [Spring Boot Gradle plugin](https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/#running-your-application) like so:

```shell
./gradlew bootRun
```

## Database
the configured datasource is an H2 file database, the db file will be created after running the project in yout home directory. `~/parking3_db`.
To connect to the DB you can use the h2-console running at: http://localhost:8080/h2-console/ or any DB client using the following credentials:
* URL: `jdbc:h2:file:~/parking3_db;AUTO_SERVER=TRUE`
* username: `sa`
* password: `password`

**Note**: Check the model diagram `model.pdf` in the root directory of the project to better understand the DB sturcture

## Sample Data
To add sample Data you can run the `initializeSampleParking()` test method in `com.iaaly.parkingsystem.ParkingSystemApplicationTests`


## Copyright

Released under the MIT License. See the [LICENSE](https://github.com/iaaly/parkingsystem/blob/master/LICENSE) file.
