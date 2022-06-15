-- DDL
CREATE TABLE PUBLIC.host_info 
  ( 
     id               SERIAL NOT NULL PRIMARY KEY, 
     hostname         TEXT NOT NULL UNIQUE, 
     cpu_number       INTEGER NOT NULL,
     cpu_architecture VARCHAR(10) NOT NULL,
     cpu_model        TEXT NOT NULL,
     cpu_mhz          REAL NOT NULL,
     l2_cache         VARCHAR(20) NOT NULL,
     total_mem        INTEGER NOT NULL,
     "timestamp"      TIMESTAMP NOT NULL
  );
  
  CREATE TABLE PUBLIC.host_usage 
  ( 
     "timestamp"    TIMESTAMP NOT NULL, 
     host_id        INTEGER NOT NULL references host_info(id),
     memory_free    INTEGER NOT NULL,
     cpu_idle       INTEGER NOT NULL,
     cpu_kernel     INTEGER NOT NULL,
     disk_io        INTEGER NOT NULL,
     disk_available VARCHAR(20) NOT NULL
  ); 

