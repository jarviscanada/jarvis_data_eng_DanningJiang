# Linux Cluster Monitoring Agent
This project is under development. Since this project follows the GitFlow, the final work will be merged to the main(master) branch after Team Code Team.<br>
This monitoring agent program will be installed on each server to collect both hardware specification data and resource usage data, and then persist data into a psql instance. Therefore, the program can be installed on each server and collect data automatically.
## ./scripts/psql_docker.sh
Implement a script to create/start/stop the psql container.<br>
**usage**
```
./scripts/psql_docker.sh start|stop|create [db_username][db_password]
```
**examples**
```
# create a psql docker container with the given username and password
./scripts/psql_docker.sh create postgres 123456
# start the stopped psql docker container
./scripts/psql_docker.sh start
# stop the running psql docker container
./scripts/psql_docker.sh stop
```
## sql/ddl.sql
Execute ddl.sql script on the host_agent database in the psql instance. `ddl.sql` including 2 table creating operations.`host_info` and `host_usage` table.
```
psql -h localhost -U postgres -d host_agent -f sql/ddl.sql
```

## scripts/host_info.sh
The script collects hardware specification data and then insert the data to the psql instance. You can assume that hardware specifications are static, so the script will be executed only once.<br>
**usage**
```
./scripts/host_info.sh psql_host psql_port db_name psql_user psql_password
```
**example**
```
./scripts/host_info.sh "localhost" 5432 "host_agent" "postgres" "mypassword"
```

## scripts/host_usage.sh
The script collects server usage data and then insert the data into the psql database. The script will be executed every minute using Linux `crontab`.<br>
**usage**
```
bash scripts/host_usage.sh psql_host psql_port db_name psql_user psql_password
```
**example**
```
bash scripts/host_usage.sh localhost 5432 host_agent postgres password
```






