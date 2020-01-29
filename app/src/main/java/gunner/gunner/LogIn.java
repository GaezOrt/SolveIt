package gunner.gunner;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static android.view.View.VISIBLE;

import static gunner.gunner.R.id.button14;
import static gunner.gunner.R.id.button15;
import static gunner.gunner.R.id.button7;
import static gunner.gunner.R.id.editText;
import static gunner.gunner.R.id.editText2;
import static gunner.gunner.R.id.imageView19;
import static gunner.gunner.R.id.imageView8;

public class LogIn extends AppCompatActivity {
   static String emailHint;
    String emailRetrieved;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
        }





        //Animations
        final Animation emailID = AnimationUtils.loadAnimation(this, R.anim.translate_email_login);
        EditText email = (EditText) findViewById(editText);
        email.setAnimation(emailID);
       FindInDatabase find= new FindInDatabase();
       System.out.println(""+find.findBasedOnUuid(WelcomeWindow.uuid));
       email.setText(emailHint);


        final Animation passwordAnim = AnimationUtils.loadAnimation(this, R.anim.translate_password_login);
        EditText passwordID = (EditText) findViewById(editText2);
        passwordID.setAnimation(passwordAnim);

        final Animation loginbutton = AnimationUtils.loadAnimation(this, R.anim.translate_login_button);
        Button loginButton = (Button) findViewById(button7);
        loginButton.setAnimation(loginbutton);


        Button signUpProveedor = (Button) findViewById(button15);
        signUpProveedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this, SignUpProveedor.class));
            }
        });
        Button button = (Button) findViewById(button14);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LogIn.this, SignUp.class));
            }
        });


        //Error al loggear
        if (LogInService.estado == 1) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("Incorrect log in or password.");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();

            LogInService.estado = 0;
        }


        //Apretar boton para loggear
        final Button logInButt = (Button) findViewById(button7);
        logInButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Hey hey");

                EditText usernameText = (EditText) LogIn.this.findViewById(editText);
                emailRetrieved = usernameText.getText().toString();
                LogInService.email = emailRetrieved;


                EditText passwordText = (EditText) LogIn.this.findViewById(editText2);

                password = passwordText.getText().toString();
                LogInService.password = password;


                Intent i = new Intent(LogIn.this, LogInService.class);
                // Add extras to the bundle
                i.putExtra("foo", "bar");
                // Start the service
                LogIn.this.startService(i);
                ImageView imageView = (ImageView) LogIn.this.findViewById(imageView8);
                GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
                Glide.with(LogIn.this).load(R.drawable.loading_animation_grey).into(imageViewTarget);
                imageView.setVisibility(VISIBLE);

            }
        });
    }

    public void logIn(){


        try {
            System.out.println("Connection is successful");
            DatabaseConnection database = new DatabaseConnection();
            database.connect();
            PreparedStatement pt = DatabaseConnection.conn.prepareStatement("SELECT * FROM Users WHERE email = ? AND Password = ?");
            pt.setString(1, LogInService.email);
            pt.setString(2, LogInService.password);
            pt.setFetchSize(1);

            Log.w("statement", "statement antes del query");
            ResultSet rs = pt.executeQuery();
            Log.w("statement", "statement despues del query");
            if (rs.next()==false) {
                LogInService.logIn=false;
                LogInService.estado=1;

            } else {
                LogInService.estado=2;
                String email = rs.getString("email");
                String passwordd = rs.getString("Password");
                System.out.println(passwordd);
                if (email.equals(LogInService.email) && passwordd.equals(LogInService.password)) {
                    String location = rs.getString("location");
                    String username = rs.getString("User");
                    String phone = rs.getString("telefono");
                    System.out.println("Adentro del login");
                    //Agarrando imagen
                    Blob blob = rs.getBlob("Foto");
                    int blobLength = (int) blob.length();
                    byte[] blobAsBytes = blob.getBytes(1, blobLength);
                    Log.w("Activity", " Array setteado desde base de datos a variable principal en MAiActivity" + MainActivity.loggedImageInDatabaseArray);

                    MainActivity.loggedEmail = email;
                    LogInService.email=email;

                    MainActivity.loggedPhone = phone;
                    LogInService.phone=phone;

                    MainActivity.loggedUsername = username;
                   LogInService.name= username;

                    MainActivity.loggedLocation = location;
                    LogInService.location=location;


                    MainActivity.loggedImageInDatabaseArray = blobAsBytes;
                    LogInService.photo=blobAsBytes;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(MainActivity.loggedImageInDatabaseArray, 0, MainActivity.loggedImageInDatabaseArray.length);
                    MainActivity.profileImage = bitmap;

                    MainActivity.loggedIn = true;
                    LogInService.logIn=true;

                    finish();
                    return;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

}