#save hostname to a variable
hostname=$(hostname -f) #rvs-remote-desktop-centos7.us-east1-c.c.warm-scion-350516.internal

#save the output of command `lscpu` to a variable lscpu_out
lscpu_out=`lscpu`
#save number of CPU to a variable
#note: `xargs` is a trick to remove leading and trailing white spaces
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs) 


#hardware info
cpu_number=$(echo "$lscpu_out"  | egrep "^CPU\(s\):" | awk '{print $2}' | xargs) #2
cpu_architecture=$(echo "$lscpu_out"|egrep "^Architecture:"|awk '{print $2}'|xargs) #x86_64
cpu_model=Intel(R) Xeon(R) CPU @ 2.30GHz
cpu_mhz=2299.998
l2_cache=256K
total_mem=$(grep MemTotal /proc/meminfo|awk '{print $2}') #7489612 #in KB
timestamp=$(date "+%Y-%m-%d %H:%M:%S") #current timestamp in `2019-11-26 14:40:19` format 
