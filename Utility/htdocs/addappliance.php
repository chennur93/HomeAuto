<?php
//$con = mysql_connect("localhost","root","student");
$con = mysql_connect("localhost","root","123456");

if (!$con){ die('Could not connect: ' . mysql_error());}




if (mysql_select_db('appliance', $con)) {
	//mysql_select_db('appliance', $con));
	$usr = $_POST["username"];
	$app = $_POST["appname"];
	$energy = $_POST["energy"];
	$optime = $_POST["optime"];
	$starttime = $_POST["starttime"];
	$deadline = $_POST["deadline"];
	$constraint = $_POST["constraints"];
	
	$q = mysql_query("SELECT EXISTS(SELECT * FROM appdetail WHERE AppName = '$app')");
	//$g =  mysql_query("SELECT EXISTS(SELECT * FROM userlist WHERE email = '$email')");
	
	if(mysql_result($q,0) == 1)
		print("false_user");
	//else if (mysql_result($g,0) ==1)
		//print("false_email");
	else {

	$result = mysql_query("INSERT INTO appdetail (username, AppName, Energy, OperationTime, UserStartTime, UserEndTime, ConstraintType, ScheduledStartTime, ScheduledEndTime)
		VALUES ('$usr','$app','$energy','$optime','$starttime', '$deadline','$constraint','$starttime','$deadline')");

		if($result)
			print("true");
		else
			print("false");
	}
}	
mysql_close();
?>
