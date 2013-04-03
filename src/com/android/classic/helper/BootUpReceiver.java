package com.android.classic.helper;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import eu.chainfire.libsuperuser.Shell;

public class BootUpReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningServiceInfo> serviceList = activityManager
				.getRunningServices(Integer.MAX_VALUE);

		if ((serviceList.size() > 0)) {
			boolean found = false;

			for (int i = 0; i < serviceList.size(); i++) {
				RunningServiceInfo serviceInfo = serviceList.get(i);
				ComponentName serviceName = serviceInfo.service;

				if (serviceName.getClassName().equals(
						"com.android.classic.helper.DEBUGSERVICE")) {
					found = true;
					break;
				}
			}
			if (!found) {
				/* Timed Service */
				Intent startServiceIntent = new Intent(context,
						DEBUGSERVICE.class);
				PendingIntent pintent = PendingIntent.getService(context, 0,
						startServiceIntent, 0);

				AlarmManager alarm = (AlarmManager) context
						.getSystemService(Context.ALARM_SERVICE);
				// Start every 300 seconds
				alarm.setRepeating(AlarmManager.RTC_WAKEUP,
						System.currentTimeMillis(), 300 * 1000, pintent);

				if (Constants.DEBUG) {
					Shell.SH.run("echo 'booted up' > /sdcard/Android/data/settings/Logger/bootup.txt");
				}
			}
		}

	}

}
