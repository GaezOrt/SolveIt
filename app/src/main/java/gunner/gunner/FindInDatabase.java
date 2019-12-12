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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static gunner.gunner.MainActivity.albanil;
import static gunner.gunner.MainActivity.carpintero;
import static gunner.gunner.MainActivity.cerrajero;
import static gunner.gunner.MainActivity.computacion;
import static gunner.gunner.MainActivity.electricista;
import static gunner.gunner.MainActivity.gasista;
import static gunner.gunner.MainActivity.pintor;
import static gunner.gunner.MainActivity.plomero;
import static gunner.gunner.R.id.MyRating;
import static gunner.gunner.R.id.all;
import static gunner.gunner.R.id.button2;
import static gunner.gunner.R.id.editText;
import static gunner.gunner.R.id.editText2;
import static gunner.gunner.R.id.editText3;
import static gunner.gunner.R.id.editText5;
import static gunner.gunner.R.id.imageView15;
import static gunner.gunner.R.id.imageView2;
import static gunner.gunner.R.id.list;
import static gunner.gunner.R.id.lista;
import static gunner.gunner.R.id.rate;


public class FindInDatabase extends AppCompatActivity {


    static ArrayList<Comentarios> comentarios =new ArrayList<Comentarios>();
    Connection con;
    static String nombre;
    static String numeroTelefono;
    static String location;
    static String namePassedViaParam;
    static int ubicacionElectricista;
    public static int X;
    static   CommentsListAdaptor adapter ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Design_NoActionBar);
        setContentView(R.layout.find_user);

        //Encontrar comentarios del usuario
        findComments(namePassedViaParam,comentarios);

        //Atras button
        final Button atrasBut = (Button) findViewById(button2);
        atrasBut.setOnClickListener((v) -> {

            startActivity(new Intent(FindInDatabase.this, Electricidad.class));
            setContentView(R.layout.activity_main);
            finish();
            comentarios.clear();
        });

           //Perfil del usuario datos
            viewProfileFromList(namePassedViaParam);

        //Boton agregar review
        ImageView image= (ImageView)findViewById(imageView15);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindInDatabase.this, addComment.class));
            }
        });

        RatingBar rating= (RatingBar)findViewById(rate);
        float x=obtenerPromedio(namePassedViaParam);
        rating.setRating(x);

        System.out.println("aaaa "+obtenerPromedio(namePassedViaParam));
        adapter = new CommentsListAdaptor(this,R.layout.comentarios,comentarios);
        final ListView listView= (ListView) findViewById(list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    //Buscar en base de datos

    public void findOnDatabase(String email) {
        try {
            PreparedStatement usersPt = con.prepareStatement("SELECT * FROM Users WHERE email= ?");
            PreparedStatement rubrosPt = con.prepareStatement("SELECT * FROM Rubro");
            usersPt.setString(1, email);
            ResultSet rs = usersPt.executeQuery();
            ResultSet rsRubros = rubrosPt.executeQuery();
            while (rs.next() && rsRubros.next()) {
                String userName = rs.getString("User");
                String emaill = rs.getString("email");
                String phone = rs.getString("telefono");
                String locationn = rs.getString("location");
                Blob blob = rs.getBlob("Foto");
                int blobLength = (int) blob.length();
                byte[] blobAsBytes = blob.getBytes(1, blobLength);

                boolean electricista = rsRubros.getBoolean("electricista");
                boolean carpintero = rsRubros.getBoolean("carpintero");
                boolean pintor = rsRubros.getBoolean("pintor");
                boolean plomero = rsRubros.getBoolean("plomero");
                boolean gasista = rsRubros.getBoolean("gasista");
                boolean albanil = rsRubros.getBoolean("albanil");
                boolean cerrajero = rsRubros.getBoolean("cerrajero");
                boolean computacion = rsRubros.getBoolean("computacion");
                if (emaill.equals(email)) {
                    numeroTelefono = phone;
                    nombre = userName;
                    email = emaill;
                    location = locationn;
                    addComment.email=email;
                    MainActivity.electricista = electricista;
                    MainActivity.carpintero = carpintero;
                    MainActivity.pintor = pintor;
                    MainActivity.plomero = plomero;
                    MainActivity.gasista = gasista;
                    MainActivity.albanil = albanil;
                    MainActivity.cerrajero = cerrajero;
                    MainActivity.computacion = computacion;
                    MainActivity.loggedImageInDatabaseArray = blobAsBytes;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(MainActivity.loggedImageInDatabaseArray, 0, MainActivity.loggedImageInDatabaseArray.length);
                    ImageView image = (ImageView) findViewById(imageView2);
                    image.setImageBitmap(bitmap);

                } else {

                }
            }
        } catch (Exception e) {
            Log.e("Error", "Error en subida");
        }

        final TextView emailButt = (TextView) findViewById(editText3);
        emailButt.setText(email);

        //Mostrar username
        final TextView usernameButt = (TextView) findViewById(editText);
        usernameButt.setText(nombre);

        //Mostrar telefono
        final TextView phoneButt = (TextView) findViewById(editText5);
        phoneButt.setText(numeroTelefono);

        //Mostrar ubicacion
        TextView locatText = (TextView) findViewById(editText2);
        locatText.setText(location);

    }
    public void viewProfileFromList(String email){
        try {
                    numeroTelefono = Electricidad.electricistas.get(ubicacionElectricista).number;
                    nombre = Electricidad.electricistas.get(ubicacionElectricista).name;
                    email = Electricidad.electricistas.get(ubicacionElectricista).email;
                    location = Electricidad.electricistas.get(ubicacionElectricista).location;
                    addComment.email=email;

                   Bitmap bitmap=Electricidad.electricistas.get(ubicacionElectricista).photo;
                    ImageView image = (ImageView) findViewById(imageView2);
                    image.setImageBitmap(bitmap);


        } catch (Exception e) {
            Log.e("Error", "Error en subida");
        }

        final TextView emailButt = (TextView) findViewById(editText3);
        emailButt.setText(email);

        //Mostrar username
        final TextView usernameButt = (TextView) findViewById(editText);
        usernameButt.setText(nombre);

        //Mostrar telefono
        final TextView phoneButt = (TextView) findViewById(editText5);
        phoneButt.setText(numeroTelefono);

        //Mostrar ubicacion
        TextView locatText = (TextView) findViewById(editText2);
        locatText.setText(location);

    }
    public void findElectricistas() {
        try {

            final DataHolderService data = new DataHolderService();

            con=data.getCon();

            PreparedStatement updN = con.prepareStatement("SELECT * FROM Rubro WHERE electricista= ?");
            updN.setBoolean(1,true);
            updN.setFetchSize(1);
            ResultSet rs = updN.executeQuery();



            PreparedStatement profilePt= con.prepareStatement("SELECT * FROM Users WHERE email= ?");

            while (rs.next()) {

                String email = rs.getString("email");
                profilePt.setFetchSize(1);
                profilePt.setString(1, email);

                ResultSet rsProfile = profilePt.executeQuery();
                while (rsProfile.next()) {
                    String telefono=rsProfile.getString("telefono");
                    String location= rsProfile.getString("location");
                    String name = rsProfile.getString("User");
                    X++;
                    Blob photo = rsProfile.getBlob("Foto");
                    int blobLength = (int) photo.length();
                    byte[] photoBytes = photo.getBytes(1, blobLength);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes .length);
                    Electricista electricista = new Electricista(bitmap, name, email, 9,location,telefono,obtenerPromedio(email),findAmountOfCommentsEachProvider(email));
                    Electricidad.electricistas.add(electricista);
                    break;
                }
            }

            rs.last();    // moves cursor to the last row
            Electricista.cantidadElectricistas = rs.getRow();
        }catch(Exception e){
            e.printStackTrace();
            Log.e("Error", ""+e.getMessage()+"Tomatela loro");
        }




    }
    public float findAmountOfCommentsEachProvider(String email){


        float cantidadDeVeces=0;
        try {
            final DataHolderService data = new DataHolderService();

            con=data.getCon();
            PreparedStatement updN = con.prepareStatement(" SELECT * FROM Comentarios WHERE emailDelReceptor= ?");
            updN.setString(1, email);
            updN.setFetchSize(1);
            ResultSet rs = updN.executeQuery();

            while (rs.next()) {
                cantidadDeVeces++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cantidadDeVeces;
    }
    public void findComments(String email, ArrayList<Comentarios> comentarios){
        try {

            final DataHolderService data = new DataHolderService();

            con=data.getCon();
            PreparedStatement updN = con.prepareStatement("SELECT * FROM Comentarios WHERE emailDelReceptor= ?");
            updN.setString(1,email);
            updN.setFetchSize(1);
            ResultSet rs = updN.executeQuery();

            while (rs.next()) {

                String comentario = rs.getString("Comentario");


                Comentarios comentarioLista= new Comentarios(comentario,rs.getFloat("Puntaje"),rs.getString("emailDelComentador"));
                comentarios.add(comentarioLista);
                System.out.println("Comentario:" + comentarioLista.comentario);
                System.out.println(comentarios.size());
            }


        }catch( Exception e){
            e.printStackTrace();
            Log.e("Error", ""+e.getMessage()+"Tomatela loro");
        }
    }
    public float obtenerPromedio(String email)  {
        float total=0;
        float cantidadDeVeces=0;
        try {
            final DataHolderService data = new DataHolderService();

            con=data.getCon();
            PreparedStatement updN = con.prepareStatement("SELECT * FROM Comentarios WHERE emailDelReceptor =?");
            updN.setString(1, email);
            updN.setFetchSize(1);
            ResultSet rs = updN.executeQuery();

            while (rs.next()) {
                total = total + rs.getFloat("Puntaje");
                cantidadDeVeces++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("total"+total+" cantidad "+cantidadDeVeces);
        return total/cantidadDeVeces;
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(FindInDatabase.this, Electricidad.class));
        comentarios.clear();
    }
}
