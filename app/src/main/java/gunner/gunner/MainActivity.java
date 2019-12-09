package gunner.gunner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import static android.view.View.INVISIBLE;
import static gunner.gunner.R.id.Profile;
import static gunner.gunner.R.id.button;
import static gunner.gunner.R.id.createAccount;
import static gunner.gunner.R.id.imageView2;
import static gunner.gunner.R.id.imageView5;
import static gunner.gunner.R.id.imageView6;
import static gunner.gunner.R.id.logIn;



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

        //Log in boton
        final Button login= (Button)findViewById(logIn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LogIn.class));
                setContentView(R.layout.login);

            }
        });



        //Boton para signup
        final Button signup= (Button)findViewById(createAccount);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
                setContentView(R.layout.register);
            }
        });


        //Cuenta loggeada
        Button profile=(Button) findViewById(Profile) ;
        ImageView image=(ImageView)findViewById(imageView5);
        ImageView randomImage=(ImageView)findViewById(imageView6);

        if(loggedIn) {

            randomImage.setVisibility(INVISIBLE);
            image.setImageBitmap(profileImage);
            profile.setVisibility(View.VISIBLE);
            login.setVisibility(INVISIBLE);
            signup.setVisibility(INVISIBLE);

            profile.setText(MainActivity.loggedUsername);
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, ProfileUser.class));
                    setContentView(R.layout.user_profile);

                }
            });

            Button logOutButt = (Button) findViewById(R.id.logOutButt);
            logOutButt.setVisibility(View.VISIBLE);
            logOutButt.setOnClickListener((v) -> {
                loggedIn = false;
                login.setVisibility(View.VISIBLE);
                signup.setVisibility(View.VISIBLE);
                logOutButt.setVisibility(INVISIBLE);
                profile.setVisibility(INVISIBLE);
                image.setVisibility(INVISIBLE);

            });



        }else{
            login.setVisibility(View.VISIBLE);
            signup.setVisibility(View.VISIBLE);
            image.setVisibility(INVISIBLE);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
