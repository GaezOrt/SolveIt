package gunner.gunner;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gunner.gunner.R.id.button2;
import static gunner.gunner.R.id.editText;
import static gunner.gunner.R.id.editText2;
import static gunner.gunner.R.id.editText3;
import static gunner.gunner.R.id.editText5;
import static gunner.gunner.R.id.imageView15;
import static gunner.gunner.R.id.imageView2;
import static gunner.gunner.R.id.imageView20;
import static gunner.gunner.R.id.list;
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



           //Perfil del usuario datos
            viewProfileFromList(namePassedViaParam);

        //Boton agregar review
        ImageView image= (ImageView)findViewById(imageView15);
        image.setOnClickListener((v)-> {
                startActivity(new Intent(FindInDatabase.this, addComment.class));
        });

        //Dandole valor a rating bar
        RatingBar rating= (RatingBar)findViewById(rate);
        float x=obtenerPromedio(namePassedViaParam);
        rating.setRating(x);

        adapter = new CommentsListAdaptor(this,R.layout.comentarios,comentarios);
        final ListView listView= (ListView) findViewById(list);
        listView.setAdapter(adapter);


        //Atras button
        final ImageView atrasBut = (ImageView) findViewById(imageView20);
        atrasBut.setOnClickListener((v) -> {
            startActivity(new Intent(FindInDatabase.this, Electricidad.class));
            finish();
            comentarios.clear();
        });

    }

    //Buscar en base de datos
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
    public void findInDatabaseByLocation(String location){
        try {
            System.out.println("Finding electricistas by loc");
            final DatabaseConnection data = new DatabaseConnection();
            con=data.connect();
            PreparedStatement profilePt= con.prepareStatement("SELECT * FROM Users");

            //Ir agregando usuarios mientras que haya mas para agregar

                ResultSet rsProfile = profilePt.executeQuery();
                while (rsProfile.next()) {
                    String telefono=rsProfile.getString("telefono");
                    String location2= rsProfile.getString("location");
                    String name = rsProfile.getString("User");
                    String fechaDeNacimiento=rsProfile.getString("date");
                    String email= rsProfile.getString("email");
                    X++;
                    Blob photo = rsProfile.getBlob("Foto");
                    int blobLength = (int) photo.length();
                    byte[] photoBytes = photo.getBytes(1, blobLength);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes .length);
                    Electricista electricista = new Electricista(bitmap, name, location2, 9,location2,telefono,obtenerPromedio(email),findAmountOfCommentsEachProvider(email),fechaDeNacimiento);
                    Electricidad.runOnUI(new Runnable()
                    {
                        public void run()
                        {
                            try {

                                List<String> locationFromSearch = Arrays.asList(location.split("\\s*,\\s*"));
                                List<String> locationFromDatabase = Arrays.asList(location2.split("\\s*,\\s*"));
                                System.out.println(locationFromSearch.size());
                                System.out.println(locationFromDatabase.size());
                                int i = 0;
                                while (locationFromSearch.get(i) != null) {
                                    int x=0;
                                    boolean breakorNot=false;
                                        while(locationFromDatabase.get(x)!=null) {

                                            if (locationFromDatabase.get(x).contains(locationFromSearch.get(i))) {
                                                Electricidad.electricistas.add(electricista);
                                                Electricidad.adapter.notifyDataSetChanged();
                                                System.out.println("Adding by location");
                                                breakorNot=true;
                                                Thread.currentThread().interrupt();
                                            }
                                            x++;
                                            if(breakorNot) {
                                                break;
                                            }
                                        }
                                    i++;
                                    if(breakorNot) {
                                        break;
                                    }


                                }
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });

                }

        }catch(Exception e){
            e.printStackTrace();
            Log.e("Error", ""+e.getMessage()+"Tomatela loro");
        }
    }
    public void findElectricistas() {

        try {
            System.out.println("Finding electricistas");
            final DatabaseConnection data = new DatabaseConnection();

            con=data.connect();

            PreparedStatement updN = con.prepareStatement("SELECT * FROM Rubro WHERE electricista= ?");
            updN.setBoolean(1,true);

            ResultSet rs = updN.executeQuery();



            PreparedStatement profilePt= con.prepareStatement("SELECT *  FROM Users WHERE email= ?");

            //Ir agregando usuarios mientras que haya mas para agregar
            while (rs.next()) {

                String email = rs.getString("email");

                profilePt.setString(1, email);

                ResultSet rsProfile = profilePt.executeQuery();
                while (rsProfile.next()) {
                    String telefono=rsProfile.getString("telefono");
                    String location= rsProfile.getString("location");
                    String name = rsProfile.getString("User");
                    String fechaDeNacimiento=rsProfile.getString("date");
                    X++;
                    Blob photo = rsProfile.getBlob("Foto");
                    int blobLength = (int) photo.length();
                    byte[] photoBytes = photo.getBytes(1, blobLength);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes .length);
                    Electricista electricista = new Electricista(bitmap, name, email, 9,location,telefono,obtenerPromedio(email),findAmountOfCommentsEachProvider(email),fechaDeNacimiento);
                    Electricidad.runOnUI(new Runnable()
                    {
                        public void run()
                        {
                            try
                            {
                                Electricidad.electricistas.add(electricista);
                                Electricidad.adapter.notifyDataSetChanged();
                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    });


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
            final DatabaseConnection data = new DatabaseConnection();

            con=data.connect();
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
            comentarios.clear();
            final DatabaseConnection data = new DatabaseConnection();

            con=data.connect();
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
            final DatabaseConnection data = new DatabaseConnection();

            con=data.connect();
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
