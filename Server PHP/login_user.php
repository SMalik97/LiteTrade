<?php
include 'DatabaseConfig.php' ;
 
 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
$Email = @$_POST['email'];

$sql="SELECT `email`,`password` FROM `litetrade_reg` WHERE `email`='$Email';";
$result=mysqli_query($con,$sql);
 
$data=array();

while($row=mysqli_fetch_assoc($result)){
$data["data"][]=$row;

 
}

	header('Content-Type:Application/json');
			
	echo json_encode($data);
	
	
	?>
	

