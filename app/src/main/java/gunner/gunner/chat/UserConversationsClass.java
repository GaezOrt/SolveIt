package gunner.gunner.chat;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import gunner.gunner.R;

import static gunner.gunner.R.id.messages_view;

public class UserConversationsClass extends AppCompatActivity {

    public static ArrayList<UserConversations> conversaciones =new ArrayList<UserConversations>();
    public static ConversacionesUsuarioAdapter adapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Design_NoActionBar);
        setContentView(R.layout.conversaciones_usuario);
        conversaciones.clear();
        adapter = new ConversacionesUsuarioAdapter(this,R.layout.list_conversaciones_usuario_view,conversaciones);
        final ListView listView= (ListView) findViewById(messages_view);
        listView.setAdapter(adapter);
        Intent u = new Intent(this, DescargarConversacionesDeUsuario.class);
        startService(u);

        //Realizar ampliacion cuando se clickea item de lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.cli);
                mp.start();
                finish();
                DescargarConversacionesDeUsuario.email= UserConversationsClass.conversaciones.get(position).nombre;
                startActivity(new Intent(UserConversationsClass.this, Chat.class));
            }
        });
    }

    public static Handler UIHandler;

    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }

    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }
}
