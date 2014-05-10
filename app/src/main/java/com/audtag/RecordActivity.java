package com.audtag;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RecordActivity extends Activity
{
  //private static final int ACTIVITY_RECORD_SOUND = 1;
  private static final String TAG = "AudTagInfo";
  private static final String audioFilepath = "/audtag/tag.m4a";
  private static final int MAX_DESC_LEN = 4000;
  private String soundFile;
  private Button recordBtn;
  private Button stopRecordBtn;
  private Button playBtn;
  private Button uploadBtn;
  private TextView errorText;
  private TextView descText;
  private ContentResolver resolver;
  private MediaRecorder recorder = new MediaRecorder();
  private HashMap<String,String> valuesHash = new HashMap<String,String>();

  
  /** Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.record_layout);
    resolver = getContentResolver();
    errorText = (TextView)findViewById(R.id.ErrorText);
    descText = (TextView)findViewById(R.id.description);
    recordBtn = (Button) findViewById(R.id.RecordButton);
    stopRecordBtn = (Button) findViewById(R.id.StopRecordButton);
    playBtn = (Button) findViewById(R.id.PlayButton);
    uploadBtn = (Button) findViewById(R.id.Upload);
    
    //make sound file and create directory if it doesn't exist
    soundFile = Environment.getExternalStorageDirectory().getAbsolutePath() + audioFilepath;
    File directory = new File(soundFile).getParentFile();
    if (!directory.exists() && !directory.mkdirs()) {
      errorText.setText("Path to file could not be created.");
      recordBtn.setClickable(false);
    }

    
    //set recordBtn for use, and add onclick listener
    recordBtn.setOnClickListener(new View.OnClickListener() 
    {
      public void onClick(View v) 
      {
        String mountState = Environment.getExternalStorageState();
        if(!mountState.equals(Environment.MEDIA_MOUNTED))
        {
          errorText.setText("SD Card is not mounted.  It is " + mountState + ".");
          recordBtn.setClickable(false);
        }
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(soundFile);
        errorText.setText(soundFile);
        try
        {
          recorder.prepare();
        }
        catch(IOException e)
        {
          errorText.setText("Unable to initialize sound recorder.");
          recordBtn.setClickable(false);
        }
        recorder.start();
      }
    });  
    
    stopRecordBtn.setOnClickListener(new View.OnClickListener() 
    {
      public void onClick(View v) 
      {
        recorder.stop();
        
        recorder.release();
        playBtn.setClickable(true);     
        uploadBtn.setClickable(true); 
        Log.i(TAG, soundFile);
      }
    });
    
    //set uploadBtn for use, and add onclick listener
    uploadBtn.setOnClickListener(new View.OnClickListener() 
    {
      public void onClick(View v) 
      {
        valuesHash.clear();
        //TODO: get real username
        valuesHash.put("username", "jason");
        valuesHash.put("password", "test");
        //TODO: get real visibility
        valuesHash.put("visibility", "public");
        //if(descText.getText() != null)
        valuesHash.put("description", descText.getText().toString());
        //get geolocation
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location currentLoc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(currentLoc != null)
        {
          valuesHash.put("latitude", String.valueOf(currentLoc.getLatitude()));
          valuesHash.put("longitude", String.valueOf(currentLoc.getLongitude()));
          valuesHash.put("altitude", String.valueOf(currentLoc.getAltitude()));
        }
        //append description
        errorText.setText(ServerInteraction.doHttpPostFile("http://mindgemas.com/audtag/uploadTag.php"
            ,new File(soundFile),valuesHash, resolver));         
      }
    }); 
  }
  /*
  protected void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    switch (requestCode) 
    {
    case ACTIVITY_RECORD_SOUND:
      soundFile = data.getData();
      
      Log.i(TAG, data.getDataString());
      break;
    }
  }*/
}
