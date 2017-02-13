<?php

mysql_connect("localhost","root","123456");

mysql_select_db("appliance");

$usr	= $_POST["username"];


if( $_POST["request"] == "update")
{
$ene 	= $_POST["energy"];
$opt 	= $_POST["optime"];
$stm 	= $_POST["stime"];
$etm	= $_POST["deadline"];
$cnst	= $_POST["constraints"];
$app	= $_POST["appliance"];

	$q=mysql_query("UPDATE appliance.appdetail SET `Energy` = '$ene', `OperationTime` = '$opt', `UserStartTime` ='$stm', `UserEndTime` = '$etm', `ConstraintType` ='$cnst' WHERE `username` ='$usr' AND `AppName`='$app'");
	
	while($e=mysql_fetch_assoc($q))
			$output[]=$e;
}

else if ( $_POST["request"] == "retrieve" )	 
{
$app 	= $_POST["appliance"];
$q=mysql_query("SELECT Energy, OperationTime, UserStartTime, UserEndTime, ConstraintType FROM appliance.appdetail WHERE username='$usr' AND AppName='$app'");
		while($e=mysql_fetch_assoc($q))
			$output[]=$e;


}

else if ( $_POST["request"] == "delete" )	 
{
$app 	= $_POST["appliance"];
$q=mysql_query("DELETE FROM appliance.appdetail WHERE username='$usr' AND AppName ='$app'");
}

print(json_encode($output));
mysql_close();
?>
