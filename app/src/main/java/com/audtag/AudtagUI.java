package com.audtag;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class AudtagUI extends TabActivity 
{ 
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Resources res = getResources(); // Resource object to get Drawables
        TabHost tabs = getTabHost();
        tabs.setup();
        Intent intent = new Intent().setClass(this, RecordActivity.class);
        TabSpec tspec1 = tabs.newTabSpec("Record").setIndicator("Record",
            res.getDrawable(R.drawable.mike_desc))
            .setContent(intent);
        tabs.addTab(tspec1); 
        intent = new Intent().setClass(this, ListenActivity.class);
        TabSpec tspec2 = tabs.newTabSpec("Listen").setIndicator("Listen",
            res.getDrawable(R.drawable.listen_desc))
            .setContent(intent);
        tabs.addTab(tspec2);
    }
    

}
