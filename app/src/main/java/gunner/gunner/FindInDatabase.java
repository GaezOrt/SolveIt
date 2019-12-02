package gunner.gunner;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static gunner.gunner.R.id.editText;
import static gunner.gunner.R.id.editText2;
import static gunner.gunner.R.id.editText3;
import static gunner.gunner.R.id.editText5;
import static gunner.gunner.R.id.textView;

public class FindInDatabase extends AppCompatActivity {

        Connection con;
        static String nombre;
        static String numeroTelefono;
        static String email;
        static String location;
        static String namePassedViaParam;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Design_NoActionBar);
        setContentView(R.layout.find_user);
        final String userName = "9QFW2Os9pV";
        final String passwordDatabase = "dKObZerUnf";
        final String url = "jdbc:mysql://remotemysql.com:3306/9QFW2Os9pV";
        final DatabaseConnection data = new DatabaseConnection();

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

        public void findOnDatabase (String name) {
            try {
                PreparedStatement updN = con.prepareStatement("SELECT * FROM Users");

                ResultSet rs = updN.executeQuery();
                while (rs.next()) {
                    String userName = rs.getString("User");
                    String emaill=rs.getString("email");
                    String phone=rs.getString("telefono");
                    String locationn=rs.getString("location");
                    if(userName.equals(name)){
                        numeroTelefono=phone;
                        nombre=userName;
                        email=emaill;
                        location=locationn;

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
}
