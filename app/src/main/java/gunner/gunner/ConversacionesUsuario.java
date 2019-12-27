package gunner.gunner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import static gunner.gunner.R.id.list;
import static gunner.gunner.R.id.messages_view;

public class ConversacionesUsuario extends AppCompatActivity {

    static ArrayList<ConversacionesUsuarioListaTipo> conversaciones =new ArrayList<ConversacionesUsuarioListaTipo>();
    static   ConversacionesUsuarioAdapter adapter ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Design_NoActionBar);
        setContentView(R.layout.conversaciones_usuario);

        adapter = new ConversacionesUsuarioAdapter(this,R.layout.list_conversaciones_usuario_view,conversaciones);
        final ListView listView= (ListView) findViewById(messages_view);
        listView.setAdapter(adapter);
        Intent u = new Intent(this, DescargarConversacionesDeUsuario.class);
        startService(u);
    }

    public static Handler UIHandler;

    static {
        UIHandler = new Handler(Looper.getMainLooper());
    }

    public static void runOnUI(Runnable runnable) {
        UIHandler.post(runnable);
    }
}
