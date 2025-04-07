Inventory Management System (Practice Repo)
===========================================

This is a practice repository for an Inventory Management System built with Java, JSP, and Servlets. Please note that this repository is intentionally filled with bugs and issues for practice purposes.

Prerequisites
-------------

- Java 21
- Gradle 8.1 or higher
- git with git bach (if using windows)
- A web browser
- sqlite3 ([how to install sqlite3 on windows](docs/install_sqlite_windows.md))

Initializing the db
--------------------

Using bash or git bash (on windows, after installing git and sqlite3), run the following command:

```bash
sqlite3 database/inventory.db < src/main/resources/database/schema.sql
```

Building the Project
--------------------

To build the project, run the following command:

```sh
gradle clean appRun
```
