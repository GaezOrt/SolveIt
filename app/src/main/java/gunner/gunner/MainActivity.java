package gunner.gunner;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Camera;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.security.Policy;

import static gunner.gunner.R.id.button;

public class MainActivity extends AppCompatActivity  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getSupportActionBar().hide();
        setTheme(R.style.Theme_Design_NoActionBar);
        System.out.println("Hol");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button electricidadBut = (Button) findViewById(button);
        electricidadBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Electricidad.class));
                setContentView(R.layout.electr);
            }
        });

        final SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
       // final Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        //final MediaPlayer future = MediaPlayer.create(this, R.raw.poweringupp);
        //final MediaPlayer ak47 = MediaPlayer.create(this, R.raw.ak);
        //final MediaPlayer bSound = MediaPlayer.create(this, R.raw.bulet);
        //final MediaPlayer rSound = MediaPlayer.create(this, R.raw.g);
        View view = getWindow().getDecorView();
        int orientation = getResources().getConfiguration().orientation;

        /*
        if (Configuration.ORIENTATION_LANDSCAPE == orientation || Configuration.ORIENTATION_UNDEFINED == orientation) {


               bSound.start();

               bSound.stop();
                ak47.start();

            // Landscape
        } else {
            rSound.start();///Do SomeThing;  // Portrait
        }


        SensorEventListener proximityListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {

                if (sensorEvent.values[0] < proximitySensor.getMaximumRange()) {


                } else {


                }


            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };
        sensorManager.registerListener(proximityListener, proximitySensor, 2 * 1000 * 1000);


    }




    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    */
    }

}
