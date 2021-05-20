 package com.example.smartmusicapp.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.smartmusicapp.Albums.AllSongs;
import com.example.smartmusicapp.DbHandler.MyDbHandler;
import com.example.smartmusicapp.MainActivity;
import com.example.smartmusicapp.R;
import com.google.firebase.database.collection.LLRBNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class FragmentPlay extends Fragment {

    private static final String TAG = "FragmentPlay";
   MediaPlayer player = new MediaPlayer();
    ImageButton play,stop;
    AudioManager audioManager;
    SeekBar seekProg,seekVol;
    ImageView songImage,favourite;
    Boolean progressChanged;
    AllSongs song,songFromMain;
    String where="";
    MainActivity mainActivity = new MainActivity();
    TextView startTime,endTime;
    AppCompatImageView home,search,playFr,menu;
    boolean playBool=false,stopBool=false;
    public List<String> favouriteSongsName = new ArrayList<>();
    public MyDbHandler db ;
    public View myView;
    public Timer timerSeek,timerStart;
    public int playing;
    public ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_play,container,false);
        myView = view;
         db = new MyDbHandler(getContext());
         favouriteSongsName = db.getFavourites();





        progressBar = view.findViewById(R.id.progressBar);
        play = view.findViewById(R.id.play2);
       // pause = view.findViewById(R.id.pause2);
        stop = view.findViewById(R.id.stop);
        songImage = view.findViewById(R.id.songImage);
        seekVol= view.findViewById(R.id.seekVol);
        seekProg = view.findViewById(R.id.seekProg);
        favourite = view.findViewById(R.id.favourite);
        startTime = view.findViewById(R.id.startTime);
        endTime = view.findViewById(R.id.endTime);



       FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

       Bundle bundle = getArguments();
       String from = bundle.getString("From");


        song = (AllSongs) bundle.getParcelable("Song");


       // where = getArguments().getString("home");

//        if(where!=null)
//        if(where.equals("home"))
//        {
//            try{
//                setupPlayer(song);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!favouriteSongsName.contains(song.getName()))
                {
                    db.addFavourite(song.getName());
                    favourite.setImageResource(R.drawable.favourite);
                    favourite.setColorFilter(Color.RED);
                }
                else
                {
                    db.deleteFavourite(song.getName());
                    favourite.setImageResource(R.drawable.unfavourite);
                    favourite.setColorFilter(Color.WHITE);
                }




            }
        });


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!playBool) {
                    player.start();
                    seekProg.setMax(player.getDuration());
                    play.setImageResource(R.drawable.pause_new);
//                    pause.setVisibility(View.VISIBLE);
                    playBool=true;
                    setSeekProgressBar(player);

                    setStartTime();
                }
                else
                {
                    player.pause();
                    play.setImageResource(R.drawable.play_new);
                    playBool=false;
                }
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                player.stop();
                play.setImageResource(R.drawable.play_new);
                playBool=false;
                player = new MediaPlayer();
                try {
                    player.setDataSource(song.getSong());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.prepareAsync();



            }
        });

        if(song !=null && song.getName()!=null)  {


            progressBar.setVisibility(View.VISIBLE);
                if (favouriteSongsName.contains(song.getName())) {
                    favourite.setImageResource(R.drawable.favourite);
                    favourite.setColorFilter(Color.RED);
                }

                if (from.equals("AllSongs")) {
                    MainActivity.setRecentList(song);
                    try {
                        this.setupPlayer(song);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    play.setImageResource(R.drawable.play_new);
                    playBool=false;
                    player = new MediaPlayer();
                    Glide.with(getContext())
                            .load(song.getImage())
                            .into(songImage);

                    try {

                        player.setDataSource(song.getSong());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    player.prepareAsync();
                    player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            progressBar.setVisibility(View.GONE);
                            setEndTime();
                        }
                    });



                }

        }


        setVolumeBar();

        return view;

    }


    @Override
    public void onStart() {
        super.onStart();

        home = getActivity().findViewById(R.id.home);
        playFr = getActivity().findViewById(R.id.play);
        search = getActivity().findViewById(R.id.search);
        menu = getActivity().findViewById(R.id.menu);

        home.setColorFilter(Color.WHITE);
        playFr.setColorFilter(Color.YELLOW);
        search.setColorFilter(Color.WHITE);
        menu.setColorFilter(Color.WHITE);
    }

    public  void setupPlayer(AllSongs song) throws IOException {



        Glide.with(getContext())
                .load(song.getImage())
                .into(songImage);

            this.player = new MediaPlayer();


            player.setDataSource(song.getSong());
            player.prepareAsync();

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                play.setImageResource(R.drawable.pause_new);
                setEndTime();
                progressBar.setVisibility(View.GONE);
                playBool=true;


                    player.start();
                    setStartTime();
                    seekProg.setMax(player.getDuration());

                    new Timer().scheduleAtFixedRate(
                            new TimerTask() {
                                @Override
                                public void run() {

                                    setSeekProgressBar(player);

                                }
                            }, 1000, 600);

            }
        });



    }

    public void setStartTime()
    {
        timerStart = new Timer();
        timerStart.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                long milliseconds = player.getCurrentPosition();
                long minutes = (milliseconds / 1000) / 60;
                long seconds = (milliseconds / 1000) % 60;
                String minute = (minutes<10?"0"+minutes:""+minutes);
                String second = (seconds<10?"0"+seconds:""+seconds);

                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startTime.setText(minute+":"+second);
                    }
                });


            }
        }, 1000,600);


    }
    public void setEndTime()
    {
        long remainingMillis = player.getDuration();
        long remMinutes = (remainingMillis/1000) / 60;
        long remSeconds = (remainingMillis/1000) % 60;
        String remMinute = (remMinutes<10?"0"+remMinutes:""+remMinutes);
        String remSecond = (remSeconds<10?"0"+remSeconds:""+remSeconds);
        endTime.setText(remMinute+":"+remSecond);

    }
    public void setSeekProgressBar(MediaPlayer player)
    {
        timerSeek = new Timer();
        timerSeek.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekProg.setProgress(player.getCurrentPosition());
                seekProg.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                        if(progressChanged!=null) {
                            if (progressChanged)
                                player.seekTo(progress);
                        }
                    }


                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        progressChanged = true;
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        progressChanged = false;
                    }
                });

            }
        },1000,600);



    }





    public void setVolumeBar()
    {
        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        int maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVol = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekVol.setMax(maxVol);
        seekVol.setProgress(curVol);
        seekVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();

        player.stop();
        if(timerSeek!=null)
        timerSeek.cancel();
        if(timerStart!=null)
        timerStart.cancel();

        playing =0;



        
    }
}
