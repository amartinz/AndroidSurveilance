
<script type="text/javascript">
function updateajax(id){
	var data = new Array();
	data['clm_cmdname'] = document.getElementById("name").value;
	data['clm_cmdvalue'] = document.getElementById("val").value;
	
	updatecmd(id,data);
}

</script>


<div id="div_cmd">

<?php if(sizeof($cmd) == 0){
	echo "No Commands Found";
}else{?>

<table border="1">
	<tr>
		<th>S.No.</th>
		<th>Name</th>
		<th>Command</th>
		<th> Options </th>
	</tr>
<?php
$ct=1;

foreach($cmd as $row){
?>
<tr>
	<?php if($row['clm_id']!=$id){?>
		<td> <?php echo $ct ?> </td>
		<td> <?php echo $row['clm_cmdname'] ?> </td>
		
		<td> <?php echo $row['clm_cmdvalue'] ?> </td>
		<td><input type = "button" name="id" value="Edit" id="<?php echo $row['clm_id'] ?>" onclick="editcmd(<?php echo $row['clm_id'] ?>)"></td>
	<?php }else{ ?>

		<td> <?php echo $ct ?> </td>
		
		<td><input type = "textbox" value="<?php echo $row['clm_cmdname'] ?>" id = "name" name = "clm_cmdname"></td>

		<td> <input type ="text" name = "clm_cmdvalue" id = "val" value="<?php echo $row['clm_cmdvalue']?>"/> </td>
		
		<td><input type = "button" name="id" value="Update" id="<?php echo $row['clm_id'] ?>" onclick="updateajax(<?php echo $row['clm_id'] ?>)">
			<input type = "button" name="id" value="Delete" id="<?php echo $row['clm_id'] ?>" onclick="deletecmd(<?php echo $row['clm_id'] ?>)"></td>
	<?php } ?>
</tr>
<?php
$ct++;
}
?>
</table>

<?php } ?>

</div>
</body>
</html>