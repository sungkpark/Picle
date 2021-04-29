How to set up the database
==========================

The server can initialize the tables it needs by itself, but some actions have to be done
manually. These actions are only needed very infrequently. The actions are:
* Creating a database
* Creating a user for the server
* Granting access of our user to the created database

To do this, only a few queries need to be executed on the SQL server:

```
CREATE DATABASE database_name;

CREATE USER 'picle_server_user'@'localhost' IDENTIFIED BY 'VERY_SECURE_PASSWORD';

GRANT ALL ON database_name.* TO 'picle_server_user'@'localhost';
```

Make sure to change `database_name` and `VERY_SECURE_PASSWORD` accordingly. You can also
change the username (`picle_server_user`), but leaving it as the default is fine too.

Notes
-----

We're using MariaDB as our SQL server. Here are some interesting links:

* https://mariadb.com/kb/en/library/java-connector-using-maven/
* https://mariadb.com/kb/en/library/about-mariadb-connector-j/
* https://docs.oracle.com/javase/10/docs/api/java/sql/package-summary.html
