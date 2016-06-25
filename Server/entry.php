<?php
header("content-type: text/xml");


echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
?>
<Response>
	<Gather numDigits="6" action="pin-received.php" method="POST">
		<Say>Hello, this is contact guardian. Please enter your pin.</Say>
	</Gather>
</Response>