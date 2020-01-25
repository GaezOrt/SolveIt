package gunner.gunner;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;

import android.util.Log;

public class LogInService extends IntentService {
    LogIn login= new LogIn();
    static String name;
    static String phone;
    static String email;
    static String password;
    static String location;
    static boolean logIn;
    static byte[] photo;
    static int estado;

    public LogInService() {
        super("Log in service");
    }

    @Override
    protected void onHandleIntent( Intent intent) {

        login.logIn();

        if(MainActivity.loggedIn){
            Intent intentt = new Intent (this, MainActivity.class);
            intentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentt);

        }
        if(!logIn){
            Intent intentt = new Intent (this, LogIn.class);
            intentt.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentt);

        }

    }

}

