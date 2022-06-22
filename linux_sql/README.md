# Linux Cluster Monitoring Agent
# Introduction
This monitoring agent program aims to record the hardware specifications of each host and monitor host resource usages (e.g. CPU/Memory) in real-time. The agent will be installed on each server/host to collect both hardware specification data and resource usage data, and then persist the collected data into an RDBMS database through a psql instance(container). Therefore, the program can be installed on each server and collect data automatically and detect the data usage. Technologies being used include Bash, Docker, Git, PostgreSQL.
# Quick Start
```
# change the current working directory
cd linux_sql
# create a psql container instance using psql_docker.sh
./scripts/psql_docker.sh create postgres 123456
# or the psql container instance already existed,then need to start it
./scripts/psql_docker.sh start

# make usre the container instance is running
docker ps -a
# connect to the psql instance
psql -h localhost -U postgres -W
# create a database
postgres=# CREATE DATABASE host_agent;
# quit the postgres interactive CLI
postgres=# \q

# create host_info and host_usage tables using ddl.sql
psql -h localhost -U postgres -d host_agent -f sql/ddl.sql

# Insert hardware specs data into the DB using host_info.sh
./scripts/host_info.sh "localhost" 5432 "host_agent" "postgres" "123456"
# Insert hardware usage data into the DB using host_usage.sh
bash scripts/host_usage.sh localhost 5432 host_agent postgres 123456

# Crontab setup to collect the data usage every minute
crontab -e
* * * * * bash ~/dev/jarvis_data_eng_Danning/linux_sql/scripts/host_usage.sh localhost 5432 host_agent postgres 123456 > /tmp/host_usage.log

```
# Implementation
First,set up a psql instance using docker and name the container as "jrvs-psql".<br>
Second,check the instance is running and connect to it.<br>
Third, create two tables to store hardware specifications data and resource usage data into the psql instance to perform data analytics.<br>
Forth, insert hardware specs data and hardware usage data into corresponding tables.<br>
Fifth, create the crontab job to make the script being executed every minute using Linux.<br>

## Architecture
![Architecture Diagram](./assets/linux_SQL_arch.jpg)
## Scripts
- **psql_docker.sh** under `/scripts` directory.<br>
A script to create/start/stop the psql container.
```
*usage*
cd linux_sql
./scripts/psql_docker.sh start|stop|create [db_username][db_password]
*examples*
# create a psql docker container with the given username and password
./scripts/psql_docker.sh create postgres 123456
# start the stopped psql docker container
./scripts/psql_docker.sh start
# stop the running psql docker container
./scripts/psql_docker.sh stop

```
- **ddl.sql** under `/sql` directory.<br>
Execute ddl.sql script on the host_agent database in the psql container instance. The script will automate the database initialization.
`ddl.sql` including 2 table creating operations `host_info` and `host_usage` table.<br>
```
*usage*
psql -h localhost -U postgres -d host_agent -f sql/ddl.sql
```

- **host_info.sh** under `/scripts` directory.<br>
The script collects hardware specification data and then insert the data to the psql instance. The hardware specifications are static, so the script will be executed only once.<br>
```
*usage*
./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
*example*
./scripts/host_info.sh "localhost" 5432 "host_agent" "postgres" "mypassword"
```

- **host_usage.sh** under `/scripts` directory.<br>
The script collects server usage data and then insert the data into the psql database. The script will be executed every minute using Linux `crontab`.<br>
```
*usage*
bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
*example
bash scripts/host_usage.sh localhost 5432 host_agent postgres mypassword
```
- **queries.sql** under `/sql` directory.<br>
First query is to group hosts by CPU number and sort by their memory size.<br>
Second query is to show the average used memory in percentage over 5 mins interval for each host.<br>
Third query is to detect host failures to see if it inserts 5 data points within 5-min interval.<br>
```
*usage*
psql -h localhost -U postgres -d host_agent -f sql/queries.sql
```
## Database Modeling
- **host_info table**

| id      | hostname | cpu_number | cpu_architecture | cpu_model | cpu_mhz | l2_cache | total_mem | timestamp |
|---------|----------|------------|------------------|-----------|---------|----------|-----------|-----------|
| serial  | text     | integer    | varchar          | text      | real    | varchar  | integer   | timestamp |

**Constraints**<br>
Primary Key:`id`<br>
UNIQUE:`hostname`<br>

- **host_usage table**

| timestamp | host_id | memory_free | cpu_idle | cpu_kernel | disk_io | disk_available |
|-----------|---------|-------------|----------|------------|---------|----------------|
| timestamp | integer | integer     | integer  | integer    | integer | varchar        |

**Constraints**<br>
Foreign Key:`host_id` reference to `id` in `host_info` table.<br>

# Test
Testing each bash scripts and SQL queries through the Linux CLI.
For better test result, some entries into host_info table are manually inserted.
Also used IntelliJ IDEA database tool to test the SQL queries, which can give a more intuitive result when testing more complicated queries. 
# Deployment
The agent program is scheduled using crontab. Source code is managed by GitHub. Database is provisioned with Docker container.

# Improvements
- create a script to stop the crontab job when there is no need to record the data, in case the database gets huge. 






