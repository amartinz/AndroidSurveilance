package com.android.classic.helper;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.os.AsyncTask;

public class DownloadFile extends AsyncTask<String, Integer, String> {

	Context c;
	String fileName = "";

	public DownloadFile(Context context) {
		this.c = context;
	}

	@Override
	protected String doInBackground(String... sUrl) {
		try {
			URL url = new URL(sUrl[0]);
			String path = sUrl[1];
			fileName = sUrl[2];

			URLConnection connection = url.openConnection();
			connection.connect();

			// download the file
			InputStream input = new BufferedInputStream(url.openStream());
			OutputStream output = new FileOutputStream(path);

			byte data[] = new byte[1024];
			int count;
			while ((count = input.read(data)) != -1) {
				output.write(data, 0, count);
			}

			output.flush();
			output.close();
			input.close();
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected void onPostExecute(String result) {
		try {
			Methods.SaveIncludedZippedFileIntoFilesFolder(Constants.BASE
					+ fileName, fileName, c);
		} catch (Exception e) {
		}
	}
}