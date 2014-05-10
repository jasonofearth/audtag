package com.audtag;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

public class audtagSoundRecord extends Activity {
	private static final int ACTIVITY_RECORD_SOUND = 1;
	private static final String TAG = "AudTagInfo";
	private ContentResolver resv;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resv = getContentResolver();
        setContentView(R.layout.main);
        
        Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
        startActivityForResult(intent, ACTIVITY_RECORD_SOUND);
        //MediaStore.Video.Media.

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	super.onActivityResult(requestCode, resultCode, data);
    	
    	switch (requestCode) {
    	case ACTIVITY_RECORD_SOUND:
    		/*ServerInteraction.doHttpPostFile("http://www.audtag.com/uploadtest.php"
    				,data.getData(), resv);*/
    		Log.i(TAG, data.getDataString());
    		break;
    	}
    }

}