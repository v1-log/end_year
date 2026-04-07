# Project_36

Simple Java auction demo with anti-sniping extension logic.

## Requirements
- Java 17
- Maven 3.9+

## Build
From the `Project_36` directory:

```bash
mvn clean compile
```

## Run
From the `Project_36` directory:

```bash
mvn -q exec:java -Dexec.mainClass="com.auction.Main"
```

If `exec:java` is unavailable, compile then run directly:

```bash
mvn -q compile
java -cp target/classes com.auction.Main
```

## Test
From the `Project_36` directory:

```bash
mvn test
```
