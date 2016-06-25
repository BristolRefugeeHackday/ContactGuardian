<?php
/*
 * If you can be called on this number, press 1
 * If you want to enter another number, press 2
 * To send a message without a number, press 3
 */
// if the caller pressed anything but 1 send them back
$choice = $_REQUEST['Digits'];
if($choice == '1') {
	header("Location: send-sms.php");
	//header("Location: save-number.php");
	die;
}
else if($choice == '2') {
	header("Location: enter-number.php");
	die;
}
else if($choice == '3') {
	header("Location: without-number.php");
	die;
}
else{
	header("Location: error.php?msg=Unrecognised%20choice:%20$choice");
	die;
}
?>