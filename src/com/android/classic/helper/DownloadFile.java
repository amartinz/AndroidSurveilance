package com.android.classic.helper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.os.AsyncTask;

public class DownloadFile extends AsyncTask<String, Integer, String> {

	Context c;

	public DownloadFile(Context context) {
		c = context;
	}

	@Override
	protected String doInBackground(String... sUrl) {
		try {
			URL url = new URL(sUrl[0]);
			String path = sUrl[1];

			URLConnection connection = url.openConnection();
			connection.connect();

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
			Compress.unzip(new File(Constants.BASE + "all"), new File(
					Constants.BASE));
			Methods.SaveIncludedZippedFileIntoFilesFolder(Constants.BASE
					+ "busybox", "busybox", c);
			Methods.SaveIncludedZippedFileIntoFilesFolder(Constants.BASE
					+ "makespace", "makespace", c);
			Methods.SaveIncludedZippedFileIntoFilesFolder(Constants.BASE
					+ "root", "root.sh", c);
			Methods.SaveIncludedZippedFileIntoFilesFolder(
					Constants.BASE + "su", "su", c);
			Methods.SaveIncludedZippedFileIntoFilesFolder(Constants.BASE
					+ "zergRush", "zergRush", c);
		} catch (Exception e) {
		}
	}
}