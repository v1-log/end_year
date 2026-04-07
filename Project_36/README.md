# Project_36

Simple Java auction demo with anti-sniping extension logic.

## Requirements
- Java 17
- Maven 3.9+

## Build
From `/home/runner/work/end_year/end_year/Project_36`:

```bash
mvn clean compile
```

## Run
From `/home/runner/work/end_year/end_year/Project_36`:

```bash
mvn -q exec:java -Dexec.mainClass="com.auction.Main"
```

If `exec:java` is unavailable, compile then run directly:

```bash
mvn -q compile
java -cp target/classes com.auction.Main
```

## Test
From `/home/runner/work/end_year/end_year/Project_36`:

```bash
mvn test
```
