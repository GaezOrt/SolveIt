package gunner.gunner;

import android.app.IntentService;
import android.content.Intent;

import java.util.ArrayList;

public class ChatInteraction extends IntentService {
    static ArrayList<Mensaje> mensajes =new ArrayList<Mensaje>();
    FindInDatabase find= new FindInDatabase();
    String emailFromList;
    boolean seguirDescargandoChat=true;
    boolean descargarDeLaListaDeChats=true;
    public ChatInteraction() {
        super("");
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        if(seguirDescargandoChat){
            Chat.mensajes.clear();
            find.findMensajesBetween2Persons(LogInService.email,FindInDatabase.emailPassed);

            seguirDescargandoChat=false;

        }

        if(descargarDeLaListaDeChats){
            Chat.mensajes.clear();
            find.findMensajesBetween2Persons(LogInService.email,DescargarConversacionesDeUsuario.email);
            descargarDeLaListaDeChats=false;
        }



    }
}
