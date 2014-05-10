package com.audtag;

public class MediaDownloader {

	/*public void downloadAudioIncrement(String mediaUrl) throws IOException {

		// First establish connection to the media provider
		URLConnection cn = new URL(mediaUrl).openConnection();
		cn.connect();
		InputStream stream = cn.getInputStream();
		if (stream == null) {

		Log.e(getClass().getName(), “Unable to create InputStream for mediaUrl:” + mediaUrl);

		}

		// Create the temporary file for buffering data into
		downloadingMediaFile = File.createTempFile(“downloadingMedia”, “.dat”);
		FileOutputStream out = new FileOutputStream(downloadingMediaFile);

		// Start reading data from the URL stream
		byte buf[] = new byte[16384];
		int totalBytesRead = 0, incrementalBytesRead = 0;
		do {

		int numread = stream.read(buf);
		if (numread <= 0) {

		// Nothing left to read so quit
		break;

		} else {

		out.write(buf, 0, numread);
		totalBytesRead += numread;
		incrementalBytesRead += numread;
		totalKbRead = totalBytesRead/1000;

		// Test whether we need to transfer buffered data to the MediaPlayer
		testMediaBuffer();

		// Update the status for ProgressBar and TextFields
		fireDataLoadUpdate();

		}

		} while (true);

		// Lastly transfer fully loaded audio to the MediaPlayer and close the InputStream
		fireDataFullyLoaded();
		stream.close();

		}
	
	private void testMediaBuffer() {

		// We’ll place our following code into a Runnable so the Handler can call it for running
		// on the main UI thread
		Runnable updater = new Runnable() {

		public void run() {

		if (mediaPlayer == null) {

		// The MediaPlayer has not yet been created so see if we have
		// the minimum buffered data yet.
		// For our purposes, we take the minimum buffered requirement to be:
		// INTIAL_KB_BUFFER = 96*10/8;//assume 96kbps*10secs/8bits per byte
		if ( totalKbRead >= INTIAL_KB_BUFFER) {

		try {

		// We have enough buffered content so start the MediaPlayer
		startMediaPlayer(bufferedFile);

		} catch (Exception e) {

		Log.e(getClass().getName(), “Error copying buffered conent.”, e);

		}

		}

		} else if ( mediaPlayer.getDuration() – mediaPlayer.getCurrentPosition() <= 1000 ){

		// The MediaPlayer has been started and has reached the end of its buffered
		// content. We test for < 1second of data (i.e. 1000ms) because the media
		// player will often stop when there are still a few milliseconds of data left to play
		transferBufferToMediaPlayer();

		}

		}

		};
		handler.post(updater);

		}*/
}
