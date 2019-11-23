package gunner.gunner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static gunner.gunner.R.id.CuentaNoCreada;
import static gunner.gunner.R.id.button2;
import static gunner.gunner.R.id.button7;
import static gunner.gunner.R.id.cuentaCreada;
import static gunner.gunner.R.id.editText;
import static gunner.gunner.R.id.editText2;
import static gunner.gunner.R.id.editText3;
import static gunner.gunner.R.id.editText5;

public class SignUp extends AppCompatActivity {

    String email;
    String username;
    String password;
    String number;
    String location;
    static boolean creadConExito=false;
    DatabaseConnection databaseAccess=new DatabaseConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        //Ir para atras
        final Button atrasBut=(Button) findViewById(button2) ;
        atrasBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SignUp.this, MainActivity.class));
                setContentView(R.layout.activity_main);
            }
        });

        //Registrar usuario en tabla base de datos
        final Button registerBut=(Button) findViewById(button7) ;
        registerBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                TextView nosePudoCrearLaCuenta= (TextView)findViewById(CuentaNoCreada);
                TextView cuentaCreadaConExito= (TextView)findViewById(cuentaCreada);

                EditText emailText=(EditText) findViewById(editText3);
                email=emailText.getText().toString();
                EditText usernameText=(EditText)findViewById(editText);
                username=usernameText.getText().toString();
                EditText passwordText=(EditText)findViewById(editText2);
                password=passwordText.getText().toString();
                EditText phoneNumberText=(EditText)findViewById(editText5);
                number=phoneNumberText.getText().toString();
                EditText locationButt=(EditText)findViewById(R.id.location);
                location=locationButt.getText().toString();

                if(email.length()!=0 && username.length()!=0 && password.length()!=0 && number.length()!=0)
                {
                    databaseAccess.DatabaseConnection(email, username, password, number,location);
                }else
                {
                    nosePudoCrearLaCuenta.setVisibility(View.VISIBLE);
                    cuentaCreadaConExito.setVisibility(View.INVISIBLE);
                }
                if(creadConExito)
                {
                    cuentaCreadaConExito.setVisibility(View.VISIBLE);
                    nosePudoCrearLaCuenta.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}


