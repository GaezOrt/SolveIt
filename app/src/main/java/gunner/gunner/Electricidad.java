package gunner.gunner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import static gunner.gunner.R.id.button2;
import static gunner.gunner.R.id.lista;
import static java.security.AccessController.getContext;

/**
 * Created by Gaston on 11/18/2019.
 */

public class Electricidad extends AppCompatActivity {

    String[] values = new String[]{"Ricardo","Marcelo","Alfredo"};


    protected void onCreate(Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, values);

        setTheme(R.style.Theme_Design_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.electr);

       //Ir para atras
        final Button atrasBut=(Button) findViewById(button2) ;
        atrasBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Electricidad.this, MainActivity.class));
                setContentView(R.layout.activity_main);

                //navigateUpTo(new Intent(getBaseContext(), MainActivity.class));
                //setContentView(R.layout.activity_main);
            }
        });


        final ListView list= (ListView) findViewById(lista);
        list.setAdapter(adapter);
        //Realizar ampliacion cuando se clickea item de lista
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                int item = position;
                String itemval = (String)list.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "Position: "+ item+" – Valor: "+itemval, Toast.LENGTH_LONG).show();
            }

        });

    }
}


