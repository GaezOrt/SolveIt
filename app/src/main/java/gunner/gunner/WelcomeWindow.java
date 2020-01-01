package gunner.gunner;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;


public class WelcomeWindow extends AppCompatActivity {
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_Design_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_window);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
        }

        //Intent k = new Intent(this, KeepEverythingUpdated.class);
        // startService(k);

        //Load electricistas list in background
        Intent u = new Intent(this, DownloadStuffInBackground.class);
        if (Electricidad.electricistas.isEmpty()) {

            startService(u);
        } else {
            stopService(u);
        }
        TextView text = (TextView) findViewById(R.id.textView);
        TextView r = (TextView) findViewById(R.id.textView32);
        final Animation fadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout);


        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                text.startAnimation(fadeout);
                r.startAnimation(fadeout);
            }
        }, 5000);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeWindow.this, LogIn.class);
                startActivity(intent);
            }
        }, 6000); // 4 seconds
    }

}



