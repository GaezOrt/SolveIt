package gunner.gunner.chat;

import android.app.IntentService;
import android.content.Intent;

import java.util.ArrayList;

import gunner.gunner.FindInDatabase;
import gunner.gunner.chat.UserConversations;
import gunner.gunner.login.LogInService;

public class DescargarConversacionesDeUsuario extends IntentService {
    static ArrayList<UserConversations> mensajes = new ArrayList<UserConversations>();
    FindInDatabase find = new FindInDatabase();
    public boolean seguirDescargandoChat = true;
    public static String email;
    public static boolean downloadPhoto=true;
    public DescargarConversacionesDeUsuario() {

        super("s");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (seguirDescargandoChat) {
            find.findConversationsOfUserInDatabase(LogInService.email);
            find.findConversationOfUserInDatabase2(LogInService.email);
            seguirDescargandoChat = false;

        }

    }
}

