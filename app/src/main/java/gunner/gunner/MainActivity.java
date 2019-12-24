package gunner.gunner;


import android.content.DialogInterface;
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
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import static gunner.gunner.R.id.button;
import static gunner.gunner.R.id.imageView14;
import static gunner.gunner.R.id.imageView16;



import static gunner.gunner.R.id.nav_viw;
import static gunner.gunner.R.id.navDrawer;
import static gunner.gunner.R.id.textView10;
import static gunner.gunner.R.id.textView11;
import static gunner.gunner.R.id.textView12;
import static gunner.gunner.R.id.textView13;
import static gunner.gunner.R.id.textView3;
import static gunner.gunner.R.id.textView31;
import static gunner.gunner.R.id.textView5;
import static gunner.gunner.R.id.textView7;
import static gunner.gunner.R.id.textView9;
import static gunner.gunner.R.id.welcomeMessage;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
    CoordinatorLayout coord;
    private Handler mHandler = new Handler();
    static LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(SignUpService.datosOk){
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

            SignUpService.datosOk=false;
        }



        setTheme(R.style.Theme_Design_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       MediaPlayer  mp=MediaPlayer.create(getApplicationContext(),R.raw.welcome);// the song is a filename which i have pasted inside a folder **raw** created under the **res** folder.//
        mp.start();

        Button electricidad= (Button)findViewById(R.id.button);
        TextView electricidadT=(TextView)findViewById(textView12);
        final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.translate_electricidad);
        electricidad.startAnimation(animRotate);
        electricidadT.startAnimation(animRotate);

        Button computacion= (Button)findViewById(R.id.button4);
        TextView computacionT=(TextView)findViewById(textView3);
        final Animation computacionAnim = AnimationUtils.loadAnimation(this, R.anim.animation_computacion);
        computacion.startAnimation(computacionAnim);
        computacionT.startAnimation(computacionAnim);

        Button plomeria= (Button)findViewById(R.id.button9);
        TextView plomeriaT=(TextView)findViewById(textView9);
        final Animation plomeriaAnim = AnimationUtils.loadAnimation(this, R.anim.translate_plomeria);
        plomeria.startAnimation(plomeriaAnim);
        plomeriaT.startAnimation(plomeriaAnim);

        Button cerrajeria= (Button)findViewById(R.id.button5);
        TextView cerrajeriaT=(TextView)findViewById(textView5);
        final Animation cerrajeriaAnim = AnimationUtils.loadAnimation(this, R.anim.translate_cerrajeria);
        cerrajeria.startAnimation(cerrajeriaAnim);
        cerrajeriaT.startAnimation(cerrajeriaAnim);


        Button albanileria= (Button)findViewById(R.id.button8);
        TextView albanileriaT=(TextView)findViewById(textView11);
        final Animation albanileriaAnim = AnimationUtils.loadAnimation(this, R.anim.translate_albanileria);
        albanileria.startAnimation(albanileriaAnim);
        albanileriaT.startAnimation(albanileriaAnim);

        Button pintureria= (Button)findViewById(R.id.button6);
        TextView pintureriaT=(TextView)findViewById(textView13);
        final Animation pintureriaAnim = AnimationUtils.loadAnimation(this, R.anim.translate_pintureria);
        pintureria.startAnimation(pintureriaAnim);
        pintureriaT.startAnimation(pintureriaAnim);

        Button gasista= (Button)findViewById(R.id.button10);
        TextView gasistaT=(TextView)findViewById(textView10);
        final Animation gasistaAnim = AnimationUtils.loadAnimation(this, R.anim.translate_gasista);
        gasista.startAnimation(gasistaAnim);
        gasistaT.startAnimation(gasistaAnim);

        Button mecanico= (Button)findViewById(R.id.button11);
        TextView mecanicoT=(TextView)findViewById(textView7);
        final Animation mecanicoAnim = AnimationUtils.loadAnimation(this, R.anim.translate_mecanico);
        mecanico.startAnimation(mecanicoAnim);
        mecanicoT.startAnimation(mecanicoAnim);


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
        }


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viw);
        navigationView.setNavigationItemSelectedListener(this);


        //Boton para electricistas
        final Animation myAnim = AnimationUtils.loadAnimation(this, R.anim.fadeout);
        final Button electricidadBut = (Button) findViewById(button);
        electricidadBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                electricidadBut.startAnimation(myAnim);
                MediaPlayer  mp=MediaPlayer.create(getApplicationContext(),R.raw.cli);
                mp.start();
                startActivity(new Intent(MainActivity.this, Electricidad.class));
            }
        });


        ImageView menuOpener = (ImageView) findViewById(imageView16);
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
                    startActivity(new Intent(this, MainActivity.class));
                    break;
            }
            return false;
        }
        return false;
    }

}
