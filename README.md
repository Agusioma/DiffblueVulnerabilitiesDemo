# racingDB Demo

As earlier mentioned, if you have Docker installed in your machine, you can use the docker-compose.yml file. Start the Docker Daemon by running the command below in your terminal.

```bash
sudo systemctl start docker
```

Open the file and set your desired values for `MYSQL_ROOT_PASSWORD`, `MYSQL_DATABASE`, `MYSQL_USER`, and `MYSQL_PASSWORD`, which will be used to run the database. When done, run the command below to download the Docker image and start the database.

```bash
docker compose up
```

You’ll know the database has been set up after you see log outputs similar to those shown below in your terminal.

```bash
[Server] /usr/sbin/mysqld: ready for connections. Version: '8.4.0'  socket: '/var/run/mysqld/mysqld.sock'  port: 3306  MySQL Community Server - GPL.

[Server] X Plugin ready for connections. Bind-address: '::' port: 33060, socket: /var/run/mysqld/mysqlx.sock
```

## Configure the application.properties file
After that, open up the application.properties file, found in the src/main/resources/ directory. You’ll need to set the properties below appropriately for the spring app to connect to the database successfully:

```bash
spring.datasource.url=jdbc:mysql://localhost:3306/<Your-Database>
spring.datasource.username=<Your-Mysql-User>
spring.datasource.password=<Your-Mysql-User-Password>
```

They will be the same values you set in the **`docker-compose.yml`** file. That is, `<Your-Database>` should match with `MYSQL_DATABASE`, `<Your-Mysql-User>` should match with `MYSQL_USER`, and `<Your-Mysql-User-Password>` should match with `MYSQL_PASSWORD`.

If you’re not using the Docker version, use the values that will connect to your database.

## Create the tables
Next, use your terminal to create the necessary tables. Note that you don’t need to follow these steps if you have other tools, like DataGrip, that you can use to run the queries. You can just go ahead and copy the queries from the **`racingDemo.txt`** file found in the root of the `racingDemo` directory. Just remember to replace `<Your-database-name>` with your database name.

You can then use the terminal to create the tables needed using these steps.

Note that you don’t need to follow these steps if you have other tools, like DataGrip, that you can use to run the queries. You can just go ahead and copy the queries from the **`racingDemo.txt`** file found in the root of the `racingDemo` directory. Just remember to replace `<Your-database-name>` with your database name.

In a new terminal instance, run the `docker ps` command to get the container ID or name.

Execute the docker container in an interactive shelling using the command below

```bash
docker exec -it <container-id-or-name> /bin/bash
```

Then, use the following command connect to the MySQL database:

```bash
mysql -u <mysql-user> -p
```
	
Enter your password when prompted.

Copy the query stored in the **`racingDemo.txt`** and run them to create the tables needed. You can exit the shell using the `exit` command.