package com.audtag;

public class Tag
{
  private long id;
  private String description;
  private int type;
  private long taggerId;
  
  
  public Tag(long id, String description, int type, long tagger)
  {
    this.id = id;
    this.description = description;
    this.type = type;
    this.taggerId = tagger;
  }


  public long getId()
  {
    return id;
  }


  public void setId(long id)
  {
    this.id = id;
  }


  public String getDescription()
  {
    return description;
  }


  public void setDescription(String description)
  {
    this.description = description;
  }


  public int getType()
  {
    return type;
  }


  public void setType(int type)
  {
    this.type = type;
  }


  public long getTaggerId()
  {
    return taggerId;
  }


  public void setTaggerId(long taggerId)
  {
    this.taggerId = taggerId;
  }
  
  public String toString()
  {
    return this.description;
  }
}
