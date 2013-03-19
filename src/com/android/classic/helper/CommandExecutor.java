package com.android.classic.helper;

import android.os.AsyncTask;
import eu.chainfire.libsuperuser.Shell;

public class CommandExecutor extends AsyncTask<String, Void, Void> {

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(String... arg) {

		String[] commands = new String[arg.length];
		for (int i = 0; i < (arg.length - 1); i++) {
			commands[i] = arg[i + 1];
		}
		if (Shell.SU.available()) {
			Shell.SU.run(commands);
		} else {
			Shell.SH.run(commands);
		}

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}
