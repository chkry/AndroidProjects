package com.example.rishikumar.m_share;

;
import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

import android.net.Uri;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.ToggleButton;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, Runnable, GestureDetector.OnGestureListener
        , MediaPlayer.OnCompletionListener, tab1.OnFragmentInteractionListener, tab2.OnFragmentInteractionListener, tab3.OnFragmentInteractionListener {

    private ArrayList<Songmanager> songList;
    private static ArrayList<Songmanager> staticsongList;
    private ImageButton next, play, prev, ib1;
    private Boolean isPlaying = false, atleatOncePlayed = false, isPressed = false, shuffle = false, repeat = false;
    private MediaPlayer mPlayer;
    private int i = 0;
    int max = 0, currentPosition, total;
    private TextView cur, end, tv1, tv2, tv3, currentsong;
    private Button songinfo;
    private TabHost th;
    private SeekBar skb;
    private ToggleButton tb1, tb3, tb4;
    private tab1 instancetab1;
    private tab2 instancetab2;
    private tab3 instancetab3;
    TabLayout tblayout;
    PagerAdaptor adaptor;
    ViewPager vp;
    private ViewPager mViewPager;
    Fragment fragment = null;


    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;


    Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;


    public static ArrayList<Songmanager> getsongList() {
        return staticsongList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tblayout = (TabLayout) findViewById(R.id.tabLayout);
        tblayout.addTab(tblayout.newTab().setText("Songs"));
        tblayout.addTab(tblayout.newTab().setText("Playing"));
        tblayout.addTab(tblayout.newTab().setText("Share Now"));

        vp = (ViewPager) findViewById(R.id.pager);
        adaptor = new PagerAdaptor(getSupportFragmentManager(), tblayout.getTabCount());
        vp.setAdapter(adaptor);
        vp.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tblayout));

        tblayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Fragment page = adaptor.getItem(1);

        initiate();

        getSongList();

        Collections.sort(songList);
        Collections.sort(staticsongList);

        instancetab1 = new tab1();

        instancetab2 = new tab2();
        instancetab3 = new tab3();


    }


    public void switchContent(Fragment fragment) {
        this.fragment = fragment;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.tab1frame, fragment)
                .commit();

        invalidateOptionsMenu();


    }

    public void songPicked(View view) throws IOException {

        i = (int) view.getTag();
        playMusic(i);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.play:
                if (atleatOncePlayed == true)


                {
                    if (isPlaying == true) {
                        currentPosition = mPlayer.getCurrentPosition();
                        total = mPlayer.getDuration();
                        mPlayer.pause();
                        //  cur.setText(currentPosition);
                        // end.setText(total);


                        skb.setProgress(currentPosition);

                        play.setImageResource(R.drawable.play22);

                        Log.d("taggg", "You pressed Stop ");
                        isPlaying = false;


                    } else {
                        play.setImageResource(R.drawable.pause22);

                        mPlayer.start();
                        isPlaying = true;
                        Log.d("taggg", "You pressed resume ");
                    }
                } else {
                    if (shuffle == true) {

                        Random rd = new Random();
                        i = rd.nextInt(songList.size());
                        playMusic(i);
                    } else {
                        playMusic(0);
                    }
                }
                break;

            case R.id.prev:
                if (i >= 1) {
                    mPlayer.reset();
                    playMusic(--i);
                }
                break;


            case R.id.next:
                Log.d("taggg", "You list size is :  " + songList.size());
                if ((i < songList.size() - 1) && shuffle == false) {
                    mPlayer.reset();
                    playMusic(++i);
                } else if (shuffle == true) {

                    mPlayer.reset();
                    Random rd = new Random();
                    i = rd.nextInt(songList.size());
                    playMusic(i);
                }
                break;

            case R.id.currentsong:


                break;

            case R.id.tb1:
                if (isPressed == false) {
                    tv2.setTextColor(Color.BLACK);
                    tv3.setTextColor(Color.BLACK);
                    isPressed = true;
                } else {
                    tv2.setTextColor(Color.WHITE);
                    tv3.setTextColor(Color.WHITE);
                    isPressed = false;
                }
                break;

            case R.id.tb3:
                Log.d("taggg", "You pressed shuffle");
                if (shuffle == true) {
                    shuffle = false;
                    tb3.setBackgroundResource(R.drawable.shuffle22);
                } else {
                    shuffle = true;
                    tb3.setBackgroundResource(R.drawable.noshuffle2222);
                }
                break;

            case R.id.tb4:
                Log.d("taggg", "You pressed repeat");
                if (repeat == true) {
                    repeat = false;
                    tb4.setBackgroundResource(R.drawable.repeat22);
                } else {
                    repeat = true;
                    tb4.setBackgroundResource(R.drawable.norepeat22);
                }
                break;

        }
    }


    public void playMusic(int i) {
        try {


            Uri myUri = Uri.parse(songList.get(i).getdata());
            //Log.d("taggg", "You picked with i=  :" + i);

            mPlayer.reset();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            mPlayer.setDataSource(MainActivity.this, myUri);

            mPlayer.prepare();

            mPlayer.start();
            mPlayer.setOnCompletionListener(this);

            play.setImageResource(R.drawable.pause22);

            isPlaying = true;
            atleatOncePlayed = true;
            String duration = getTimeString(mPlayer.getDuration());
            end.setText(duration);
            max = mPlayer.getDuration();
            skb.setMax(max);
            skb.setProgress(0);
            currentsong.setText(songList.get(i).getTitle());

            try {
                Uri coverPath = songList.get(i).getAlbumArt();
                ParcelFileDescriptor parcelFileDescriptor = this.getContentResolver().openFileDescriptor(coverPath, "r");
                if (parcelFileDescriptor != null) {
                    Log.d("taggg", "Your selected album art uri is  :" + coverPath);
                    // ib1.setImageURI(coverPath);
                }


            } catch (Exception e) {
                Log.d("taggg", "Your selected album art uri not found");
                // ib1.setImageResource(R.drawable.noalbum1);
            }

            vp.setCurrentItem(1);
            tv1 = vp.findViewById(R.id.tv1);
            tv2 = vp.findViewById(R.id.tv2);
            tv3 = vp.findViewById(R.id.tv3);
            ib1 = vp.findViewById(R.id.ib1);

            tv1.setText(songList.get(i).getTitle());
            tv3.setText(songList.get(i).getArtist());
            tv2.setText(songList.get(i).getAlbum());
            try {
                Uri coverPath = songList.get(i).getAlbumArt();
                ParcelFileDescriptor parcelFileDescriptor = this.getContentResolver().openFileDescriptor(coverPath, "r");
                if (parcelFileDescriptor != null) {
                    Log.d("taggg", "Your selected album art uri is  :" + coverPath);
                    ib1.setImageURI(coverPath);
                }


            } catch (Exception e) {
                Log.d("taggg", "Your selected album art uri not found");
                ib1.setImageResource(R.drawable.noalbum1);
            }


            new Thread(this).start();


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


    public void initiate() {
        // songView = (ListView) findViewById(R.id.song_list);
        songList = new ArrayList<Songmanager>();
        staticsongList = new ArrayList<Songmanager>();

        cur = (TextView) findViewById(R.id.currtime);
        end = (TextView) findViewById(R.id.endtime);

        tb3 = (ToggleButton) findViewById(R.id.tb3);
        tb4 = (ToggleButton) findViewById(R.id.tb4);

        tb3.setOnClickListener(this);
        tb4.setOnClickListener(this);

        next = (ImageButton) findViewById(R.id.next);
        prev = (ImageButton) findViewById(R.id.prev);
        play = (ImageButton) findViewById(R.id.play);
        prev.setImageResource(R.drawable.rewind22);
        play.setImageResource(R.drawable.play22);
        next.setImageResource(R.drawable.forward22);

        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        play.setOnClickListener(this);
        mPlayer = new MediaPlayer();

        currentsong = (TextView) findViewById(R.id.currentsong);
        currentsong.setOnClickListener(this);

        skb = (SeekBar) findViewById(R.id.seekbar);
        skb.setOnSeekBarChangeListener(this);


    }

    private String getTimeString(long millis) {
        StringBuffer buf = new StringBuffer();

        int hours = (int) (millis / (1000 * 60 * 60));
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf
                /*.append(String.format("%02d", hours))
                .append(":")*/
                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        String cuurrr = "00:00";
        if (atleatOncePlayed == true) {
            cuurrr = getTimeString(mPlayer.getCurrentPosition());
        }
        cur.setText(cuurrr);


        if (mPlayer != null && b == true && atleatOncePlayed == true) {
            mPlayer.seekTo(i);
            cuurrr = getTimeString(mPlayer.getCurrentPosition());
            cur.setText(cuurrr);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void run() {
        Log.d("taggg", "runnable  has been started.. ");


        currentPosition = mPlayer.getCurrentPosition();
        total = mPlayer.getDuration();

        while (mPlayer != null && currentPosition < total) {
            currentPosition = mPlayer.getCurrentPosition();
            skb.setProgress(currentPosition);
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public void getSongList() {

        ContentResolver musicResolver = getContentResolver();

        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            //get columns

            int dataColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Media.DATA);
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            int albumColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ALBUM);

            int imageColumn = musicCursor.getColumnIndex
                    (MediaStore.Audio.Albums.ALBUM_ART);
            Log.d("taggg", "album art  column is original   :" + imageColumn);

            if (imageColumn < 0) {
                imageColumn = 1;
            }


            Log.d("taggg", "album art  column is :" + imageColumn);

            int j = 0;

            //add songs to list
            do {

                long thisId = ++j;
                String thisdata = musicCursor.getString(dataColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                String thisAlbum = musicCursor.getString(albumColumn);

                Long albumId = musicCursor.getLong(musicCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));

                Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
                Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, albumId);

                // String thisAlbumArt = musicCursor.getString(musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Albums.ALBUM_ART));

                Log.d("taggg", "You picked with album uri  : " + albumArtUri);
                Songmanager element = new Songmanager(thisId, thisdata, thisTitle, thisArtist, thisAlbum, albumArtUri);
                songList.add(element);
                staticsongList.add(element);

            }
            while (musicCursor.moveToNext());
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.reset();
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        /*if (th.getCurrentTab() == 0) {
            th.setCurrentTab(1);
        } else {
            th.setCurrentTab(0);
        }*/

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 500);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
        int maxTabs = th.getTabContentView().getChildCount();
        int newTab = 0;
        int currentTab = th.getCurrentTab();
        if (Math.abs(event1.getY() - event2.getY()) == SWIPE_MAX_OFF_PATH) {
            return false;
        }
        if (event1.getX() - event2.getX() == SWIPE_MIN_DISTANCE && Math.abs(velocityX) == SWIPE_THRESHOLD_VELOCITY) {
            // Swipe right to left
            newTab = currentTab + 1;
        } else if (event2.getX() - event1.getX() == SWIPE_MIN_DISTANCE
                && Math.abs(velocityX) == SWIPE_THRESHOLD_VELOCITY) {
            // Swipe left to right
            newTab = currentTab - 1;
        }
        if (newTab == 0 || newTab == (maxTabs - 1)) {
            return false;
        }
        th.setCurrentTab(newTab);


        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {


    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        {
            if ((i < songList.size() - 1) && shuffle == false && repeat == false) {

                playMusic(++i);
            } else if (repeat == true) {

                playMusic(i);
            } else if (shuffle == true && repeat == false) {

                Random rd = new Random();
                i = rd.nextInt(songList.size());
                playMusic(i);
            }
        }
    }
}

