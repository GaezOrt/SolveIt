package gunner.gunner;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class ChatInteraction extends IntentService {
    static ArrayList<Mensaje> mensajes =new ArrayList<Mensaje>();
    FindInDatabase find= new FindInDatabase();
    boolean seguirDescargandoChat=true;
    public ChatInteraction() {
        super("");
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        if(seguirDescargandoChat){
            find.findMensajesBetween2Persons(LogInService.email,FindInDatabase.namePassedViaParam);

            seguirDescargandoChat=false;

        }


    }
}
