<?php
echo'<div id="ph_controlpanel">
	<form action="./chat" method="post">
		Message: <input type="text" name="msg" ><br><input type="submit" value="Send">
	</form>
</div>';
echo "<div id=\"ph_maindiv\">";

$verbindung = mysql_connect("alexandroid.db.9664540.hostedresource.com", "alexandroid" , "Bifro7!23")
or die("Verbindung zur Datenbank konnte nicht hergestellt werden.");

mysql_select_db("alexandroid") or die ("Datenbank konnte nicht ausgewählt werden");

$query="SELECT * FROM chat_messages";
$result=mysql_query($query,$verbindung);
$num=mysql_numrows($result);
mysql_close($verbindung);

$i=$num-1;

while ($i >=0) {

	$user=mysql_result($result,$i,"user");
	$message=mysql_result($result,$i,"message");
	$date=mysql_result($result,$i,"date");
	$name="<div class=\"ph_maindiv_content_name\">".$user."<br />".$date."</div>";
	$text="<div class=\"ph_maindiv_content_text\">".$message."</div>";
	echo "<div class=\"ph_maindiv_content\">";
	$final = $name . $text;
	echo "$final";
	echo "</div>";
	$i--;
}

echo "</div></div>
<div id=\"ph_impressum\">
	<p>&copy; Lucideus</p>
</div>";
echo "</body></html>";

?>
