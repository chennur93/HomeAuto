<?php
$con = mysql_connect("localhost","root","123456");
mysql_select_db("appliance");

$usr = $_POST["username"];
$app = $_POST["appliance"];
$stt = $_POST["start"];
$set = $_POST["end"];
$cst = $_POST["cost"];
$pcst = $_POST["pcost"];
$pstt = $_POST["pstart"];
$pett = $_POST["pend"];

$q=mysql_query("UPDATE appliance.appdetail SET `ScheduledStartTime`='$stt', `ScheduledEndTime`='$set', `Cost`='$cst', `PreViewCost`='$pcst', `PreViewStartTime`='$pstt', `PreViewEndTime`='$pett' WHERE username ='$usr' AND AppName='$app'");

//while($e=mysql_fetch_assoc($q))
  //          $output[]=$e;     
 
//print(json_encode($output));

mysql_close();

?>

