package com.example.mediaplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button playButton;
    MediaPlayer mediaPlayer;

    FirebaseStorage storage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = findViewById(R.id.playButton);

        storage = FirebaseStorage.getInstance();
        StorageReference musicFileReference = storage.getReference().child("Album1/bensound-ukulele.mp3");
        musicFileReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                System.out.println("Sound file successfully fetched....." + bytes.length);
                File path = new File(getCacheDir() + "/music.mp3");
                FileOutputStream fileOutputStream;
                try {
                    fileOutputStream = new FileOutputStream(path);
                    fileOutputStream.write(bytes);
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(getCacheDir() + "/music.mp3");
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println("Sound file FAILED....." + e.getMessage());
                onFetchFailure();
            }
        });
    }

    public void onFetchFailure(){
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.seekTo(10000);
        mediaPlayer.setVolume(0.4f, 0.4f);
    }

    public void playButtonClicked(View v){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            playButton.setText("Play");
        }
        else{
            mediaPlayer.start();
            playButton.setText("Pause");
        }
    }
}
