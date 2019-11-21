package gunner.gunner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static gunner.gunner.R.id.button2;
import static gunner.gunner.R.id.button7;
import static gunner.gunner.R.id.cuentaCreada;
import static gunner.gunner.R.id.editText;
import static gunner.gunner.R.id.editText2;
import static gunner.gunner.R.id.editText3;
import static gunner.gunner.R.id.editText5;
import static gunner.gunner.R.id.imageView;
import static gunner.gunner.R.id.imageView2;

public class SignUp extends AppCompatActivity {

    String email;
    String username;
    String password;
    String number;
    static boolean creadConExito=false;
    DatabaseConnection databaseAccess=new DatabaseConnection();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //Ir para atras
        final Button atrasBut=(Button) findViewById(button2) ;
        atrasBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Holaa");
                startActivity(new Intent(SignUp.this, MainActivity.class));
                setContentView(R.layout.activity_main);
            }
        });

        //Registrar usuario en tabla base de datos
        final Button registerBut=(Button) findViewById(button7) ;
        registerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailText=(EditText) findViewById(editText3);
                email=emailText.getText().toString();
                EditText usernameText=(EditText)findViewById(editText);
                username=usernameText.getText().toString();
                EditText passwordText=(EditText)findViewById(editText2);
                password=passwordText.getText().toString();
                EditText phoneNumberText=(EditText)findViewById(editText5);
                number=phoneNumberText.getText().toString();
                databaseAccess.DatabaseConnection(email,username,password,number);
                if(creadConExito){
                TextView cuentaCreadaConExito= (TextView)findViewById(cuentaCreada);
                cuentaCreadaConExito.setVisibility(View.VISIBLE);
                         }
                //startActivity(new Intent(SignUp.this, MainActivity.class));
                //setContentView(R.layout.activity_main);
            }
        });
    }
}


