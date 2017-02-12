<?php
$con=mysql_connect("localhost","root","123456");

mysql_select_db("appliance");

//$usr = $_POST["username"];




		//$q=mysql_query("SELECT * FROM appliance_data WHERE appname = '$appname'");
		$q=mysql_query("SELECT * FROM appdetail WHERE username='meghashyam'");
		echo("Querry Fired...\n");
		echo("value...\n".$q);

$handle = fopen('/home/hduser/tmp/newtimestamp'.'.txt','w+');
echo("file opened...\n");

//mysql_fetch_assoc is the key here, don't use mysql_fetch_array as it creates double results
while($row = mysql_fetch_assoc($q)) {
  fputs($handle, join(',', $row)."\n");
}

fclose($handle);

mysql_close();
?>


