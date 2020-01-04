package gunner.gunner;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
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

import de.hdodenhof.circleimageview.CircleImageView;

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
    static String emailPassed;
    static int ubicacionElectricista;
    public static int X;
    static   CommentsListAdaptor adapter ;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Design_NoActionBar);
        setContentView(R.layout.find_user);


        //Contactar
        Button button12= (Button)findViewById(R.id.button12);
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chat.vieneDeBusqueda=true;
                startActivity(new Intent(FindInDatabase.this, Chat.class));
            }
        });


        //Perfil del usuario datos
        viewProfileFromList(emailPassed);

        //Boton agregar review
        ImageView image= (ImageView)findViewById(imageView15);
        image.setOnClickListener((v)-> {
            MediaPlayer  mp=MediaPlayer.create(getApplicationContext(),R.raw.cli);
            mp.start();
            startActivity(new Intent(FindInDatabase.this, addComment.class));
        });

        //Dandole valor a rating bar
        RatingBar rating= (RatingBar)findViewById(rate);
        float x=obtenerPromedio(emailPassed);
        rating.setRating(x);

        adapter = new CommentsListAdaptor(this,R.layout.comentarios,comentarios);
        final ListView listView= (ListView) findViewById(list);
        listView.setAdapter(adapter);


        //Atras button
        final ImageView atrasBut = (ImageView) findViewById(imageView20);
        atrasBut.setOnClickListener((v) -> {
            MediaPlayer  mp=MediaPlayer.create(getApplicationContext(),R.raw.cli);
            mp.start();
            startActivity(new Intent(FindInDatabase.this, Electricidad.class));
            finish();
            comentarios.clear();
        });

    }

    //Buscar en base de datos
    public void findConversationOfUserInDatabase2(String email){
        try {
            System.out.println("finding messages");

            final DatabaseConnection data = new DatabaseConnection();

            con = data.connect();
            PreparedStatement updN = con.prepareStatement("SELECT * FROM Conversaciones where segundoIntegrante=? ");
            updN.setString(1,email);
            updN.setFetchSize(1);
            ResultSet rs = updN.executeQuery();

            while (rs.next()) {

                ConversacionesUsuarioListaTipo conversacion=new ConversacionesUsuarioListaTipo(rs.getString("mensaje"),rs.getString("primerIntegrante"));
                ConversacionesUsuario.runOnUI(new Runnable() {
                    public void run() {
                        try {
                            if(ConversacionesUsuario.conversaciones.contains(conversacion.nombre)) {
                            }else{
                                insertUniqueItem(conversacion);
                                ConversacionesUsuario.adapter.notifyDataSetChanged();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "" + e.getMessage() + "Tomatela loro");


        }
    }
    public void findConversationsOfUserInDatabase(String email){
        try {
            System.out.println("finding messages");
            ConversacionesUsuario.conversaciones.clear();
            final DatabaseConnection data = new DatabaseConnection();

            con = data.connect();
            PreparedStatement updN = con.prepareStatement("SELECT * FROM Conversaciones where primerIntegrante=? ");
            updN.setString(1,email);
            updN.setFetchSize(1);
            ResultSet rs = updN.executeQuery();

            while (rs.next()) {
                FindInDatabase find= new FindInDatabase();
                ConversacionesUsuarioListaTipo conversacion=new ConversacionesUsuarioListaTipo(rs.getString("mensaje"),rs.getString("segundoIntegrante"));
                ConversacionesUsuario.runOnUI(new Runnable() {
                    public void run() {
                        try {
                            if(ConversacionesUsuario.conversaciones.contains(conversacion.nombre)) {
                            }else{
                                insertUniqueItem(conversacion);
                                ConversacionesUsuario.adapter.notifyDataSetChanged();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "" + e.getMessage() + "Tomatela loro");


        }
    }

    public void insertUniqueItem(ConversacionesUsuarioListaTipo item) {
        if(!contains(item)) {
            ConversacionesUsuario.conversaciones.add(item);
        }
    }
    public  String findBasedOnUuid(String uiid){
                System.out.println("Based on uui");
        String email="asa";
            try {
                final DatabaseConnection data = new DatabaseConnection();
                con = data.connect();

                PreparedStatement profilePt = con.prepareStatement("SELECT *  FROM androidID WHERE androidID= ?");
                profilePt.setString(1,uiid);
                ResultSet rs = profilePt.executeQuery();
                while(rs.next()){


                    email= rs.getString("email");
                }
            }catch (Exception e){

            }
            return email;
    }
    private boolean contains(ConversacionesUsuarioListaTipo item) {
        for(ConversacionesUsuarioListaTipo i : ConversacionesUsuario.conversaciones) {
            if(i.nombre.equals(item.nombre)) {
                return true;
            }
        }
        return false;
    }
    public void viewProfileFromList(String email){
        try {
            numeroTelefono = Electricidad.electricistas.get(ubicacionElectricista).number;
            nombre = Electricidad.electricistas.get(ubicacionElectricista).name;
            email = Electricidad.electricistas.get(ubicacionElectricista).email;
            location = Electricidad.electricistas.get(ubicacionElectricista).location;
            addComment.email=email;

            Bitmap bitmap=Electricidad.electricistas.get(ubicacionElectricista).photo;
            CircleImageView image = (CircleImageView) findViewById(imageView2);
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
                Electricista electricista = new Electricista(bitmap, name, email, 9,location2,telefono,obtenerPromedio(email),findAmountOfCommentsEachProvider(email),fechaDeNacimiento);
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
    public void insertAllTheTime(String persona1,String persona2){
        try {
            System.out.println("finding messages");

            final DatabaseConnection data = new DatabaseConnection();

            con = data.connect();
            PreparedStatement updN = con.prepareStatement("SELECT * FROM Conversaciones ",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            updN.setFetchSize(1);
            ResultSet rs = updN.executeQuery();

            while (rs.next()) {
                if ((rs.getString("primerIntegrante").equals(persona1) && rs.getString("segundoIntegrante").equals(persona2))|| (rs.getString("primerIntegrante").equals(persona2) && rs.getString("segundoIntegrante").equals(persona1))) {
                    String g=rs.getString("mensaje");
                    System.out.println(rs.getString("mensaje"));
                    Mensaje mensaje;
                    if(!rs.getString("segundoIntegrante").equals(LogInService.email)) {
                        mensaje = new Mensaje(g, rs.getString("segundoIntegrante"),rs.getTime("horario"));
                    }else{
                        mensaje = new Mensaje(g, rs.getString("primerIntegrante"),rs.getTime("horario"));
                    }
                    Chat.runOnUI(new Runnable() {
                        public void run() {
                            try {
                                System.out.println("ROWW"+ rs.getRow());
                                if(Chat.mensajes.size()<rs.getRow()) {
                                    Chat.mensajes.add(mensaje);
                                    Chat.messageAdapter.notifyDataSetChanged();
                                }
                                System.out.println(" MENSAJE" + g);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "" + e.getMessage() + "Tomatela loro");


        }
    }
    public int chequearConstantemente(String persona1,String persona2){
        int x=0;
        try {

            final DatabaseConnection data = new DatabaseConnection();

            con = data.connect();
            PreparedStatement updN = con.prepareStatement("SELECT * FROM Conversaciones WHERE (primerIntegrante=? AND segundoIntegrante=?) OR (primerIntegrante=? AND segundoIntegrante=?)",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            updN.setFetchSize(1);
            updN.setString(1, persona1);
            updN.setString(2, persona2);
            updN.setString(3, persona2);
            updN.setString(4, persona1);
            ResultSet rs = updN.executeQuery();

            rs.last();
            x=rs.getRow();
            } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error", "" + e.getMessage() + "Tomatela loro");

        System.out.println("Base de datos"+x);
        System.out.println("Chat del telefono"+Chat.mensajes.size());
        }
        return x;
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

                Comentarios comentarioLista= new Comentarios(comentario,rs.getFloat("Puntaje"),rs.getString("emailDelComentador"),findPictureFromUser(rs.getString("emailDelComentador")),findNameFromuser(rs.getString("emailDelComentador")));

                FindInDatabase.runOnUI(new Runnable()
                {
                    public void run()
                    {
                        try
                        {
                            comentarios.add(comentarioLista);
                            FindInDatabase.adapter.notifyDataSetChanged();
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
            }


        }catch( Exception e){
            e.printStackTrace();
            Log.e("Error", ""+e.getMessage()+"Tomatela loro");
        }
    }
    public Bitmap findPictureFromUser(String email){
        Bitmap bitmap;
        try {
            final DatabaseConnection data = new DatabaseConnection();
            con = data.connect();

            PreparedStatement profilePt = con.prepareStatement("SELECT *  FROM Users WHERE email= ?");
            profilePt.setString(1,email);
            ResultSet rs = profilePt.executeQuery();
            while(rs.next()){
                Blob photo = rs.getBlob("Foto");
                int blobLength = (int) photo.length();
                byte[] photoBytes = photo.getBytes(1, blobLength);
                bitmap = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes .length);
                return bitmap;
            }
        }catch (Exception e){

        }
        return null;
    }
    public String findNameFromuser(String email){

        try {
            final DatabaseConnection data = new DatabaseConnection();
            con = data.connect();

            PreparedStatement profilePt = con.prepareStatement("SELECT *  FROM Users WHERE email= ?");
            profilePt.setString(1,email);
            ResultSet rs = profilePt.executeQuery();
            while(rs.next()){
                String name = rs.getString("User");
                return name;
            }
        }catch (Exception e){

        }
        return null;
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
        MediaPlayer mp=MediaPlayer.create(getApplicationContext(),R.raw.cli);
        mp.start();
        startActivity(new Intent(FindInDatabase.this, Electricidad.class));
        comentarios.clear();
    }

    public static Handler UIHandler;

    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }

    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }
}