<?php

$con=mysql_connect("localhost","root","123456");

mysql_select_db("appliance");

$usr = $_POST["username"];




		//$q=mysql_query("SELECT * FROM appliance_data WHERE appname = '$appname'");
		$q=mysql_query("SELECT * FROM appdetail WHERE username='$usr'");
		echo("Querry Fired...\n");
		echo("value...\n");

$handle = fopen('/home/hduser/tmp/newtimestamp'.'.txt','w+');
echo("file opened...\n");

//mysql_fetch_assoc is the key here, don't use mysql_fetch_array as it creates double results
while($row = mysql_fetch_assoc($q)) {
  fputs($handle, join(',', $row)."\n");
}

fclose($handle);

mysql_close();
//----------------------wait here till o/p is not available------------------//

$outfile = '/home/hduser/hadoop-output.txt';
unlink($outfile);

while (!file_exists($outfile)) 
	{
		sleep(2);
	}

sleep(4);

/*---------------------script to update the db---------------------------------*/
$handle = fopen("/home/hduser/hadoop-output.txt", "r");

echo("file opened succesfully....");

$conn = mysql_connect("localhost","root","123456"); 

mysql_select_db("appliance");

while (($data = fgetcsv($handle, ",")) !== FALSE)
{
echo("fetching record....");
$sql = "UPDATE appliance.appdetail SET PreViewCost='$data[8]',PreViewStartTime='$data[6]',PreViewEndTime='$data[7]' WHERE AppName='$data[1]' AND username='$data[0]'";  
mysql_query($sql,$conn) or die(mysql_error());
if (mysql_query($sql)){
//echo "$data[12] \n";
print("positive");
}
}

?>


