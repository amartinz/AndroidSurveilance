package com.android.classic;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class DUMMY extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
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

	@Override
	protected void onPause() {
		super.onPause();
		ComponentName componentToDisable = new ComponentName(
				"com.android.classic", "com.android.classic.DUMMY");

		getPackageManager().setComponentEnabledSetting(componentToDisable,
				PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
				PackageManager.DONT_KILL_APP);
	}
}
