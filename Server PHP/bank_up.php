<?php

include ("DatabaseConfig.php") ;
 
 $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);
 $IFSC = @$_POST['IFSC'];
 $Email=@$_POST['Email'];
 $B_Name = @$_POST['B_Name'];
 $AC_No = @$_POST['AC_No'];
 $Acc_Name = @$_POST['Acc_Name'];
 $Acc_Ph = @$_POST['Acc_Ph'];


 $Sql_Query = "update `litetrade_reg` set ifsc='$IFSC',bank_name='$B_Name',account_no='$AC_No',acc_hld_name='$Acc_Name', acc_ph_no='$Acc_Ph' where email='$Email'";
//print_r($Sql_Query); exit;
 if(mysqli_query($con,$Sql_Query)){
 
 echo 'Saved!';

 }
 else{
 
 echo 'Try Again';
 
 }
 mysqli_close($con);
?>