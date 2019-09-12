<?php
include 'DatabaseConfig.php' ;
 
 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 $email = @$_POST['email'];

$sql="SELECT `name`, `email`, `phone`, `dob`, `gender`, `id_name`, `id_number`, `profile_pic`, `ifsc`, `bank_name`, `account_no`, `acc_hld_name`, `acc_ph_no` FROM `litetrade_reg` WHERE `email`='$email'";
$result=mysqli_query($con,$sql);
 
$data=array();

while($row=mysqli_fetch_assoc($result)){
$data["data"][]=$row;

 
}

	header('Content-Type:Application/json');
			
	echo json_encode($data);
	
	
	?>
	



