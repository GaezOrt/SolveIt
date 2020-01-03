package gunner.gunner;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

import static gunner.gunner.R.id.imageView14;
import static gunner.gunner.R.id.imageView16;


import static gunner.gunner.R.id.imageView23;
import static gunner.gunner.R.id.imageView7;
import static gunner.gunner.R.id.nav_viw;
import static gunner.gunner.R.id.navDrawer;
import static gunner.gunner.R.id.textView31;

import static gunner.gunner.R.id.welcomeMessage;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnTouchListener {

    static boolean loggedIn = false;
    static String loggedUsername;
    static String loggedEmail;
    static String loggedPhone;
    static String loggedLocation;
    static Bitmap profileImage;
    static byte[] loggedImageInDatabaseArray;
    static boolean electricista;
    static boolean plomero;
    static boolean computacion;
    static boolean carpintero;
    static boolean pintor;
    static boolean gasista;
    static boolean cerrajero;
    static boolean albanil;
    private View hiddenPanel;
    static boolean firstTimeLoogedIn=true;
    static boolean esProveedor;
    CoordinatorLayout coord;
    private Handler mHandler = new Handler();
    static LocationManager locationManager;
    static RubrosListAdapter adapter;
    static RubrosListAdapter adapter2;
    ArrayList<Rubro> rubros= new ArrayList<Rubro>();
    ArrayList<Rubro> rubros2= new ArrayList<Rubro>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setTheme(R.style.Theme_Design_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       MediaPlayer  mp=MediaPlayer.create(getApplicationContext(),R.raw.welcome);// the song is a filename which i have pasted inside a folder **raw** created under the **res** folder.//
        mp.start();


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
        }


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viw);
        navigationView.setNavigationItemSelectedListener(this);

        adapter = new RubrosListAdapter(this, R.layout.rubross, rubros);
        final ListView listView = (ListView) findViewById(R.id.listita);
        listView.setAdapter(adapter);
        Rubro electricidad= new Rubro("Electricidad",getApplicationContext().getResources().getDrawable(R.drawable.electricimage));
        rubros.add(electricidad);
        Rubro plomeria= new Rubro("Plomería",getApplicationContext().getResources().getDrawable(R.drawable.plomeriaa));
        rubros.add(plomeria);

        Rubro computacion= new Rubro("Computacion",getApplicationContext().getResources().getDrawable(R.drawable.computacion));
        rubros.add(computacion);
        Rubro carpinteria= new Rubro("Carpintería",getApplicationContext().getResources().getDrawable(R.drawable.carpintero));
        rubros.add(carpinteria);
        Rubro gasista= new Rubro("Gasista",getApplicationContext().getResources().getDrawable(R.drawable.gasista));
        rubros.add(gasista);
        Rubro construccion= new Rubro("Construccion",getApplicationContext().getResources().getDrawable(R.drawable.construccion));
        rubros.add(construccion);


        adapter2 = new RubrosListAdapter(this, R.layout.rubross, rubros2);
        final ListView listView2 = (ListView) findViewById(R.id.lista2);

        Rubro maestraParticular= new Rubro("Profesor part.",getApplicationContext().getResources().getDrawable(R.drawable.profesor));
        rubros2.add(maestraParticular);
        Rubro mecanico= new Rubro("Mecanico",getApplicationContext().getResources().getDrawable(R.drawable.mecanico));
        rubros2.add(mecanico);
        Rubro cerrajeria= new Rubro("Cerrajería",getApplicationContext().getResources().getDrawable(R.drawable.cerrajeria));
        rubros2.add(cerrajeria);
        Rubro pintor= new Rubro("Pintor",getApplicationContext().getResources().getDrawable(R.drawable.pintor));
        rubros2.add(pintor);
        listView2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();
        listView2.invalidateViews();




        ImageView menuOpener = (ImageView) findViewById(imageView7);
        menuOpener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawerLayout drawer = (DrawerLayout) findViewById(navDrawer);
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        //Cuenta loggeada

        NavigationView nav = (NavigationView) findViewById(nav_viw);

        if (loggedIn) {

            if(firstTimeLoogedIn) {
                ConstraintLayout con= (ConstraintLayout)findViewById(welcomeMessage);
                //con.setVisibility(VISIBLE);
                TextView text= (TextView)findViewById(textView31);
                text.setText("Bienvenido "+ MainActivity.loggedUsername+ "!");
                final Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
                final Animation fadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout);
                con.startAnimation(fadeIn);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                      con.startAnimation(fadeout);
                    }
                }, 4000);


               firstTimeLoogedIn=false;
            }

            ImageView notifcaciones= (ImageView)findViewById(imageView16);
            notifcaciones.setVisibility(VISIBLE);
            ImageView mensajes= (ImageView)findViewById(imageView23);
            mensajes.setVisibility(VISIBLE);

            mensajes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, ConversacionesUsuario.class));
                }
            });

            View hView =  navigationView.getHeaderView(0);
            TextView nav_user = (TextView)hView.findViewById(R.id.textView14);
            nav_user.setText("►"+ LogInService.name);

            TextView phone = (TextView)hView.findViewById(R.id.textView21);
            phone.setText("►"+ LogInService.phone);

            TextView location = (TextView)hView.findViewById(R.id.textView22);
            location.setText("►"+ LogInService.location);

            ImageView profileImage=(ImageView)hView.findViewById(imageView14);
            Bitmap bitmap = BitmapFactory.decodeByteArray(LogInService.photo, 0, LogInService.photo.length);
            profileImage.setImageBitmap(bitmap);
            //image.setImageBitmap(profileImage);

            nav.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_loggedin);
        } else {
            nav.getMenu().clear();
            navigationView.inflateMenu(R.menu.menu_loggedout);

        }

        //Realizar ampliacion cuando se clickea item de lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.cli);
                mp.start();
                finish();
                if(rubros.get(position).rubro=="Electricidad") {
                    startActivity(new Intent(MainActivity.this, Electricidad.class));
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (!loggedIn) {
            switch (item.getItemId()) {
                case R.id.first:
                    startActivity(new Intent(this, LogIn.class));
                    break;
                case R.id.second:
                    startActivity(new Intent(this, SignUp.class));
                    break;
            }
        } else {
            switch (item.getItemId()) {
                case R.id.first:
                    startActivity(new Intent(this, ProfileUser.class));
                    break;
                case R.id.second:
                    loggedIn = false;
                    firstTimeLoogedIn=true;
                    startActivity(new Intent(this, LogIn.class));
                    break;
            }
            return false;
        }
        return false;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}
