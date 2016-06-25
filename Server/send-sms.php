<?php
header("content-type: text/xml");

$incomingNumber = $_REQUEST['From'];

echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
?>
<Response>
	<Sms from="+441494372668" to="+447855369483">Dale is detained and wants you to call him on <?php echo $incomingNumber?></Sms>
	<Say>Your message has been sent</Say>
</Response>