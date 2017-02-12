
#! /bin/bash
# A script to run the Hadoop wordcount example program on all
# files stored in the /home/hduser/tmp/snip directory (*.txt)


oldTstamp=0

while true; do
newTstamp=`stat -c %Y /home/hduser/tmp/newtimestamp.txt`

# Compare the Timestamps

if [ $newTstamp -gt $oldTstamp ]
then
	echo "New data to be processed \n"
	oldTstamp=$newTstamp

	# If new data is available start the hadoop processes
	cd /usr/local/hadoop

	# Clear all the snip directories under tmp
	bin/hadoop fs -rmr /usr/local/tmp/snip-hdfs
	bin/hadoop fs -rmr /usr/local/tmp/snip-output

	# Copy files into the HDFS
	echo "Copying the new data into HDFS \n"
	bin/hadoop dfs -copyFromLocal /home/hduser/tmp/newtimestamp.txt /usr/local/tmp/snip-hdfs
	bin/hadoop dfs -ls /usr/local/tmp/snip-hdfs

	# Run the mapreduce job
	echo "Running the map-reduce"
	echo
	bin/hadoop jar /home/hduser/tmp/HomeServer.jar sa.WordCount /usr/local/tmp/snip-hdfs /usr/local/tmp/snip-output
	echo
	bin/hadoop dfs -cat /usr/local/tmp/snip-output/part-00000 > /home/hduser/hadoop-output.txt
	chmod 777 /home/hduser/hadoop-output.txt
	echo "Output processed to /usrlocal/tmp/hadoop-output"
fi
done

exit
