<?php

	if($_SERVER['REQUEST_METHOD']=='POST'){
		
		$image = $_POST['image'];
        $name = $_POST['name'];
		
	define('DB_USERNAME', 'hsgawb_android');
define('DB_PASSWORD', 'hsgawb_android');
define('DB_HOST', 'localhost');
define('DB_NAME', 'hsgawb_android');
		
		$conn = new mysqli(DB_HOST, DB_USERNAME, DB_PASSWORD, DB_NAME);
		
		$path = "uploads/$name.png";
		
		$actualpath = "https://hsgawb.com/LiteTrade/$path";
		$sql = "UPDATE `litetrade_reg` SET `profile_pic`='$actualpath' WHERE `email`='$name'";
		//print_r($sql); exit;
		
		if(mysqli_query($conn,$sql)){
			file_put_contents($path,base64_decode($image));
			echo "Successfully Uploaded";
		}
		
		mysqli_close($conn);
	}else{
		echo "Error";
	}
	?>

