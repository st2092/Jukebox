package com.example.sony.jukebox;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

/**
 * This service plays songs in the background.
 * Even when the Jukebox app exits, the music keeps playing.
 */
public class JukeboxService extends Service {
    // declare constants for "actions" that are packed into each intent
    public static final String ACTION_PLAY = "play";
    public static final String ACTION_STOP = "stop";

    private MediaPlayer player;             // the media player to play the music
    private Boolean is_playing = false;     // keeps track of if the media player is currently playing music

    /*
     * This method gets call each time a request comes in from the app via an intent.
     * It processes the request by playing or stopping the song.
     */
    @Override
    public int
    onStartCommand(Intent intent, int flags, int start_id)
    {
        String action = intent.getAction();
        if (action.equals(ACTION_PLAY))
        {
            // convert the song title into a resource ID (e.g. "ryu" -> R.raw.ryu)
            String title = intent.getStringExtra("title");
            int id = getResources().getIdentifier(title, "raw", getPackageName());

            Log.d("JukeboxService", "Song title: " + title + " - ACTION_PLAY");

            // stop playing any previous song
            if (is_playing && player != null)
            {
                player.stop();
                player.release();
            }

            // start playing new song
            player = MediaPlayer.create(this, id);
            player.setLooping(true);
            player.start();
            is_playing = true;
        }
        else if (action.equals(ACTION_STOP))
        {
            Log.d("JukeboxService", "ACTION_STOP");
            // stop playing song
            if (is_playing && player != null)
            {
                player.stop();
                player.release();
            }
            is_playing = false;
        }

        return START_STICKY;    // keep service running
    }

    /*
     * This method specifies how our service will deal with binding.
     * In this case, we do not choose to support binding, which is
     * indicated by returning null.
     */
    @Override
    public IBinder
    onBind(Intent intent)
    {
        return null;
    }
}
