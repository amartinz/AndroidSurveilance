<div id="content">
<b> Users Registered : </b> <br>

<script type="text/javascript">
function viewdevice (id) {
	alert(id);
}

function checkall(ct){
	var chk = document.getElementById('all').checked;
	var box = document.getElementsByName('device');
	if(chk){
		for (var i = 0; i < ct; i++) {
			box[i].checked = true;
		};
	}else{
		for (var i = 0; i < ct; i++) {
			box[i].checked = false;
		};
	}
}

// Onload
window.onload=function(){
	var selectbox = document.getElementById('selectcmd').value;
	var textbox = document.getElementById('cmd');
	textbox.value = selectbox;
};

function updatetext(){
	var selectbox = document.getElementById('selectcmd').value;
	var textbox = document.getElementById('cmd');
	textbox.value = selectbox;
}
</script>

<hr>
	<div class="ph_maindiv_content">
<input type="checkbox" name="all" id = "all" value="Select All" onclick="checkall(<?php echo sizeof($users); ?>)"> </input> ID || Device ID || Registered || Command Seen || Current Command
	</div>

	<?php
	$ct =1;
	foreach ($users as $row) {
	?>
	<div class="ph_maindiv_content">
<input type="checkbox" name="device" id = "<?php echo $row['clm_device_id'] ?>" value = "<?php echo $row['clm_device_id'] ?>"></input>	<?php echo $ct ?> || <a style=" color: #46aeeb; TEXT-DECORATION: NONE" href="<?php echo base_url() ?>admin/viewdevice/<?php echo $row['clm_device_id'] ?>/<?php echo $row['clm_device_name'] ?>/<?php echo $row['clm_device_number'] ?>"><?php echo $row['clm_device_id'] ?></a> || <?php echo $row['clm_registered'] ?> || <?php echo $row['clm_commandseen'] ?> || <?php echo $row['clm_currentcommand'] ?>
	</div>
	<?php
	$ct++;
	}
	?>
<hr>

<select id="selectbox">
	<option value="sendcmd"> Send Command </option>
	<option value="unreg"> Unregister Selected </option>
</select>

<br>

<select name="selectcmd" onchange="updatetext()" id="selectcmd">
	<?php
	//print_r($cmd);
	foreach ($cmd as $command) {
		?>

	 	<option value = "<?php echo $command["clm_cmdvalue"]; ?>"> <?php echo $command["clm_cmdname"]; ?> </option>;
	 <?php } 
	?>
</select>

<input type="text" id = "cmd">

<br>
<input type="button" name="send" value="Go" id="send" onclick="updatecontent(<?php echo $ct; ?>)">
</div>

</body>
</html>