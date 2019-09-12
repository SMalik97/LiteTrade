<?php

include ("DatabaseConfig.php") ;
 
 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 $ID = @$_POST['ID'];
 $Email=@$_POST['Email'];
 $Number = @$_POST['Number'];


 $Sql_Query = "update `litetrade_reg` set id_name='$ID', id_number='$Number' where email='$Email'";
//print_r($Sql_Query); exit;
 if(mysqli_query($con,$Sql_Query)){
 
 echo 'Saved!';

 }
 else{
 
 echo 'Try Again';
 
 }
 mysqli_close($con);
?>
