package gunner.gunner;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class LogInService extends IntentService {

    public LogInService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent( Intent intent) {

        LogIn login= new LogIn();
        login.logIn();
        }

    }

