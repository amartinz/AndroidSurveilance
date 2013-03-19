package com.android.classic;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import eu.chainfire.libsuperuser.Shell;

public class DUMMY extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			if (Shell.SU.available()) {
				Shell.SU.run("id");
			}
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_MAIN);
			ComponentName com = new ComponentName("com.android.settings",
					"com.android.settings.LanguageSettings");
			intent.setComponent(com);
			startActivity(intent);
		} catch (Exception e) {
		}
		finish();
	}
}
