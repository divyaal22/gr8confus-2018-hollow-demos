# gr8confus-2018-hollow-demos
GR8Conf US 2018 - Hollow demos

# Demo

## Producer Demo

Allows to generate random books and publish them to local disk using Hollow Producer.

```
./gradlew :producer-demo:run
```

## Consumer Demo

### Print all books and heap usage

```
./gradlew :consumer-demo:execute
```

Example output:

```
5352852201, SgPekGvVodfQvxonkyA, Packt Publishing, German
3123285969, AgaUzToygeYkkMszS, Prentice Hall, French
7139483745, RYOuCasrkqUQfauk, Addison-Wesley Signature Series, Portuguese
Language: 2053
Book: 8652688
SetOfAuthor: 8724473
Author: 43213389
Publisher: 2057
TOTAL: 60594660
```

The heap usage is calculated in bytes

### Initialize Hollow Explorer to inspect data

Run Explorer UI jetty server

```
./gradlew :consumer-demo:execute -PmainClass=io.perezalcolea.hollow.enhanced.ExplorerUIDemo
```

Go to http://localhost:8080

### Initialize Hollow History UI to inspect historical data

Run History UI jetty server

```
./gradlew :consumer-demo:execute -PmainClass=io.perezalcolea.hollow.enhanced.HistoryUIDemo
```

Go to http://localhost:8090

* Note: In order to see historical changes, you will need to create more records by executing the Producer Demo.

## Ratpack Demo

### Running the application

```
./gradlew :ratpack-demo:run
```

### Retrieve Book by Id

```
 curl "http://localhost:5050/books/[bookId]"
```

### Retrieve Author by Id

```
 curl "http://localhost:5050/authors/[authorId]"
```

### Retrieve Books by Publisher

```
 curl "http://localhost:5050/books/publisher/Pragmatic%20Bookshelf"
```

* Note: book and author ids are dynamic. You will need to look for a valid id via the Hollow Explorer.

## Micronaut Demo

### Running the application

```
./gradlew :micronaut-demo:run
```

### Retrieve Book by Id

```
 curl "http://localhost:5051/books/[bookId]"
```

### Retrieve Author by Id

```
 curl "http://localhost:5051/authors/[authorId]"
```

### Retrieve Books by Publisher

```
 curl "http://localhost:5051/books/publisher/Pragmatic%20Bookshelf"
```

* Note: book and author ids are dynamic. You will need to look for a valid id via the Hollow Explorer.

# Useful links

# Useful links

[https://hollow.how/](https://hollow.how/)

[https://github.com/Netflix/hollow-reference-implementation](https://github.com/Netflix/hollow-reference-implementation)

[https://github.com/Netflix/hollow](https://github.com/Netflix/hollow)

[https://hollow.how/advanced-topics/#in-memory-data-layout](https://hollow.how/advanced-topics/#in-memory-data-layout)
