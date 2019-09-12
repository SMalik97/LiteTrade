<?php

include ("DatabaseConfig.php") ;
 
 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 $Money = @$_POST['Money'];
 $phone = @$_POST['Phone'];

 $Sql_Query = "update `litetrade_reg` set total_balance='$Money' where phone='$phone'";
 //print_r($Sql_Query); exit;
 if(mysqli_query($con,$Sql_Query)){
 
 echo 'Saved!';

 }
 else{
 
 echo 'Try Again';
 
 }
 mysqli_close($con);
?>