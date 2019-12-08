package gunner.gunner;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;


import static android.view.View.VISIBLE;
import static gunner.gunner.R.id.CuentaNoCreada;
import static gunner.gunner.R.id.Electricista;
import static gunner.gunner.R.id.button2;
import static gunner.gunner.R.id.button7;
import static gunner.gunner.R.id.cuentaCreada;
import static gunner.gunner.R.id.editText;
import static gunner.gunner.R.id.editText2;
import static gunner.gunner.R.id.editText3;
import static gunner.gunner.R.id.editText5;
import static gunner.gunner.R.id.imageView10;
import static gunner.gunner.R.id.imageView2;
import static gunner.gunner.R.id.imageView8;

public class SignUp extends AppCompatActivity {

    String email;
    String username;
    String password;
    String number;
    String location;
    Uri selectedImage;
    byte[] byteArray;
    private ImageView imageView;



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.w("Activity", "Activity result");
        super.onActivityResult(requestCode, resultCode, data);
        selectedImage= data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray= stream.toByteArray();
            SignUpService.pathForImage=byteArray;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void selectImage() {
        selectedImage = null;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), 9000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        if(SignUpService.estado==1){

            AlertDialog.Builder builder1 = new AlertDialog.Builder(this,R.style.MyDialogTheme);
            builder1.setTitle("Sign up error");
            builder1.setIcon(R.drawable.errorlogin);

            builder1.setMessage("No se pudo crear la cuenta.");
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
            SignUpService.estado=0;
        }else if(SignUpService.estado==2){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this,R.style.MyDialogTheme);
            builder1.setTitle("Sign up correcto");
            builder1.setIcon(R.drawable.usercorrect);

            builder1.setMessage("Se creo la cuenta correctamente");
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

            SignUpService.datosOk=true;
        }


        //Ir para atras
        final Button atrasBut = (Button) findViewById(button2);
        atrasBut.setOnClickListener(
                (View v) ->  {
                    finish();
                    startActivity(new Intent(SignUp.this, MainActivity.class));
                    setContentView(R.layout.activity_main);
                }
        );

        //Clicking on image
        imageView = (ImageView) findViewById(imageView2);
        imageView.setOnClickListener( e -> selectImage( ));

        //Registrar usuario en tabla base de datos
        try {


            final Button registerBut = (Button) findViewById(button7);
            registerBut.setOnClickListener(
                    (view) -> {

                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);


                        EditText emailText = (EditText) findViewById(editText3);
                        email = emailText.getText().toString();
                        SignUpService.email=email;
                        EditText usernameText = (EditText) findViewById(editText);
                        username = usernameText.getText().toString();
                        SignUpService.username=email;
                        EditText passwordText = (EditText) findViewById(editText2);
                        password = passwordText.getText().toString();
                        SignUpService.password=password;
                        EditText phoneNumberText = (EditText) findViewById(editText5);
                        number = phoneNumberText.getText().toString();
                        SignUpService.phoneNumber=number;
                        EditText locationButt = (EditText) findViewById(R.id.location);
                        location = locationButt.getText().toString();
                        SignUpService.location=location;

                        CheckBox electricista= (CheckBox) findViewById(Electricista);
                        if(electricista.isChecked()){
                            MainActivity.electricista=true;
                        }else{
                            MainActivity.electricista=false;
                        }
                        CheckBox gasista= (CheckBox) findViewById(R.id.gasista);
                        if(gasista.isChecked()){
                            MainActivity.gasista=true;
                        }else{
                            MainActivity.gasista=false;
                        }
                        CheckBox plomero= (CheckBox) findViewById(R.id.plomero);
                        if(plomero.isChecked()){
                            MainActivity.plomero=true;
                        }else{
                            MainActivity.plomero=false;
                        }
                        CheckBox computacion= (CheckBox) findViewById(R.id.computacion);
                        if(computacion.isChecked()){
                            MainActivity.computacion=true;
                        }else{
                            MainActivity.computacion=false;
                        }
                        CheckBox pintor= (CheckBox) findViewById(R.id.pintor);
                        if(pintor.isChecked())
                        {
                            MainActivity.pintor = true;
                        }else{
                            MainActivity.pintor=false;
                        }
                        CheckBox carpintero = (CheckBox) findViewById(R.id.carpintero);
                        if (carpintero.isChecked()) {
                            MainActivity.carpintero = true;
                        }else{
                            MainActivity.carpintero=false;
                        }
                        CheckBox cerrajero = (CheckBox) findViewById(R.id.cerrajero);
                        if (cerrajero.isChecked()) {
                            MainActivity.cerrajero = true;
                        }else{
                            MainActivity.cerrajero=false;
                        }
                        CheckBox albanil = (CheckBox) findViewById(R.id.albanil);
                        if (albanil.isChecked()) {
                            MainActivity.albanil = true;
                        }else{
                            MainActivity.albanil=false;
                        }

                        Intent i = new Intent(this, SignUpService.class);
                        startService(i);
                        ImageView imageView = (ImageView) findViewById(imageView10);
                        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
                        Glide.with(this).load(R.drawable.loading_animation_grey).into(imageViewTarget);
                        imageView.setVisibility(VISIBLE);

                    });
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void signUp() {

        if (SignUpService.email.length() == 0 || SignUpService.username.length() == 0 ||
                SignUpService.password.length() == 0 ||
                SignUpService.phoneNumber.length() == 0|| SignUpService.pathForImage==null) {
            SignUpService.datosOk = false;
            SignUpService.estado=1;
        }else{
            SignUpService.datosOk=true;
            SignUpService.estado=2;
        }

        if (!SignUpService.datosOk) {
            Log.e("", "errores en los datos");

        } else {
            try {
                final DatabaseConnection databaseConnection = new DatabaseConnection();
                databaseConnection.connect();
                databaseConnection.createUser(
                        SignUpService.email, SignUpService.username, SignUpService.password,
                        SignUpService.phoneNumber, SignUpService.location, SignUpService.pathForImage,
                        MainActivity.electricista,
                        MainActivity.carpintero,
                        MainActivity.computacion,
                        MainActivity.plomero,
                        MainActivity.gasista,
                        MainActivity.albanil,
                        MainActivity.pintor,
                        MainActivity.cerrajero);

            } catch (Exception e) {
                SignUpService.datosOk=false;
                e.printStackTrace();
                SignUpService.estado=1;
            }

        }
    }

}