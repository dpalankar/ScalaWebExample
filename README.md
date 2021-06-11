# How to run ScalaWebExamples Project

We are storing data in casandra database. It allows add, update and delete data from database.
This application also store data in memory (i.e in form of array)

To use this example you will need the correct version of Java and sbt. The template requires:

* Java Software Developer's Kit (SE) 1.8 or higher
* sbt 1.3.4 or higher. 

To check your Java version, enter the following in a command window:

```bash
java -version
```

To check your sbt version, enter the following in a command window:

```bash
sbt sbtVersion
```

## Build and run the project

It includes all Play components and an Akka HTTP server. The project is also configured with filters for Cross-Site Request Forgery (CSRF) protection and security headers.

To build and run the project:

1. Go to folder `cd ScalaWebExamples`

2. Build & Run project : `sbt run` `sbt "~ run 8087"` `sbt run 8087`

3. After the message `Server started, ...` displays, enter the following URL in a browser: <http://localhost:9000>

## Run Cassandra
Go to Cassandra installed location `C:\Cassandra\apache-cassandra-3.11.10\bin>`

Open command line and run `cassandra` command



