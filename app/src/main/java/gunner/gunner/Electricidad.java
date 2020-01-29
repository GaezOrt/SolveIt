package gunner.gunner;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import static gunner.gunner.R.id.imageView5;
import static gunner.gunner.R.id.imageView6;
import static gunner.gunner.R.id.swiperefresh;
import static gunner.gunner.R.id.textView29;


/**
 * Created by Gaston on 11/18/2019.
 */

public class Electricidad extends AppCompatActivity implements MultiSpinner.MultiSpinnerListener {

    static String location;
    static int comentarios;
    TextView locations;
    static ArrayList<Electricista> electricistas = new ArrayList<Electricista>();
    static boolean showLoad;
    static MyListAdaptor adapter;


    protected void onCreate(Bundle savedInstanceState) {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));
        }


        setTheme(R.style.Theme_Design_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.electr);


        //onLocationChanged(loc);
        final Animation listaAnim = AnimationUtils.loadAnimation(this, R.anim.translate_electricidad);
        ListView lista = (ListView) findViewById(R.id.lista);
        lista.setAnimation(listaAnim);

        final Animation locationAnim = AnimationUtils.loadAnimation(this, R.anim.translate_location_search);
        ImageView imageLoc = (ImageView) findViewById(R.id.imageView22);
        imageLoc.startAnimation(locationAnim);

        Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        spinner2.startAnimation(locationAnim);

        ImageView searchImage = (ImageView) findViewById(imageView6);
        searchImage.startAnimation(locationAnim);

        TextView text2 = (TextView) findViewById(textView29);
        text2.startAnimation(locationAnim);


        MultiSpinner ms = (MultiSpinner) findViewById(R.id.spinner2);
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
        ms.setItems(list, "Zonas de trabajo", this);

        ImageView search = (ImageView) findViewById(imageView6);

        final Intent r = new Intent(this, DownloadStuffInBackground.class);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Electricidad.electricistas.clear();
                DownloadStuffInBackground.lookForLocation = true;
                Spinner spinner = (Spinner) Electricidad.this.findViewById(R.id.spinner2);
                DownloadStuffInBackground.LookingAfterLocation = spinner.getSelectedItem().toString();
                Electricidad.this.startService(r);
                adapter.notifyDataSetChanged();

            }
        });

        adapter = new MyListAdaptor(this, R.layout.list_view, electricistas);
        final ListView listView = (ListView) findViewById(R.id.lista);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listView.invalidateViews();
        final Intent u = new Intent(this, DownloadStuffInBackground.class);

        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(swiperefresh);

        swipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Electricidad.electricistas.clear();
                        DownloadStuffInBackground.keepLooking = true;
                        startService(u);
                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(true);
                        if (!DownloadStuffInBackground.isRunning) {
                            swipe.setRefreshing(false);
                        }
                    }

                });

        DownloadStuffInBackground.searchComments = true;
        final Intent d = new Intent(this, DownloadStuffInBackground.class);

        //Realizar ampliacion cuando se clickea item de lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startService(d);
                FindInDatabase.emailPassed = electricistas.get(position).email;
                DescargarConversacionesDeUsuario.email=electricistas.get(position).email;
                FindInDatabase.ubicacionElectricista = position;
                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.cli);
                mp.start();
                finish();
                startActivity(new Intent(Electricidad.this, FindInDatabase.class));
            }
        });

        //Ir para atras
        final ImageView atrasBut = (ImageView) findViewById(imageView5);
        atrasBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MediaPlayer mp = MediaPlayer.create(Electricidad.this.getApplicationContext(), R.raw.cli);
                mp.start();
                Electricidad.this.finish();
                Electricidad.this.startActivity(new Intent(Electricidad.this, MainActivity.class));
            }
        });
    }


    @Override
    public void onBackPressed() {
        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.cli);
        mp.start();
        startActivity(new Intent(Electricidad.this, MainActivity.class));

    }


    public static Handler UIHandler;

    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }

    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }

    @Override
    public void onItemsSelected(boolean[] selected) {

    }


}





