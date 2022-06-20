--1.Group hosts by CPU number and sort by their memory size in descending order(within each cpu_number group).
SELECT cpu_number,id AS host_id,total_mem 
OVER (PARTITION BY cpu_number ORDER BY total_mem DESC) 
FROM host_info;

--2.Average used memory in percentage over 5 mins interval for each host. (used memory = total memory - free memory). 
-- Function to round timestamp to the nearest 5 minutes
CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;
SELECT DISTINCT
    hu.host_id,
    hi.hostname,
    round5(hu."timestamp") AS "timestamp",
    AVG(
    (hi.total_mem - hu.memory_free)* 100 / hi.total_mem
        ) 
    OVER(
        PARTITION BY round5(hu."timestamp"), hu.host_id
        ) AS used_mem_percentage
FROM
    host_usage hu,
    host_info hi
WHERE
    hu.host_id = hi.id;

--3.Detect host failure
--show the number of host_usage data collections happened in 5 minute intervals.
SELECT DISTINCT
    host_id,
    round5("timestamp") AS ts,
    COUNT(host_id) OVER(
        PARTITION BY round5("timestamp"), host_id
    ) AS num_data_points
FROM
    host_usage
ORDER BY
    host_id, ts;



