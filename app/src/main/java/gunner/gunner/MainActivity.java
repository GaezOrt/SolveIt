package gunner.gunner;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import static gunner.gunner.R.id.Profile;
import static gunner.gunner.R.id.button;
import static gunner.gunner.R.id.createAccount;
import static gunner.gunner.R.id.logIn;


public class MainActivity extends AppCompatActivity  {
    static boolean loggedIn=false;
    static String loggedUsername;
    static String loggedEmail;
    static String loggedPhone;
    static String loggedLocation;
    DatabaseConnection connection= new DatabaseConnection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.Theme_Design_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Button profile=(Button) findViewById(Profile) ;

        //Boton para electricistas
        final Button electricidadBut = (Button) findViewById(button);
        electricidadBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        if(loggedIn){

            profile.setVisibility(View.VISIBLE);
            login.setVisibility(View.INVISIBLE);
            signup.setVisibility(View.INVISIBLE);

            profile.setText(MainActivity.loggedUsername);
            profile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, ProfileUser.class));
                    setContentView(R.layout.user_profile);

                }
            });

            Button logOutButt=(Button) findViewById(R.id.logOutButt);;
            logOutButt.setVisibility(View.VISIBLE);
            logOutButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    loggedIn=false;
                    login.setVisibility(View.VISIBLE);
                    signup.setVisibility(View.VISIBLE);
                    logOutButt.setVisibility(View.INVISIBLE);
                    profile.setVisibility(View.INVISIBLE);

                }
            });


        }else{
            login.setVisibility(View.VISIBLE);
            signup.setVisibility(View.VISIBLE);
        }


    }

}
