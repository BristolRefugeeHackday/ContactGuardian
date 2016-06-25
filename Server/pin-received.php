<?php
echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";

// Check the PIN
// @todo Check the PIN against tha database, rather than accept '123456'
if ($_REQUEST['Digits'] == 123456) {
	?>

	<Response>
		<Say>Hello Elliot. You have 3 contacts saved</Say>
		<Gather numDigits="1" action="choose-contact-to-call.php" method="POST">
			<Say>To call a single contact, press 1. To alert all your contacts press 2.</Say>
		</Gather>
	</Response>

	<?php
} else {
	// @todo Needs to go back to the main menu
	?>
	
	<Response>
		<Say>Sorry, I didn't recognise that number. Please try again.</Say>
	</Response>

	<?php
}

?>