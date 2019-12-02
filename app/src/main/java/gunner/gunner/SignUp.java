package gunner.gunner;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;



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


import static gunner.gunner.R.id.CuentaNoCreada;
import static gunner.gunner.R.id.button2;
import static gunner.gunner.R.id.button7;
import static gunner.gunner.R.id.cuentaCreada;
import static gunner.gunner.R.id.editText;
import static gunner.gunner.R.id.editText2;
import static gunner.gunner.R.id.editText3;
import static gunner.gunner.R.id.editText5;
import static gunner.gunner.R.id.imageView2;

public class SignUp extends AppCompatActivity {

    String email;
    String username;
    String password;
    String number;
    String location;
    Uri selectedImage;
    byte[] byteArray;
    private ImageView imageView;
    DatabaseConnection databaseAccess = new DatabaseConnection();

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.w("Activity", "Activity result");
        super.onActivityResult(requestCode, resultCode, data);
        selectedImage= data.getData();
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
             byteArray= stream.toByteArray();
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
        final DatabaseConnection databaseConnection = new DatabaseConnection();

        try {

            databaseConnection.connect();

            final Button registerBut = (Button) findViewById(button7);
            registerBut.setOnClickListener(
                    (view) -> {

                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        TextView nosePudoCrearLaCuenta = (TextView) findViewById(CuentaNoCreada);
                        TextView cuentaCreadaConExito = (TextView) findViewById(cuentaCreada);

                        EditText emailText = (EditText) findViewById(editText3);
                        email = emailText.getText().toString();
                        EditText usernameText = (EditText) findViewById(editText);
                        username = usernameText.getText().toString();
                        EditText passwordText = (EditText) findViewById(editText2);
                        password = passwordText.getText().toString();
                        EditText phoneNumberText = (EditText) findViewById(editText5);
                        number = phoneNumberText.getText().toString();
                        EditText locationButt = (EditText) findViewById(R.id.location);
                        location = locationButt.getText().toString();

                        boolean datosOK = true;

                        if ( email.length() == 0 ) {
                            // cartel de email mal
                            datosOK = false;
                        }


                        if ( username.length() == 0 ||
                             password.length() == 0 ||
                             number.length() == 0 ||
                             selectedImage == null ) {
                            datosOK = false;
                        }

                        if ( !datosOK ) {
                            Log.e( "", "errores en los datos" );
                            // /algun dato estuvo
                        }

                        else {
                            boolean creacionCuentaOk = false;
                            try {
                                databaseConnection.createUser(
                                    email, username, password,
                                    number, location, byteArray );
                                creacionCuentaOk = true;
                            }

                            catch ( Throwable e ) {
                                Log.e( "FileNotFound", e.getMessage(), e );
                            }

                            nosePudoCrearLaCuenta.setVisibility( creacionCuentaOk ? View.INVISIBLE : View.VISIBLE);
                            cuentaCreadaConExito.setVisibility( !creacionCuentaOk ? View.INVISIBLE : View.VISIBLE);
                        }
                    }
            );
        }
        catch ( SQLException | ClassNotFoundException ex )
        {
            Log.e("", ex.toString(), ex );
        }
    }
}


