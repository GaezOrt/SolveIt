package gunner.gunner.login;

import android.app.IntentService;
import android.content.Intent;

import gunner.gunner.MainActivity;

public class LogInService extends IntentService {
    LogIn login= new LogIn();
    public static String name;
    public static String phone;
    public static String email;
    static String password;
    static String location;
    static boolean logIn;
    public static byte[] photo;
    static int estado;
    static boolean loggedFromGoogle;
    public LogInService() {
        super("Log in service");
    }

    @Override
    protected void onHandleIntent( Intent intent) {
        if(!loggedFromGoogle) {
            login.logIn();
        }

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

