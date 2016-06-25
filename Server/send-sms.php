<?php
header("content-type: text/xml");

$incomingNumber = $_REQUEST['From'];

?>
<?xml version="1.0" encoding="UTF-8"?>
<Response>
	<Message to="+447855369483" from="<?php echo $incomingNumber?>">
		<Body>Dale is detained and wants you to call him on <?php echo $incomingNumber?></Body>
	</Message>
</Response>