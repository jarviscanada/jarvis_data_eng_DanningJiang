-- DDL
CREATE TABLE PUBLIC.host_info 
  ( 
     id               SERIAL NOT NULL, 
     hostname         VARCHAR NOT NULL UNIQUE, 
     cpu_number       INTEGER NOT NULL,
     cpu_architecture VARCHAR NOT NULL,
     cpu_model        VARCHAR NOT NULL,
     cpu_mhz          float8 NOT NULL,
     L2_cache         INTEGER NOT NULL,
     total_mem        INTEGER NOT NULL,
     timestamp        TIMESTAMP NOT NULL
  );

-- DML
-- INSERT statement
INSERT INTO host_info (id, hostname ...
