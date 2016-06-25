<?php
echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

// Check the PIN
// @todo Check the PIN against tha database, rather than accept '123456'
if ($_REQUEST['Digits'] == 1) {
	?>
	<Response>
		<Gather numDigits="1" action="step2.php" method="POST">
			<Say>
				To call Nigel, press 1. 
				To call Silvia, press 2
				To call Light, press 3
				To start again, press 0
			</Say>
		</Gather>
	</Response>
	<?php
} elseif ($_REQUEST['Digits'] == 2) {

	header("Location: enter-phone-number.php");
}
?>
