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

import static gunner.gunner.R.id.button;
import static gunner.gunner.R.id.createAccount;
import static gunner.gunner.R.id.logIn;
import static gunner.gunner.R.id.loggedInUsername;

public class MainActivity extends AppCompatActivity  {
    static boolean loggedIn=false;
    static String loggedUsername;
    static String loggedEmail;
    DatabaseConnection connection= new DatabaseConnection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Design_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);



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
            login.setVisibility(View.INVISIBLE);
            signup.setVisibility(View.INVISIBLE);
            TextView usernameLogged=(TextView) findViewById(loggedInUsername);
            usernameLogged.setText("Ingresado como: "+loggedUsername);
        }


    }

}
