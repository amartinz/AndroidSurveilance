<html>
<head>
	<title> Control Panel</title>
</head>

<body>
	<form action = "<?php echo base_url();?>admin/login" method = "POST">
	<table>
		<tr>
			<td> Username : </td>
			<td> <input type = "text" name = "username" id = "usr"/> </td>
		</tr>

		<tr>
			<td> Password : </td>
			<td> <input type = "password" name = "password" id = "pass"/> </td>
		</tr>

		<tr>
			<td></td>
			<td> <input type = "submit" name = "submit" value = "Login"/> </td>
		</tr>
	</table>
</form>
</body>
</html>