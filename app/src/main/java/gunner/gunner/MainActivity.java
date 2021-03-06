package gunner.gunner;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import gunner.gunner.chat.UserConversationsClass;
import gunner.gunner.login.LogIn;
import gunner.gunner.login.LogInService;
import gunner.gunner.rubros.Rubro;
import gunner.gunner.rubros.RubrosListAdapter;
import gunner.gunner.rubros.electricistas.Electricidad;
import gunner.gunner.signup.SignUp;

import static android.view.View.VISIBLE;

import static gunner.gunner.R.id.imageView14;
import static gunner.gunner.R.id.imageView16;


import static gunner.gunner.R.id.imageView23;
import static gunner.gunner.R.id.imageView7;
import static gunner.gunner.R.id.nav_viw;
import static gunner.gunner.R.id.navDrawer;
import static gunner.gunner.R.id.textView31;

import static gunner.gunner.R.id.welcomeMessage;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private boolean requestedFocus = false;
    public static boolean loggedIn = false;
   public static String loggedUsername;
    public static String loggedEmail;
    public static String loggedPhone;
    public static String loggedLocation;
    static String lastName;
    public static Bitmap profileImage;
    public static byte[] loggedImageInDatabaseArray;
    public static boolean electricista;
    public static boolean plomero;
    public static boolean computacion;
    public static boolean carpintero;
    public static boolean pintor;
    public static boolean gasista;
    public static boolean cerrajero;
    public static boolean albanil;
    public static String uniqueGoogleId;
    private View hiddenPanel;
    static boolean firstTimeLoogedIn = true;
    public static boolean esProveedor;
    public static boolean downloadPhoto=true;
    CoordinatorLayout coord;
    private Handler mHandler = new Handler();
    static LocationManager locationManager;
    static RubrosListAdapter adapter;
    static RubrosListAdapter adapter2;
    ArrayList<Rubro> rubros = new ArrayList<Rubro>();
    ArrayList<Rubro> rubros2 = new ArrayList<Rubro>();
   public static Bitmap userConvPhoto;
    ListView listView;
    ListView listView2;
    boolean isLeftListEnabled = true;
    boolean isRightListEnabled = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.welcome);// the song is a filename which i have pasted inside a folder **raw** created under the **res** folder.//
        mp.start();


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.originalGreen));
        }


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viw);
        navigationView.setNavigationItemSelectedListener(this);

        adapter = new RubrosListAdapter(this, R.layout.rubross, rubros);
        listView = (ListView) findViewById(R.id.listita);
        listView.setAdapter(adapter);
        Rubro electricidad = new Rubro("Electricidad", getApplicationContext().getResources().getDrawable(R.drawable.elec));
        rubros.add(electricidad);
        Rubro plomeria = new Rubro("Plomería", getApplicationContext().getResources().getDrawable(R.drawable.plomeria));
        rubros.add(plomeria);

        Rubro computacion = new Rubro("Computacion", getApplicationContext().getResources().getDrawable(R.drawable.computacion));

        rubros.add(computacion);
        Rubro carpinteria = new Rubro("Carpintería", getApplicationContext().getResources().getDrawable(R.drawable.carpinteria));
        rubros.add(carpinteria);
        Rubro gasista = new Rubro("Gasista", getApplicationContext().getResources().getDrawable(R.drawable.gasista));
        rubros.add(gasista);



        adapter = new RubrosListAdapter(this, R.layout.rubross, rubros2);
    listView2 = (ListView) findViewById(R.id.lista2);


        Rubro maestraParticular = new Rubro("Profesor part.", getApplicationContext().getResources().getDrawable(R.drawable.iconoprof));
        rubros2.add(maestraParticular);
        Rubro mecanico = new Rubro("Mecánica", getApplicationContext().getResources().getDrawable(R.drawable.iconoauto));
        rubros2.add(mecanico);
        Rubro cerrajeria = new Rubro("Cerrajería", getApplicationContext().getResources().getDrawable(R.drawable.icono_cerrajero));
        rubros2.add(cerrajeria);
        Rubro pintor = new Rubro("Pintor", getApplicationContext().getResources().getDrawable(R.drawable.icono_pintor));
        rubros2.add(pintor);
        Rubro construccion = new Rubro("Construcción", getApplicationContext().getResources().getDrawable(R.drawable.icono_albanil));
        rubros2.add(construccion);
        listView2.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        listView2.invalidateViews();


        listView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        listView2.setOverScrollMode(ListView.OVER_SCROLL_NEVER);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // onScroll will be called and there will be an infinite loop.
                // That's why i set a boolean value
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    isRightListEnabled = false;
                } else if (scrollState == SCROLL_STATE_IDLE) {
                    isRightListEnabled = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                View c = view.getChildAt(0);
                if (c != null && isLeftListEnabled) {
                    listView2.setSelectionFromTop(firstVisibleItem, c.getTop());
                }
            }
        });

        listView2.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
                    isLeftListEnabled = false;
                } else if (scrollState == SCROLL_STATE_IDLE) {
                    isLeftListEnabled = true;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                View c = view.getChildAt(0);
                if (c != null && isRightListEnabled) {
                    listView.setSelectionFromTop(firstVisibleItem, c.getTop());
                }
            }
        });





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

            if (firstTimeLoogedIn) {
                ConstraintLayout con = (ConstraintLayout) findViewById(welcomeMessage);
                //con.setVisibility(VISIBLE);
                TextView text = (TextView) findViewById(textView31);
                text.setText("Bienvenido " + MainActivity.loggedUsername + "!");
                final Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
                final Animation fadeout = AnimationUtils.loadAnimation(this, R.anim.fadeout);
                con.startAnimation(fadeIn);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        con.startAnimation(fadeout);
                    }
                }, 4000);


                firstTimeLoogedIn = false;
            }

            ImageView notifcaciones = (ImageView) findViewById(imageView16);
            notifcaciones.setVisibility(VISIBLE);
            ImageView mensajes = (ImageView) findViewById(imageView23);
            mensajes.setVisibility(VISIBLE);

            mensajes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, UserConversationsClass.class));
                }
            });

            View hView = navigationView.getHeaderView(0);
            TextView nav_user = (TextView) hView.findViewById(R.id.textView14);
            nav_user.setText("►" + LogInService.name);

            TextView phone = (TextView) hView.findViewById(R.id.textView21);
            phone.setText("►" + LogInService.phone);


            ImageView profileImage = (ImageView) hView.findViewById(imageView14);
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
                if (rubros.get(position).rubro == "Electricidad") {
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
                    firstTimeLoogedIn = true;
                    startActivity(new Intent(this, LogIn.class));
                    break;
            }
            return false;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}