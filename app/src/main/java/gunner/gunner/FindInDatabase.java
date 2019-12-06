package gunner.gunner;


import android.content.Intent;
import android.database.CursorJoiner;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static gunner.gunner.R.id.all;
import static gunner.gunner.R.id.button2;
import static gunner.gunner.R.id.editText;
import static gunner.gunner.R.id.editText2;
import static gunner.gunner.R.id.editText3;
import static gunner.gunner.R.id.editText5;
import static gunner.gunner.R.id.imageView2;
import static gunner.gunner.R.id.textView;

public class FindInDatabase extends AppCompatActivity {

    Connection con;
    static String nombre;
    static String numeroTelefono;
    static String location;
    static String namePassedViaParam;

    public static int X;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Design_NoActionBar);
        setContentView(R.layout.find_user);
        final String userName = "9QFW2Os9pV";
        final String passwordDatabase = "dKObZerUnf";
        final String url = "jdbc:mysql://remotemysql.com:3306/9QFW2Os9pV";
        final DatabaseConnection data = new DatabaseConnection();


        //Atras button
        final Button atrasBut=(Button) findViewById(button2) ;
        atrasBut.setOnClickListener((v)-> {

            startActivity(new Intent(FindInDatabase.this, Electricidad.class));
            setContentView(R.layout.activity_main);
            Electricidad.electricistas.remove(all);
            finish();
        });
        //Conectar con base de datos
        con = null;
        try {
            con = DriverManager.getConnection(url, userName, passwordDatabase);
            data.connect();
            findOnDatabase(namePassedViaParam);
        } catch (Exception e) {
            Log.e("Error", "Error en conexion a base de datos");
        }
    }

    //Buscar en base de datos

    public void findOnDatabase (String email) {
        try {
            PreparedStatement usersPt = con.prepareStatement("SELECT * FROM Users WHERE email= ?");
            PreparedStatement rubrosPt= con.prepareStatement("SELECT * FROM Rubro");
            usersPt.setString(1,email);
            ResultSet rs = usersPt.executeQuery();
            ResultSet rsRubros= rubrosPt.executeQuery();
            while (rs.next()&& rsRubros.next()) {
                String userName = rs.getString("User");
                String emaill=rs.getString("email");
                String phone=rs.getString("telefono");
                String locationn=rs.getString("location");
                Blob blob =rs.getBlob("Foto");
                int blobLength = (int) blob.length();
                byte[] blobAsBytes = blob.getBytes(1, blobLength);

                boolean electricista= rsRubros.getBoolean("electricista");
                boolean carpintero= rsRubros.getBoolean("carpintero");
                boolean pintor= rsRubros.getBoolean("pintor");
                boolean plomero= rsRubros.getBoolean("plomero");
                boolean gasista= rsRubros.getBoolean("gasista");
                boolean albanil= rsRubros.getBoolean("albanil");
                boolean cerrajero= rsRubros.getBoolean("cerrajero");
                boolean computacion= rsRubros.getBoolean("computacion");
                if(emaill.equals(email)){
                    numeroTelefono=phone;
                    nombre=userName;
                    email=emaill;
                    location=locationn;
                    MainActivity.electricista=electricista;
                    MainActivity.carpintero=carpintero;
                    MainActivity.pintor=pintor;
                    MainActivity.plomero=plomero;
                    MainActivity.gasista=gasista;
                    MainActivity.albanil=albanil;
                    MainActivity.cerrajero=cerrajero;
                    MainActivity.computacion=computacion;
                    MainActivity.loggedImageInDatabaseArray=blobAsBytes;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(MainActivity.loggedImageInDatabaseArray, 0, MainActivity.loggedImageInDatabaseArray .length);
                    ImageView image= (ImageView) findViewById(imageView2);
                    image.setImageBitmap(bitmap);

                }else{
                    TextView locatText=(TextView) findViewById(textView) ;
                    locatText.setVisibility(View.VISIBLE);
                }
            }
        }catch( Exception e){
            TextView locatText=(TextView) findViewById(textView) ;
            locatText.setVisibility(View.VISIBLE);
            Log.e("Error","Error en subida");
        }

        final TextView emailButt=(TextView) findViewById(editText3) ;
        emailButt.setText(email);

        //Mostrar username
        final TextView usernameButt=(TextView) findViewById(editText) ;
        usernameButt.setText(nombre);

        //Mostrar telefono
        final TextView phoneButt=(TextView) findViewById(editText5) ;
        phoneButt.setText(numeroTelefono);

        //Mostrar ubicacion
        TextView locatText=(TextView) findViewById(editText2) ;
        locatText.setText(location);

    }
    public void findElectricistas(){
        try {
            Log.w("","Eeee");
            final String userName = "9QFW2Os9pV";
            final String passwordDatabase = "dKObZerUnf";
            final String url = "jdbc:mysql://remotemysql.com:3306/9QFW2Os9pV";
            final DatabaseConnection data = new DatabaseConnection();

            //Conectar con base de datos
            con = null;
            try {
                con = DriverManager.getConnection(url, userName, passwordDatabase);
                data.connect();
            } catch (Exception e) {

            }

            PreparedStatement updN = con.prepareStatement("SELECT * FROM Rubro WHERE electricista= ?");
            updN.setBoolean(1,true);
            ResultSet rs = updN.executeQuery();

            PreparedStatement profilePt= con.prepareStatement("SELECT * FROM Users WHERE email= ?");

            while (rs.next()) {

                String email = rs.getString("email");

                profilePt.setString(1, email);
                ResultSet rsProfile = profilePt.executeQuery();
                while (rsProfile.next()) {
                    String name = rsProfile.getString("User");
                    X++;
                    Blob photo = rsProfile.getBlob("Foto");
                    int blobLength = (int) photo.length();
                    byte[] photoBytes = photo.getBytes(1, blobLength);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes .length);
                    Electricista electricista = new Electricista(bitmap, name, email, 9);
                    Electricidad.electricistas.add(electricista);


                    break;
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
            Log.e("Error", ""+e.getMessage()+"Tomatela loro");
        }

    }
}
