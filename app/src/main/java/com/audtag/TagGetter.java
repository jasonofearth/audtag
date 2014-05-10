package com.audtag;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.location.LocationManager;

public class TagGetter implements Runnable
{
  LocationManager m_locationManager = null;
  ArrayList<Tag> m_tags = null;

  TagGetter(LocationManager lm)
  {
    m_locationManager = lm;
  }

  public void run()
  {
    if (m_locationManager == null)
    {
      return;
    }
    Location currentLoc = m_locationManager
        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
    String data = "";
    if(currentLoc != null)
    {
      data = ServerInteraction
        .DownloadText("http://mindgemas.com/audtag/getTags.php?lat="
            + currentLoc.getLatitude() + "&long" + currentLoc.getLongitude()
            + "&alt" + currentLoc.getAltitude());
    }
    else
    {
      data = ServerInteraction
      .DownloadText("http://mindgemas.com/audtag/getTags.php");
    }
    if (data != "")
    {
      JSONObject tagObjects;
      JSONArray tagArray;
      JSONObject jsonTag;
      m_tags = new ArrayList<Tag>();
      try
      {
        tagObjects = new JSONObject(data);
        tagArray = tagObjects.getJSONArray("tags");
        for (int i = 0; i < tagArray.length(); i++)
        {
          jsonTag = tagArray.getJSONObject(i).getJSONObject("tag");
            //get all the values into a real tag object
            m_tags.add(new Tag(jsonTag.getLong("tagId"),
                               jsonTag.getString("description"),
                               0,
                               jsonTag.getLong("userId")));
        }
      }
      catch (JSONException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      catch(Exception ex)
      {
        ex.printStackTrace();
      }
    }
  }

  public ArrayList<Tag> getM_tags()
  {
    return m_tags;
  }

  public void setM_tags(ArrayList<Tag> m_tags)
  {
    this.m_tags = m_tags;
  }
}
