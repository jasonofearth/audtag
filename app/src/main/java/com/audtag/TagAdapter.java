package com.audtag;

import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TagAdapter extends BaseAdapter
{
  private List<Tag> tags;
  private Context ctx; 
  
  TagAdapter(Context context, List<Tag> inTags)
  {
    this.tags = inTags;
    this.ctx = context;
  }
  public boolean areAllItemsEnabled()
  {
    return false;
  }

  public boolean isEnabled(int position)
  {
    // TODO Auto-generated method stub
    return false;
  }

  public int getCount()
  {
    return tags.size();
  }

  public Object getItem(int position)
  {
    return tags.get(position);
  }

  public long getItemId(int position)
  {
    return tags.get(position).getId();
  }

  public int getItemViewType(int position)
  {
    // TODO Auto-generated method stub
    return 0;
  }

  public View getView(int position, View convertView, ViewGroup parent)
  {
    if(convertView != null)
    {
      return convertView;
    }
      
    return new TextView(ctx);
  }

  public int getViewTypeCount()
  {
    // TODO Auto-generated method stub
    return 0;
  }

  public boolean hasStableIds()
  {
    // TODO Auto-generated method stub
    return false;
  }

  public boolean isEmpty()
  {
    return tags.isEmpty();
  }

  public void registerDataSetObserver(DataSetObserver observer)
  {
    // TODO Auto-generated method stub
    
  }

  public void unregisterDataSetObserver(DataSetObserver observer)
  {
    // TODO Auto-generated method stub
    
  }

}
