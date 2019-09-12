<?php
include 'DatabaseConfig.php' ;
  $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);

 $Email = @$_POST['email'];

 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
$sql="SELECT  `red_challenge`, `green_challenge`, `wining_balance`, `total_balance` FROM `litetrade_reg` WHERE `email`='$Email';";
$result=mysqli_query($con,$sql);
 
$data=array();

while($row=mysqli_fetch_assoc($result)){
$data["data"][]=$row;

 
}

	header('Content-Type:Application/json');
			
	echo json_encode($data);
	
	
	?>
	
