package gunner.gunner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static gunner.gunner.R.id.all;
import static gunner.gunner.R.id.button2;
import static gunner.gunner.R.id.lista;

/**
 * Created by Gaston on 11/18/2019.
 */

public class Electricidad extends AppCompatActivity {


    static ArrayList<Electricista> electricistas =new ArrayList<Electricista>();


    protected void onCreate(Bundle savedInstanceState) {


        setTheme(R.style.Theme_Design_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.electr);


        //Cargar datos electricidad
        DownloadList download= new DownloadList();
        download.execute();
        while(electricistas.isEmpty()){
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }






        //Ir para atras
        final Button atrasBut=(Button) findViewById(button2) ;
        atrasBut.setOnClickListener((v)-> {

            //Electricidad.electricistas.remove(all);
            finish();
            startActivity(new Intent(Electricidad.this, MainActivity.class));
            setContentView(R.layout.activity_main);
        });

        MyListAdaptor adapter = new MyListAdaptor(this,R.layout.list_view,electricistas);
        final ListView listView= (ListView) findViewById(lista);
        listView.setAdapter(adapter);
        System.out.println(electricistas.get(0).name);

        //Realizar ampliacion cuando se clickea item de lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                FindInDatabase.namePassedViaParam=electricistas.get(position).email;
                Log.w("A","List object "+listView.getItemAtPosition(position).toString());
                finish();
                startActivity(new Intent(Electricidad.this, FindInDatabase.class));
                setContentView(R.layout.find_user);
            }
        });

    }
}

 class DownloadList extends AsyncTask<Void,Void,Void>{

    @Override
    protected Void doInBackground(Void... voids) {
        Log.w("Background","Entering doInBACKGROUND");
        Looper.prepare();

        FindInDatabase find= new FindInDatabase();
        find.findElectricistas();

                System.out.println();

    return null;
    }
}
