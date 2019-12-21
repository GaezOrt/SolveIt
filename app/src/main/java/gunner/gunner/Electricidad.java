package gunner.gunner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static gunner.gunner.R.id.all;
import static gunner.gunner.R.id.button2;
import static gunner.gunner.R.id.editText6;
import static gunner.gunner.R.id.imageView10;
import static gunner.gunner.R.id.imageView5;
import static gunner.gunner.R.id.imageView6;
import static gunner.gunner.R.id.lista;

import static gunner.gunner.R.id.spinner2;
import static gunner.gunner.R.id.swiperefresh;
import static gunner.gunner.R.id.textView27;

/**
 * Created by Gaston on 11/18/2019.
 */

public class Electricidad extends AppCompatActivity implements MultiSpinner.MultiSpinnerListener{

    static String location;
    static int comentarios;
    static ArrayList<Electricista> electricistas =new ArrayList<Electricista>();
    static boolean showLoad;
    static   MyListAdaptor adapter ;
    protected void onCreate(Bundle savedInstanceState) {


        setTheme(R.style.Theme_Design_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.electr);


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

        ImageView search=(ImageView)findViewById(imageView6);
        EditText edit=(EditText)findViewById(editText6);
        Intent r = new Intent(this, DownloadStuffInBackground.class);
        search.setOnClickListener((v)->{
            Electricidad.electricistas.clear();
            DownloadStuffInBackground.lookForLocation=true;
            Spinner spinner= (Spinner)findViewById(spinner2);
            DownloadStuffInBackground.LookingAfterLocation=spinner.getSelectedItem().toString();
            startService(r);
            adapter.notifyDataSetChanged();

        });

        adapter = new MyListAdaptor(this,R.layout.list_view,electricistas);
        final ListView listView= (ListView) findViewById(lista);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
         listView.invalidateViews();
        Intent u = new Intent(this, DownloadStuffInBackground.class);

        SwipeRefreshLayout swipe= (SwipeRefreshLayout)findViewById(swiperefresh);

        swipe.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Electricidad.electricistas.clear();
                        DownloadStuffInBackground.keepLooking=true;
                        startService(u);
                        adapter.notifyDataSetChanged();
                        swipe.setRefreshing(true);
                        if(!DownloadStuffInBackground.isRunning){
                            swipe.setRefreshing(false);
                        }
                    }

                });


        //Realizar ampliacion cuando se clickea item de lista
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    FindInDatabase.namePassedViaParam = electricistas.get(position).email;
                    FindInDatabase.ubicacionElectricista=position;
                    MediaPlayer mp=MediaPlayer.create(getApplicationContext(),R.raw.cli);
                    mp.start();
                    finish();
                    startActivity(new Intent(Electricidad.this, FindInDatabase.class));

                }
            });

        //Ir para atras
        final ImageView atrasBut=(ImageView) findViewById(imageView5) ;
        atrasBut.setOnClickListener((v)-> {
            MediaPlayer  mp=MediaPlayer.create(getApplicationContext(),R.raw.cli);
            mp.start();
            finish();
            startActivity(new Intent(Electricidad.this, MainActivity.class));

        });
    }

    @Override
    public void onBackPressed() {
        MediaPlayer  mp=MediaPlayer.create(getApplicationContext(),R.raw.cli);
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



