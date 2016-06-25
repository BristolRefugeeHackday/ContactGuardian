<?php

// the user pressed 1, connect the call to 310-555-1212
header("content-type: text/xml");
echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
?>
<Response>
	<Gather numDigits="1" action="start-phone-number-response.php" method="POST">
		<Say>
			We can let your contacts know how to call you.
			If you can be called on this number, press 1
			If you want to enter another number, press 2
			To send a message without a number, press 3
		</Say>
	</Gather>
</Response>