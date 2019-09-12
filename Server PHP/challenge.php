<?php

include ("DatabaseConfig.php") ;
 
 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 
 $Rc = @$_POST['rc'];
 $Gc = @$_POST['gc'];
 $Tb = @$_POST['tb'];
 $Wb = @$_POST['wb'];
 $Email = @$_POST['email'];




 $Sql_Query = "UPDATE `litetrade_reg` SET `red_challenge`='$Rc',`green_challenge`='$Gc', `total_balance`='$Tb', `wining_balance`='$Wb' WHERE `email`='$Email'";
 //print_r($Sql_Query); exit;
 if(mysqli_query($con,$Sql_Query)){
 
 echo 'Registered Successful!';
 
 }
 else{
 
 echo 'Some Error Occurred!';
 
 }
 mysqli_close($con);
?>


