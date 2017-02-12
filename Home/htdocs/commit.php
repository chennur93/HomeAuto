<?php
$con = mysql_connect("localhost","root","123456");
//$con = mysql_connect("localhost","root","student");
mysql_select_db("appliance");
 
$usr = $_POST["username"];

        //$q=mysql_query("SELECT * FROM appliance_data WHERE appname = '$appname'");
        $q=mysql_query("UPDATE appliance.appdetail SET Cost= PreViewCost, ScheduledStartTime= PreViewStartTime, ScheduledEndTime= PreViewEndTime WHERE username='$usr'");
mysql_close();
?>
