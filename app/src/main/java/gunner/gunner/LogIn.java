package gunner.gunner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static gunner.gunner.R.id.PasswordInc;
import static gunner.gunner.R.id.Profile;
import static gunner.gunner.R.id.button2;
import static gunner.gunner.R.id.button7;
import static gunner.gunner.R.id.editText;
import static gunner.gunner.R.id.editText2;
import static gunner.gunner.R.id.editText3;
import static gunner.gunner.R.id.imageView2;
import static gunner.gunner.R.id.imageView5;

public class LogIn extends AppCompatActivity {

    String username;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        //Apretar boton para ir para atras
        final Button atrasBut=(Button) findViewById(button2) ;
        atrasBut.setOnClickListener ((v)-> {
            finish();
            startActivity(new Intent(LogIn.this, MainActivity.class));
            setContentView(R.layout.activity_main);
        });

        //Apretar boton para loggear
        final Button logInButt=(Button) findViewById(button7) ;
        logInButt.setOnClickListener((v)-> {

            EditText usernameText=(EditText) findViewById(editText);
            username=usernameText.getText().toString();
            EditText passwordText=(EditText) findViewById(editText2);
            password=passwordText.getText().toString();



            final TextView passwordIncText=(TextView) findViewById(PasswordInc) ;
            try {
                System.out.println("Connection is successful");


                PreparedStatement pt = DatabaseConnection.conn.prepareStatement("SELECT * FROM Users where User = ? AND Password = ?");
                pt.setString(1, username);
                pt.setString(2,password);
                ResultSet rs= pt.executeQuery();
                while( rs.next() ) {

                    String userName = rs.getString("User");
                    String passwordd = rs.getString("Password");

                    if (userName.equals(username) && passwordd.equals(password)) {
                        String location=rs.getString("location");
                        String emails=rs.getString("email");
                        String phone=rs.getString("telefono");

                        //Agarrando imagen
                        Blob blob =rs.getBlob("Foto");
                        int blobLength = (int) blob.length();
                        byte[] blobAsBytes = blob.getBytes(1, blobLength);
                        Log.w("Activity"," Array setteado desde base de datos a variable principal en MAiActivity" + MainActivity.loggedImageInDatabaseArray);

                        MainActivity.loggedEmail=emails;
                        MainActivity.loggedPhone=phone;
                        MainActivity.loggedUsername=username;
                        MainActivity.loggedLocation=location;
                        MainActivity.loggedImageInDatabaseArray=blobAsBytes ;
                        Bitmap bitmap = BitmapFactory.decodeByteArray(MainActivity.loggedImageInDatabaseArray, 0, MainActivity.loggedImageInDatabaseArray .length);
                        MainActivity.profileImage=bitmap;

                        MainActivity.loggedIn=true;
                        startActivity(new Intent(LogIn.this, MainActivity.class));
                        setContentView(R.layout.activity_main);
                        finish();
                        return;
                    } else {

                        passwordIncText.setText("Username or password incorrect.");

                        System.out.println("Username or password wrong");
                    }

                }
            }catch (Exception e){
                passwordIncText.setText("Username or password incorrect.");

            }
        });

    }
}
