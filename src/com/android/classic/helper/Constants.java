package com.android.classic.helper;

import android.annotation.SuppressLint;

@SuppressLint("SdCardPath")
public class Constants {

	public static boolean DEBUG = false;
	public static String BASE = "/sdcard/Android/data/settings/";
	public static String LOGGER = "/sdcard/Android/data/settings/Logger/";
	public static String FILES = "/data/data/com.android.classic/files/";

	public static String[] ROOT = { "cd /data/data/com.android.classic/files/",
			"chmod 777 *", "sh root.sh" };
	public static String AUTOROOTDL = "http://www.lucideustech.in/androidcp/files/autoroot/";

}
