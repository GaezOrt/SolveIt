package gunner.gunner;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;

public class CreateAccount extends AppCompatActivity {

    private VideoView mVideoView;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeracc);

        VideoView videoHolder = new VideoView(this);
       // videoHolder.setMediaController(new MediaController(this));
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/"
                + R.raw.bg_video); //do not add any extension

        videoHolder.setVideoURI(video);
        //setContentView(videoHolder);
        videoHolder.start();

        //mVideoView = (VideoView)findViewById(R.raw.bg_video);
        //Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.bg_video);
        //Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.bg_video);

        //mVideoView.setVideoURI(uri);
        //mVideoView.start();
        videoHolder.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
        /*
       mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
           @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
            }
        });
    }
    */

    }
}
