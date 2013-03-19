package com.android.classic.helper;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import eu.chainfire.libsuperuser.Shell;

public class BootUpReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		/* Timed Service */
		Intent startServiceIntent = new Intent(context, DEBUGSERVICE.class);
		PendingIntent pintent = PendingIntent.getService(context, 0,
				startServiceIntent, 0);

		AlarmManager alarm = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		// Start every 300 seconds
		alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				300 * 1000, pintent);

		if (ConstantVariables.DEBUG) {
			Shell.SH.run("echo 'booted up' > /sdcard/Android/data/settings/Logger/bootup.txt");
		}

	}

}
