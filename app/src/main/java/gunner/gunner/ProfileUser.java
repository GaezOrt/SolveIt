package gunner.gunner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static gunner.gunner.R.id.button2;
import static gunner.gunner.R.id.editText;
import static gunner.gunner.R.id.editText2;
import static gunner.gunner.R.id.editText3;
import static gunner.gunner.R.id.editText5;
import static gunner.gunner.R.id.location;


public class ProfileUser extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        //Boton para atras
        final Button atrasBut=(Button) findViewById(button2) ;
        atrasBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ProfileUser.this, MainActivity.class));
                setContentView(R.layout.activity_main);
            }
        });

        //Mostrar email
        final TextView emailButt=(TextView) findViewById(editText3) ;
        emailButt.setText(MainActivity.loggedEmail);
        //Mostrar username
        final TextView usernameButt=(TextView) findViewById(editText) ;
        usernameButt.setText(MainActivity.loggedUsername);
        //Mostrar telefono
        final TextView phoneButt=(TextView) findViewById(editText5) ;
        phoneButt.setText(MainActivity.loggedPhone);
        //Mostrar ubicacion
        TextView locatText=(TextView) findViewById(editText2) ;
        locatText.setText(MainActivity.loggedLocation);
    }

}
