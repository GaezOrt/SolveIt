package gunner.gunner;

import android.app.IntentService;
import android.content.Intent;

import java.util.ArrayList;

public class ChatInteraction extends IntentService {

    FindInDatabase find = new FindInDatabase();
    String emailFromList;
    boolean seguirDescargandoChat = true;
    static boolean descargarDeLaListaDeChats = true;
    static int numeroDeRS;

    public ChatInteraction() {
        super("");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        while (find.chequearConstantemente(LogInService.email,DescargarConversacionesDeUsuario.email)+1>Chat.mensajes.size()) {
            //find.insertAllTheTime(LogInService.email, DescargarConversacionesDeUsuario.email);

            find.findMensajesBetween2Persons(LogInService.email, DescargarConversacionesDeUsuario.email);


        }
    }
}





