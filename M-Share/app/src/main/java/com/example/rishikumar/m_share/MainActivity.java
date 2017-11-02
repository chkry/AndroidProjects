package com.example.rishikumar.m_share;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import android.net.Uri;
import android.content.ContentResolver;
import android.database.Cursor;

import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;


public class MainActivity extends Activity implements View.OnClickListener {

    private ArrayList<Songmanager> songList;
    private ListView songView;
    private ImageButton next, play, prev;
    private Boolean isPlaying = false;
    private MediaPlayer mPlayer;
    private int i = 0;


    Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songView = (ListView) findViewById(R.id.song_list);
        songList = new ArrayList<Songmanager>();
        getSongList();
        Collections.sort(songList);
        SongAdapter songAdt = new SongAdapter(this, songList);
        songView.setAdapter(songAdt);
        next = (ImageButton) findViewById(R.id.next);
        prev = (ImageButton) findViewById(R.id.prev);
        play = (ImageButton) findViewById(R.id.play);
        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        play.setOnClickListener(this);
        mPlayer = new MediaPlayer();
    }


    public void getSongList() {

        ContentResolver musicResolver = getContentResolver();

        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int dataColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
            int j = 0;

            //add songs to list
            do {

                long thisId = ++j;
                String thisdata = musicCursor.getString(dataColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                Log.d("taggg", "You picked with id  : " + thisId);
                songList.add(new Songmanager(thisId, thisTitle, thisArtist, thisdata));
            }
            while (musicCursor.moveToNext());
        }
    }


    public void songPicked(View view) throws IOException {


        try {
           /* ViewGroup viewgp = (ViewGroup)view.getParent();

            View v = viewgp.getChildAt(2);*/
            i = (int) view.getTag();
            Log.d("taggg", "You picked with i=  :" + i);
            Uri myUri = Uri.parse(songList.get(i).getdata());


            mPlayer.reset();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mPlayer.setDataSource(MainActivity.this, myUri);

            mPlayer.prepare();

            mPlayer.start();

            isPlaying = true;

        } catch (IllegalArgumentException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        } catch (SecurityException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        } catch (IllegalStateException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        } catch (IOException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.play:
                if (1 == 1) {
                    mPlayer.reset();
                    Log.d("taggg", "You pressed Stop ");
                }
                break;

            case R.id.prev:

                try {


                    Uri myUri = Uri.parse(songList.get(--i).getdata());
                    Log.d("taggg", "You picked with i=  :" + i);

                    mPlayer.reset();
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    mPlayer.setDataSource(MainActivity.this, myUri);

                    mPlayer.prepare();

                    mPlayer.start();

                    isPlaying = true;

                } catch (IllegalArgumentException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                } catch (SecurityException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                } catch (IllegalStateException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                } catch (IOException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                }


                break;


            case R.id.next:

                try {


                    Uri myUri = Uri.parse(songList.get(++i).getdata());
                    Log.d("taggg", "You picked with i=  :" + i);

                    mPlayer.reset();
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

                    mPlayer.setDataSource(MainActivity.this, myUri);

                    mPlayer.prepare();

                    mPlayer.start();

                    isPlaying = true;

                } catch (IllegalArgumentException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                } catch (SecurityException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                } catch (IllegalStateException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                } catch (IOException e) {

                    // TODO Auto-generated catch block

                    e.printStackTrace();

                }


                break;


        }
    }
}