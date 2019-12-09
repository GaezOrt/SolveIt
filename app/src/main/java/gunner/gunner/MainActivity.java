package gunner.gunner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import static gunner.gunner.R.id.button;

import static gunner.gunner.R.id.imageView10;
import static gunner.gunner.R.id.imageView16;
import static gunner.gunner.R.id.imageView2;
import static gunner.gunner.R.id.imageView5;
import static gunner.gunner.R.id.imageView6;

import static gunner.gunner.R.id.nav_viw;
import static gunner.gunner.R.id.navmenubar;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    static boolean loggedIn=false;
    static String loggedUsername;
    static String loggedEmail;
    static String loggedPhone;
    static String loggedLocation;
    static Bitmap profileImage;
    static byte[] loggedImageInDatabaseArray;
    static boolean electricista;
    static boolean plomero;
    static boolean computacion;
    static boolean carpintero;
    static boolean pintor;
    static boolean gasista;
    static boolean cerrajero;
    static boolean albanil;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.button_animation);

        setTheme(R.style.Theme_Design_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viw);
        navigationView.setNavigationItemSelectedListener(this);


        //Load electricistas list in background
        if(Electricidad.electricistas.isEmpty()) {
            Intent i = new Intent(this, DownloadStuffInBackground.class);
            startService(i);
        }



        //Boton para electricistas
        final Button electricidadBut = (Button) findViewById(button);
        electricidadBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                electricidadBut.startAnimation(myAnim);
                startActivity(new Intent(MainActivity.this, Electricidad.class));
                setContentView(R.layout.electr);

            }
        });


        ImageView menuOpener= (ImageView)findViewById(imageView16);
        menuOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer= (DrawerLayout)findViewById(navmenubar);
                drawer.openDrawer(Gravity.LEFT);


            }
        });


        //Cuenta loggeada

        ImageView image=(ImageView)findViewById(imageView5);
        ImageView randomImage=(ImageView)findViewById(imageView6);

        if(loggedIn) {

            randomImage.setVisibility(INVISIBLE);
            image.setImageBitmap(profileImage);

        }else{

            image.setVisibility(INVISIBLE);
        }


    }

    public boolean onCreateOptionMenu(Menu menu){

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.



        switch (item.getItemId()){
            case R.id.first:
                startActivity(new Intent(this, LogIn.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.first:
                startActivity(new Intent(this, LogIn.class));
                break;
            case R.id.second:
                startActivity(new Intent(this, SignUp.class));
                break;
        }
        return false;
    }
}
