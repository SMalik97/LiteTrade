<?php

include ("DatabaseConfig.php") ;
 
 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 
 $Name = @$_POST['name'];
 $Phone = @$_POST['phone'];
 $Email = @$_POST['email'];
 $dob = @$_POST['dob'];
 $Password = @$_POST['pass'];
 $Gender = @$_POST['gen'];



 $Sql_Query = "INSERT INTO `litetrade_reg`(`name`, `email`, `phone`, `dob`, `gender`, `password`) VALUES ('$Name','$Email','$Phone','$dob','$Gender','$Password')";
 //print_r($Sql_Query); exit;
 if(mysqli_query($con,$Sql_Query)){
 
 echo 'Registered Successful!';
 
 }
 else{
 
 echo 'Some Error Occurred!';
 
 }
 mysqli_close($con);
?>


