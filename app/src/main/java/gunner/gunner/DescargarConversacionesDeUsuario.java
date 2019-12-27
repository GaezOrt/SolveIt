package gunner.gunner;

import android.app.IntentService;
import android.content.Intent;

import java.util.ArrayList;

public class DescargarConversacionesDeUsuario extends IntentService {
    static ArrayList<ConversacionesUsuarioListaTipo> mensajes = new ArrayList<ConversacionesUsuarioListaTipo>();
    FindInDatabase find = new FindInDatabase();
    boolean seguirDescargandoChat = true;

    public DescargarConversacionesDeUsuario() {

        super("s");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (seguirDescargandoChat) {
            find.findConversationsOfUserInDatabase(LogInService.email);

            seguirDescargandoChat = false;

        }
    }
}

