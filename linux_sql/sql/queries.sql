--Group hosts by CPU number and sort by their memory size in descending order(within each cpu_number group).
SELECT cpu_number,id,total_mem OVER (PARTITION BY cpu_number ORDER BY total_mem) FROM host_info;

--Average used memory in percentage over 5 mins interval for each host. (used memory = total memory - free memory). 
SELECT host_id,hostname,round5(host_usage.timestamp),(total_mem-memory_free)/total_mem AS avg_used_mem_percentage
GROUP BY host_id FROM host_usage 
INNER JOIN host_info ON host_info.id = host_usage.host_id; 
