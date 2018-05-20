package org.firstinspires.ftc.teamcode.XVIItoXVIIISchoolYear;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;

import org.firstinspires.ftc.teamcode.R;

import java.io.IOException;
import java.util.HashMap;

class Music {
    //The player handling the audio
    private static MediaPlayer mediaPlayer = null;

    static HashMap<Integer, Song> songs;

    @SuppressLint("UseSparseArrays")
    static void initSongs(){
        songs = new HashMap<>();
        songs.put(0, new Song(R.raw.mrroboto, "Mr Roboto"));
        songs.put(1, new Song(R.raw.axelf, "Axel F"));
        songs.put(2, new Song(R.raw.hookedonafeeling, "Hooked On a Feeling"));
        songs.put(3, new Song(R.raw.comeandgetyourlove, "Come and Get Your Love"));
        songs.put(4, new Song(R.raw.aintnomountainhighenough, "Ain't No Mountain High Enough"));
        songs.put(5, new Song(R.raw.intergalactic, "Intergalactic"));
    }

    static void start(Context context, Song song) {
        if (mediaPlayer == null) mediaPlayer = MediaPlayer.create(context, song.getSongId());
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
        mediaPlayer.getAudioSessionId();
    }

    static void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            try { mediaPlayer.prepare(); }
            catch (IOException e) {}
        }
    }
}
class Song{

    int getSongId() {
        return songId;
    }

    private int songId;

    public String getName() {
        return name;
    }

    private String name;

    Song(int songId, String name){
        this.songId = songId;
        this.name = name;
    }


}
