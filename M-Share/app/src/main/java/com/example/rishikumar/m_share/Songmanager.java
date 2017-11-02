package com.example.rishikumar.m_share;

import android.support.annotation.NonNull;

/**
 * Created by rishi.kumar on 10/17/2017.
 */

public class Songmanager implements Comparable {


    private long id;
    private String title;
    private String artist;
    private String data;


    public Songmanager(long songID, String songTitle, String songArtist , String songData) {
        id=songID;
        title=songTitle;
        artist=songArtist;
        data= songData;
    }


    public long getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
    public String getdata(){return data;}





    @Override
    public int compareTo(@NonNull Object o) {
        String  comp  =  ((Songmanager) o ).getTitle();
        return this.getTitle().compareTo(comp);
    }
}
