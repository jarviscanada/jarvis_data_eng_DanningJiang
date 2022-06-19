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

SELECT host_id,hostname,
round5(host_usage."timestamp") AS "timestamp",
AVG((total_mem-memory_free)*100/total_mem) AS "avg_used_mem_percentage"
GROUP BY host_id,round5(host_usage."timestamp")
FROM host_usage 
LEFT JOIN host_info 
ON host_info.id = host_usage.host_id;

--3.Detect host failure



