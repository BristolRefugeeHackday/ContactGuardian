<?php
echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

// Check the PIN
// @todo Check the PIN against tha database, rather than accept '123456'
if ($_REQUEST['Digits'] == 1) {
	@todo add feature to actually call someone!
	?>
	<Response>
		<Gather numDigits="1" action="FIXME.php" method="POST">
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
	?>
	<Response>
		<Redirect>./enter-phone-number.php</Redirect>
	</Response>
	<?
}
?>