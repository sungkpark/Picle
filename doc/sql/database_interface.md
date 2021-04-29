# The interface between the server and the database

The connection between the server, or the Data Access Object Layer of the server to be
precise, and the database, is handled by the `DatabaseController` class. This class reads
the database credentials and host from the `database.properties` file and creates a
connection with the database. To execute a SQL query on the database, one simply creates
an instance of the `DatabaseController` class and users the `getConn()` method to receive
a SQL `Connection` object.

The following properties are expected in the `database.properties` file:
_note that the values are samples_
```
dbHost=127.0.0.1
dbUser=picle_server_user
dbPass=VERY_SECURE_PASSWORD
dbName=picle_test
```
