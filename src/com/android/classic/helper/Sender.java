package com.android.classic.helper;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;

public class Sender extends AsyncTask<String, Void, String> {

	String URL = "http://www.lucideustech.in/androidcp/device/receiver";

	@Override
	protected void onPostExecute(String result) {
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected String doInBackground(String... params) {

		String type = params[0];
		String data = params[1];

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(URL);
		BasicNameValuePair devicePair = new BasicNameValuePair("did",
				android.os.Build.MODEL);
		BasicNameValuePair sendType = new BasicNameValuePair("type", type);
		BasicNameValuePair sendData = new BasicNameValuePair("data", data);
		List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
		nameValuePairList.add(devicePair);
		nameValuePairList.add(sendType);
		nameValuePairList.add(sendData);
		try {
			UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
					nameValuePairList);
			httpPost.setEntity(urlEncodedFormEntity);
			try {
				@SuppressWarnings("unused")
				HttpResponse httpResponse = httpClient.execute(httpPost);
				return ("done");
			} catch (Exception exc) {
				return (exc.getMessage());
			}
		} catch (Exception uee) {
			return (uee.getMessage());
		}
	}

}
