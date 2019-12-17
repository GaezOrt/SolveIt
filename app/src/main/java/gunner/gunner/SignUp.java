package gunner.gunner;
import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import static android.view.View.VISIBLE;
import static gunner.gunner.R.id.Electricista;
import static gunner.gunner.R.id.button2;
import static gunner.gunner.R.id.button7;
import static gunner.gunner.R.id.editText;
import static gunner.gunner.R.id.editText2;
import static gunner.gunner.R.id.editText3;
import static gunner.gunner.R.id.editText5;
import static gunner.gunner.R.id.imageView10;
import static gunner.gunner.R.id.imageView18;
import static gunner.gunner.R.id.imageView2;
import static gunner.gunner.R.id.imageView8;
import static gunner.gunner.R.id.location3;

public class SignUp extends AppCompatActivity  implements MultiSpinner.MultiSpinnerListener {

    String email;
    String username;
    String password;
    String number;
    String location;
    Uri selectedImage;
    byte[] byteArray;
    private ImageView imageView;
    private DatePickerDialog.OnDateSetListener mdate;
    public static String dateString;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.w("Activity", "Activity result");
        super.onActivityResult(requestCode, resultCode, data);
        selectedImage= data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            bitmap=scaleDown(bitmap,190,true);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byteArray= stream.toByteArray();
            SignUpService.pathForImage=byteArray;
            SignUpService.imagenUsada=true;
            ImageView image= (ImageView)findViewById(imageView2);
            image.setImageResource(R.drawable.usercorrect);
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

        MultiSpinner ms = (MultiSpinner) findViewById(R.id.spinner);
        List<String> list = new ArrayList<String>();
        list.add("Palermo");
        list.add("Recoleta");
        list.add("Almagro");
        list.add("Caballito");
        list.add("Boedo");
        list.add("Belgrano");
        list.add("Villa Pueyrredon");
        list.add("Villa Urquiza");
        list.add("Flores");
        list.add("Lugano");
        ms.setItems(list, "select", this);




        //Date of birth
        EditText date= (EditText)findViewById(location3);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal= Calendar.getInstance();
                int year= cal.get(Calendar.YEAR);
                int month= cal.get(Calendar.MONTH);
                int day= cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog= new DatePickerDialog(SignUp.this,R.style.MyDialogTheme,mdate,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mdate= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                dateString=""+day+ "/"+ month+"/"+ year;
                date.setText(dateString);
            }
        };

        //Si el email ya ha sido utilizado
        if(SignUpService.cuentaYaUtilizada){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this,R.style.MyDialogTheme);
            builder1.setTitle("Sign up incorrecto");
            builder1.setIcon(R.drawable.usercorrect);

            builder1.setMessage("No se pudo crear la cuenta correctamente. Email ya utilizado.");
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

            EditText email= (EditText)findViewById(editText3);
            email.setText(SignUpService.email);
            EditText username= (EditText)findViewById(editText);
            username.setText(SignUpService.username);
            EditText password= (EditText)findViewById(editText2);
            password.setText(SignUpService.password);
            EditText phone= (EditText)findViewById(editText2);
            phone.setText(SignUpService.phoneNumber);

        }

        //Si el error esta en algun campo vacío
        if(SignUpService.estado==1 && SignUpService.imagenUsada){

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

            EditText email= (EditText)findViewById(editText3);
            email.setText(SignUpService.email);
            EditText username= (EditText)findViewById(editText);
            username.setText(SignUpService.username);
            EditText password= (EditText)findViewById(editText2);
            password.setText(SignUpService.password);
            EditText phone= (EditText)findViewById(editText2);
            phone.setText(SignUpService.phoneNumber);


            SignUpService.estado=0;

        }

        // si esta todo bien
        else if(SignUpService.estado==2 && !SignUpService.cuentaYaUtilizada){
            Intent i = new Intent(this, DownloadStuffInBackground.class);
            startService(i);
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

        //Si el error esta en que el usuario no registro una foto
        else if(SignUpService.imagenUsada==false && SignUpService.estado==1){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this,R.style.MyDialogTheme);
            builder1.setTitle("Sign up error");
            builder1.setIcon(R.drawable.errorlogin);

            builder1.setMessage("No se pudo crear la cuenta. Por favor selecciona una foto");
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
            EditText email= (EditText)findViewById(editText3);
            email.setText(SignUpService.email);
            EditText username= (EditText)findViewById(editText);
            username.setText(SignUpService.username);
            EditText password= (EditText)findViewById(editText2);
            password.setText(SignUpService.password);
            EditText phone= (EditText)findViewById(editText2);
            phone.setText(SignUpService.phoneNumber);

        }


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

                        SignUpService.username=username;
                        EditText passwordText = (EditText) findViewById(editText2);
                        password = passwordText.getText().toString();
                        SignUpService.password=password;
                        EditText phoneNumberText = (EditText) findViewById(editText5);
                        number = phoneNumberText.getText().toString();
                        SignUpService.phoneNumber=number;
                        Spinner spinner=(Spinner)findViewById(R.id.spinner);
                        location=spinner.getSelectedItem().toString();
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

        //Ir para atras
        final ImageView atrasBut = (ImageView) findViewById(imageView18);
        atrasBut.setOnClickListener(
                (View v) ->  {
                    finish();
                    startActivity(new Intent(SignUp.this, MainActivity.class));
                }
        );
    }
    public void signUp() {
        try {
            PreparedStatement pt = DatabaseConnection.conn.prepareStatement("SELECT * FROM Users WHERE email = ?");
            pt.setString(1, SignUpService.email);
            pt.setFetchSize(1);
            ResultSet rs = pt.executeQuery();
            if (rs.next()==false) {
                SignUpService.cuentaYaUtilizada=false;
            }else{
                SignUpService.cuentaYaUtilizada=true;
            }

        }catch (Exception e){

        }
        if (SignUpService.email.length() == 0 || SignUpService.username.length() == 0 ||
                SignUpService.password.length() == 0 ||
                SignUpService.phoneNumber.length() == 0|| SignUpService.imagenUsada==false || SignUpService.cuentaYaUtilizada) {
            SignUpService.datosOk = false;
            SignUpService.estado=1;
            if(SignUpService.imagenUsada==false){
                SignUpService.imagenUsada=false;

            }
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
    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize,
                                   boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(SignUp.this, MainActivity.class));
    }

    @Override
    public void onItemsSelected(boolean[] selected) {

    }
}