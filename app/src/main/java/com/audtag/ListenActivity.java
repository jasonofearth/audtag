package com.audtag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListenActivity extends Activity
{
  
  ArrayList<Tag> tags;
  String[] tagList;
  public void onCreate(Bundle savedInstanceState) 
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.listen_layout);
    
    ListView tagListView = (ListView)findViewById(R.id.listenList);
    
    //get area  
    LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    
    TagGetter getter = new TagGetter(lm);
    
    Thread thread =  new Thread(null, getter, "tagBackground");
    thread.start();
    try
    {
      thread.join();
    }
    catch (InterruptedException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    ArrayList<Tag> tagList = getter.getM_tags();
    //TagAdapter tagsAdapter = new TagAdapter(this.getApplicationContext(),tagList);
    ArrayAdapter<Tag> tagAdapter = new ArrayAdapter<Tag>(this, android.R.layout.simple_list_item_1, tagList);
    tagListView.setAdapter(tagAdapter);
    //tagListView.setOnItemSelectedListener(tagClickListener);
    tagListView.setOnItemClickListener(tagClickListener);
  }
  
  public AdapterView.OnItemClickListener tagClickListener = new AdapterView.OnItemClickListener()
        {
          public void onItemClick(AdapterView parentView, View childView, int position, long id)
          {
            //stream sound
            Tag selectedTag = (Tag)parentView.getAdapter().getItem(position);
            
            
            MediaPlayer MPX = new MediaPlayer();
            try
            {
              MPX.setDataSource("http://mindgemas.com/audtag/getTag.php?id="+ selectedTag.getId());
              MPX.setAudioStreamType(AudioManager.STREAM_MUSIC);
              MPX.prepare();
            }
            catch(Exception e)
            {
              e.printStackTrace();
            }
            MPX.start();
            /*Intent intent = new Intent(android.content.Intent.ACTION_VIEW);

            Uri data = Uri.parse("http://mindgemas.com/audtag/getTag.php?id=23");
            	
            		//"getTag.php?id=" + selectedTag.getId());
            intent.setDataAndType(data,"audio/mpeg");
            try 
            {
              startActivity(intent);  
            } 
            catch (ActivityNotFoundException e) 
            {
              e.printStackTrace();

            } */
          }
          public void onNothingSelected(AdapterView parentView) 
          {  

          } 
         };
}
