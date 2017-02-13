<?php
$con = mysql_connect("localhost","root","123456");
//$con = mysql_connect("localhost","root","student");
mysql_select_db("appliance");
 

if (!empty($_POST)) {
$usr = $_POST["username"];

        //$q=mysql_query("SELECT * FROM appliance_data WHERE appname = '$appname'");
        $q=mysql_query("SELECT * FROM appdetail  WHERE username ='$usr'");
       

if (mysql_num_rows($q) == 0) {
    echo "Id doesn't exist";
    exit;
}



 while ($row = mysql_fetch_assoc($q)) {

print($row["AppName"].",");

}


mysql_close();


}
else {

	?>

	        <h1>Login</h1>

	        <form action="manApp.php" method="post">

	            Username:<br />

	            <input type="text" name="username" placeholder="username" />

	            <br /><br />

	           


	            <input type="submit" value="Login" />

	        </form>

	        <a href="register.php">Register</a>

	    <?php

	}

	 

	?> 
