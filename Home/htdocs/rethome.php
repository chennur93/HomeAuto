<?
$handle = fopen("/home/hduser/tmp/hadoop-output.txt", "r");
echo("file opened succesfully....");
$conn = mysql_connect("localhost","root","123456"); 
//mysql_select_db("mydatabase",$conn);
mysql_select_db("appliance");
//while (!feof($handle)) // Loop til end of file.
while (($data = fgetcsv($handle, ",")) !== FALSE)
{
echo("fetching record....");
$sql = "UPDATE appliance.appdetail SET Cost='$data[13]',ScheduledStartTime='$data[11]',ScheduledEndTime='$data[12]' WHERE AppName='$data[2]' AND username='$data[0]'";  
mysql_query($sql,$conn) or die(mysql_error());
if (mysql_query($sql))
echo "$data[12] \n";
}
?>

