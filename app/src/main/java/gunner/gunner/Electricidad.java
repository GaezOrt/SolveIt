package gunner.gunner;

import android.content.Intent;
import android.os.Bundle;
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

    static List<String> electricistas =new ArrayList<String>();


    protected void onCreate(Bundle savedInstanceState) {
        FindInDatabase findInDatabase= new FindInDatabase();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, electricistas);

        setTheme(R.style.Theme_Design_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.electr);


        //Cargar datos electricidad
        findInDatabase.findElectricistas();


        //Ir para atras
        final Button atrasBut=(Button) findViewById(button2) ;
        atrasBut.setOnClickListener((v)-> {

            Electricidad.electricistas.remove(all);
            finish();
            startActivity(new Intent(Electricidad.this, MainActivity.class));
            setContentView(R.layout.activity_main);
        });

        final ListView listView= (ListView) findViewById(lista);

        listView.setAdapter(adapter);

        //Realizar ampliacion cuando se clickea item de lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                FindInDatabase.namePassedViaParam=listView.getItemAtPosition(position).toString();
                Log.w("A","List object "+listView.getItemAtPosition(position).toString());
                finish();
                startActivity(new Intent(Electricidad.this, FindInDatabase.class));
                setContentView(R.layout.find_user);
            }
        });

    }
}
