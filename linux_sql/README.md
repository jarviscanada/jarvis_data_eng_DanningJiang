# Linux Cluster Monitoring Agent
This project is under development. Since this project follows the GitFlow, the final work will be merged to the main(master) branch after Team Code Team.
## ./scripts/psql_docker.sh usage
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

