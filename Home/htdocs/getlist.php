<?php
$con = mysql_connect("localhost","root","123456");

mysql_select_db("appliance");
 
$usr = $_POST["username"];

        //$q=mysql_query("SELECT * FROM appliance_data WHERE appname = '$appname'");
        $q=mysql_query("SELECT `AppName`, `ScheduledStartTime`, `ScheduledEndTime`, `Cost`, `PreViewCost`, `PreViewStartTime`, `PreViewEndTime` FROM `appdetail` WHERE `username`='$usr'");
        while($e=mysql_fetch_assoc($q))
            $output[]=$e;     
 
print(json_encode($output));
mysql_close();
?>
