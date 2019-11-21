package gunner.gunner;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import static gunner.gunner.R.id.button;
import static gunner.gunner.R.id.createAccount;
import static gunner.gunner.R.id.logIn;

public class MainActivity extends AppCompatActivity  {

    DatabaseConnection connection= new DatabaseConnection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        getSupportActionBar().hide();
        setTheme(R.style.Theme_Design_NoActionBar);
        System.out.println("Hol");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Animation animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.fadeout);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        final Button electricidadBut = (Button) findViewById(button);

        electricidadBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Electricidad.class));
                electricidadBut.startAnimation(animation);
                setContentView(R.layout.electr);

            }
        });

        final Button login= (Button)findViewById(logIn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LogIn.class));
                setContentView(R.layout.login);

            }
        });
        final Button signup= (Button)findViewById(createAccount);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUp.class));
                setContentView(R.layout.register);
            }
        });
    }

}
