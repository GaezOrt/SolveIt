package gunner.gunner;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;

public class LogInService extends IntentService {
    LogIn login= new LogIn();
    static String username;
    static String password;
    static boolean logIn;
    static int estado;
    public LogInService() {
        super("Log in service");
    }

    @Override
    protected void onHandleIntent( Intent intent) {

        login.logIn();

        if(MainActivity.loggedIn){
            startActivity(new Intent(LogInService.this, MainActivity.class));
        }
        if(!logIn){
            startActivity(new Intent(LogInService.this, LogIn.class));
        }
        }

    }

