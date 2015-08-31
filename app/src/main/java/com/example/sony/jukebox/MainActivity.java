package com.example.sony.jukebox;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;

/*
 * This activity is the main GUI for the jukebox application.
 * Click on a song from the list and it will play in the background
 * using a service.
 * Even when the app exits, the song will continue playing because
 * it is running in a service.
 */
public class MainActivity extends ActionBarActivity
        implements AdapterView.OnItemSelectedListener {

    private ArrayList<String> list_of_songs;    // list of song titles for spinner
    private ArrayAdapter<String> adapter;       // string adapter for spinner
    private static final String[] SONGS = {     // static string array of song titles
            "Ryu",
            "Ken",
            "Chun Li",
            "E Honda",
            "Blanka",
            "Guile",
            "Zangief",
            "Dhalsim"
    };

    /*
     * This method runs when the activity first starts up.
     * Set up initial state of the GUI.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize list of songs
        list_of_songs = new ArrayList<>();
        fillListWithSongTitles();

        // set up spinner layout
        Spinner spinner = (Spinner) findViewById(R.id.song_list);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list_of_songs);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // set up event listener for clicks on the spinner's drop down menu
        spinner.setOnItemSelectedListener(this);
    }

    /*
     * This method initializes the ArrayList with all the song titles.
     */
    private void
    fillListWithSongTitles()
    {
        for (int i = 0; i < SONGS.length; i++)
        {
            list_of_songs.add(SONGS[i]);
        }
    }

    /*
     * This method gets call when the user selects an item in the spinner.
     * It initiates a request to the JukeboxService to play the given song.
     */
    @Override
    public void
    onItemSelected(AdapterView<?> parent, View view, int index, long id)
    {
        Log.d("MainActivity", SONGS[index] + " chosen - onItemSelected");
        String title = SONGS[index].toLowerCase().replace(" ", "");

        // update image to corresponding character
        updateImage(index);

        // send an intent to JukeboxService to play song
        Intent intent = new Intent(this, JukeboxService.class);
        intent.putExtra("title", title);
        intent.setAction(JukeboxService.ACTION_PLAY);
        startService(intent);
    }

    /*
     * This method gets call when the user does not select an item on drop down menu.
     * We do nothing in this case.
     */
    @Override
    public void
    onNothingSelected(AdapterView<?> parent)
    {
        // do nothing
    }

    /*
     * This method gets call when the Play button is clicked.
     * We do nothing here since intent is send to JukeboxService when user
     * picks a song from spinner.
     */
    public void
    onClickPlay(View view)
    {
        // do nothing
    }

    /*
     * This method gets call when the Stop button is clicked.
     * It initiates a request to the JukeboxService to stop the
     * current playback.
     */
    public void
    onClickStop(View view)
    {
        Log.d("MainActivity", "onClickStop called ...");
        Intent intent = new Intent(this, JukeboxService.class);
        intent.setAction(JukeboxService.ACTION_STOP);
        startService(intent);
    }

    /*
     * This method gets call when the user select a song via the spinner.
     * Updates the image to the character corresponding to the song.
     */
    private void
    updateImage(int index)
    {
        ImageView image = (ImageView) findViewById(R.id.character);
        switch (index)
        {
            case 0:     // Ryu
                image.setImageResource(R.drawable.ryu);
                break;
            case 1:     // Ken
                image.setImageResource(R.drawable.ken);
                break;
            case 2:     // Chun Li
                image.setImageResource(R.drawable.chunli);
                break;
            case 3:     // E Honda
                image.setImageResource(R.drawable.ehonda);
                break;
            case 4:     // Blanka
                image.setImageResource(R.drawable.blanka);
                break;
            case 5:     // Guile
                image.setImageResource(R.drawable.guile);
                break;
            case 6:     // Zangief
                image.setImageResource(R.drawable.zangief);
                break;
            case 7:     // Dhalsim
                image.setImageResource(R.drawable.dhalsim);
                break;
            default:
                break;
        }
    }
}
